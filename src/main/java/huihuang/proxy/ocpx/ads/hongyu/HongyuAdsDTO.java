package huihuang.proxy.ocpx.ads.hongyu;

import cn.hutool.core.annotation.Alias;
import huihuang.proxy.ocpx.common.CommonColumn;
import huihuang.proxy.ocpx.marketinterface.IMarkDto;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class HongyuAdsDTO extends CommonColumn implements IMarkDto {

    private String appId;
    private String channel;
    private String idfa;
    private String imei;
    private String imeiMd5;
    private String oaid;
    private String oaidMd5;
    private String ip;
    private String androidId;
    private String ts;
    private String ua;
    private String os;
    private String clickId;
    private String callback;
    private String accountId;

    private String extra;

}
