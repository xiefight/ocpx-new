package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.weibo.WeiboParamField;
import huihuang.proxy.ocpx.ads.weibo.xueersi.WeiboXueersiPath;
import huihuang.proxy.ocpx.ads.weibo.yingke.WeiboYingkePath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IWeiboYingkeAdsDao;
import huihuang.proxy.ocpx.channel.baidu.BaiduPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.weibo.BaiduWeiboReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("bdwbykChannelAds")
public class BaiduWeiboYingkeChannelAds extends BaiduWeiboReportFactory {

    String channelAdsKey = Constants.ChannelAdsKey.BAIDU_WEIBO_YINGKE;

    @Autowired
    private IWeiboYingkeAdsDao wbykAdsDao;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.BAIDU_WEIBO_YINGKE;
    }

    @Override
    protected String channelName() {
        return BaiduPath.BAIDU_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return wbykAdsDao;
    }

    @Override
    protected String initAdsUrl() {
        return null;
    }

    @Override
    protected String initAdsUrl(Object adsObj) {
        WeiboParamField weiboParamField = (WeiboParamField) adsObj;
        String monitorType = weiboParamField.getMonitorType();
        if ("0".equals(monitorType)){
            //曝光监测
            return WeiboYingkePath.EXPOSURE_URI;
        }else {
            //点击监测
            return WeiboYingkePath.BASIC_URI;
        }
    }
}
