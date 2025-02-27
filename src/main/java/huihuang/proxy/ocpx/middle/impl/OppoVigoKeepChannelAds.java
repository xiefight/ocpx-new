package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.bussiness.dao.ads.IVigoKeepAdsDao;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.vigo.OppoVigoReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("oppovigokeepChannelAds")
public class OppoVigoKeepChannelAds extends OppoVigoReportFactory {

    String channelAdsKey = Constants.ChannelAdsKey.OPPO_VIGO_KEEP;

    @Autowired
    private IVigoKeepAdsDao vigoAdsDao;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.OPPO_VIGO_KEEP;
    }

    @Override
    protected IMarkDao adsDao() {
        return vigoAdsDao;
    }


}
