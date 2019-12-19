package top.itcat.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import top.itcat.entity.Doctor;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 医生 Mapper 接口
 * </p>
 *
 * @author ITcathyh
 * @since 2019-06-14
 */
@CacheNamespace(implementation = top.itcat.cache.RedisCache.class)
public interface DoctorMapper extends BaseMapper<Doctor> {
    @SelectProvider(type = DoctorMapper.OutpatientDoctorDaoProvider.class, method = "selectOutpatientDoctorIds")
    List<Long> selectOutpatientDoctorIds(Map<String, Object> map);
    @SelectProvider(type = DoctorMapper.MedicalDoctorDaoProvider.class, method = "selectMedicalDoctorIds")
    List<Long> selectMedicalDoctorIds(Map<String, Object> map);

    class OutpatientDoctorDaoProvider {
        public String selectOutpatientDoctorIds(Map<String, Object> map) {
            return new SQL() {
                {
                    SELECT("id");
                    FROM("doctor");
                    WHERE("role = 0 and valid = 1");

                    if (map.containsKey("departId")) {
                        WHERE("department_id = " + map.get("departId"));
                    }
                }
            }.toString();
        }
    }

    class MedicalDoctorDaoProvider {
        public String selectMedicalDoctorIds(Map<String, Object> map) {
            return new SQL() {
                {
                    SELECT("id");
                    FROM("doctor");
                    WHERE("role = 1 and valid = 1");

                    if (map.containsKey("departId")) {
                        WHERE("department_id = " + map.get("departId"));
                    }
                }
            }.toString();
        }
    }
}
