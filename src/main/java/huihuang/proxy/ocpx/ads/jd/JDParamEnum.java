package huihuang.proxy.ocpx.ads.jd;

import huihuang.proxy.ocpx.channel.honor.HonorParamEnum;
import huihuang.proxy.ocpx.channel.oppo.OppoParamEnum;

import java.util.HashMap;
import java.util.Map;

public enum JDParamEnum {

    PLATFORM("platform", "String", 1, "设备操作系统 android：安卓 ios：苹果"),
    IDFA("idfa", "String", 1, "ios设备标识，传idfa原值或idfa原值md5"),
    OAID("oa_id", "String", 1, "android设备标识，传oaid原值或oaid原值md5"),
    CLICK_TIME("click_time", "String", 1, "毫秒时间戳 自己上报"),
    CALLBACK_URL("callback_url", "String", 1, "转化回调地址 需URL ENCODE 处理"),

    ACCOUNT_ID("account_id", "String", 0, "我们规定的账户id,用于同一pid区分不出来的情况下,使用accountId区分"),
    CODE("code", "String", 0, ""),

    ;


    private String name;
    private String type;
    private Integer necessary;
    private String remark;

    JDParamEnum(String name, String type, Integer necessary, String remark) {
        this.name = name;
        this.type = type;
        this.necessary = necessary;
        this.remark = remark;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Integer getNecessary() {
        return necessary;
    }

    public String getRemark() {
        return remark;
    }


    public static Map<JDParamEnum, HonorParamEnum> jdHonorMap;

    static {
        jdHonorMap = new HashMap<>();
        jdHonorMap.put(OAID, HonorParamEnum.OAID);
        jdHonorMap.put(IDFA, null);

        jdHonorMap.put(CLICK_TIME, HonorParamEnum.TIME);
        jdHonorMap.put(PLATFORM, HonorParamEnum.OS);
        jdHonorMap.put(CALLBACK_URL, null);
        jdHonorMap.put(ACCOUNT_ID, HonorParamEnum.ACCOUNT_ID);
        jdHonorMap.put(CODE, HonorParamEnum.JD_CODE);
    }


    public static Map<JDParamEnum, OppoParamEnum> jdOppoMap;

    static {
        jdOppoMap = new HashMap<>();
        jdOppoMap.put(OAID, OppoParamEnum.OAID);
        jdOppoMap.put(IDFA, null);

        jdOppoMap.put(CLICK_TIME, OppoParamEnum.TS);
        jdOppoMap.put(PLATFORM, OppoParamEnum.OS);
        jdOppoMap.put(CALLBACK_URL, null);
        jdOppoMap.put(ACCOUNT_ID, OppoParamEnum.ACCOUNT_ID);
        jdOppoMap.put(CODE, OppoParamEnum.JD_CODE);
    }


}
