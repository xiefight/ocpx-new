package huihuang.proxy.ocpx.ads.huihuangmingtian.ads;

import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianPath;
import org.springframework.stereotype.Component;

/**
 * 智投的  其他是丰芒的
 */

@Component
public class HuihuangYitaoPath extends HuihuangmingtianPath {
    @Override
    public String baseAdsName() {
        return "huihuang-yitao";
    }
}
