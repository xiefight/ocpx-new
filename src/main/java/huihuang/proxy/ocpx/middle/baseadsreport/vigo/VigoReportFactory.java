package huihuang.proxy.ocpx.middle.baseadsreport.vigo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.net.URLEncoder;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSONObject;
import huihuang.proxy.ocpx.ads.quannenghudong.QuannengHudongAdsDTO;
import huihuang.proxy.ocpx.ads.quannenghudong.QuannengHudongParamEnum;
import huihuang.proxy.ocpx.ads.vigo.VigoAdsDTO;
import huihuang.proxy.ocpx.ads.vigo.VigoParamField;
import huihuang.proxy.ocpx.ads.vigo.VigoPath;
import huihuang.proxy.ocpx.bussiness.service.BaseServiceInner;
import huihuang.proxy.ocpx.common.BasicResult;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.BaseSupport;
import huihuang.proxy.ocpx.middle.IChannelAds;
import huihuang.proxy.ocpx.util.JsonParameterUtil;
import huihuang.proxy.ocpx.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public abstract class VigoReportFactory extends BaseSupport implements IChannelAds {

    @Autowired
    protected BaseServiceInner baseServiceInner;

    protected abstract String channelAdsKey();

    protected abstract String serverPathKey();

    protected abstract String channelName();

    protected abstract IMarkDao adsDao();


    @Override
    protected void convertParams(Object adsObj) {
        VigoParamField vigoParamField = (VigoParamField) adsObj;
        if (null != vigoParamField.getCallback()) {
            vigoParamField.setCallback(URLEncoder.createQuery().encode(vigoParamField.getCallback(), StandardCharsets.UTF_8));
        }
        vigoParamField.setRequest_id(RandomUtil.randomStamp());
        //请求设备 oaid 原值 md5 编码后转小写
        if (null != vigoParamField.getOaid()) {
            vigoParamField.setOaid_md5(MD5.create().digestHex(vigoParamField.getOaid()).toLowerCase(Locale.ROOT));
        }
        //请求设备 imei 原值 md5 编码后转小写
        if (null != vigoParamField.getImei()) {
            vigoParamField.setImei_md5(MD5.create().digestHex(vigoParamField.getImei()).toLowerCase(Locale.ROOT));
        }
        //IDFA ，大写后 MD5，再转小写
        if (null != vigoParamField.getIdfa()) {
            vigoParamField.setIdfa(vigoParamField.getIdfa().toUpperCase(Locale.ROOT));
            vigoParamField.setIdfa_md5(MD5.create().digestHex(vigoParamField.getIdfa()).toLowerCase(Locale.ROOT));
        }
        logger.info("clickReport {} 特殊参数进行转换 convertParams:{}", channelAdsKey(), vigoParamField);
    }


    @Override
    protected Response judgeParams(Object adsObj) {
        VigoParamField vigoParamField = (VigoParamField) adsObj;
//        if (Objects.isNull(vigoParamField.getCallback())) {
//            return BasicResult.getFailResponse(QuannengHudongParamEnum.CALLBACK.getName() + "不能为空");
//        }
//        if (Objects.isNull(vigoParamField.getUniqueid())) {
//            return BasicResult.getFailResponse(QuannengHudongParamEnum.UNIQUE_ID.getName() + "不能为空");
//        }
        if (Objects.isNull(vigoParamField.getIdfa())
                && Objects.isNull(vigoParamField.getImei())
                && Objects.isNull(vigoParamField.getOaid())
        ) {
            return BasicResult.getFailResponse("安卓设备：" + QuannengHudongParamEnum.IMEI.getName() + "、" + QuannengHudongParamEnum.OAID.getName() + "不能同时为空；"
                    + " ios设备" + QuannengHudongParamEnum.IDFA.getName() + "不能为空");
        }
        return BasicResult.getSuccessResponse();
    }

    @Override
    protected Object saveOriginParamData(Object adsObj) {
        VigoParamField vigoParamField = (VigoParamField) adsObj;
        VigoAdsDTO vigoAdsDTO = new VigoAdsDTO();
        BeanUtil.copyProperties(vigoParamField, vigoAdsDTO);
        vigoAdsDTO.setChannelName(channelName());
        baseServiceInner.insertAdsObject(vigoAdsDTO, adsDao());
        logger.info("clickReport {} 将原始参数保存数据库，返回数据库对象 saveOriginParamData:{}", channelAdsKey(), vigoAdsDTO);
        return vigoAdsDTO;
    }

    @Override
    protected void replaceCallbackUrl(Object adsObj, Object adsDtoObj) {
        VigoParamField vigoParamField = (VigoParamField) adsObj;
        VigoAdsDTO vigoAdsDTO = (VigoAdsDTO) adsDtoObj;
        String ocpxUrl = queryServerPath() + serverPathKey() + Constants.ServerPath.ADS_CALLBACK + "/" + vigoAdsDTO.getId() + "?";
        logger.info("clickReport {} 客户回调渠道的url：{}", channelAdsKey(), ocpxUrl);
        String encodeUrl = URLEncoder.createQuery().encode(ocpxUrl, StandardCharsets.UTF_8);
//            ocpxUrl = URLEncoder.encode(ocpxUrl, "UTF-8");
        vigoParamField.setCallback(encodeUrl);
        logger.info("clickReport {} 回调参数 replaceCallbackUrl:{}", channelAdsKey(), vigoParamField);
    }

    @Override
    protected String initAdsUrl() {
        return VigoPath.BASIC_URI;
    }

    @Override
    protected Response reportAds(String adsUrl, Object adsDtoObj) throws Exception {
        logger.info("调用用户侧的地址 {} adsUrl:{}", channelAdsKey(), adsUrl);
        HttpResponse response = HttpRequest.get(adsUrl).timeout(20000).header("token", "application/json").execute();
        Map<String, Object> responseBodyMap = JsonParameterUtil.jsonToMap(response.body(), Exception.class);
        VigoAdsDTO vigoAdsDTO = (VigoAdsDTO) adsDtoObj;
        VigoAdsDTO vigoAdsVO = new VigoAdsDTO();
        vigoAdsVO.setId(vigoAdsDTO.getId());
        //上报成功
        if (HttpStatus.HTTP_OK == response.getStatus() && Integer.parseInt(String.valueOf(Objects.requireNonNull(responseBodyMap).get("code"))) == 200) {
            vigoAdsVO.setReportStatus(Constants.ReportStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(vigoAdsVO, adsDao());
            logger.info("clickReport {} 上报广告侧接口请求成功:{} 数据:{}", channelAdsKey(), response, vigoAdsVO);
            return BasicResult.getSuccessResponse(vigoAdsDTO.getId());
        } else {
            vigoAdsVO.setReportStatus(Constants.ReportStatus.FAIL.getCode() + "--" + JSONObject.toJSONString(responseBodyMap));
            baseServiceInner.updateAdsObject(vigoAdsVO, adsDao());
            logger.error("clickReport {} 上报广告侧接口请求失败:{} 数据:{}", channelAdsKey(), response, vigoAdsVO);
            return BasicResult.getFailResponse("上报广告侧接口请求失败", 0);
        }
    }
}
