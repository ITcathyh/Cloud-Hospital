package top.itcat.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.jdbc.SQL;
import top.itcat.entity.ChargeItem;

import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-29
 */
//@CacheNamespace(implementation = top.itcat.cache.RedisCache.class)
public interface ChargeItemMapper extends BaseMapper<ChargeItem> {
//    class DaoProvider {
//        public String selectAmount(Map<String, Object> map) {
//            return new SQL() {
//                {
//                    SELECT("regi.*");
//                    FROM("charge_item ci");
//
//                    if (map.containsKey("doctorId")) {
//                        WHERE("ci.creator_id = " + map.get("doctorId"));
//                    }
//
//                    if (map.containsKey("departId")) {
//                        WHERE("ci.department_id = " + map.get("departId"));
//                    }
//
//                    if (map.containsKey("curTime")) {
//                        IN
//                        WHERE("regi.registration_time = " + map.get("curTime"));
//                    }
//                }
//            }.toString();
//        }
//    }
}
