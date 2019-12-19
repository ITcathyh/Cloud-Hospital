package top.itcat.mapper;

import org.apache.ibatis.annotations.CacheNamespace;
import top.itcat.entity.HospitalManager;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 医院管理员 Mapper 接口
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-27
 */
@CacheNamespace(implementation = top.itcat.cache.RedisCache.class)
public interface HospitalManagerMapper extends BaseMapper<HospitalManager> {

}
