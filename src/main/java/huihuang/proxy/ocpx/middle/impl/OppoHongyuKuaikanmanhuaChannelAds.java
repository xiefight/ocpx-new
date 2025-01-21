package huihuang.proxy.ocpx.middle.impl;

import cn.hutool.crypto.digest.MD5;
import huihuang.proxy.ocpx.ads.hongyu.HongyuParamField;
import huihuang.proxy.ocpx.ads.quannenghudong.QuannengHudongParamEnum;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHongyuKuaikanmanhuaAdsDao;
import huihuang.proxy.ocpx.channel.oppo.OppoPath;
import huihuang.proxy.ocpx.common.BasicResult;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.hongyu.OppoHongyuReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Component
public class OppoHongyuKuaikanmanhuaChannelAds extends OppoHongyuReportFactory {

    @Autowired
    private IHongyuKuaikanmanhuaAdsDao hykkmhAdsDao;

    String channelAdsKey = Constants.ChannelAdsKey.OPPO_HONGYU_KUAIKANMANHUA;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.OPPO_HONGYU_KUAIKANMANHUA;
    }

    @Override
    protected String channelName() {
        return OppoPath.OPPO_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return hykkmhAdsDao;
    }


    @Override
    protected void convertParams(Object adsObj) {
        super.convertParams(adsObj);
        HongyuParamField hongyuParamField = (HongyuParamField) adsObj;
        if (null != hongyuParamField.getOaid()) {
            hongyuParamField.setOaid_md5(MD5.create().digestHex(hongyuParamField.getOaid(), StandardCharsets.UTF_8));
        }

        logger.info("oppo clickReport {} 特殊参数进行转换 convertParams:{}", channelAdsKey(), hongyuParamField);
    }

    @Override
    protected Response judgeParams(Object adsObj) {
        return BasicResult.getSuccessResponse();
    }


}
