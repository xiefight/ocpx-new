package huihuang.proxy.ocpx.bussiness.dao.ads;

import huihuang.proxy.ocpx.ads.hongyu.HongyuAdsDTO;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IHongyuQiduAdsDao extends IMarkDao {

    /**
     * 新增一条监测记录
     */
    int insert(HongyuAdsDTO qiduAdsDTO);

    /**
     * 更新监测记录
     */
    int update(HongyuAdsDTO qiduAdsDTO);

    /**
     * 根据id查询
     */
    HongyuAdsDTO queryHongyuQiduAdsById(Integer id);

}
