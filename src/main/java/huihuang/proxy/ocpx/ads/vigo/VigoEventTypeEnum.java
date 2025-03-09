package huihuang.proxy.ocpx.ads.vigo;

import cn.hutool.core.collection.CollUtil;
import huihuang.proxy.ocpx.channel.oppo.OppoEventTypeEnum;

import java.util.Map;

public enum VigoEventTypeEnum {

    ACTIVATE("1", "激活"),
    REGISTER("2", "注册"),
    PURCHASE("3", "付费"),
    DAY1RETENTION("4", "次留"),
    ACTIVE("5", "活跃"),
    ORDER("6", "下单"),
    BEHAVIOR("7", "关键行为"),

    ;

    private String code;
    private String desc;

    VigoEventTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }


    public static Map<String, OppoEventTypeEnum> vigoOppoEventTypeMap;

    static {
        vigoOppoEventTypeMap = CollUtil.newHashMap();
        //激活
        vigoOppoEventTypeMap.put(ACTIVATE.code, OppoEventTypeEnum.ACTIVE);
        //注册
        vigoOppoEventTypeMap.put(REGISTER.code, OppoEventTypeEnum.REGISTER);
        //次留
        vigoOppoEventTypeMap.put(DAY1RETENTION.code, OppoEventTypeEnum.RETAIN_2DAY);
        //购买
        vigoOppoEventTypeMap.put(PURCHASE.code, null);
    }

}
