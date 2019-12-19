package top.itcat.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import top.itcat.entity.Patient;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 患者 Mapper 接口
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-27
 */
@CacheNamespace(implementation = top.itcat.cache.RedisCache.class)
public interface PatientMapper extends BaseMapper<Patient> {
    @SelectProvider(type = DaoProvider.class, method = "selectPatient")
    List<Patient> selectPatient(Map<String, Object> map);

    class DaoProvider {
        public String selectPatient(Map<String, Object> map) {
            return new SQL() {
                {
                    SELECT("pa.*");
                    FROM("patient pa, registration regi");
                    WHERE("pa.identity_card_no = regi.identity_card_no");
                    WHERE("pa.valid = 1");
                    WHERE("regi.valid = 1");

                    if (map.containsKey("searchKey")) {
                        WHERE("(pa.patient_name = " + map.get("searchKey")
                                + " or " + "pa.identity_card_no = " + map.get("searchKey")
                                + " or " + "pa.phone = " + map.get("searchKey")
                                + " or " + "regi.medical_record_no = " + map.get("searchKey") + ")");
                    }
                }
            }.toString();
        }
    }
}
