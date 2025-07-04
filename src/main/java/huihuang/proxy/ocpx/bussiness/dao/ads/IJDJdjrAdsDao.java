package huihuang.proxy.ocpx.bussiness.dao.ads;

import huihuang.proxy.ocpx.ads.jd.JDAdsDTO;
import huihuang.proxy.ocpx.ads.quannenghudong.QuannengHudongAdsDTO;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IJDJdjrAdsDao extends IMarkDao {

    /**
     * 新增一条监测记录
     */
    int insert(JDAdsDTO jdAdsDTO);

    /**
     * 更新监测记录
     */
    int update(JDAdsDTO jdAdsDTO);

    /**
     * 根据id查询
     */
    JDAdsDTO queryJDJdjrAdsById(Integer id);

}
