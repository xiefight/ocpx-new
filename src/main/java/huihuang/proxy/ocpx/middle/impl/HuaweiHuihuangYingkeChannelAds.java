package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianParamField;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuangYingkeAdsDao;
import huihuang.proxy.ocpx.channel.huawei.HuaweiPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.huihuangmingtian.HuaweiHuihuangReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("hwhhykChannelAds")
public class HuaweiHuihuangYingkeChannelAds extends HuaweiHuihuangReportFactory {

    String channelAdsKey = Constants.ChannelAdsKey.HUAWEI_HUIHUANG_YINGKE;

    @Autowired
    private IHuihuangYingkeAdsDao hhykAdsDao;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.HUAWEI_HUIHUANG_YINGKE;
    }

    @Override
    protected String channelName() {
        return HuaweiPath.HUAWEI_CHANNEL_NAME;
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
