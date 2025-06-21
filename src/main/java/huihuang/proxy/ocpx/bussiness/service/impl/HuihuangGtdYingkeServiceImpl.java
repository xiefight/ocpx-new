package huihuang.proxy.ocpx.bussiness.service.impl;

import cn.hutool.core.net.URLDecoder;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSON;
import huihuang.proxy.ocpx.ads.gtd.GtdAdsDTO;
import huihuang.proxy.ocpx.ads.gtd.GtdEventTypeEnum;
import huihuang.proxy.ocpx.bussiness.dao.ads.IGtdYingkeAdsDao;
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

@Service("hhgtdyingkeService")
public class HuihuangGtdYingkeServiceImpl implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(HuihuangGtdYingkeServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private IGtdYingkeAdsDao gtdYingkeAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;

    String channelAdsKey = Constants.ChannelAdsKey.HUIHUANG_GTD_YINGKE;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        logger.info("adsCallBack {} 开始回调渠道  parameterMap:{}", channelAdsKey, JSON.toJSON(parameterMap));
        String eventType = parameterMap.get("event_type")[0];
        logger.info("adsCallBack {} 开始回调渠道  id:{}  eventType:{}", channelAdsKey, id, eventType);
        //根据id查询对应的点击记录
        GtdAdsDTO gtdAdsDTO = gtdYingkeAdsDao.queryGtdYingkeAdsById(id);
        if (null == gtdAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }
        String code = GtdEventTypeEnum.gtdHuihuangEventTypeMap.get(eventType).getCode();
        String callback = gtdAdsDTO.getCallback();
        String channelUrl = URLDecoder.decode(callback, StandardCharsets.UTF_8);
        //辉煌的链接，将事件拼接上
//        channelUrl = channelUrl + "?event_type=" + code;
        channelUrl = channelUrl + (channelUrl.contains("?") ? "&" : "?") + "event_type=" + code;
        logger.info("{} 回调渠道链接：{}", channelAdsKey, channelUrl);
        HttpResponse result = HttpRequest.get(channelUrl).execute();
        //todo 回调辉煌的结果暂不保存数据库

        //更新回调状态
        GtdAdsDTO dto = new GtdAdsDTO();
        dto.setId(id);
        dto.setCallBackTime(String.valueOf(System.currentTimeMillis()));

        if (HttpStatus.HTTP_OK == result.getStatus()) {
            dto.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(dto, gtdYingkeAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, id);
            return BasicResult.getSuccessResponse(id);
        } else {
            dto.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(dto, gtdYingkeAdsDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, id);
            return BasicResult.getFailResponse("回调渠道失败:"+id);
        }
    }

}
