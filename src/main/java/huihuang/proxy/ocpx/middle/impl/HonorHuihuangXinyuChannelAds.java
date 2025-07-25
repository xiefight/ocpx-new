package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianParamField;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuangXinyuAdsDao;
import huihuang.proxy.ocpx.channel.honor.HonorPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.huihuangmingtian.HonorHuihuangReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("honorhhxinyuChannelAds")
public class HonorHuihuangXinyuChannelAds extends HonorHuihuangReportFactory {

    String channelAdsKey = Constants.ChannelAdsKey.HONOR_HUIHUANG_XINYU;

    @Autowired
    private IHuihuangXinyuAdsDao hhxinyuAdsDao;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.HONOR_HUIHUANG_XINYU;
    }

    @Override
    protected String channelName() {
        return HonorPath.HONOR_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return hhxinyuAdsDao;
    }


    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        HuihuangmingtianParamField huihuangmingtianParamField = (HuihuangmingtianParamField) super.channelParamToAdsParam(parameterMap);
        huihuangmingtianParamField.setVersion("v2");
        huihuangmingtianParamField.setIpUa("true");
        return huihuangmingtianParamField;
    }


}
