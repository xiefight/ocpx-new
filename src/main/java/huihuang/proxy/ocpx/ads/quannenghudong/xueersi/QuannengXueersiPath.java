package huihuang.proxy.ocpx.ads.quannenghudong.xueersi;

import huihuang.proxy.ocpx.ads.quannenghudong.QuannengHudongPath;
import org.springframework.stereotype.Component;

@Component
public class QuannengXueersiPath extends QuannengHudongPath {
    @Override
    public String baseAdsName() {
        return "quanneng-xueersi";
    }
}
