package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianParamField;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuangXinyuAdsDao;
import huihuang.proxy.ocpx.channel.oppo.OppoPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.huihuangmingtian.OppoHuihuangReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("oppohhxinyuChannelAds")
public class OppoHuihuangXinyuChannelAds extends OppoHuihuangReportFactory {

    String channelAdsKey = Constants.ChannelAdsKey.OPPO_HUIHUANG_XINYU;

    @Autowired
    private IHuihuangXinyuAdsDao hhxinyuAdsOppoDao;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.OPPO_HUIHUANG_XINYU;
    }

    @Override
    protected String channelName() {
        return OppoPath.OPPO_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return hhxinyuAdsOppoDao;
    }

    @Override
    protected void convertParams(Object adsObj) {
        super.convertParams(adsObj);
        HuihuangmingtianParamField huihuangmingtianParamField = (HuihuangmingtianParamField) adsObj;
        huihuangmingtianParamField.setVersion("v2");
        huihuangmingtianParamField.setIpUa("true");
    }
}
