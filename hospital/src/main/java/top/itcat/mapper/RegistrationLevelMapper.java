package top.itcat.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import top.itcat.entity.RegistrationLevel;

/**
 * <p>
 * 挂号级别 Mapper 接口
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-28
 */
@CacheNamespace(implementation = top.itcat.cache.RedisCache.class)
public interface RegistrationLevelMapper extends BaseMapper<RegistrationLevel> {

}
