package huihuang.proxy.ocpx.main;

import cn.hutool.core.net.URLEncoder;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-10-17 20:42
 **/
public class TestMain {

    static List<Integer> ids = Arrays.asList(
//            499129,
//            499130,
//            499155,
//            499158,
//            499159,
//            499160,
//            499161,
//            499162,
//            499172,
//            499174,
//            499175,
//            499177,
//            499178,
//            499179,
//            499181,
//            499182,
//            499183,
//            499184,
//            499185,
//            499186,
//            499187,
//            499188,
//            499189,
//            499190,
//            499191,
//            499192,
//            499193,
//            499194,
//            499195,
//            499196,
//            499197,
//            499198,
//            499199,
//            499200,
//            499201,
//            499203,
//            499206,
//            499207,
//            499208,
//            499210,
//            499213,
//            499214,
//            499215,
//            499216,
//            499217,
//            499219,
//            499220,
//            499221,
//            499223,
//            499224,
//            499225,
//            499227,
//            499228,
//            499229,
//            499230,
//            499231,
//            499232,
//            499233,
//            499234,
//            499235,
//            499236,
//            499237,
//            499238,
//            499239,
//            499240,
//            499241,
//            499242,
//            499243,
//            499244,
//            499245,
//            499246,
//            499247,
//            499248,
//            499249,
//            499250,
//            499251,
//            499252,
//            499253,
//            499254,
//            499255,
//            499256,
//            499257,
//            499258,
//            499259,
//            499260,
//            499261,
//            499262,
//            499263,
//            499264,
//            499265,
//            499266,
//            499267,
//            499268,
//            499269,
//            499270,
//            499271,
//            499273,
//            499274,
//            499275,
//            499276
    );

    public static void main(String[] args) throws InterruptedException {

        Integer id = 499300;
//499293
//499294
//499295
//499296
//499297
//499298
//499299
//499300



        String url = "https://hzpvip.com/honorhhjdjrServer/adsCallBack/"+id+"?event_type=0";

        HttpResponse execute = HttpRequest.get(url).execute();
        System.out.println("id:"+id+"  -  "+execute.getStatus()+"  -  "+execute.body());

        /*for (Integer id : ids ) {
            String url = "https://hzpvip.com/honorhhjdjrServer/adsCallBack/"+id+"?event_type=0";

            HttpResponse execute = HttpRequest.get(url).execute();
            int sleep = new Random().nextInt(5000);
            System.out.println("id:"+id+"  -  "+execute.getStatus()+"  -  "+execute.body() +"  -  "+sleep);
            Thread.sleep(sleep);
        }*/

    }


    /*public static void main(String[] args) {
        String format = "cxbottle://url/openurl?url=%s&internal_share=true&activity_id=%d";

        String encode = new URLEncoder().encode("https://stage-activity.weizhiyanchina.com/catch-fish/#/?code=MAYDAY2025", Charset.defaultCharset());

//        URLEncoder.createQuery().encode("https://stage-activity.weizhiyanchina.com/catch-fish/#/?code=MAYDAY2025");
        System.err.println(String.format(format, encode, 98));
    }*/

}
