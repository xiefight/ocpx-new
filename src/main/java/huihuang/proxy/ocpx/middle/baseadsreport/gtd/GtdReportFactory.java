package huihuang.proxy.ocpx.middle.baseadsreport.gtd;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.net.URLEncoder;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSONObject;
import huihuang.proxy.ocpx.ads.gtd.GtdAdsDTO;
import huihuang.proxy.ocpx.ads.gtd.GtdParamField;
import huihuang.proxy.ocpx.bussiness.service.BaseServiceInner;
import huihuang.proxy.ocpx.common.BasicResult;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.BaseSupport;
import huihuang.proxy.ocpx.middle.IChannelAds;
import huihuang.proxy.ocpx.util.JsonParameterUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public abstract class GtdReportFactory extends BaseSupport implements IChannelAds {

    @Autowired
    protected BaseServiceInner baseServiceInner;

    protected abstract String channelAdsKey();

    protected abstract String serverPathKey();

    protected abstract String channelName();

    protected abstract IMarkDao adsDao();


    @Override
    protected void convertParams(Object adsObj) {
        GtdParamField gtdParamField = (GtdParamField) adsObj;
        if (null != gtdParamField.getCallback()) {
            gtdParamField.setCallback(URLEncoder.createQuery().encode(gtdParamField.getCallback(), StandardCharsets.UTF_8));
        }
        if (gtdParamField.getIdfaMd5() != null) {
            gtdParamField.setOs("ios");
        } else {
            gtdParamField.setOs("android");
        }
        //md5转小写
        if (gtdParamField.getOaidMd5() != null) {
            gtdParamField.setOaidMd5(gtdParamField.getOaidMd5().toLowerCase(Locale.ROOT));
        }
        if (gtdParamField.getIdfaMd5() != null) {
            gtdParamField.setIdfaMd5(gtdParamField.getIdfaMd5().toLowerCase(Locale.ROOT));
        }
        if (gtdParamField.getImeiMd5() != null) {
            gtdParamField.setImeiMd5(gtdParamField.getImeiMd5().toLowerCase(Locale.ROOT));
        }
        gtdParamField.setTs(String.valueOf((System.currentTimeMillis())));
        logger.info("clickReport {} 特殊参数进行转换 convertParams:{}", channelAdsKey(), gtdParamField);
    }


    @Override
    protected Response judgeParams(Object adsObj) {
        /*QuannengHudongParamField quannengHudongParamField = (QuannengHudongParamField) adsObj;
        if (Objects.isNull(quannengHudongParamField.getCallback())) {
            return BasicResult.getFailResponse(QuannengHudongParamEnum.CALLBACK.getName() + "不能为空");
        }
        if (Objects.isNull(quannengHudongParamField.getUniqueid())) {
            return BasicResult.getFailResponse(QuannengHudongParamEnum.UNIQUE_ID.getName() + "不能为空");
        }
        if (Objects.isNull(quannengHudongParamField.getIdfa())
                && Objects.isNull(quannengHudongParamField.getImei())
                && Objects.isNull(quannengHudongParamField.getOaid())
        ) {
            return BasicResult.getFailResponse("安卓设备：" + QuannengHudongParamEnum.IMEI.getName() + "、" + QuannengHudongParamEnum.OAID.getName() + "不能同时为空；"
                    + " ios设备" + QuannengHudongParamEnum.IDFA.getName() + "不能为空");
        }*/
        return BasicResult.getSuccessResponse();
    }

    @Override
    protected Object saveOriginParamData(Object adsObj) {
        GtdParamField gtdParamField = (GtdParamField) adsObj;
        GtdAdsDTO gtdAdsDTO = new GtdAdsDTO();
        try {
            BeanUtil.copyProperties(gtdParamField, gtdAdsDTO);
            gtdAdsDTO.setChannelName(channelName());
            baseServiceInner.insertAdsObject(gtdAdsDTO, adsDao());
            logger.info("clickReport {} 将原始参数保存数据库，返回数据库对象 saveOriginParamData:{}", channelAdsKey(), gtdAdsDTO);
        } catch (Exception e) {
            logger.error("clickReport {} 保存原始参数数据异常:{}", channelAdsKey(), e);
        }
        return gtdAdsDTO;
    }

    @Override
    protected void replaceCallbackUrl(Object adsObj, Object adsDtoObj) {
        GtdParamField gtdParamField = (GtdParamField) adsObj;
        GtdAdsDTO gtdAdsDTO = (GtdAdsDTO) adsDtoObj;
        String ocpxUrl = queryServerPath() + serverPathKey() + Constants.ServerPath.ADS_CALLBACK + "/" + gtdAdsDTO.getId() + "?";
        logger.info("clickReport {} 客户回调渠道的url：{}", channelAdsKey(), ocpxUrl);
        String encodeUrl = URLEncoder.createQuery().encode(ocpxUrl, StandardCharsets.UTF_8);
//            ocpxUrl = URLEncoder.encode(ocpxUrl, "UTF-8");
        gtdParamField.setCallback(encodeUrl);
        logger.info("clickReport {} 回调参数 replaceCallbackUrl:{}", channelAdsKey(), gtdParamField);
    }

    @Override
    protected Response reportAds(String adsUrl, Object adsDtoObj) throws Exception {
        logger.info("调用用户侧的地址 {} adsUrl:{}", channelAdsKey(), adsUrl);
        HttpResponse response = HttpRequest.get(adsUrl).timeout(20000).header("token", "application/json").execute();
        Map<String, Object> responseBodyMap = JsonParameterUtil.jsonToMap(response.body(), Exception.class);
        GtdAdsDTO gtdAdsDTO = (GtdAdsDTO) adsDtoObj;
        GtdAdsDTO gtdAdsVO = new GtdAdsDTO();
        gtdAdsVO.setId(gtdAdsDTO.getId());
        //上报成功
        if (HttpStatus.HTTP_OK == response.getStatus() && Integer.parseInt(String.valueOf(Objects.requireNonNull(responseBodyMap).get("ret"))) == 0) {
            gtdAdsVO.setReportStatus(Constants.ReportStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(gtdAdsVO, adsDao());
            logger.info("clickReport {} 上报广告侧接口请求成功:{} 数据:{}", channelAdsKey(), response, gtdAdsVO);
            return BasicResult.getSuccessResponse(gtdAdsDTO.getId());
        } else {
            gtdAdsVO.setReportStatus(Constants.ReportStatus.FAIL.getCode() + "--" + JSONObject.toJSONString(responseBodyMap));
            baseServiceInner.updateAdsObject(gtdAdsVO, adsDao());
            logger.error("clickReport {} 上报广告侧接口请求失败:{} 数据:{}", channelAdsKey(), response, gtdAdsVO);
            return BasicResult.getFailResponse("上报广告侧接口请求失败", 0);
        }
    }
}
