package huihuang.proxy.ocpx.ads.weibo.zhipuqingyan;

import huihuang.proxy.ocpx.ads.weibo.WeiboPath;
import org.springframework.stereotype.Component;

@Component
public class WeiboZhipuqingyanPath extends WeiboPath {
    @Override
    public String baseAdsName() {
        return "weibo-zhipuqingyan";
    }

    public static final String EXPOSURE_URI = "https://vs.biz.weibo.com/x/pv?fid=1255&a=2905571&mi=&si=200032&";

    public static final String BASIC_URI = "https://vs.biz.weibo.com/x/bhv?fid=1255&a=2905571&mi=&si=200032&";

}
