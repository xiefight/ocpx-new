package huihuang.proxy.ocpx.ads.gtd.yingke;

import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianPath;
import org.springframework.stereotype.Component;

@Component
public class GtdYingkePath extends HuihuangmingtianPath {
    @Override
    public String baseAdsName() {
        return "gtd-yingke";
    }
}
