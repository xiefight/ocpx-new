package huihuang.proxy.ocpx.bussiness.service.impl;

import cn.hutool.core.util.StrUtil;
import huihuang.proxy.ocpx.ads.vigo.VigoAdsDTO;
import huihuang.proxy.ocpx.ads.vigo.VigoEventTypeEnum;
import huihuang.proxy.ocpx.ads.vigo.keep.VigoKeepPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IVigoKeepAdsDao;
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

@Service("oppovigokeepService")
public class OppoVigoKeepServiceImpl extends OppoChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(OppoVigoKeepServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private IVigoKeepAdsDao vigoKeepAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private VigoKeepPath vigoKeepPath;

    String channelAdsKey = Constants.ChannelAdsKey.OPPO_VIGO_KEEP;

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
        VigoAdsDTO vigoAdsDTO = vigoKeepAdsDao.queryVigoKeepAdsById(id);

        if (null == vigoAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }

        String oppoSecret = "";
        String adsName = vigoKeepPath.baseAdsName();
        Long adId = OppoPath.VIGO_KEEP_ADID;
//        Long adId = OppoPath.KUAISHOU_ADID;
        String pkg = OppoPath.OPPO_VIGO_KEEP_PKG;


        long currentTime = System.currentTimeMillis();
        Ads2OppoVO oppoVO = new Ads2OppoVO();
        if (StrUtil.isNotEmpty(vigoAdsDTO.getImei())) {
            oppoVO.setImei(encode(vigoAdsDTO.getImei().getBytes(StandardCharsets.UTF_8)));
        }
        if (StrUtil.isNotEmpty(vigoAdsDTO.getOaid())) {
            oppoVO.setOuId(encode(vigoAdsDTO.getOaid().getBytes(StandardCharsets.UTF_8)));
        }

        oppoVO.setAdsId(id);
        oppoVO.setAdsName(adsName);
        oppoVO.setChannel(1);
        oppoVO.setTimestamp(currentTime);
        oppoVO.setPkg(pkg);
        oppoVO.setDataType(VigoEventTypeEnum.vigoOppoEventTypeMap.get(eventType).getCode());
//        oppoVO.setDataType(QuannengHudongEventTypeEnum.quannengHudongOppoEventTypeMap.get(eventType).getCode());
        oppoVO.setAscribeType(0);
        oppoVO.setAdId(adId);
        logger.info("adsCallBack {} 组装调用渠道参数:{}", channelAdsKey, oppoVO);

        Response response = baseAdsCallBack(oppoVO);
        OppoCallbackDTO data = (OppoCallbackDTO) response.getData();

        //更新回调状态
        VigoAdsDTO vigo = new VigoAdsDTO();
        vigo.setId(id);
        vigo.setCallBackTime(String.valueOf(System.currentTimeMillis()));
        if (response.getCode() == 0) {
            vigo.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(vigo, vigoKeepAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, data);
            return BasicResult.getSuccessResponse(data.getId());
        } else {
            vigo.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(vigo, vigoKeepAdsDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, data);
            return BasicResult.getFailResponse(data.getCallBackMes());
        }
    }


}
