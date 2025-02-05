package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.weibo.WeiboParamField;
import huihuang.proxy.ocpx.ads.weibo.xueersi.WeiboXueersiPath;
import huihuang.proxy.ocpx.ads.weibo.zhipuqingyan.WeiboZhipuqingyanPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IWeiboZhipuqingyanAdsDao;
import huihuang.proxy.ocpx.channel.honor.HonorPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.weibo.HonorWeiboReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("honorwbzpqyChannelAds")
public class HonorWeiboZhipuqingyanChannelAds extends HonorWeiboReportFactory {

    String channelAdsKey = Constants.ChannelAdsKey.HONOR_WEIBO_ZHIPUQINGYAN;

    @Autowired
    private IWeiboZhipuqingyanAdsDao wbzpqyAdsDao;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.HONOR_WEIBO_ZHIPUQINGYAN;
    }

    @Override
    protected String channelName() {
        return HonorPath.HONOR_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return wbzpqyAdsDao;
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
            return WeiboZhipuqingyanPath.EXPOSURE_URI;
        } else {
            //点击监测
            return WeiboZhipuqingyanPath.BASIC_URI;
        }
    }
}
