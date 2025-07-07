package huihuang.proxy.ocpx.ads.gtd;

import huihuang.proxy.ocpx.channel.baidu.BaiduParamEnum;
import huihuang.proxy.ocpx.channel.huihuang.HuihuangChannelParamEnum;

import java.util.HashMap;
import java.util.Map;

public enum GtdParamEnum {
    IDFA("idfa", "String", 1, "idfa 的原生值"),
    IDFA_MD5("idfaMd5", "String", 2, "iOS 设备广告标识 idfa md5值 小写"),
    IMEI("imei", "String", 1, "安卓设备广告标识 IMEI md5值 小写"),
    IMEI_MD5("imeiMd5", "String", 1, "安卓设备广告标识 IMEI md5值 小写"),
    OAID("oaid", "String", 1, "oaid 的原生值"),
    OAID_MD5("oaidMd5", "String", 1, "安卓设备广告标识 oaid md5值 小写"),
    IP("ip", "String", 2, "点击 ip"),
    UA("adAgent", "String", 2, "点 击 数 据 上 报 时http的 header 中的user_agent，一次urlencode 编码"),
    UA2("ua", "String", 2, "点 击 数 据 上 报 时http的 header 中的user_agent，一次urlencode 编码"),
    TS("ts", "String", 1, "点击时间，时间戳，单位毫秒"),

    OS("os", "String", 1, "0:android 1:ios"),
    CALLBACK("callback", "String", 1, "渠道回调地址"),
    CHANNEL("channel", "String", 2, "渠道标识"),
    ADID("ad_id", "String", 2, "渠道标识"),

    ACCOUNT_ID("account_id", "String", 1, ""),

    ;

    private String name;
    private String type;
    private Integer necessary;
    private String remark;

    GtdParamEnum(String name, String type, Integer necessary, String remark) {
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

    public static Map<GtdParamEnum, HuihuangChannelParamEnum> gtdHuihuangMap;

    static {
        gtdHuihuangMap = new HashMap<>();
        gtdHuihuangMap.put(IMEI_MD5, HuihuangChannelParamEnum.IMEI_MD5);
        gtdHuihuangMap.put(OAID_MD5, HuihuangChannelParamEnum.OAID_MD5);
        gtdHuihuangMap.put(IDFA_MD5, HuihuangChannelParamEnum.IDFA_MD5);
        gtdHuihuangMap.put(IMEI, null);
        gtdHuihuangMap.put(OAID, HuihuangChannelParamEnum.OAID);
        gtdHuihuangMap.put(IDFA, HuihuangChannelParamEnum.IDFA);

        gtdHuihuangMap.put(TS, HuihuangChannelParamEnum.TMS);
        gtdHuihuangMap.put(OS, HuihuangChannelParamEnum.OS);
        gtdHuihuangMap.put(IP, HuihuangChannelParamEnum.IP);
        gtdHuihuangMap.put(UA, HuihuangChannelParamEnum.UA);
        gtdHuihuangMap.put(ADID, HuihuangChannelParamEnum.AD_ID);
        gtdHuihuangMap.put(CHANNEL, HuihuangChannelParamEnum.GTD_CHANNEL);

        gtdHuihuangMap.put(CALLBACK, HuihuangChannelParamEnum.CALLBACK_URL);

        gtdHuihuangMap.put(ACCOUNT_ID, HuihuangChannelParamEnum.ACCOUNT_ID);
    }


    public static Map<GtdParamEnum, BaiduParamEnum> gtdBaiduMap;

    static {
        gtdBaiduMap = new HashMap<>();
        gtdBaiduMap.put(IMEI_MD5, BaiduParamEnum.IMEI_MD5);
        gtdBaiduMap.put(OAID_MD5, BaiduParamEnum.OAID_MD5);
        gtdBaiduMap.put(IDFA_MD5, null);
        gtdBaiduMap.put(IMEI, null);
        gtdBaiduMap.put(OAID, BaiduParamEnum.OAID);
        gtdBaiduMap.put(IDFA, BaiduParamEnum.IDFA);

        gtdBaiduMap.put(TS, BaiduParamEnum.TS);
        gtdBaiduMap.put(OS, BaiduParamEnum.OS_TYPE);
        gtdBaiduMap.put(IP, BaiduParamEnum.IP);
        gtdBaiduMap.put(UA2, BaiduParamEnum.UA);
        gtdBaiduMap.put(ADID, null);
        gtdBaiduMap.put(CHANNEL, BaiduParamEnum.GTD_CHANNEL);

        gtdBaiduMap.put(CALLBACK, BaiduParamEnum.CALLBACK_URL);

        gtdBaiduMap.put(ACCOUNT_ID, BaiduParamEnum.ACCOUNT_ID);
    }


}
