package huihuang.proxy.ocpx.bussiness.dao.ads;

import huihuang.proxy.ocpx.ads.hongyu.HongyuAdsDTO;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IHongyuKuaikanmanhuaAdsDao extends IMarkDao {

    /**
     * 新增一条监测记录
     */
    int insert(HongyuAdsDTO hongyuAdsDTO);

    /**
     * 更新监测记录
     */
    int update(HongyuAdsDTO hongyuAdsDTO);

    /**
     * 根据id查询
     */
    HongyuAdsDTO queryHongyuKuaikanmanhuaAdsById(Integer id);

}
