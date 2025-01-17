package huihuang.proxy.ocpx.ads.hongyu;

import huihuang.proxy.ocpx.channel.oppo.OppoParamEnum;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiParamEnum;

import java.util.HashMap;
import java.util.Map;

public enum HongyuParamEnum {

    APPID("appid", "String", 1, "广告主的appid"),
    CHANNEL("channel", "String", 1, "渠道标识"),
    IDFA("idfa", "String", 1, "iOS设备的idfa原值"),
    IMEI("imei", "String", 1, "安卓设备的imei原值"),
    IMEI_MD5("imei_md5", "String", 0, "安卓设备的imei原值md5加密小写值"),
    OAID("oaid", "String", 1, "安卓设备oaid原值"),
    OAID_MD5("oaid_md5", "String", 0, "安卓设备oaid原值md5加密小写值"),
    ANDROID_ID("androidid", "String", 1, "安卓设备androidid原值"),
    IP("ip", "String", 1, "客户端ip"),
    UA("ua", "String", 0, "客户端设备ua，当ios设备获取不到idfa值时，需要传此值"),
    CLICK_ID("clickid", "String", 0, "点击id"),
    TS("ts", "String", 0, "请求时间（毫秒单位）"),
    CALLBACK("callback", "String", 1, "渠道回调地址"),


    ;

    private String name;
    private String type;
    private Integer necessary;
    private String remark;

    HongyuParamEnum(String name, String type, Integer necessary, String remark) {
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


    public static Map<HongyuParamEnum, XiaomiParamEnum> hongyuXiaomiMap;

    static {
        hongyuXiaomiMap = new HashMap<>();
        hongyuXiaomiMap.put(APPID, XiaomiParamEnum.QIDU_APPID);
        hongyuXiaomiMap.put(CHANNEL, XiaomiParamEnum.QIDU_CHANNEL);
        hongyuXiaomiMap.put(IDFA, null);
        hongyuXiaomiMap.put(IMEI, null);//
        hongyuXiaomiMap.put(IMEI_MD5, XiaomiParamEnum.IMEI);//需要md5
        hongyuXiaomiMap.put(OAID, XiaomiParamEnum.OAID);
        hongyuXiaomiMap.put(OAID_MD5, XiaomiParamEnum.OAID);
        hongyuXiaomiMap.put(IP, XiaomiParamEnum.IP);
        hongyuXiaomiMap.put(ANDROID_ID, null);
        hongyuXiaomiMap.put(UA, XiaomiParamEnum.UA);
        hongyuXiaomiMap.put(CALLBACK, XiaomiParamEnum.CALLBACK);
        hongyuXiaomiMap.put(CLICK_ID, null);
        hongyuXiaomiMap.put(TS, XiaomiParamEnum.TS);
    }


    public static Map<HongyuParamEnum, OppoParamEnum> hongyuOppoMap;

    static {
        hongyuOppoMap = new HashMap<>();
        hongyuOppoMap.put(APPID, OppoParamEnum.QIDU_APPID);
        hongyuOppoMap.put(CHANNEL, OppoParamEnum.QIDU_CHANNEL);
        hongyuOppoMap.put(IDFA, null);
        hongyuOppoMap.put(IMEI, null);//
        hongyuOppoMap.put(IMEI_MD5, OppoParamEnum.IMEI);//需要md5
        hongyuOppoMap.put(OAID, OppoParamEnum.OAID);
        hongyuOppoMap.put(OAID_MD5, OppoParamEnum.OAID);
        hongyuOppoMap.put(IP, OppoParamEnum.IP);
        hongyuOppoMap.put(ANDROID_ID, null);
        hongyuOppoMap.put(UA, OppoParamEnum.USERAGENT);
        hongyuOppoMap.put(CALLBACK, null);
        hongyuOppoMap.put(CLICK_ID, null);
        hongyuOppoMap.put(TS, OppoParamEnum.TS);
    }


}
