package huihuang.proxy.ocpx.main;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-10-17 20:42
 **/
public class TestMain {

    public static void main(String[] args) {
        for (int id = 21222;id<21500;id++){
            String url = "https://hzpvip.com/hqfServer/adsCallBack/"+id+"?action_type=activate";

            HttpResponse execute = HttpRequest.get(url).execute();
            System.out.println("id:"+id+"  -  "+execute.getStatus()+"  -  "+execute.body());
        }

    }

}
