package top.itcat.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import top.itcat.entity.apply.Apply;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ITcathyh
 * @since 2019-06-04
 */
@CacheNamespace(implementation = top.itcat.cache.RedisCache.class)
public interface ApplyMapper extends BaseMapper<Apply> {

}
