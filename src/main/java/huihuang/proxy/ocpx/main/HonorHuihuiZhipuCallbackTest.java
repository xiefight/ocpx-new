package huihuang.proxy.ocpx.main;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2025-02-20 22:39
 **/
public class HonorHuihuiZhipuCallbackTest {

    public static void main(String[] args) {

//        String str = "https://hzpvip.com/honorhhzpqyServer/adsCallBack/"+43424+"?conv_action=android_register";
//        HttpResponse response = HttpRequest.get(str).execute();


        for (int i = 42373;i < 43424;i++){
            String str = "https://hzpvip.com/honorhhzpqyServer/adsCallBack/"+i+"?conv_action=android_register";
            HttpResponse response = HttpRequest.get(str).execute();
        }

    }

}
