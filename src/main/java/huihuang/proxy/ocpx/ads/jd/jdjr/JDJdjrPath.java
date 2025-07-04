package huihuang.proxy.ocpx.ads.jd.jdjr;

import huihuang.proxy.ocpx.ads.jd.JDPath;
import org.springframework.stereotype.Component;

@Component
public class JDJdjrPath extends JDPath {
    @Override
    public String baseAdsName() {
        return "jd-jdjr";
    }
}
