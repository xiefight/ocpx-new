package huihuang.proxy.ocpx.ads.jd;

import cn.hutool.core.annotation.Alias;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class JDParamField {

    private String platform;
    private String idfa;
    @Alias("oaId")
    private String oa_id;
    @Alias("clickTime")
    private String click_time;
    @Alias("callbackUrl")
    private String callback_url;
    @Alias("accountId")
    private String account_id;

    private String extra;
    private String code;

}
