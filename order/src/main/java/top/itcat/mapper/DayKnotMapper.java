package top.itcat.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import top.itcat.entity.DayKnot;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-29
 */
@CacheNamespace(implementation = top.itcat.cache.RedisCache.class)
public interface DayKnotMapper extends BaseMapper<DayKnot> {

}
