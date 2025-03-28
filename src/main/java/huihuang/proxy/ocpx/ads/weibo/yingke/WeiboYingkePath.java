package huihuang.proxy.ocpx.ads.weibo.yingke;

import huihuang.proxy.ocpx.ads.weibo.WeiboPath;
import org.springframework.stereotype.Component;

/**
 * 一刻相册
 */
@Component
public class WeiboYingkePath extends WeiboPath {
    @Override
    public String baseAdsName() {
        return "weibo-yikexiangce";
    }

    public static final String EXPOSURE_URI = "https://vs.biz.weibo.com/x/pv?fid=1431&a=2345253&mi=&si=200032&";

    public static final String BASIC_URI = "https://vs.biz.weibo.com/x/bhv?fid=1431&a=2345253&mi=&si=200032&";

}
