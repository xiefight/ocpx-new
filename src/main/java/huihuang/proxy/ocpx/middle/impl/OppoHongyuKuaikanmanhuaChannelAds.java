package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.bussiness.dao.ads.IHongyuKuaikanmanhuaAdsDao;
import huihuang.proxy.ocpx.channel.oppo.OppoPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.hongyu.OppoHongyuReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OppoHongyuKuaikanmanhuaChannelAds extends OppoHongyuReportFactory {

    @Autowired
    private IHongyuKuaikanmanhuaAdsDao hykkmhAdsDao;

    String channelAdsKey = Constants.ChannelAdsKey.OPPO_HONGYU_KUAIKANMANHUA;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.OPPO_HONGYU_KUAIKANMANHUA;
    }

    @Override
    protected String channelName() {
        return OppoPath.OPPO_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return hykkmhAdsDao;
    }


}
