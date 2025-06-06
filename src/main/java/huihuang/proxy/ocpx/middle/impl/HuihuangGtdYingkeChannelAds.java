package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.gtd.GtdParamField;
import huihuang.proxy.ocpx.ads.gtd.GtdPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IGtdYingkeAdsDao;
import huihuang.proxy.ocpx.channel.huihuang.HuihuangChannelPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.gtd.HuihuangGtdReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("hhgtdyingkeChannelAds")
public class HuihuangGtdYingkeChannelAds extends HuihuangGtdReportFactory {

    String channelAdsKey = Constants.ChannelAdsKey.HUIHUANG_GTD_YINGKE;

    @Autowired
    private IGtdYingkeAdsDao gtdYingkeAdsDao;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.HUIHUANG_GTD_YINGKE;
    }

    @Override
    protected String channelName() {
        return HuihuangChannelPath.CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return gtdYingkeAdsDao;
    }

    @Override
    protected String initAdsUrl() {
        return GtdPath.BASIC_URI;
    }

    @Override
    protected void convertParams(Object adsObj) {
        super.convertParams(adsObj);
        GtdParamField weiboParamField = (GtdParamField) adsObj;
        if (weiboParamField.getAccount_id() == null){
            weiboParamField.setAccount_id("hhgtdyingke01");
        }
        logger.info("clickReport {} 特殊参数进行转换 convertParams:{}", channelAdsKey(), weiboParamField);
    }

}
