package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.bussiness.dao.ads.IQuannengElemeAdsDao;
import huihuang.proxy.ocpx.channel.oppo.OppoPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.quannenghudong.OppoQuannengHudongReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("oppoqnelemeChannelAds")
public class OppoQuannengElemeChannelAds extends OppoQuannengHudongReportFactory {

    String channelAdsKey = Constants.ChannelAdsKey.OPPO_QUANNENG_ELEME;

    @Autowired
    private IQuannengElemeAdsDao qnelemeAdsDao;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.OPPO_QUANNENG_ELEME;
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
        return qnelemeAdsDao;
    }


}
