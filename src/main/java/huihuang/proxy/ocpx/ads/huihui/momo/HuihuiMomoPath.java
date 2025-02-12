package huihuang.proxy.ocpx.ads.huihui.momo;

import huihuang.proxy.ocpx.ads.huihui.HuihuiPath;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2025-02-12 11:05
 **/
@Component
public class HuihuiMomoPath extends HuihuiPath {
    @Override
    public String baseAdsName() {
        return "huihui-momo";
    }
}
