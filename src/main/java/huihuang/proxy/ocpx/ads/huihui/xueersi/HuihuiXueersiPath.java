package huihuang.proxy.ocpx.ads.huihui.xueersi;

import huihuang.proxy.ocpx.ads.huihui.HuihuiPath;
import org.springframework.stereotype.Component;

@Component
public class HuihuiXueersiPath extends HuihuiPath {
    @Override
    public String baseAdsName() {
        return "huihui-xueersi";
    }
}
