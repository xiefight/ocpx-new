package huihuang.proxy.ocpx.ads.huihuangmingtian.ads;

import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianPath;
import org.springframework.stereotype.Component;

/**
 * huihuang/proxy/ocpx/ads/huihuang/HuihuangEventTypeEnum.java事件
 */

@Component
public class HuihuangGaotuPath extends HuihuangmingtianPath {
    @Override
    public String baseAdsName() {
        return "huihuang-gaotu";
    }
}