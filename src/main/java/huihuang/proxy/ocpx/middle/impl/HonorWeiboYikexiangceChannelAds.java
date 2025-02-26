package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.weibo.WeiboParamField;
import huihuang.proxy.ocpx.ads.weibo.yike.WeiboYikexiangcePath;
import huihuang.proxy.ocpx.ads.weibo.zhipuqingyan.WeiboZhipuqingyanPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IWeiboYikexiangceAdsDao;
import huihuang.proxy.ocpx.channel.honor.HonorPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.weibo.HonorWeiboReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("honorwbykxcChannelAds")
public class HonorWeiboYikexiangceChannelAds extends HonorWeiboReportFactory {

    String channelAdsKey = Constants.ChannelAdsKey.HONOR_WEIBO_YIKEXIANGCE;

    @Autowired
    private IWeiboYikexiangceAdsDao wbykxcAdsDao;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.HONOR_WEIBO_YIKEXIANGCE;
    }

    @Override
    protected String channelName() {
        return HonorPath.HONOR_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return wbykxcAdsDao;
    }

    @Override
    protected String initAdsUrl() {
        return null;
    }

    @Override
    protected String initAdsUrl(Object adsObj) {
        WeiboParamField weiboParamField = (WeiboParamField) adsObj;
        String monitorType = weiboParamField.getMonitorType();
        if ("0".equals(monitorType)) {
            //曝光监测
            return WeiboYikexiangcePath.EXPOSURE_URI;
        } else {
            //点击监测
            return WeiboYikexiangcePath.BASIC_URI;
        }
    }
}
