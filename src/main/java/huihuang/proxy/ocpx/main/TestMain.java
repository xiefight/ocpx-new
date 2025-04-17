package huihuang.proxy.ocpx.main;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;

import java.util.Arrays;
import java.util.List;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-10-17 20:42
 **/
public class TestMain {

    static List<Integer> ids = Arrays.asList(
            2232257

    );

    public static void main(String[] args) {

        for (Integer id : ids ) {
            String url = "https://hzpvip.com/honorhhyoukuServer/adsCallBack/"+id+"?event_type=0";

            HttpResponse execute = HttpRequest.get(url).execute();
            System.out.println("id:"+id+"  -  "+execute.getStatus()+"  -  "+execute.body());
        }

    }

}
