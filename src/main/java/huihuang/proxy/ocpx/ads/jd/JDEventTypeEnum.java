package huihuang.proxy.ocpx.ads.jd;

import cn.hutool.core.collection.CollUtil;
import huihuang.proxy.ocpx.channel.baidu.BaiduEventTypeEnum;
import huihuang.proxy.ocpx.channel.honor.HonorEventTypeEnum;

import java.util.Map;

public enum JDEventTypeEnum {

    ACTIVATE("1", "设备激活"),
    REGISTER("0", "注册"),

    ;


    private String code;
    private String desc;

    JDEventTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static Map<String, BaiduEventTypeEnum> weiboBaiduEventTypeMap;


    public static Map<String, HonorEventTypeEnum> JdHonorEventTypeMap;

    static {
        JdHonorEventTypeMap = CollUtil.newHashMap();
        JdHonorEventTypeMap.put(ACTIVATE.code, HonorEventTypeEnum.ACTIVE);
        JdHonorEventTypeMap.put(REGISTER.code, HonorEventTypeEnum.REGISTER);
    }


}
