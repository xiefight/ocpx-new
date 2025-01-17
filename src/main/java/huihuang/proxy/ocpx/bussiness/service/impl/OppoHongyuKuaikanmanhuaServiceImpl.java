package huihuang.proxy.ocpx.bussiness.service.impl;

import cn.hutool.core.util.StrUtil;
import huihuang.proxy.ocpx.ads.hongyu.HongyuAdsDTO;
import huihuang.proxy.ocpx.ads.hongyu.HongyuEventTypeEnum;
import huihuang.proxy.ocpx.ads.hongyu.kuaikanmanhua.HongyuKuaikanmanhuaPath;
import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangFengmangEventTypeEnum;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHongyuKuaikanmanhuaAdsDao;
import huihuang.proxy.ocpx.bussiness.service.BaseServiceInner;
import huihuang.proxy.ocpx.bussiness.service.IChannelAdsService;
import huihuang.proxy.ocpx.bussiness.service.basechannel.OppoChannelFactory;
import huihuang.proxy.ocpx.bussiness.service.basechannel.vo.Ads2OppoVO;
import huihuang.proxy.ocpx.channel.oppo.OppoCallbackDTO;
import huihuang.proxy.ocpx.channel.oppo.OppoPath;
import huihuang.proxy.ocpx.common.BasicResult;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.middle.IChannelAds;
import huihuang.proxy.ocpx.middle.factory.ChannelAdsFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service("oppohykkmhService")
public class OppoHongyuKuaikanmanhuaServiceImpl extends OppoChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(OppoHongyuKuaikanmanhuaServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private IHongyuKuaikanmanhuaAdsDao hykkmhAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;
//    @Autowired
//    private HongyuKuaikanmanhuaPath hykkmhPath;

    String channelAdsKey = Constants.ChannelAdsKey.OPPO_HONGYU_KUAIKANMANHUA;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        //转化类型字段
        String eventType = parameterMap.get("event_type")[0];
        logger.info("adsCallBack {} 开始回调渠道  id:{}  eventType:{}", channelAdsKey, id, eventType);
        //根据id查询对应的点击记录
        HongyuAdsDTO hongyuAdsDTO = hykkmhAdsDao.queryHongyuKuaikanmanhuaAdsById(id);

        if (null == hongyuAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }

        String pkg = OppoPath.OPPO_HONGYU_KUAIKANMANHUA_PKG;


        long currentTime = System.currentTimeMillis();
        Ads2OppoVO oppoVO = new Ads2OppoVO();
        if (StrUtil.isNotEmpty(hongyuAdsDTO.getImeiMd5())) {
            oppoVO.setImei(encode(hongyuAdsDTO.getImeiMd5().getBytes(StandardCharsets.UTF_8)));
        }
        if (StrUtil.isNotEmpty(hongyuAdsDTO.getOaid())) {
            oppoVO.setOuId(encode(hongyuAdsDTO.getOaid().getBytes(StandardCharsets.UTF_8)));
        }

        oppoVO.setAdsId(id);
        oppoVO.setAdsName(HongyuKuaikanmanhuaPath.ADS_NAME);
        oppoVO.setChannel(1);
        oppoVO.setTimestamp(currentTime);
        oppoVO.setPkg(pkg);
        oppoVO.setDataType(HongyuEventTypeEnum.hongyuOppoEventTypeMap.get(eventType).getCode());
        oppoVO.setAscribeType(0);
        oppoVO.setAdId(OppoPath.HONGYU_KUAIKANMANHUA_ADID);
//        oppoVO.setAdId(Long.valueOf(hongyuAdsDTO.getAdid()));
        logger.info("adsCallBack {} 组装调用渠道参数:{}", channelAdsKey, oppoVO);

        Response response = baseAdsCallBack(oppoVO);
        OppoCallbackDTO data = (OppoCallbackDTO) response.getData();

        //更新回调状态
        HongyuAdsDTO hongyuAds = new HongyuAdsDTO();
        hongyuAds.setId(id);
        hongyuAds.setCallBackTime(String.valueOf(System.currentTimeMillis()));
        if (response.getCode() == 0) {
            hongyuAds.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(hongyuAds, hykkmhAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, data);
            return BasicResult.getSuccessResponse(data.getId());
        } else {
            hongyuAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(hongyuAds, hykkmhAdsDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, data);
            return BasicResult.getFailResponse(data.getCallBackMes());
        }
    }


}
