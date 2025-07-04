package huihuang.proxy.ocpx.ads.jd;

import cn.hutool.core.annotation.Alias;
import huihuang.proxy.ocpx.common.CommonColumn;
import huihuang.proxy.ocpx.marketinterface.IMarkDto;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class JDAdsDTO extends CommonColumn implements IMarkDto {


    private String platform;
    private String idfa;
    private String oaId;
    private String clickTime;
    private String callbackUrl;
    private String accountId;
    private String extra;

    private String code;

}
