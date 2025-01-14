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
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiParamEnum;
import huihuang.proxy.ocpx.common.BasicResult;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.util.JsonParameterUtil;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public abstract class XiaomiHongyuReportFactory extends HongyuReportFactory {


    /**
     * 生成监测链接
     */
    @Override
    public String findMonitorAddress() {
        StringBuilder macro = new StringBuilder();
        //1.遍历广告主查找渠道对应的宏参数
        Set<HongyuParamEnum> hongyuParamEnums = HongyuParamEnum.hongyuXiaomiMap.keySet();
        for (HongyuParamEnum hongyu : hongyuParamEnums) {
            XiaomiParamEnum xiaomi = HongyuParamEnum.hongyuXiaomiMap.get(hongyu);
            if (Objects.isNull(xiaomi) || StrUtil.isEmpty(xiaomi.getMacro())) {
                continue;
            }
            macro.append(xiaomi.getParam()).append("=").append(xiaomi.getMacro()).append("&");
        }
        String macroStr = macro.toString();
        if (macroStr.endsWith("&")) {
            macroStr = macroStr.substring(0, macroStr.length() - 1);
        }
        //2.config中查找服务地址
        String serverPath = queryServerPath();
        //3.拼接监测地址
        return serverPath + serverPathKey() + Constants.ServerPath.CLICK_REPORT + "?" + macroStr;
    }


    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        HongyuParamField hongyuParamField = new HongyuParamField();

        Set<Map.Entry<HongyuParamEnum, XiaomiParamEnum>> blSet = HongyuParamEnum.hongyuXiaomiMap.entrySet();
        blSet.stream().filter(bl -> Objects.nonNull(bl.getValue())).forEach(bl -> {
            HongyuParamEnum hongyu = bl.getKey();
            XiaomiParamEnum xiaomi = bl.getValue();
            String hongyuField = hongyu.getName();
            String baiduParam = xiaomi.getParam();
            String[] value = parameterMap.get(baiduParam);
            if (Objects.isNull(value) || value.length == 0) return;
            if ("null".equals(value[0]) || "NULL".equals(value[0])) return;
            if (value[0].startsWith("__") && value[0].endsWith("__")) return;
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(hongyuField, hongyuParamField.getClass());
                Method setMethod = descriptor.getWriteMethod();
                setMethod.invoke(hongyuParamField, value[0]);
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
//        hongyuParamField.setAccess_id(LiangdamaoPath.ACCESS_ID);
        return hongyuParamField;
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
