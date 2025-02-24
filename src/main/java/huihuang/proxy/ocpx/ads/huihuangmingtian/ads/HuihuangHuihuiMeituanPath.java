package huihuang.proxy.ocpx.ads.huihuangmingtian.ads;

import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianPath;
import org.springframework.stereotype.Component;

/**
 * 美团是有道的
 * 被辉煌封装了一层，进行外放
 */
@Component
public class HuihuangHuihuiMeituanPath extends HuihuangmingtianPath {
    @Override
    public String baseAdsName() {
        return "huihuang-huihui-meituan";
    }
}
