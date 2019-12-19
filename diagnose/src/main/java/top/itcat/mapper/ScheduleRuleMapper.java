package top.itcat.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import top.itcat.entity.registrantion.ScheduleRule;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-24
 */
@CacheNamespace(implementation = top.itcat.cache.RedisCache.class)
public interface ScheduleRuleMapper extends BaseMapper<ScheduleRule> {
}
