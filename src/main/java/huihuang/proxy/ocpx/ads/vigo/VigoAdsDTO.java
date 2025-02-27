package huihuang.proxy.ocpx.ads.vigo;

import huihuang.proxy.ocpx.common.CommonColumn;
import huihuang.proxy.ocpx.marketinterface.IMarkDto;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class VigoAdsDTO extends CommonColumn implements IMarkDto {

    private String requestId;
    private String pid;
    private String idfa;//iOS：IDFA ，原值，大写
    private String idfaMd5;//iOS:IDFA ，大写后 MD5，再转小写
    private String imei;
    private String imeiMd5;//imei 原值 md5 编码后转小写
    private String oaid;
    private String oaidMd5;//oaid 原值 md5 编码后转小写
    private String caid;
    private String ip;
    private String androidId;
    private String ua;
    private String model;
    private String callback;
    private String accountId;

    private String extra;

}
