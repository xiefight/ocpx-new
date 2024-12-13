package huihuang.proxy.ocpx.bussiness.service.impl;

import huihuang.proxy.ocpx.ads.huihuang.HuihuangEventTypeEnum;
import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianAdsDTO;
import huihuang.proxy.ocpx.ads.huihuangmingtian.ads.HuihuangAiliaoPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuangAiliaoAdsDao;
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

@Service("hhhalService")
public class HuaweiHuihuangAiliaoServiceImpl extends HuaweiChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(HuaweiHuihuangAiliaoServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private IHuihuangAiliaoAdsDao hhalAdsDao;
    @Autowired
    private HuihuangAiliaoPath hhalPath;

    String channelAdsKey = Constants.ChannelAdsKey.HUAWEI_HUIHUANG_AILIAO;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        logger.info("adsCallBack {} 开始回调渠道  id:{}  eventType:{}", channelAdsKey, id, parameterMap.get("event_type")[0]);

        //根据id查询对应的点击记录
        HuihuangmingtianAdsDTO huihuangmingtianAdsDTO = hhalAdsDao.queryHuihuangAiliaoAdsById(id);
        if (null == huihuangmingtianAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }

        long currentTime = System.currentTimeMillis();
        Ads2HuaweiVO huaweiVO = new Ads2HuaweiVO();
        huaweiVO.setAdsId(id);
        huaweiVO.setAdsName(hhalPath.baseAdsName());
        huaweiVO.setCallbackUrl(huihuangmingtianAdsDTO.getCallbackUrl());

        huaweiVO.setTimestamp(String.valueOf(currentTime));
        huaweiVO.setCampaignId(huihuangmingtianAdsDTO.getCampaignId());
        huaweiVO.setContentId(getContentFromExtra(huihuangmingtianAdsDTO, HuaweiParamEnum.CONTENT_ID.getParam(), null));
        huaweiVO.setTrackingEnabled(getContentFromExtra(huihuangmingtianAdsDTO, HuaweiParamEnum.TRACKING_ENABLED.getParam(), "1"));
        huaweiVO.setConversionTime(String.valueOf(currentTime / 1000));
        huaweiVO.setConversionType(HuihuangEventTypeEnum.huihuangHuaweiEventTypeMap.get(parameterMap.get("event_type")[0]).getCode());
        huaweiVO.setOaid(huihuangmingtianAdsDTO.getOaid());
        if (HuaweiPath.HUIHUANG_AILIAO_ACCOUNT_01.equals(huihuangmingtianAdsDTO.getAccountId())) {
            huaweiVO.setSecret(HuaweiPath.HUIHUANG_AILIAO_SECRET_01);
        }

        logger.info("adsCallBack {} 组装调用渠道参数:{}", channelAdsKey, huaweiVO);

        Response response = super.baseAdsCallBack(huaweiVO);
        HuaweiCallbackDTO data = (HuaweiCallbackDTO) response.getData();

        //更新回调状态
        HuihuangmingtianAdsDTO huihuangmingtianAds = new HuihuangmingtianAdsDTO();
        huihuangmingtianAds.setId(id);
        huihuangmingtianAds.setCallBackTime(String.valueOf(currentTime));

        if (response.getCode() == 0) {
            huihuangmingtianAds.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(huihuangmingtianAds, hhalAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, data);
            return BasicResult.getSuccessResponse(data.getId());
        } else {
            huihuangmingtianAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(huihuangmingtianAds, hhalAdsDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, data);
            return BasicResult.getFailResponse(data.getCallBackMes());
        }
    }

}