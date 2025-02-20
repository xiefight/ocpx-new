package huihuang.proxy.ocpx.ads.huihui.meituan;

import huihuang.proxy.ocpx.ads.huihui.HuihuiPath;
import org.springframework.stereotype.Component;

@Component
public class HuihuiMeituanPath extends HuihuiPath {

    public static final String MEITUAN_ADS_NAME = "huihui-meituan";

    @Override
    public String baseAdsName() {
        return MEITUAN_ADS_NAME;
    }

}
