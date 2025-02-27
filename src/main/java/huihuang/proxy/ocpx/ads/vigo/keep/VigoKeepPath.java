package huihuang.proxy.ocpx.ads.vigo.keep;

import huihuang.proxy.ocpx.ads.quannenghudong.QuannengHudongPath;
import org.springframework.stereotype.Component;

@Component
public class VigoKeepPath extends QuannengHudongPath {
    @Override
    public String baseAdsName() {
        return "vigo-keep";
    }
}
