package huihuang.proxy.ocpx.bussiness.service.impl;

import cn.hutool.core.util.StrUtil;
import huihuang.proxy.ocpx.ads.huihui.HuihuiAdsDTO;
import huihuang.proxy.ocpx.ads.huihui.HuihuiEventTypeEnum;
import huihuang.proxy.ocpx.ads.huihui.iqiyi.HuihuiIQiyiPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuiIQiyiAdsDao;
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

@Service("oppohhiqiyiService")
public class OppoHuihuiIQiyiServiceImpl extends OppoChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(OppoHuihuiIQiyiServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private IHuihuiIQiyiAdsDao hhiqiyiAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private HuihuiIQiyiPath hhiqiyiPath;

    String channelAdsKey = Constants.ChannelAdsKey.OPPO_HUIHUI_IQIYI;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        //转化类型字段
        String eventType = parameterMap.get("conv_action")[0];
        logger.info("adsCallBack {} 开始回调渠道  id:{}  eventType:{}", channelAdsKey, id, eventType);
        //根据id查询对应的点击记录
        HuihuiAdsDTO huihuiAdsDTO = hhiqiyiAdsDao.queryIQiyiAdsById(id);

        if (null == huihuiAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }

        String pkg = OppoPath.OPPO_HUIHUI_IQIYI_PKG;


        long currentTime = System.currentTimeMillis();
        Ads2OppoVO oppoVO = new Ads2OppoVO();
//        if (StrUtil.isNotEmpty(huihuiAdsDTO.getImei())) {
//            oppoVO.setImei(encode(huihuiAdsDTO.getImeiMd5().getBytes(StandardCharsets.UTF_8)));
//        }
        if (StrUtil.isNotEmpty(huihuiAdsDTO.getOaid())) {
            oppoVO.setOuId(encode(huihuiAdsDTO.getOaid().getBytes(StandardCharsets.UTF_8)));
        }
        oppoVO.setImei(huihuiAdsDTO.getImei());
        oppoVO.setAdsId(id);
        oppoVO.setAdsName(hhiqiyiPath.baseAdsName());
        oppoVO.setChannel(1);
        oppoVO.setTimestamp(currentTime);
        oppoVO.setPkg(pkg);
        oppoVO.setDataType(HuihuiEventTypeEnum.huihuiOppoEventTypeMap.get(eventType).getCode());
        oppoVO.setAscribeType(0);
        oppoVO.setAdId(OppoPath.HUIHUI_MOMO_ADID);
        if (StrUtil.isNotEmpty(huihuiAdsDTO.getOaid())) {
            oppoVO.setOuId(encode(huihuiAdsDTO.getOaid().getBytes(StandardCharsets.UTF_8)));
        } else if (StrUtil.isNotEmpty(huihuiAdsDTO.getOaid_md5())) {
            oppoVO.setOuId(encode(huihuiAdsDTO.getOaid_md5().getBytes(StandardCharsets.UTF_8)));
        }
//        oppoVO.setAdId(Long.valueOf(huihuiAdsDTO.getAdid()));
        logger.info("adsCallBack {} 组装调用渠道参数:{}", channelAdsKey, oppoVO);

        Response response = baseAdsCallBack(oppoVO);
        OppoCallbackDTO data = (OppoCallbackDTO) response.getData();

        //更新回调状态
        HuihuiAdsDTO huihuiAds = new HuihuiAdsDTO();
        huihuiAds.setId(id);
        huihuiAds.setCallBackTime(String.valueOf(System.currentTimeMillis()));
        if (response.getCode() == 0) {
            huihuiAds.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(huihuiAds, hhiqiyiAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, data);
            return BasicResult.getSuccessResponse(data.getId());
        } else {
            huihuiAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(huihuiAds, hhiqiyiAdsDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, data);
            return BasicResult.getFailResponse(data.getCallBackMes());
        }
    }


}
