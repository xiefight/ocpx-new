package huihuang.proxy.ocpx.bussiness.service.impl;

import huihuang.proxy.ocpx.ads.jd.JDAdsDTO;
import huihuang.proxy.ocpx.ads.jd.JDEventTypeEnum;
import huihuang.proxy.ocpx.ads.jd.jdjr.JDJdjrPath;
import huihuang.proxy.ocpx.ads.weibo.WeiboAdsDTO;
import huihuang.proxy.ocpx.ads.weibo.WeiboEventTypeEnum;
import huihuang.proxy.ocpx.bussiness.dao.ads.IJDJdjrAdsDao;
import huihuang.proxy.ocpx.bussiness.service.BaseServiceInner;
import huihuang.proxy.ocpx.bussiness.service.IChannelAdsService;
import huihuang.proxy.ocpx.bussiness.service.basechannel.HonorChannelFactory;
import huihuang.proxy.ocpx.bussiness.service.basechannel.vo.Ads2HonorVO;
import huihuang.proxy.ocpx.channel.honor.HonorCallbackDTO;
import huihuang.proxy.ocpx.channel.honor.HonorParamEnum;
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

@Service("honorjdjdjrService")
public class HonorJDJdjrServiceImpl extends HonorChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(HonorJDJdjrServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private IJDJdjrAdsDao jdJdjrAdsDao;
    @Autowired
    private JDJdjrPath jdJdjrPath;

    String channelAdsKey = Constants.ChannelAdsKey.HONOR_JD_JDJR;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        String eventType = parameterMap.get("event_type")[0];
        logger.info("adsCallBack {} 开始回调渠道  id:{}  eventType:{}", channelAdsKey, id, eventType);

        //根据id查询对应的点击记录
        JDAdsDTO jdAdsDTO = jdJdjrAdsDao.queryJDJdjrAdsById(id);
        if (null == jdAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }

        //京东金融的注册  对应  荣耀的激活
//        if (HuihuangFengmangEventTypeEnum.REGISTER.getCode().equals(eventType)) {
//            eventType = HuihuangFengmangEventTypeEnum.ACTIVATE.getCode();
//        }

        long currentTime = System.currentTimeMillis();
        Ads2HonorVO honorVO = new Ads2HonorVO();
        honorVO.setAdsId(id);
        honorVO.setAdsName(jdJdjrPath.baseAdsName());
//        honorVO.setCallbackUrl(jdAdsDTO.getCallbackUrl());

//        honorVO.setTimestamp(String.valueOf(currentTime));
        honorVO.setConversionTime(String.valueOf(currentTime));
        honorVO.setConversionId(JDEventTypeEnum.JdHonorEventTypeMap.get(eventType).getCode());
        honorVO.setTrackId(getContentFromExtra(jdAdsDTO, HonorParamEnum.TRACK_ID.getParam(),
                getContentFromExtra(jdAdsDTO, "trackId", "")));
        honorVO.setAdvertiserId(getContentFromExtra(jdAdsDTO, HonorParamEnum.ADVERTISER_ID.getParam(),
                getContentFromExtra(jdAdsDTO, "advertiserId", "")));
        honorVO.setOaid(jdAdsDTO.getOaId());
        logger.info("adsCallBack {} 组装调用渠道参数:{}", channelAdsKey, honorVO);

        Response response = super.baseAdsCallBack(honorVO);
        HonorCallbackDTO data = (HonorCallbackDTO) response.getData();

        //更新回调状态
        JDAdsDTO jdAds = new JDAdsDTO();
        jdAds.setId(id);
        jdAds.setCallBackTime(String.valueOf(currentTime));

        if (response.getCode() == 0) {
            jdAds.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(jdAds, jdJdjrAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, data);
            return BasicResult.getSuccessResponse(data.getId());
        } else {
            jdAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(jdAds, jdJdjrAdsDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, data);
            return BasicResult.getFailResponse(data.getCallBackMes());
        }
    }

}
