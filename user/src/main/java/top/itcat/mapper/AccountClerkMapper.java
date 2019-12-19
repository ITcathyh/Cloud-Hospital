package top.itcat.mapper;

import org.apache.ibatis.annotations.CacheNamespace;
import top.itcat.entity.AccountClerk;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 财务管理员 Mapper 接口
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-27
 */
@CacheNamespace(implementation = top.itcat.cache.RedisCache.class)
public interface AccountClerkMapper extends BaseMapper<AccountClerk> {

}
