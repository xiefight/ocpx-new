package huihuang.proxy.ocpx.ads.quannenghudong.zhipuqingyan;

import huihuang.proxy.ocpx.ads.quannenghudong.QuannengHudongPath;
import org.springframework.stereotype.Component;

@Component
public class QuannengZhipuqingyanPath extends QuannengHudongPath {
    @Override
    public String baseAdsName() {
        return "quanneng-zhipuqingyan";
    }
}
