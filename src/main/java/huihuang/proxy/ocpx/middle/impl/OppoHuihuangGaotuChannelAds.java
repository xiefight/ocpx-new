package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianParamField;
import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuangGaotuAdsDao;
import huihuang.proxy.ocpx.channel.oppo.OppoPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.huihuangmingtian.OppoHuihuangReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("oppohhgaotuChannelAds")
public class OppoHuihuangGaotuChannelAds extends OppoHuihuangReportFactory {

    String channelAdsKey = Constants.ChannelAdsKey.OPPO_HUIHUANG_GAOTU;

    @Autowired
    private IHuihuangGaotuAdsDao hhgaotuAdsOppoDao;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.OPPO_HUIHUANG_GAOTU;
    }

    @Override
    protected String channelName() {
        return OppoPath.OPPO_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return hhgaotuAdsOppoDao;
    }

    @Override
    protected void convertParams(Object adsObj) {
        super.convertParams(adsObj);
        HuihuangmingtianParamField huihuangmingtianParamField = (HuihuangmingtianParamField) adsObj;
        huihuangmingtianParamField.setVersion("v2");
    }


    @Override
    protected String initAdsUrl() {
        return null;
    }

    @Override
    protected String initAdsUrl(Object adsObj) {
        return HuihuangmingtianPath.BASIC_URI + "paramType=0";
    }
}
