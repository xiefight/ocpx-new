package huihuang.proxy.ocpx.middle.baseadsreport.hongyu;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.net.URLEncoder;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSONObject;
import huihuang.proxy.ocpx.ads.hongyu.HongyuAdsDTO;
import huihuang.proxy.ocpx.ads.hongyu.HongyuParamEnum;
import huihuang.proxy.ocpx.ads.hongyu.HongyuParamField;
import huihuang.proxy.ocpx.ads.hongyu.HongyuPath;
import huihuang.proxy.ocpx.ads.quannenghudong.QuannengHudongParamEnum;
import huihuang.proxy.ocpx.bussiness.service.BaseServiceInner;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiParamEnum;
import huihuang.proxy.ocpx.common.BasicResult;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.BaseSupport;
import huihuang.proxy.ocpx.middle.IChannelAds;
import huihuang.proxy.ocpx.util.JsonParameterUtil;
import huihuang.proxy.ocpx.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public abstract class HongyuReportFactory extends BaseSupport implements IChannelAds {

    @Autowired
    protected BaseServiceInner baseServiceInner;

    protected abstract String channelAdsKey();

    protected abstract String serverPathKey();

    protected abstract String channelName();

    protected abstract IMarkDao adsDao();


    @Override
    protected void convertParams(Object adsObj) {
        HongyuParamField hongyuParamField = (HongyuParamField) adsObj;
        if (null != hongyuParamField.getCallback()) {
            hongyuParamField.setCallback(URLEncoder.createQuery().encode(hongyuParamField.getCallback(), StandardCharsets.UTF_8));
        }
        //uniqueid
        hongyuParamField.setClickid(RandomUtil.randomStamp());
//        if (null != hongyuParamField.getAdAgent()) {
//            hongyuParamField.setAdAgent(URLEncoder.createQuery().encode(hongyuParamField.getAdAgent(), StandardCharsets.UTF_8));
//        }
//        if (hongyuParamField.getIdfa()!=null){
//            hongyuParamField.setOs("ios");
//        }else {
//            hongyuParamField.setOs("android");
//        }
        logger.info("clickReport {} 特殊参数进行转换 convertParams:{}", channelAdsKey(), hongyuParamField);
    }

    @Override
    protected Response judgeParams(Object adsObj) {
        HongyuParamField hongyuParamField = (HongyuParamField) adsObj;
        if (Objects.isNull(hongyuParamField.getCallback())) {
            return BasicResult.getFailResponse(QuannengHudongParamEnum.CALLBACK.getName() + "不能为空");
        }
//        if (Objects.isNull(hongyuParamField.getUniqueid())) {
//            return BasicResult.getFailResponse(QuannengHudongParamEnum.UNIQUE_ID.getName() + "不能为空");
//        }
//        if (Objects.isNull(hongyuParamField.getIdfa())
//                && Objects.isNull(hongyuParamField.getImei())
//                && Objects.isNull(hongyuParamField.getOaid())
//        ) {
//            return BasicResult.getFailResponse("安卓设备：" + QuannengHudongParamEnum.IMEI.getName() + "、" + QuannengHudongParamEnum.OAID.getName() + "不能同时为空；"
//                    + " ios设备" + QuannengHudongParamEnum.IDFA.getName() + "不能为空");
//        }
        return BasicResult.getSuccessResponse();
    }

    @Override
    protected Object saveOriginParamData(Object adsObj) {
        HongyuParamField hongyuParamField = (HongyuParamField) adsObj;
        HongyuAdsDTO hongyuAdsDTO = new HongyuAdsDTO();
        BeanUtil.copyProperties(hongyuParamField, hongyuAdsDTO);
        hongyuAdsDTO.setChannelName(channelName());
        baseServiceInner.insertAdsObject(hongyuAdsDTO, adsDao());
        logger.info("clickReport {} 将原始参数保存数据库，返回数据库对象 saveOriginParamData:{}", channelAdsKey(), hongyuAdsDTO);
        return hongyuAdsDTO;
    }

    @Override
    protected void replaceCallbackUrl(Object adsObj, Object adsDtoObj) {
        HongyuParamField hongyuParamField = (HongyuParamField) adsObj;
        HongyuAdsDTO hongyuAdsDTO = (HongyuAdsDTO) adsDtoObj;
        String ocpxUrl = queryServerPath() + serverPathKey() + Constants.ServerPath.ADS_CALLBACK + "/" + hongyuAdsDTO.getId() + "?";
        logger.info("clickReport {} 客户回调渠道的url：{}", channelAdsKey(), ocpxUrl);
        String encodeUrl = URLEncoder.createQuery().encode(ocpxUrl, StandardCharsets.UTF_8);
//            ocpxUrl = URLEncoder.encode(ocpxUrl, "UTF-8");
        hongyuParamField.setCallback(encodeUrl);
        logger.info("clickReport {} 回调参数 replaceCallbackUrl:{}", channelAdsKey(), hongyuParamField);
    }

    @Override
    protected String initAdsUrl() {
        return HongyuPath.BASIC_URI;
    }

    @Override
    protected Response reportAds(String adsUrl, Object adsDtoObj) throws Exception {
        logger.info("调用用户侧的地址 {} adsUrl:{}", channelAdsKey(), adsUrl);
        HttpResponse response = HttpRequest.get(adsUrl).timeout(20000).header("token", "application/json").execute();
        Map<String, Object> responseBodyMap = JsonParameterUtil.jsonToMap(response.body(), Exception.class);
        HongyuAdsDTO hongyuAdsDTO = (HongyuAdsDTO) adsDtoObj;
        HongyuAdsDTO hongyuAdsVO = new HongyuAdsDTO();
        hongyuAdsVO.setId(hongyuAdsDTO.getId());
        //上报成功
        if (HttpStatus.HTTP_OK == response.getStatus() && Integer.parseInt(String.valueOf(Objects.requireNonNull(responseBodyMap).get("error_code"))) == 0) {
            hongyuAdsVO.setReportStatus(Constants.ReportStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(hongyuAdsVO, adsDao());
            logger.info("clickReport {} 上报广告侧接口请求成功:{} 数据:{}", channelAdsKey(), response, hongyuAdsVO);
            return BasicResult.getSuccessResponse(hongyuAdsDTO.getId());
        } else {
            hongyuAdsVO.setReportStatus(Constants.ReportStatus.FAIL.getCode() + "--" + JSONObject.toJSONString(responseBodyMap));
            baseServiceInner.updateAdsObject(hongyuAdsVO, adsDao());
            logger.error("clickReport {} 上报广告侧接口请求失败:{} 数据:{}", channelAdsKey(), response, hongyuAdsVO);
            return BasicResult.getFailResponse("上报广告侧接口请求失败", 0);
        }
    }
}
