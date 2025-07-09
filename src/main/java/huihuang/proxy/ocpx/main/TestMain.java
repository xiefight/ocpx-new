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
    );

    public static void main(String[] args) throws InterruptedException {
        //2501
        //2502
        //2503
        //2504
        //2505
        //2506
        //2507
        //2508
        //2509
        //2510
        //2511
        //2512
        //2513
        //2514
        //2515
        //2516
        //2517
        //2518
        //2519
        //2520

        Integer id = 2520;




        String url = "https://hzpvip.com/honorjdjdjrServer/adsCallBack/"+id+"?event_type=0";

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
