package huihuang.proxy.ocpx.middle.baseadsreport.jd;

import cn.hutool.core.util.StrUtil;
import huihuang.proxy.ocpx.ads.jd.JDAdsDTO;
import huihuang.proxy.ocpx.ads.jd.JDParamEnum;
import huihuang.proxy.ocpx.ads.jd.JDParamField;
import huihuang.proxy.ocpx.bussiness.service.basechannel.HonorChannelFactory;
import huihuang.proxy.ocpx.channel.honor.HonorParamEnum;
import huihuang.proxy.ocpx.channel.honor.HonorPath;
import huihuang.proxy.ocpx.common.Constants;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public abstract class HonorJDReportFactory extends BaseJDReportFactory {

    @Override
    protected String channelName() {
        return HonorPath.HONOR_CHANNEL_NAME;
    }

    /**
     * 生成监测链接
     */
    @Override
    public String findMonitorAddress() {
        StringBuilder macro = new StringBuilder();
        //1.遍历客户侧查找渠道对应的宏参数
        Set<JDParamEnum> jdParamEnums = JDParamEnum.jdHonorMap.keySet();
        for (JDParamEnum jd : jdParamEnums) {
            HonorParamEnum honor = JDParamEnum.jdHonorMap.get(jd);
            if (Objects.isNull(honor) || StrUtil.isEmpty(honor.getMacro())) {
                continue;
            }
            macro.append(honor.getParam()).append("=").append(honor.getMacro()).append("&");
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

        Set<Map.Entry<JDParamEnum, HonorParamEnum>> jhSet = JDParamEnum.jdHonorMap.entrySet();
        jhSet.stream().filter(bh -> Objects.nonNull(bh.getValue())).forEach(bh -> {
            JDParamEnum jd = bh.getKey();
            HonorParamEnum honor = bh.getValue();
            String jdField = jd.getName();
            String honorParam = honor.getParam();
            String[] value = parameterMap.get(honorParam);
            if (Objects.isNull(value) || value.length == 0) return;
            if ("null".equals(value[0]) || "NULL".equals(value[0])) return;
            String val = value[0];
            //特殊处理channel字段
            if (HonorParamEnum.HUIHUANG_CHANNEL.getParam().equals(honorParam)) {
                for (String channel : value) {
                    if (!"-1".equals(channel)) {
                        val = channel;
                        break;
                    }
                }
            }
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(jdField, jdParamField.getClass());
                Method setMethod = descriptor.getWriteMethod();
                setMethod.invoke(jdParamField, val);
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        String extras = HonorChannelFactory.fitExtras(parameterMap,
                HonorParamEnum.TRACK_ID.getParam(),
                "trackId",
                "advertiserId",
                HonorParamEnum.ADVERTISER_ID.getParam(),
                HonorParamEnum.GROUPID.getParam(),
                HonorParamEnum.CREATIVE_ID.getParam(),
                HonorParamEnum.REQUESTID.getParam());
        if (extras.length() > 0) {
            jdParamField.setExtra(extras);
        }
        return jdParamField;
    }

    @Override
    protected void replaceCallbackUrl(Object adsObj, Object adsDtoObj) {
        super.replaceCallbackUrl(adsObj, adsDtoObj);
        JDParamField jdParamField = (JDParamField) adsObj;
        //特殊处理account_id：account_id和现有字段冲突，接口传参时使用我门自己的，保存到数据库后，替换成客户的，上报给客户
        if ("honorjdjdjr02".equals(jdParamField.getAccount_id())) {
            jdParamField.setAccount_id("yunlu01");
        }
        if ("honorjdjdjr03".equals(jdParamField.getAccount_id())) {
            jdParamField.setAccount_id("FM_zxj01");
        }
        logger.info("clickReport {} 回调参数 replaceCallbackUrl:{}", channelAdsKey(), jdParamField);
    }

}
