package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianParamField;
import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuangSoulAdsDao;
import huihuang.proxy.ocpx.channel.baidu.BaiduPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.huihuangmingtian.BaiduHuihuangReportFactory;
import huihuang.proxy.ocpx.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("bhhsoulChannelAds")
public class BaiduHuihuangSoulChannelAds extends BaiduHuihuangReportFactory {

    @Autowired
    private IHuihuangSoulAdsDao hhsoulAdsDao;

    String channelAdsKey = Constants.ChannelAdsKey.BAIDU_HUIHUANG_SOUL;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.BAIDU_HUIHUANG_SOUL ;
    }

    @Override
    protected String channelName() {
        return BaiduPath.BAIDU_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return hhsoulAdsDao;
    }


    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        HuihuangmingtianParamField huihuangmingtianParamField = (HuihuangmingtianParamField) super.channelParamToAdsParam(parameterMap);
        huihuangmingtianParamField.setVersion("v2");
        return huihuangmingtianParamField;
    }

    @Override
    protected String initAdsUrl() {
        return null;
    }

    @Override
    protected String initAdsUrl(Object adsObj) {
        StringBuffer sb = new StringBuffer(HuihuangmingtianPath.BASIC_URI);
        sb.append("ts=").append(System.currentTimeMillis()).append("&");
        sb.append("requestId=").append(RandomUtil.randomStamp()).append("&");
        return sb.toString();
    }

}
