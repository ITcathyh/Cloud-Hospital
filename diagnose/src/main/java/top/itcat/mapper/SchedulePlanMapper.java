package top.itcat.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.jdbc.SQL;
import org.joda.time.DateTime;
import top.itcat.entity.registrantion.SchedulePlan;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-25
 */
@CacheNamespace(implementation = top.itcat.cache.RedisCache.class)
public interface SchedulePlanMapper extends BaseMapper<SchedulePlan> {
    @Update("update schedule_plan set remain = remain - 1 where id = #{id} and remain > 0")
    public Integer decrRemain(Long id);

    @Update("update schedule_plan set remain = remain + 1 where id = #{id}")
    public Integer addRemain(Long id);

    @SelectProvider(type = DaoProvider.class, method = "selectUsingRule")
    List<SchedulePlan> selectUsingRule(Page<SchedulePlan> page,
                                       Map<String, Object> map);

    class DaoProvider {
        public String selectUsingRule(Map<String, Object> map) {
            return new SQL() {
                {
                    SELECT("sp.*");
                    FROM("schedule_plan sp, schedule_rule sr");
                    WHERE("sp.schedule_id = sr.id");
                    ORDER_BY("id desc");

                    if (map.containsKey("doctorId")) {
                        WHERE("sr.doctor_id = " + map.get("doctorId"));
                    }

                    if (map.containsKey("departId")) {
                        WHERE("sr.department_id = " + map.get("departId"));
                    }

                    if (map.containsKey("curTime")) {
                        WHERE("sp.start_time = " + new DateTime((long) map.get("curTime")).
                                withMillisOfDay(0).getMillis());
                    }

                    if (map.containsKey("startTime")) {
                        WHERE("sp.start_time >= " + new DateTime((long) map.get("startTime")).
                                withMillisOfDay(0).getMillis());
                    }

                    if (map.containsKey("endTime")) {
                        WHERE("sp.start_time <= " + new DateTime((long) map.get("endTime")).
                                withHourOfDay(23).getMillis());
                    }

                    if (!map.containsKey("needExpired") || !((boolean) map.get("needExpired"))) {
                        WHERE("sr.valid >= 1");
                        WHERE("sp.valid >= 1");
                    }

//                    WHERE("sp.valid = 1 and sr.valid > 0");
                }
            }.toString();
        }
    }
}
