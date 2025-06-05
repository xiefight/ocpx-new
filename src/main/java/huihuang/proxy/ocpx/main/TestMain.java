package huihuang.proxy.ocpx.main;

import cn.hutool.core.net.URLEncoder;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-10-17 20:42
 **/
public class TestMain {

    static List<Integer> ids = Arrays.asList(
//            2232872,
//            2232873,
//            2232874,
//            2232875,
//            2232876,
//            2232877,
//            2232878,
//            2232879,
//            2232880,
//            2232881,
//            2232882,
//            2232883,
//            2232884,
//            2232885,
//            2232886,
//            2232887,
//            2232888,
//            2232889,
//            2232890,
//            2232891,
//            2232892,
//            2232893,
//            2232894,
//            2232895,
//            2232896,
//            2232897,
//            2232898,
//            2232899,
//            2232900,
//            2232901,
//            2232902,
//            2232903,
//            2232904,
//            2232905,
//            2232906,
//            2232907,
//            2232908,
//            2232909,
//            2232910,
//            2232911,
//            2232912,
//            2232913,
//            2232914,
//            2232915,
//            2232916,
//            2232917,
//            2232918,
//            2232919,
//            2232920,
//            2232921,
//            2232922,
//            2232923,
//            2232924,
//            2232925,
//            2232926,
//            2232927,
//            2232928,
//            2232929,
//            2232930,
//            2232931,
//            2232932,
//            2232933,
//            2232934,
//            2232935,
//            2232936,
//            2232937,
//            2232938,
//            2232939,
//            2232940,
//            2232941,
//            2232942,
//            2232943,
//            2232944,
//            2232945,
//            2232946,
//            2232947,
//            2232948,
//            2232949,
//            2232950,
//            2232951,
//            2232952,
//            2232953,
//            2232954,
//            2232955,
//            2232956,
//            2232957,
//            2232958,
//            2232959,
//            2232960,
//            2232961,
//            2232962,
//            2232963,
//            2232964,
//            2232965,
//            2232966,
//            2232967,
//            2232969,
            2232970
//            2232971
//            2232972
    );

    public static void main(String[] args) throws InterruptedException {

        for (Integer id : ids ) {
            String url = "https://hzpvip.com/honorhhyoukuServer/adsCallBack/"+id+"?event_type=0";

            HttpResponse execute = HttpRequest.get(url).execute();
            System.out.println("id:"+id+"  -  "+execute.getStatus()+"  -  "+execute.body());
            Thread.sleep(1000);
        }

    }


    /*public static void main(String[] args) {
        String format = "cxbottle://url/openurl?url=%s&internal_share=true&activity_id=%d";

        String encode = new URLEncoder().encode("https://stage-activity.weizhiyanchina.com/catch-fish/#/?code=MAYDAY2025", Charset.defaultCharset());

//        URLEncoder.createQuery().encode("https://stage-activity.weizhiyanchina.com/catch-fish/#/?code=MAYDAY2025");
        System.err.println(String.format(format, encode, 98));
    }*/

}
