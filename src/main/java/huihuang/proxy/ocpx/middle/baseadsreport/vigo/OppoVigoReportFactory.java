package huihuang.proxy.ocpx.middle.baseadsreport.vigo;

import cn.hutool.core.util.StrUtil;
import huihuang.proxy.ocpx.ads.vigo.VigoParamEnum;
import huihuang.proxy.ocpx.ads.vigo.VigoParamField;
import huihuang.proxy.ocpx.channel.oppo.OppoParamEnum;
import huihuang.proxy.ocpx.channel.oppo.OppoPath;
import huihuang.proxy.ocpx.common.Constants;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public abstract class OppoVigoReportFactory extends VigoReportFactory {

    /**
     * 生成监测链接
     */
    @Override
    public String findMonitorAddress() {
        StringBuilder macro = new StringBuilder();
        //1.遍历广告主查找渠道对应的宏参数
        Set<VigoParamEnum> vigoParamEnums = VigoParamEnum.vigoOppoMap.keySet();
        for (VigoParamEnum vigoParamEnum : vigoParamEnums) {
            OppoParamEnum oppo = VigoParamEnum.vigoOppoMap.get(vigoParamEnum);
            if (Objects.isNull(oppo) || StrUtil.isEmpty(oppo.getMacro())) {
                continue;
            }
            macro.append(oppo.getParam()).append("=").append(oppo.getMacro()).append("&");
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
        VigoParamField vigoParamField = new VigoParamField();

        Set<Map.Entry<VigoParamEnum, OppoParamEnum>> qhSet = VigoParamEnum.vigoOppoMap.entrySet();
        qhSet.stream().filter(bh -> Objects.nonNull(bh.getValue())).forEach(bh -> {
            VigoParamEnum quannengHudong = bh.getKey();
            OppoParamEnum oppo = bh.getValue();
            String vigoField = quannengHudong.getName();
            String oppoParam = oppo.getParam();
            String[] value = parameterMap.get(oppoParam);
            if (Objects.isNull(value) || value.length == 0) return;
            if ("null".equals(value[0]) || "NULL".equals(value[0])) return;
            if (value[0].startsWith("__") && value[0].endsWith("__")) return;
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(vigoField, vigoParamField.getClass());
                Method setMethod = descriptor.getWriteMethod();
                setMethod.invoke(vigoParamField, value[0]);
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey(), vigoParamField);
        return vigoParamField;
    }


    @Override
    protected String channelName() {
        return OppoPath.OPPO_CHANNEL_NAME;
    }

}
