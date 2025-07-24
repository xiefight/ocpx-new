package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.gtd.GtdPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IGtdYingkeAdsDao;
import huihuang.proxy.ocpx.channel.oppo.OppoPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.gtd.OppoGtdReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("oppogtdyingkeChannelAds")
public class OppoGtdYingkeChannelAds extends OppoGtdReportFactory {

    String channelAdsKey = Constants.ChannelAdsKey.OPPO_GTD_YINGKE;

    @Autowired
    private IGtdYingkeAdsDao gtdYingkeAdsDao;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.OPPO_GTD_YINGKE;
    }

    @Override
    protected String channelName() {
        return OppoPath.OPPO_CHANNEL_NAME;
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
