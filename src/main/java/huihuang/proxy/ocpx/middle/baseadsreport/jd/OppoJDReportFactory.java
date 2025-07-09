package huihuang.proxy.ocpx.middle.baseadsreport.jd;

import cn.hutool.core.util.StrUtil;
import huihuang.proxy.ocpx.ads.jd.JDParamEnum;
import huihuang.proxy.ocpx.ads.jd.JDParamField;
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

public abstract class OppoJDReportFactory extends BaseJDReportFactory {

    @Override
    protected String channelName() {
        return OppoPath.OPPO_CHANNEL_NAME;
    }

    /**
     * 生成监测链接
     */
    @Override
    public String findMonitorAddress() {
        StringBuilder macro = new StringBuilder();
        //1.遍历客户侧查找渠道对应的宏参数
        Set<JDParamEnum> jdParamEnums = JDParamEnum.jdOppoMap.keySet();
        for (JDParamEnum jd : jdParamEnums) {
            OppoParamEnum oppo = JDParamEnum.jdOppoMap.get(jd);
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
        JDParamField jdParamField = new JDParamField();

        Set<Map.Entry<JDParamEnum, OppoParamEnum>> jhSet = JDParamEnum.jdOppoMap.entrySet();
        jhSet.stream().filter(bh -> Objects.nonNull(bh.getValue())).forEach(bh -> {
            JDParamEnum jd = bh.getKey();
            OppoParamEnum oppo = bh.getValue();
            String jdField = jd.getName();
            String oppoParam = oppo.getParam();
            String[] value = parameterMap.get(oppoParam);
            if (Objects.isNull(value) || value.length == 0) return;
            if ("null".equals(value[0]) || "NULL".equals(value[0])) return;
            if (value[0].startsWith("__") && value[0].endsWith("__")) return;
            if (value[0].startsWith("$") && value[0].endsWith("$")) return;
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(jdField, jdParamField.getClass());
                Method setMethod = descriptor.getWriteMethod();
                setMethod.invoke(jdParamField, value[0]);
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        return jdParamField;
    }


}
