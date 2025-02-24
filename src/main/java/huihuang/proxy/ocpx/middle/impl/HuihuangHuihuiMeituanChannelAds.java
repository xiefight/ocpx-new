package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianParamField;
import huihuang.proxy.ocpx.ads.huihuangmingtian.ads.HuihuangHuihuiMeituanPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuangHuihuiMeituanAdsDao;
import huihuang.proxy.ocpx.channel.huihuang.HuihuangChannelPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.huihuangmingtian.HuihuangHuihuangReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("hhhhmtChannelAds")
public class HuihuangHuihuiMeituanChannelAds extends HuihuangHuihuangReportFactory {

    @Autowired
    private IHuihuangHuihuiMeituanAdsDao hhhhmtAdsDao;

    String channelAdsKey = Constants.ChannelAdsKey.HUIHUANG_HUIHUI_MEITUAN;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.HUIHUANG_HUIHUI_MEITUAN;
    }

    @Override
    protected String channelName() {
        return HuihuangChannelPath.CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return hhhhmtAdsDao;
    }


    /*@Override
    protected String initAdsUrl() {
        return HuihuangHuihuiMeituanPath.BASIC_URI;
    }*/

    /*@Override
    protected String initAdsUrl(Object adsObj) {
        HuihuangmingtianParamField hhmtParamField = (HuihuangmingtianParamField) adsObj;
        String monitorType = hhmtParamField.getMonitorType();
        if ("0".equals(monitorType)) {
            //曝光监测
            return HuihuangHuihuiMeituanPath.EXPOSURE_URI;
        } else {
            //点击监测
            return HuihuangHuihuiMeituanPath.BASIC_URI;
        }
    }*/


    @Override
    protected void convertParams(Object adsObj) {
        super.convertParams(adsObj);
        HuihuangmingtianParamField hhmtParamField = (HuihuangmingtianParamField) adsObj;
        if (hhmtParamField.getAccount_id() == null) {
            hhmtParamField.setAccount_id("hhhhmt01");
        }
        logger.info("clickReport {} 特殊参数进行转换 convertParams:{}", channelAdsKey(), hhmtParamField);
    }

}
