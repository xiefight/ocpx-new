package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.bussiness.dao.ads.IQuannengXueersiAdsDao;
import huihuang.proxy.ocpx.channel.honor.HonorPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.quannenghudong.HonorQuannengHudongReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("honorqnxesChannelAds")
public class HonorQuannengXueersiChannelAds extends HonorQuannengHudongReportFactory {

    String channelAdsKey = Constants.ChannelAdsKey.HONOR_QUANNENG_XUEERSI;

    @Autowired
    private IQuannengXueersiAdsDao xesAdsDao;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.HONOR_QUANNENG_XUEERSI;
    }

    @Override
    protected String channelName() {
        return HonorPath.HONOR_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return xesAdsDao;
    }


}
