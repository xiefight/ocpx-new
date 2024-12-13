package huihuang.proxy.ocpx.bussiness.service.impl;

import cn.hutool.core.net.URLDecoder;
import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangFengmangEventTypeEnum;
import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianAdsDTO;
import huihuang.proxy.ocpx.ads.huihuangmingtian.ads.HuihuangYingkePath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuangYingkeAdsDao;
import huihuang.proxy.ocpx.bussiness.service.BaseServiceInner;
import huihuang.proxy.ocpx.bussiness.service.IChannelAdsService;
import huihuang.proxy.ocpx.bussiness.service.basechannel.BaiduChannelFactory;
import huihuang.proxy.ocpx.bussiness.service.basechannel.vo.Ads2BaiduVO;
import huihuang.proxy.ocpx.channel.baidu.BaiduCallbackDTO;
import huihuang.proxy.ocpx.channel.baidu.BaiduPath;
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

@Service("bhhyingkeService")
public class BaiduHuihuangYingkeServiceImpl extends BaiduChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(BaiduHuihuangYingkeServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private IHuihuangYingkeAdsDao hhyingkeAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private HuihuangYingkePath hhyingkePath;

    String channelAdsKey = Constants.ChannelAdsKey.BAIDU_HUIHUANG_YINGKE;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        String eventType = parameterMap.get("event_type")[0];
        logger.info("adsCallBack {} 开始回调渠道  id:{}  eventType:{}", channelAdsKey, id, eventType);

        //根据id查询对应的点击记录
        HuihuangmingtianAdsDTO hhtmAdsDTO = hhyingkeAdsDao.queryHuihuangYingkeAdsById(id);
        if (null == hhtmAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }
        String callback = hhtmAdsDTO.getCallbackUrl();
        String channelUrl = URLDecoder.decode(callback, StandardCharsets.UTF_8);

        Ads2BaiduVO baiduVO = new Ads2BaiduVO();
        baiduVO.setAdsId(id);
        baiduVO.setAdsName(hhyingkePath.baseAdsName());
        baiduVO.setChannelUrl(channelUrl);
        baiduVO.setaType(HuihuangFengmangEventTypeEnum.huihuangmingtianBaiduEventTypeMap.get(eventType).getCode());
        baiduVO.setaValue(0);
        baiduVO.setCbEventTime(String.valueOf(System.currentTimeMillis()));
        baiduVO.setCbOaid(hhtmAdsDTO.getOaid());
        baiduVO.setCbOaidMd5(hhtmAdsDTO.getOaidMd5());
        baiduVO.setCbIdfa(hhtmAdsDTO.getIdfa());
        baiduVO.setCbImei(null);
        baiduVO.setCbImeiMd5(hhtmAdsDTO.getImeiMd5());
        baiduVO.setCbAndroidIdMd5(null);
        baiduVO.setCbIp(hhtmAdsDTO.getIp());
        if (BaiduPath.HUIHUANG_YINGKE_ACCOUNT_01.equals(hhtmAdsDTO.getAccountId())) {
            baiduVO.setSecret(BaiduPath.HUIHUANG_YINGKE_SECRET_01);
        } else if (BaiduPath.HUIHUANG_YINGKE_ACCOUNT_02.equals(hhtmAdsDTO.getAccountId())) {
            baiduVO.setSecret(BaiduPath.HUIHUANG_YINGKE_SECRET_02);
        } else if (BaiduPath.HUIHUANG_YINGKE_ACCOUNT_03.equals(hhtmAdsDTO.getAccountId())) {
            baiduVO.setSecret(BaiduPath.HUIHUANG_YINGKE_SECRET_03);
        }
        logger.info("adsCallBack {} 组装调用渠道参数:{}", channelAdsKey, baiduVO);

        Response response = baseAdsCallBack(baiduVO);
        BaiduCallbackDTO data = (BaiduCallbackDTO) response.getData();

        //更新回调状态
        HuihuangmingtianAdsDTO huihuangmingtianAds = new HuihuangmingtianAdsDTO();
        huihuangmingtianAds.setId(id);
        huihuangmingtianAds.setCallBackTime(String.valueOf(System.currentTimeMillis()));
        if (response.getCode() == 0) {
            huihuangmingtianAds.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(huihuangmingtianAds, hhyingkeAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, data);
            return BasicResult.getSuccessResponse(data.getId());
        } else {
            huihuangmingtianAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(huihuangmingtianAds, hhyingkeAdsDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, data);
            return BasicResult.getFailResponse(data.getCallBackMes());
        }
    }

}