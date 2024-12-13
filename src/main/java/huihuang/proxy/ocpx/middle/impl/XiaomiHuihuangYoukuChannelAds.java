package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianParamField;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuangYoukuAdsDao;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.huihuangmingtian.XiaomiHuihuangReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("xhhykChannelAds")
public class XiaomiHuihuangYoukuChannelAds extends XiaomiHuihuangReportFactory {

    String channelAdsKey = Constants.ChannelAdsKey.XIAOMI_HUIHUANG_YOUKU;

    @Autowired
    private IHuihuangYoukuAdsDao hhykAdsDao;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.XIAOMI_HUIHUANG_YOUKU;
    }

    @Override
    protected String channelName() {
        return XiaomiPath.XIAOMI_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return hhykAdsDao;
    }

    @Override
    protected void convertParams(Object adsObj) {
        super.convertParams(adsObj);
        HuihuangmingtianParamField huihuangmingtianParamField = (HuihuangmingtianParamField) adsObj;
        if ("0".equals(huihuangmingtianParamField.getOs())){
            huihuangmingtianParamField.setOs("2");
        }
    }

}
