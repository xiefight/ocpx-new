package huihuang.proxy.ocpx.middle.baseadsreport.jd;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.net.URLEncoder;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSONObject;
import huihuang.proxy.ocpx.ads.jd.JDAdsDTO;
import huihuang.proxy.ocpx.ads.jd.JDParamField;
import huihuang.proxy.ocpx.ads.jd.JDPath;
import huihuang.proxy.ocpx.bussiness.service.BaseServiceInner;
import huihuang.proxy.ocpx.common.BasicResult;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.BaseSupport;
import huihuang.proxy.ocpx.middle.IChannelAds;
import huihuang.proxy.ocpx.util.JsonParameterUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public abstract class BaseJDReportFactory extends BaseSupport implements IChannelAds {

    @Autowired
    protected BaseServiceInner baseServiceInner;

    protected abstract String channelAdsKey();

    protected abstract String serverPathKey();

    protected abstract IMarkDao adsDao();

    protected abstract String channelName();

    @Override
    protected void convertParams(Object adsObj) {
        JDParamField jdParamField = (JDParamField) adsObj;
        if (null != jdParamField.getCallback_url()) {
            jdParamField.setCallback_url(URLEncoder.createQuery().encode(jdParamField.getCallback_url(), StandardCharsets.UTF_8));
        }

        //时间戳，秒
        String ts = Optional.ofNullable(jdParamField.getClick_time()).orElse(String.valueOf(System.currentTimeMillis()));
        jdParamField.setClick_time(String.valueOf(Long.parseLong(ts)));

        //操作系统
        if (null == jdParamField.getPlatform() && (StrUtil.isNotEmpty(jdParamField.getIdfa()))) {
            jdParamField.setPlatform("ios");
        } else {
            jdParamField.setPlatform("android");
        }
        //签名
//        signature(jdParamField);
    }

    @Override
    protected Response judgeParams(Object adsObj) {
        return BasicResult.getSuccessResponse();
    }

    @Override
    protected Object saveOriginParamData(Object adsObj) {
        JDParamField jdParamField = (JDParamField) adsObj;
        JDAdsDTO jdAdsDTO = new JDAdsDTO();
        BeanUtil.copyProperties(jdParamField, jdAdsDTO);
        jdAdsDTO.setChannelName(channelName());
        baseServiceInner.insertAdsObject(jdAdsDTO, adsDao());
        logger.info("clickReport {} 将原始参数保存数据库，返回数据库对象 saveOriginParamData:{}", channelAdsKey(), jdAdsDTO);
        return jdAdsDTO;
    }

    @Override
    protected void replaceCallbackUrl(Object adsObj, Object adsDtoObj) {
        JDParamField jdParamField = (JDParamField) adsObj;
        JDAdsDTO jdAdsDTO = (JDAdsDTO) adsDtoObj;
        String ocpxUrl = queryServerPath() + serverPathKey() + Constants.ServerPath.ADS_CALLBACK + "/" + jdAdsDTO.getId() + "?";
        logger.info("clickReport {} 客户回调渠道的url：{}", channelAdsKey(), ocpxUrl);
//        String encodeUrl = URLEncoder.createQuery().encode(ocpxUrl, StandardCharsets.UTF_8);
//            ocpxUrl = URLEncoder.encode(ocpxUrl, "UTF-8");
        String encodeUrl = null;
        try {
            encodeUrl = java.net.URLEncoder.encode(ocpxUrl, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        jdParamField.setCallback_url(encodeUrl);
        logger.info("clickReport {} 回调参数 replaceCallbackUrl:{}", channelAdsKey(), jdParamField);
    }


    @Override
    protected String initAdsUrl() {
        return JDPath.BASIC_URI;
    }


    @Override
    protected Response reportAds(String adsUrl, Object adsDtoObj) throws Exception {
        logger.info("调用用户侧的地址 {} adsUrl:{}", channelAdsKey(), adsUrl);
        HttpResponse response = HttpRequest.get(adsUrl).timeout(20000).header("token", "application/json").execute();
        Map<String, Object> responseBodyMap = JsonParameterUtil.jsonToMap(response.body(), Exception.class);
        JDAdsDTO jdAdsDTO = (JDAdsDTO) adsDtoObj;
        JDAdsDTO jdAdsVO = new JDAdsDTO();
        jdAdsVO.setId(jdAdsDTO.getId());
        //上报成功
        if (HttpStatus.HTTP_OK == response.getStatus() && Objects.requireNonNull(responseBodyMap).get("code").equals("0")) {
            jdAdsVO.setReportStatus(Constants.ReportStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(jdAdsVO, adsDao());
            logger.info("clickReport {} 上报广告侧接口请求成功:{} 数据:{}", channelAdsKey(), response, jdAdsVO);
            return BasicResult.getSuccessResponse(jdAdsDTO.getId());
        } else {
            jdAdsVO.setReportStatus(Constants.ReportStatus.FAIL.getCode() + "--" + JSONObject.toJSONString(responseBodyMap));
            baseServiceInner.updateAdsObject(jdAdsVO, adsDao());
            logger.error("clickReport {} 上报广告侧接口请求失败:{} 数据:{}", channelAdsKey(), response, jdAdsVO);
            return BasicResult.getFailResponse("上报广告侧接口请求失败", 0);
        }
    }

}
