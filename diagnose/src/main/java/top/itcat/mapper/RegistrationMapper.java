package top.itcat.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.joda.time.DateTime;
import top.itcat.entity.registrantion.Registration;
import top.itcat.rpc.service.model.RegistrationStatusEnum;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 挂号 Mapper 接口
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-28
 */
@CacheNamespace(implementation = top.itcat.cache.RedisCache.class)
public interface RegistrationMapper extends BaseMapper<Registration> {
    @SelectProvider(type = DaoProvider.class, method = "selectUsingDoctorOrDepart")
    List<Registration> selectUsingDoctorOrDepart(Page<Registration> page,
                                                 Map<String, Object> map);

    @SelectProvider(type = DaoProvider.class, method = "selectRegistrationCount")
    Integer selectRegistrationCount(Map<String, Object> map);

    class DaoProvider {
        public String selectUsingDoctorOrDepart(Map<String, Object> map) {
            return new SQL() {
                {
                    SELECT("regi.*");
                    FROM("schedule_plan sp, schedule_rule sr, registration regi");
                    WHERE("sp.schedule_id = sr.id");
                    WHERE("regi.schedule_plan_id = sp.id");

                    if (map.containsKey("doctorId")) {
                        WHERE("sr.doctor_id = " + map.get("doctorId"));
                    }

                    if (map.containsKey("departId")) {
                        WHERE("sr.department_id = " + map.get("departId"));
                    }

                    if (map.containsKey("curTime")) {
                        long beginTime = new DateTime(map.get("curTime")).withMillisOfDay(0).getMillis();
                        WHERE("regi.registration_time >= " + beginTime);
                        WHERE("regi.registration_time <= " + map.get("curTime"));
                    }

                    WHERE("sp.valid = 1");
                    WHERE("sr.valid >= 1");
                    WHERE("regi.valid = 1");
                    ORDER_BY("id desc");
                }
            }.toString();
        }

        public String selectRegistrationCount(Map<String, Object> map) {
            return new SQL() {
                {
                    SELECT("count(*)");
                    FROM("schedule_plan sp, schedule_rule sr, registration regi");
                    WHERE("sp.schedule_id = sr.id");
                    WHERE("regi.schedule_plan_id = sp.id");
                    WHERE("regi.status = " + RegistrationStatusEnum.Done.getValue());

                    if (map.containsKey("doctorId")) {
                        WHERE("sr.doctor_id = " + map.get("doctorId"));
                    }

                    if (map.containsKey("departId")) {
                        WHERE("sr.department_id = " + map.get("departId"));
                    }

                    if (map.containsKey("startTime")) {
                        WHERE("regi.registration_time >= " + map.get("startTime"));
                    }

                    if (map.containsKey("endTime")) {
                        WHERE("regi.registration_time <= " + map.get("endTime"));
                    }

                    WHERE("sp.valid = 1");
                    WHERE("sr.valid >= 1");
                    WHERE("regi.valid = 1");
                }
            }.toString();
        }
    }
}
