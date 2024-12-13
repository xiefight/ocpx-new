package huihuang.proxy.ocpx.bussiness.service.impl;

import huihuang.proxy.ocpx.ads.huihuang.HuihuangEventTypeEnum;
import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangFengmangEventTypeEnum;
import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianAdsDTO;
import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianEventTypeEnum;
import huihuang.proxy.ocpx.ads.huihuangmingtian.ads.HuihuangJingdongPath;
import huihuang.proxy.ocpx.ads.huihuangmingtian.ads.HuihuangJingdongjinrongPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuangJingdongAdsDao;
import huihuang.proxy.ocpx.bussiness.service.BaseServiceInner;
import huihuang.proxy.ocpx.bussiness.service.IChannelAdsService;
import huihuang.proxy.ocpx.bussiness.service.basechannel.XiaomiChannelFactory;
import huihuang.proxy.ocpx.bussiness.service.basechannel.vo.Ads2XiaomiVO;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiCallbackDTO;
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

@Service("xhhjdService")
public class XiaomiHuihuangJingdongServiceImpl extends XiaomiChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(XiaomiHuihuangJingdongServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private IHuihuangJingdongAdsDao hhjdAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private HuihuangJingdongPath hhjdPath;
    @Autowired
    private HuihuangJingdongjinrongPath hhjdjrPath;


    String channelAdsKey = Constants.ChannelAdsKey.XIAOMI_HUIHUANG_JINGDONG;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        String eventType = parameterMap.get("event_type")[0];
        logger.info("adsCallBack {} 开始回调渠道  id:{}  event:{}", channelAdsKey, id, eventType);
        //根据id查询对应的点击记录
        HuihuangmingtianAdsDTO hhmtAdsDTO = hhjdAdsDao.queryHuihuangJingdongAdsById(id);
        if (null == hhmtAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }

//        if (eventType.equals(HuihuangFengmangEventTypeEnum.ACTIVATE.getCode())) {
//            eventType = eventType + "new";
//        }

        //京东和京东金融  揉到了一起  京东使用的是HuihuangmingtianEventTypeEnum  京东金融使用的是HuihuangFengmangEventTypeEnum
        //根据accountId判断
        String backEvent = HuihuangmingtianEventTypeEnum.huihuangmingtianXiaomiEventTypeMap.get(eventType).getCode();
        String adsName = hhjdPath.baseAdsName();
        if ("xmhhjdjr01".equals(hhmtAdsDTO.getAccountId())) {
            backEvent = HuihuangFengmangEventTypeEnum.huihuangmingtianXiaomiEventTypeMap.get(eventType).getCode();
            adsName = hhjdjrPath.baseAdsName();
        }


        Ads2XiaomiVO xiaomiVO = new Ads2XiaomiVO();
        xiaomiVO.setAdsId(id);
        xiaomiVO.setAdsName(adsName);
        xiaomiVO.setEventType(backEvent);
        xiaomiVO.setEventTimes(String.valueOf(System.currentTimeMillis()));
        xiaomiVO.setCallBackUrl(hhmtAdsDTO.getCallbackUrl());
        xiaomiVO.setOaid(hhmtAdsDTO.getOaid());
        xiaomiVO.setImei(hhmtAdsDTO.getImeiMd5());

        Response response = super.baseAdsCallBack(xiaomiVO);
        XiaomiCallbackDTO data = (XiaomiCallbackDTO) response.getData();

        //更新回调状态
        HuihuangmingtianAdsDTO huihuangAds = new HuihuangmingtianAdsDTO();
        huihuangAds.setId(id);
        huihuangAds.setCallBackTime(String.valueOf(System.currentTimeMillis()));

        if (response.getCode() == 0) {
            huihuangAds.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(huihuangAds, hhjdAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, data);
            return BasicResult.getSuccessResponse(data.getId());
        } else {
            huihuangAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(huihuangAds, hhjdAdsDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, data);
            return BasicResult.getFailResponse(data.getCallBackMes());
        }
    }


}