package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.bussiness.dao.ads.IQuannengYoukuAdsDao;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.quannenghudong.BaiduQuannengHudongReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("bdqnykChannelAds")
public class BaiduQuannengYoukuChannelAds extends BaiduQuannengHudongReportFactory {

    String channelAdsKey = Constants.ChannelAdsKey.BAIDU_QUANNENG_YOUKU;

    @Autowired
    private IQuannengYoukuAdsDao qnykAdsDao;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.BAIDU_QUANNENG_YOUKU;
    }

    @Override
    protected IMarkDao adsDao() {
        return qnykAdsDao;
    }


}
