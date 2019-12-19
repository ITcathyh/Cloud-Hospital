package top.itcat.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import top.itcat.entity.TollCollector;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import top.itcat.rpc.service.diagnose.GetSchedulePlanRequest;

import java.util.List;

/**
 * <p>
 * 挂号收费员 Mapper 接口
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-27
 */
@CacheNamespace(implementation = top.itcat.cache.RedisCache.class)
public interface TollCollectorMapper extends BaseMapper<TollCollector> {
}
