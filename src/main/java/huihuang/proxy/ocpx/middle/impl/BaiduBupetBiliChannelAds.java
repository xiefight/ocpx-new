package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.bupet.BupetParamField;
import huihuang.proxy.ocpx.ads.bupet.bili.BupetBiliPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IBupetBiliAdsDao;
import huihuang.proxy.ocpx.channel.baidu.BaiduPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.buget.BaiduBupetReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("bdbupetbiliChannelAds")
public class BaiduBupetBiliChannelAds extends BaiduBupetReportFactory {

    @Autowired
    private IBupetBiliAdsDao bupetBiliAdsDao;

    String channelAdsKey = Constants.ChannelAdsKey.BAIDU_BUPET_BILI;


    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.BAIDU_BUPET_BILI;
    }

    @Override
    protected String channelName() {
        return BaiduPath.BAIDU_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return bupetBiliAdsDao;
    }

    @Override
    protected String initAdsUrl() {
        return null;
    }

    @Override
    protected String initAdsUrl(Object adsObj) {
        BupetParamField bupetParamField = (BupetParamField) adsObj;
        String accountId = bupetParamField.getAccountId();
        if (BaiduPath.BAIDU_BUPET_BILI_ACCOUNT_02.equals(accountId)
          || BaiduPath.BAIDU_BUPET_BILI_ACCOUNT_03.equals(accountId)) {
            return BupetBiliPath.BASIC_URI_2;
        } else {
            //点击监测
            return BupetBiliPath.BASIC_URI;
        }
    }

}
