package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.gtd.GtdPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IGtdYingkeAdsDao;
import huihuang.proxy.ocpx.channel.huawei.HuaweiPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.gtd.HuaweiGtdReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("hwgtdyingkeChannelAds")
public class HuaweiGtdYingkeChannelAds extends HuaweiGtdReportFactory {

    String channelAdsKey = Constants.ChannelAdsKey.HUAWEI_GTD_YINGKE;

    @Autowired
    private IGtdYingkeAdsDao gtdYingkeAdsDao;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.HUAWEI_GTD_YINGKE;
    }

    @Override
    protected String channelName() {
        return HuaweiPath.HUAWEI_CHANNEL_NAME;
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
