package huihuang.proxy.ocpx.ads.quannenghudong.cainiao;

import huihuang.proxy.ocpx.ads.quannenghudong.QuannengHudongPath;
import org.springframework.stereotype.Component;

@Component
public class QuannengCainiaoPath extends QuannengHudongPath {
    @Override
    public String baseAdsName() {
        return "quanneng-cainiao";
    }
}
