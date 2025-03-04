package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.weibo.WeiboParamField;
import huihuang.proxy.ocpx.ads.weibo.yike.WeiboYikexiangcePath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IWeiboYikexiangceAdsDao;
import huihuang.proxy.ocpx.channel.oppo.OppoPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.weibo.OppoWeiboReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("oppowbykxcChannelAds")
public class OppoWeiboYikexiangceChannelAds extends OppoWeiboReportFactory {

    String channelAdsKey = Constants.ChannelAdsKey.OPPO_WEIBO_YIKEXIANGCE;

    @Autowired
    private IWeiboYikexiangceAdsDao wbykxcAdsOppoDao;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.OPPO_WEIBO_YIKEXIANGCE;
    }

    @Override
    protected String channelName() {
        return OppoPath.OPPO_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return wbykxcAdsOppoDao;
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
