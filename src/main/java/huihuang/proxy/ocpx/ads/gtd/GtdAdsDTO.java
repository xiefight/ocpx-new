package huihuang.proxy.ocpx.ads.gtd;

import cn.hutool.core.annotation.Alias;
import huihuang.proxy.ocpx.common.CommonColumn;
import huihuang.proxy.ocpx.marketinterface.IMarkDto;
import lombok.Data;
import lombok.ToString;

/**
 * @Author: xietao
 * @Date: 2023/6/8 17:21
 */
@ToString
@Data
public class GtdAdsDTO extends CommonColumn implements IMarkDto {

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
    private String adid;

    private String accountId;

}
