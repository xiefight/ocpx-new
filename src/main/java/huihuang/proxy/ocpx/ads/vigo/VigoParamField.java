package huihuang.proxy.ocpx.ads.vigo;

import cn.hutool.core.annotation.Alias;
import lombok.Data;

@Data
public class VigoParamField {

    @Alias("requestId")
    private String request_id;
    private String pid;
    private String idfa;//iOS：IDFA ，原值，大写
    @Alias("idfaMd5")
    private String idfa_md5;//iOS:IDFA ，大写后 MD5，再转小写
    private String imei;
    @Alias("imeiMd5")
    private String imei_md5;//imei 原值 md5 编码后转小写
    private String oaid;
    @Alias("oaidMd5")
    private String oaid_md5;//oaid 原值 md5 编码后转小写
    private String caid;
    private String ip;
    private String androidId;
    private String ua;
    private String model;
    private String callback;
    @Alias("accountId")
    private String account_id;

    private String extra;

}
