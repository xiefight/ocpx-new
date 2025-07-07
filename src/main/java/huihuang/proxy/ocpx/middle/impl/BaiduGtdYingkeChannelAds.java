package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.gtd.GtdPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IGtdYingkeAdsDao;
import huihuang.proxy.ocpx.channel.baidu.BaiduPath;
import huihuang.proxy.ocpx.channel.huihuang.HuihuangChannelPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.gtd.BaiduGtdReportFactory;
import huihuang.proxy.ocpx.middle.baseadsreport.gtd.HuihuangGtdReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("bdgtdyingkeChannelAds")
public class BaiduGtdYingkeChannelAds extends BaiduGtdReportFactory {

    String channelAdsKey = Constants.ChannelAdsKey.BAIDU_GTD_YINGKE;

    @Autowired
    private IGtdYingkeAdsDao gtdYingkeAdsDao;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.BAIDU_GTD_YINGKE;
    }

    @Override
    protected String channelName() {
        return BaiduPath.BAIDU_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return gtdYingkeAdsDao;
    }

    @Override
    protected String initAdsUrl() {
        return GtdPath.BASIC_URI;
    }

}
