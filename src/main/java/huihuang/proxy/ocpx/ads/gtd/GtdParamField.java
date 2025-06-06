package huihuang.proxy.ocpx.ads.gtd;

import cn.hutool.core.annotation.Alias;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class GtdParamField {

    private String imei;
    private String imeiMd5;
    private String oaid;
    private String oaidMd5;
    private String idfa;
    private String idfaMd5;
    private String ts;
    private String os;
    private String ip;
    private String ua;
    private String mac;
    private String callback;
    private String extra;
    private String channel;
    @Alias("adId")
    private String ad_id;

    @Alias("accountId")
    private String account_id;

}
