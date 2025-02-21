package huihuang.proxy.ocpx.ads.weibo.iqiyi;

import huihuang.proxy.ocpx.ads.weibo.WeiboPath;
import org.springframework.stereotype.Component;

@Component
public class WeiboIQiyiPath extends WeiboPath {
    @Override
    public String baseAdsName() {
        return "weibo-iqiyi";
    }

    public static final String EXPOSURE_URI = "https://vs.biz.weibo.com/x/pv?fid=1259&a=84672&mi=&si=200032&";

    public static final String BASIC_URI = "https://vs.biz.weibo.com/x/bhv?fid=1259&a=84672&mi=&si=200032&";

}
