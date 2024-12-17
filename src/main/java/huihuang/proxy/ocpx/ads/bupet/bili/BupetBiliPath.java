package huihuang.proxy.ocpx.ads.bupet.bili;

import huihuang.proxy.ocpx.ads.bupet.BupetPath;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2024-08-27 17:36
 **/
@Component
public class BupetBiliPath extends BupetPath {
    @Override
    public String baseAdsName() {
        return "bili";
    }

    /**
     * 点击上报及转化数据回调接口
     */
    // bilibili  一户的地址
    public static final String BASIC_URI = "https://ad.bupet.net/openapi/click/upload/am26fi?";

    /**
     * 点击上报及转化数据回调接口
     */
    // bilibili  二户的地址
    public static final String BASIC_URI_2 = " https://ad.bupet.net/openapi/click/upload/ZVRRVn?";

}
