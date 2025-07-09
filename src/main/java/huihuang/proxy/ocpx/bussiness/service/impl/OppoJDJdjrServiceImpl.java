package huihuang.proxy.ocpx.bussiness.service.impl;

import cn.hutool.core.util.StrUtil;
import huihuang.proxy.ocpx.ads.huihui.HuihuiEventTypeEnum;
import huihuang.proxy.ocpx.ads.jd.JDAdsDTO;
import huihuang.proxy.ocpx.ads.jd.JDEventTypeEnum;
import huihuang.proxy.ocpx.ads.jd.jdjr.JDJdjrPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IJDJdjrAdsDao;
import huihuang.proxy.ocpx.bussiness.service.BaseServiceInner;
import huihuang.proxy.ocpx.bussiness.service.IChannelAdsService;
import huihuang.proxy.ocpx.bussiness.service.basechannel.OppoChannelFactory;
import huihuang.proxy.ocpx.bussiness.service.basechannel.vo.Ads2OppoVO;
import huihuang.proxy.ocpx.channel.oppo.OppoCallbackDTO;
import huihuang.proxy.ocpx.channel.oppo.OppoPath;
import huihuang.proxy.ocpx.common.BasicResult;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.middle.IChannelAds;
import huihuang.proxy.ocpx.middle.factory.ChannelAdsFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service("oppojdjdjrService")
public class OppoJDJdjrServiceImpl extends OppoChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(OppoJDJdjrServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private IJDJdjrAdsDao jdjrAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private JDJdjrPath jdjdjrPath;

    String channelAdsKey = Constants.ChannelAdsKey.OPPO_JD_JDJR;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        //转化类型字段
        String eventType = parameterMap.get("event_type")[0];
        logger.info("adsCallBack {} 开始回调渠道  id:{}  eventType:{}", channelAdsKey, id, eventType);
        //根据id查询对应的点击记录
        JDAdsDTO jdAdsDTO = jdjrAdsDao.queryJDJdjrAdsById(id);

        if (null == jdAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }

        String pkg = OppoPath.OPPO_JD_JDJR_PKG;


        long currentTime = System.currentTimeMillis();
        Ads2OppoVO oppoVO = new Ads2OppoVO();
//        if (StrUtil.isNotEmpty(jdAdsDTO.getImei())) {
//            oppoVO.setImei(encode(jdAdsDTO.getImeiMd5().getBytes(StandardCharsets.UTF_8)));
//        }
        if (StrUtil.isNotEmpty(jdAdsDTO.getOaId())) {
            oppoVO.setOuId(encode(jdAdsDTO.getOaId().getBytes(StandardCharsets.UTF_8)));
        }
//        oppoVO.setImei(jdAdsDTO.getImei());
        oppoVO.setAdsId(id);
        oppoVO.setAdsName(jdjdjrPath.baseAdsName());
        oppoVO.setChannel(1);
        oppoVO.setTimestamp(currentTime);
        oppoVO.setPkg(pkg);
        oppoVO.setDataType(JDEventTypeEnum.JdOppoEventTypeMap.get(eventType).getCode());
        oppoVO.setAscribeType(0);
        oppoVO.setAdId(OppoPath.HUIHUI_MOMO_ADID);
        if (StrUtil.isNotEmpty(jdAdsDTO.getOaId())) {
            oppoVO.setOuId(encode(jdAdsDTO.getOaId().getBytes(StandardCharsets.UTF_8)));
        }
//        oppoVO.setAdId(Long.valueOf(jdAdsDTO.getAdid()));
        logger.info("adsCallBack {} 组装调用渠道参数:{}", channelAdsKey, oppoVO);

        Response response = baseAdsCallBack(oppoVO);
        OppoCallbackDTO data = (OppoCallbackDTO) response.getData();

        //更新回调状态
        JDAdsDTO jdAds = new JDAdsDTO();
        jdAds.setId(id);
        jdAds.setCallBackTime(String.valueOf(System.currentTimeMillis()));
        if (response.getCode() == 0) {
            jdAds.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(jdAds, jdjrAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, data);
            return BasicResult.getSuccessResponse(data.getId());
        } else {
            jdAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(jdAds, jdjrAdsDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, data);
            return BasicResult.getFailResponse(data.getCallBackMes());
        }
    }


}
