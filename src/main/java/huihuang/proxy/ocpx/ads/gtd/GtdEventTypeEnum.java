package huihuang.proxy.ocpx.ads.gtd;

import cn.hutool.core.collection.CollUtil;
import huihuang.proxy.ocpx.channel.baidu.BaiduEventTypeEnum;
import huihuang.proxy.ocpx.channel.honor.HonorEventTypeEnum;
import huihuang.proxy.ocpx.channel.huawei.HuaweiEventTypeEnum;
import huihuang.proxy.ocpx.channel.huihuang.HuihuangChannelEventTypeEnum;
import huihuang.proxy.ocpx.channel.iqiyi.IQiyiEventTypeEnum;
import huihuang.proxy.ocpx.channel.oppo.OppoEventTypeEnum;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiEventTypeEnum;

import java.util.Map;

public enum GtdEventTypeEnum {

    ACTIVATE("1", "激活"),
    REGISTER("2", "注册"),
    DAY1RETENTION("3", "次留"),
    PAID("4", "付费"),


    ;

    private String code;
    private String desc;

    GtdEventTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }


    public static Map<String, HuihuangChannelEventTypeEnum> gtdHuihuangEventTypeMap;

    static {
        gtdHuihuangEventTypeMap = CollUtil.newHashMap();
        gtdHuihuangEventTypeMap.put(ACTIVATE.code, HuihuangChannelEventTypeEnum.ACTIVATE);
        gtdHuihuangEventTypeMap.put(REGISTER.code, HuihuangChannelEventTypeEnum.NEW_LOGIN);
        gtdHuihuangEventTypeMap.put(DAY1RETENTION.code, HuihuangChannelEventTypeEnum.DAY1RETENTION);
        gtdHuihuangEventTypeMap.put(PAID.code, HuihuangChannelEventTypeEnum.PAID);
    }


    public static Map<String, BaiduEventTypeEnum> gtdBaiduEventTypeMap;

    static {
        gtdBaiduEventTypeMap = CollUtil.newHashMap();
        gtdBaiduEventTypeMap.put(ACTIVATE.code, BaiduEventTypeEnum.ACTIVE);
        gtdBaiduEventTypeMap.put(REGISTER.code, BaiduEventTypeEnum.REGISTER);
        gtdBaiduEventTypeMap.put(DAY1RETENTION.code, BaiduEventTypeEnum.RETAIN_1DAY);
        gtdBaiduEventTypeMap.put(PAID.code, BaiduEventTypeEnum.ORDERS);
    }

}
