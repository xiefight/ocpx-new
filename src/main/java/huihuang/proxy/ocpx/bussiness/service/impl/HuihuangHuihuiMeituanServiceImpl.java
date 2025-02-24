package huihuang.proxy.ocpx.bussiness.service.impl;

import cn.hutool.core.net.URLDecoder;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianAdsDTO;
import huihuang.proxy.ocpx.ads.weibo.WeiboAdsDTO;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuangHuihuiMeituanAdsDao;
import huihuang.proxy.ocpx.bussiness.service.BaseServiceInner;
import huihuang.proxy.ocpx.bussiness.service.IChannelAdsService;
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

@Service("hhhuihmtService")
public class HuihuangHuihuiMeituanServiceImpl implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(HuihuangHuihuiMeituanServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private IHuihuangHuihuiMeituanAdsDao huihuiMeituanAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;

    String channelAdsKey = Constants.ChannelAdsKey.HUIHUANG_HUIHUI_MEITUAN;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        String eventType = parameterMap.get("event_type")[0];
        logger.info("adsCallBack {} 开始回调渠道  id:{}  eventType:{}", channelAdsKey, id, eventType);
        //根据id查询对应的点击记录
        HuihuangmingtianAdsDTO hhmtAdsDTO = huihuiMeituanAdsDao.queryHuihuangHuihuiMeituanAdsById(id);
        if (null == hhmtAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }

        String callback = hhmtAdsDTO.getCallbackUrl();
        String channelUrl = URLDecoder.decode(callback, StandardCharsets.UTF_8);
        //将事件event_type给下游，让其对接
        channelUrl = channelUrl + "?event_type=" + eventType;
        logger.info("{} 回调渠道链接：{}", channelAdsKey, channelUrl);
        HttpResponse result = HttpRequest.get(channelUrl).execute();
        //todo 回调辉煌的结果暂不保存数据库

        //更新回调状态
        HuihuangmingtianAdsDTO dto = new HuihuangmingtianAdsDTO();
        dto.setId(id);
        dto.setCallBackTime(String.valueOf(System.currentTimeMillis()));

        if (HttpStatus.HTTP_OK == result.getStatus()) {
            dto.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(dto, huihuiMeituanAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, id);
            return BasicResult.getSuccessResponse(id);
        } else {
            dto.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(dto, huihuiMeituanAdsDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, id);
            return BasicResult.getFailResponse("回调渠道失败:"+id);
        }
    }

}
