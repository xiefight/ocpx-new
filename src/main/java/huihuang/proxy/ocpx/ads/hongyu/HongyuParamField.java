package huihuang.proxy.ocpx.ads.hongyu;

import cn.hutool.core.annotation.Alias;
import lombok.Data;

@Data
public class HongyuParamField {

    @Alias("appId")
    private String appid;
    private String channel;
    private String idfa;
    private String imei;
    private String imei_md5;
    private String oaid;
    @Alias("oaidMd5")
    private String oaid_md5;
    @Alias("androidId")
    private String androidid;
    private String ua;
    private String ip;
    private String ts;
    @Alias("clickId")
    private String clickid;
    private String callback;
    @Alias("accountId")
    private String account_id;

    private String extra;

}
