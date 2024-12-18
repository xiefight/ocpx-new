package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.bussiness.dao.ads.IQuannengCainiaoAdsDao;
import huihuang.proxy.ocpx.channel.oppo.OppoPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.quannenghudong.OppoQuannengHudongReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("oppoqncnChannelAds")
public class OppoQuannengCainiaoChannelAds extends OppoQuannengHudongReportFactory {

    String channelAdsKey = Constants.ChannelAdsKey.OPPO_QUANNENG_CAINIAO;

    @Autowired
    private IQuannengCainiaoAdsDao qncnAdsDao;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.OPPO_QUANNENG_CAINIAO;
    }

    /**
     * 渠道名称固定，可提取到上层
     */
    @Override
    protected String channelName() {
        return OppoPath.OPPO_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return qncnAdsDao;
    }


}
