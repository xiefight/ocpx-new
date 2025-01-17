package huihuang.proxy.ocpx.ads.hongyu;

import cn.hutool.core.collection.CollUtil;
import huihuang.proxy.ocpx.channel.oppo.OppoEventTypeEnum;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiEventTypeEnum;

import java.util.Map;

public enum HongyuEventTypeEnum {

    ACTIVATE("0", "激活"),
    REGISTER("1", "注册"),
    PURCHASE("2", "付费"),
    DAY1RETENTION("3", "次留"),

    ;

    private String code;
    private String desc;

    HongyuEventTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }


    public static Map<String, XiaomiEventTypeEnum> hongyuXiaomiEventTypeMap;

    static {
        hongyuXiaomiEventTypeMap = CollUtil.newHashMap();
        //激活
        hongyuXiaomiEventTypeMap.put(ACTIVATE.code, XiaomiEventTypeEnum.APP_ACTIVE);
        //自定义新增激活
        hongyuXiaomiEventTypeMap.put(ACTIVATE.code + "new", XiaomiEventTypeEnum.APP_ACTIVE_NEW);
        //注册
        hongyuXiaomiEventTypeMap.put(REGISTER.code, XiaomiEventTypeEnum.APP_REGISTER);
        //次留
        hongyuXiaomiEventTypeMap.put(DAY1RETENTION.code, XiaomiEventTypeEnum.APP_RETENTION);
        //购买
        hongyuXiaomiEventTypeMap.put(PURCHASE.code, XiaomiEventTypeEnum.APP_PURCHASE);
    }


    public static Map<String, OppoEventTypeEnum> hongyuOppoEventTypeMap;

    static {
        hongyuOppoEventTypeMap = CollUtil.newHashMap();
        //激活
        hongyuOppoEventTypeMap.put(ACTIVATE.code, OppoEventTypeEnum.ACTIVE);
        //注册
        hongyuOppoEventTypeMap.put(REGISTER.code, OppoEventTypeEnum.REGISTER);
        //次留
        hongyuOppoEventTypeMap.put(DAY1RETENTION.code, OppoEventTypeEnum.RETAIN_2DAY);
        //购买
        hongyuOppoEventTypeMap.put(PURCHASE.code, OppoEventTypeEnum.DEEP_PAGE_ACCESS);
    }


}
