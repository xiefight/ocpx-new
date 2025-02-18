package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.huihui.HuihuiParamField;
import huihuang.proxy.ocpx.ads.huihui.HuihuiPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuiZhipuqingyanAdsDao;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.huihuiyoudao.XiaomiHuihuiReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("xmhhzpqyChannelAds")
public class XiaomiHuihuiZhipuqingyanChannelAds extends XiaomiHuihuiReportFactory {

    @Autowired
    private IHuihuiZhipuqingyanAdsDao hhzpqyAdsDao;

    String channelAdsKey = Constants.ChannelAdsKey.XIAOMI_HUIHUI_ZHIPUQINGYAN;


    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.XIAOMI_HUIHUI_ZHIPUQINGYAN;
    }

    @Override
    protected IMarkDao adsDao() {
        return hhzpqyAdsDao;
    }

    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        HuihuiParamField huihuiParamField = (HuihuiParamField) super.channelParamToAdsParam(parameterMap);
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey, huihuiParamField);
        huihuiParamField.setRedirect("false");
        huihuiParamField.setDeveloper_id("5f7a582066184032");
        return huihuiParamField;
    }


    @Override
    protected String initAdsUrl() {
        return HuihuiPath.BASIC_URI_2;
    }

}
