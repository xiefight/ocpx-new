package huihuang.proxy.ocpx.bussiness.dao.ads;

import huihuang.proxy.ocpx.ads.gtd.GtdAdsDTO;
import huihuang.proxy.ocpx.ads.weibo.WeiboAdsDTO;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IGtdYingkeAdsDao extends IMarkDao {

    /**
     * 新增一条监测记录
     */
    int insert(GtdAdsDTO gtdAdsDTO);

    /**
     * 更新监测记录
     */
    int update(GtdAdsDTO gtdAdsDTO);

    /**
     * 根据id查询
     */
    GtdAdsDTO queryGtdYingkeAdsById(Integer id);

}
