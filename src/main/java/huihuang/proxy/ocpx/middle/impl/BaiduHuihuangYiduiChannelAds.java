package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuangYiduiAdsDao;
import huihuang.proxy.ocpx.channel.baidu.BaiduPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.huihuangmingtian.BaiduHuihuangReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("bhhyiduiChannelAds")
public class BaiduHuihuangYiduiChannelAds extends BaiduHuihuangReportFactory {

    @Autowired
    private IHuihuangYiduiAdsDao hhyiduiAdsDao;

    String channelAdsKey = Constants.ChannelAdsKey.BAIDU_HUIHUANG_YIDUI;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.BAIDU_HUIHUANG_YIDUI;
    }

    @Override
    protected String channelName() {
        return BaiduPath.BAIDU_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return hhyiduiAdsDao;
    }

}
