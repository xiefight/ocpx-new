package huihuang.proxy.ocpx.ads.quannenghudong.feizhu;

import huihuang.proxy.ocpx.ads.quannenghudong.QuannengHudongPath;
import org.springframework.stereotype.Component;

@Component
public class QuannengFeizhuPath extends QuannengHudongPath {
    @Override
    public String baseAdsName() {
        return "quanneng-feizhu";
    }
}
