package huihuang.proxy.ocpx.bussiness.service.impl;

import huihuang.proxy.ocpx.ads.gtd.GtdAdsDTO;
import huihuang.proxy.ocpx.ads.gtd.GtdEventTypeEnum;
import huihuang.proxy.ocpx.ads.gtd.yingke.GtdYingkePath;
import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangFengmangEventTypeEnum;
import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianAdsDTO;
import huihuang.proxy.ocpx.bussiness.dao.ads.IGtdYingkeAdsDao;
import huihuang.proxy.ocpx.bussiness.service.BaseServiceInner;
import huihuang.proxy.ocpx.bussiness.service.IChannelAdsService;
import huihuang.proxy.ocpx.bussiness.service.basechannel.HuaweiChannelFactory;
import huihuang.proxy.ocpx.bussiness.service.basechannel.vo.Ads2HuaweiVO;
import huihuang.proxy.ocpx.channel.huawei.HuaweiCallbackDTO;
import huihuang.proxy.ocpx.channel.huawei.HuaweiParamEnum;
import huihuang.proxy.ocpx.channel.huawei.HuaweiPath;
import huihuang.proxy.ocpx.common.BasicResult;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.middle.IChannelAds;
import huihuang.proxy.ocpx.middle.factory.ChannelAdsFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("hwgtdykService")
public class HuaweiGtdYingkeServiceImpl extends HuaweiChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(HuaweiGtdYingkeServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private IGtdYingkeAdsDao gtdYingkeAdsDao;
    @Autowired
    private GtdYingkePath gtdYingkePath;

    String channelAdsKey = Constants.ChannelAdsKey.HUAWEI_GTD_YINGKE;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        String eventType = parameterMap.get("event_type")[0];
        logger.info("adsCallBack {} 开始回调渠道  id:{}  eventType:{}", channelAdsKey, id, eventType);

        //根据id查询对应的点击记录
        GtdAdsDTO gtdAdsDTO = gtdYingkeAdsDao.queryGtdYingkeAdsById(id);
        if (null == gtdAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }

        long currentTime = System.currentTimeMillis();
        Ads2HuaweiVO huaweiVO = new Ads2HuaweiVO();
        huaweiVO.setAdsId(id);
        huaweiVO.setAdsName(gtdYingkePath.baseAdsName());
        huaweiVO.setCallbackUrl(gtdAdsDTO.getCallback());

        huaweiVO.setTimestamp(String.valueOf(currentTime));
//        huaweiVO.setCampaignId(gtdAdsDTO.getCampaignId());
        huaweiVO.setContentId(getContentFromExtra(gtdAdsDTO, HuaweiParamEnum.CONTENT_ID.getParam(), null));
        huaweiVO.setTrackingEnabled(getContentFromExtra(gtdAdsDTO, HuaweiParamEnum.TRACKING_ENABLED.getParam(), "1"));
        huaweiVO.setConversionTime(String.valueOf(currentTime / 1000));
        huaweiVO.setConversionType(GtdEventTypeEnum.gtdHuaweiEventTypeMap.get(eventType).getCode());
        huaweiVO.setOaid(gtdAdsDTO.getOaid());
        if (HuaweiPath.HW_GTD_YINGKE_ACCOUNT_01.equals(gtdAdsDTO.getAccountId())) {
            huaweiVO.setSecret(HuaweiPath.HW_GTD_YINGKE_SECRET_01);
        }
        logger.info("adsCallBack {} 组装调用渠道参数:{}", channelAdsKey, huaweiVO);

        Response response = super.baseAdsCallBack(huaweiVO);
        HuaweiCallbackDTO data = (HuaweiCallbackDTO) response.getData();

        //更新回调状态
        GtdAdsDTO gtdAds = new GtdAdsDTO();
        gtdAds.setId(id);
        gtdAds.setCallBackTime(String.valueOf(currentTime));

        if (response.getCode() == 0) {
            gtdAds.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(gtdAds, gtdYingkeAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, data);
            return BasicResult.getSuccessResponse(data.getId());
        } else {
            gtdAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(gtdAds, gtdYingkeAdsDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, data);
            return BasicResult.getFailResponse(data.getCallBackMes());
        }
    }

}
