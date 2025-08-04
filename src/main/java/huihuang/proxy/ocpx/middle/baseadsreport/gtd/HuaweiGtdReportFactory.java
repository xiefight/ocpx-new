package huihuang.proxy.ocpx.middle.baseadsreport.gtd;

import cn.hutool.core.util.StrUtil;
import huihuang.proxy.ocpx.ads.gtd.GtdParamEnum;
import huihuang.proxy.ocpx.ads.gtd.GtdParamField;
import huihuang.proxy.ocpx.bussiness.service.basechannel.HuaweiChannelFactory;
import huihuang.proxy.ocpx.channel.huawei.HuaweiParamEnum;
import huihuang.proxy.ocpx.common.Constants;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public abstract class HuaweiGtdReportFactory extends GtdReportFactory {

    /**
     * 生成监测链接
     */
    @Override
    public String findMonitorAddress() {
        StringBuilder macro = new StringBuilder();
        //1.遍历广告主查找渠道对应的宏参数
        Set<GtdParamEnum> gtdParamEnums = GtdParamEnum.gtdHuaweiMap.keySet();
        for (GtdParamEnum gtd : gtdParamEnums) {
            HuaweiParamEnum huawei = GtdParamEnum.gtdHuaweiMap.get(gtd);
            if (Objects.isNull(huawei) || StrUtil.isEmpty(huawei.getMacro())) {
                continue;
            }
            macro.append(huawei.getParam()).append("=").append(huawei.getMacro()).append("&");
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
        GtdParamField gtdParamField = new GtdParamField();

        Set<Map.Entry<GtdParamEnum, HuaweiParamEnum>> hbSet = GtdParamEnum.gtdHuaweiMap.entrySet();
        hbSet.stream().filter(hb -> Objects.nonNull(hb.getValue())).forEach(hb -> {
            GtdParamEnum gtd = hb.getKey();
            HuaweiParamEnum huawei = hb.getValue();
            String gtdField = gtd.getName();
            String huaweiParam = huawei.getParam();
            String[] value = parameterMap.get(huaweiParam);
            if (Objects.isNull(value) || value.length == 0) return;
            if ("null".equals(value[0]) || "NULL".equals(value[0])) return;
            if (value[0].startsWith("__") && value[0].endsWith("__")) return;
            String val = value[0];
            //特殊处理channel字段
            if (HuaweiParamEnum.HUIHUANG_CHANNEL.getParam().equals(huaweiParam)) {
                for (String channel : value) {
                    if (!"-1".equals(channel)) {
                        val = channel;
                        break;
                    }
                }
            }
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(gtdField, gtdParamField.getClass());
                Method setMethod = descriptor.getWriteMethod();
                setMethod.invoke(gtdParamField, val);
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        String extras = HuaweiChannelFactory.fitExtras(parameterMap,
                HuaweiParamEnum.CONTENT_ID.getParam(),
                HuaweiParamEnum.EVENT_TYPE.getParam(),
                HuaweiParamEnum.TRACE_TIME.getParam(),
                HuaweiParamEnum.TRACKING_ENABLED.getParam(),
                HuaweiParamEnum.CAMPAIGN_ID.getParam());
        if (extras.length() > 0) {
            gtdParamField.setExtra(extras);
        }
        return gtdParamField;
    }

}
