package huihuang.proxy.ocpx.bussiness.controller;

import huihuang.proxy.ocpx.bussiness.service.IChannelAdsService;
import huihuang.proxy.ocpx.common.BasicResult;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.common.XiaomiResponse;
import huihuang.proxy.ocpx.util.JsonParameterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * baidu-ltjd
 * @Author: xietao
 * @Date: 2023/5/11 20:20
 */
@RestController
@RequestMapping(Constants.ServerPath.BAIDU_LTJD)
public class BaiduLtjdController {

    @Autowired
    @Qualifier("bjService")
    private IChannelAdsService bjService;

    /**
     * 监测地址
     */
    @RequestMapping(Constants.ServerPath.MONITOR_ADDRESS)
    public Response monitorAddress(HttpServletRequest request, @RequestBody String reqBody) {
        try {
            Map<String, Object> params = JsonParameterUtil.jsonToMap(reqBody, Exception.class);
            return bjService.monitorAddress(params);
        } catch (Exception e) {
            return BasicResult.getFailResponse("请求异常", e.getMessage());
        }
    }

    /**
     * 点击上报和回传
     */
    @RequestMapping(Constants.ServerPath.CLICK_REPORT)
    public Response clickReport(HttpServletRequest request) {
        Response response;
        try {
            Map<String, String[]> parameterMap = request.getParameterMap();
            response = bjService.clickReport(parameterMap);
        } catch (Exception e) {
            e.printStackTrace();
            response = BasicResult.getFailResponse("请求异常", e.getMessage());
        }
        return new XiaomiResponse(response.getCode(), response.getMessage(), response.getData(), "");
    }

    /**
     * 客户侧回调
     */
    @GetMapping(Constants.ServerPath.ADS_CALLBACK_ID)
    public Response adsCallBack(HttpServletRequest request, @PathVariable Integer id) {
        try {
            Map<String, String[]> parameterMap = request.getParameterMap();
            return bjService.adsCallBack(id, parameterMap);
        } catch (Exception e) {
            e.printStackTrace();
            return BasicResult.getFailResponse("请求异常", e.getMessage());
        }
    }


}