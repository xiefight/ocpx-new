package huihuang.proxy.ocpx.bussiness.service.impl;

import huihuang.proxy.ocpx.ads.dingyun.DingyunAdsDTO;
import huihuang.proxy.ocpx.ads.dingyun.DingyunEventTypeEnum;
import huihuang.proxy.ocpx.ads.dingyun.huoshan.DingyunDouyinHuoshanPath;
import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangFengmangEventTypeEnum;
import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianAdsDTO;
import huihuang.proxy.ocpx.bussiness.dao.ads.IDingyunDouyinhuoshanAdsDao;
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

@Service("hdydyhsService")
public class HuaweiDingyunDouyinhuoshanServiceImpl extends HuaweiChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(HuaweiDingyunDouyinhuoshanServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private IDingyunDouyinhuoshanAdsDao dydyhsAdsDao;
    @Autowired
    private DingyunDouyinHuoshanPath dydyhsPath;

    String channelAdsKey = Constants.ChannelAdsKey.HUAWEI_DINGYUN_DOUYINHUOSHAN;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        logger.info("adsCallBack {} 开始回调渠道  id:{}  eventType:{}", channelAdsKey, id, parameterMap.get("actionType")[0]);

        //根据id查询对应的点击记录
        DingyunAdsDTO dyAdsDTO = dydyhsAdsDao.queryDingyunDouyinhuoshanAdsById(id);
        if (null == dyAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }

        long currentTime = System.currentTimeMillis();
        Ads2HuaweiVO huaweiVO = new Ads2HuaweiVO();
        huaweiVO.setAdsId(id);
        huaweiVO.setAdsName(dydyhsPath.baseAdsName());
        huaweiVO.setCallbackUrl(dyAdsDTO.getCallback());

        huaweiVO.setTimestamp(String.valueOf(currentTime));
//        huaweiVO.setCampaignId(dyAdsDTO.getCampaignId());
        huaweiVO.setContentId(getContentFromExtra(dyAdsDTO, HuaweiParamEnum.CONTENT_ID.getParam(), null));
        huaweiVO.setTrackingEnabled(getContentFromExtra(dyAdsDTO, HuaweiParamEnum.TRACKING_ENABLED.getParam(), "1"));
        huaweiVO.setConversionTime(String.valueOf(currentTime / 1000));
        huaweiVO.setConversionType(DingyunEventTypeEnum.dingyunHuaweiEventTypeMap.get(parameterMap.get("actionType")[0]).getCode());
        huaweiVO.setOaid(dyAdsDTO.getOaid());
        huaweiVO.setSecret(HuaweiPath.DINGYUN_DOUYINHUOSHAN_SECRET);
        logger.info("adsCallBack {} 组装调用渠道参数:{}", channelAdsKey, huaweiVO);

        Response response = super.baseAdsCallBack(huaweiVO);
        HuaweiCallbackDTO data = (HuaweiCallbackDTO) response.getData();

        //更新回调状态
        DingyunAdsDTO dingyunAds = new DingyunAdsDTO();
        dingyunAds.setId(id);
        dingyunAds.setCallBackTime(String.valueOf(currentTime));

        if (response.getCode() == 0) {
            dingyunAds.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(dingyunAds, dydyhsAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, data);
            return BasicResult.getSuccessResponse(data.getId());
        } else {
            dingyunAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(dingyunAds, dydyhsAdsDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, data);
            return BasicResult.getFailResponse(data.getCallBackMes());
        }
    }

}
