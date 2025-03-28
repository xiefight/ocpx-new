package huihuang.proxy.ocpx.main;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2025-02-20 22:39
 **/
public class HonorHuihuiZhipuCallbackTest {

    public static void main(String[] args) throws InterruptedException {

//        String str = "https://hzpvip.com/honorhhzpqyServer/adsCallBack/"+43424+"?conv_action=android_register";
//        HttpResponse response = HttpRequest.get(str).execute();


        for (int i = 2029375;i <= 2029382;i++){
            String str = "https://hzpvip.com/honorhhyitaoServer/adsCallBack/"+i+"?event_type=6";
            HttpResponse response = HttpRequest.get(str).execute();
            Thread.sleep(500);
        }

    }

}
