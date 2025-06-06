package huihuang.proxy.ocpx.ads.gtd;

public abstract class GtdPath {


    protected abstract String baseAdsName();

    /**
     * 点击上报及转化数据回调接口
     */
    public static final String BASIC_URI = "https://service.busi.inke.cn/gtd/chl/api/click/save?";


    /**
     * 曝光链接
     */
//    public static final String EXPOSURE_URI = "https://dmp-data.vip.com/dmp/data/collector?";

}
