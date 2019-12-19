package top.itcat.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import top.itcat.entity.MedicalRecord;
import top.itcat.exception.CommonException;
import top.itcat.exception.InvalidParamException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ITcathyh
 * @since 2019-06-03
 */

public interface MedicalRecordMapper extends BaseMapper<MedicalRecord> {
    @SelectProvider(type = DaoProvider.class, method = "selectUsingIdNum")
    List<MedicalRecord> selectUsingIdNum(Page<MedicalRecord> page,
                                         Map<String, Object> map);

    class DaoProvider {
        public String selectUsingIdNum(Map<String, Object> map) {
            return new SQL() {
                {
                    SELECT("mr.*");
                    FROM("medical_record mr, registration regi");
                    WHERE("mr.medical_record_no = regi.medical_record_no");
                    WHERE("mr.valid = 1 and regi.valid = 1");

                    if (map.containsKey("medicalRecordNo")) {
                        WHERE("mr.medical_record_no = " + map.get("medicalRecordNo"));
                    }

                    if (map.containsKey("idNum")) {
                        WHERE("regi.identity_card_no = " + map.get("idNum"));
                    }

                    // todo 筛选科室
//                    if (map.containsKey("departmentId")) {
//                        WHERE("regi.identity_card_no = " + map.get("idNum"));
//                    }
                }
            }.toString();
        }
    }
}
