package huihuang.proxy.ocpx.ads.weibo.youku;

import huihuang.proxy.ocpx.ads.weibo.WeiboPath;
import org.springframework.stereotype.Component;

/**
 * 一刻相册
 */
@Component
public class WeiboYoukuPath extends WeiboPath {
    @Override
    public String baseAdsName() {
        return "weibo-youku";
    }

    public static final String EXPOSURE_URI = "https://vs.biz.weibo.com/x/pv?fid=1539&a=93124&mi=&si=200032&";

    public static final String BASIC_URI = "https://vs.biz.weibo.com/x/bhv?fid=1539&a=93124&mi=&si=200032&";

}
