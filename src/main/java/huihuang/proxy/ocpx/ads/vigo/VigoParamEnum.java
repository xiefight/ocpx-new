package huihuang.proxy.ocpx.ads.vigo;

import huihuang.proxy.ocpx.channel.oppo.OppoParamEnum;

import java.util.HashMap;
import java.util.Map;

public enum VigoParamEnum {

    REQUEST_ID("request_id", "String", 1, "唯一请求 id；若为 RTA 投放，需要回传 RTA 接 口返回 id(request_id),如需曝光，曝光和点击需传同一值"),
    PID("pid", "String", 1, "广告商分配的标识"),
    IDFA("idfa", "String", 1, "iOS 设备广告标识 idfa（iOS必填）"),
    IDFA_MD5("idfa_md5", "String", 1, "iOS:IDFA ，大写后 MD5，再转小写"),
    IMEI("imei", "String", 1, "imei原生值的md5， 32位小写"),
    IMEI_MD5("imei_md5", "String", 1, "请求设备 imei 原值 md5 编码后转小写"),
    OAID("oaid", "String", 1, "安卓设备广告标识 oaid 原值"),
    OAID_MD5("oaid_md5", "String", 1, "请求设备 oaid 原值 md5 编码后转小写"),
    CAID("caid", "String", 1, "iOS：caid  原值;  需将值和版本号   使 用”_”拼接(若无版 本号使用 0，多个使用”,”拼接)"),
    IP("ip", "String", 0, "媒体投放系统获取的⽤户终端的公共IP地址"),
    UA("ua", "String", 1, "点 击 数 据 上 报 时http的 header 中的user_agent，一次urlencode 编码"),
    MODEL("model", "String", 1, "手机机型"),
    CALLBACK("callback", "String", 1, "渠道回调地址"),
    ACCOUNT_ID("account_id", "String", 0, "我们规定的账户id,用于同一pid区分不出来的情况下,使用accountId区分"),

    ;

    private String name;
    private String type;
    private Integer necessary;
    private String remark;

    VigoParamEnum(String name, String type, Integer necessary, String remark) {
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


    public static Map<VigoParamEnum, OppoParamEnum> vigoOppoMap;

    static {
        vigoOppoMap = new HashMap<>();
        vigoOppoMap.put(PID, OppoParamEnum.QUANNENGHUDONG_PID);
        vigoOppoMap.put(IDFA, null);
        vigoOppoMap.put(IMEI, OppoParamEnum.IMEI);//需要md5
        vigoOppoMap.put(OAID, OppoParamEnum.OAID);
        vigoOppoMap.put(CAID, null);
        vigoOppoMap.put(IP, OppoParamEnum.IP);
        vigoOppoMap.put(UA, OppoParamEnum.USERAGENT);
        vigoOppoMap.put(MODEL, null);
        vigoOppoMap.put(CALLBACK, null);
        vigoOppoMap.put(ACCOUNT_ID, OppoParamEnum.ACCOUNT_ID);
    }


}
