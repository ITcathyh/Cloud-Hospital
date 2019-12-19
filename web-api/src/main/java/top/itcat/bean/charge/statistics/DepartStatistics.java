package top.itcat.bean.charge.statistics;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import top.itcat.entity.hospital.Department;

import java.util.List;

@Data
public class DepartStatistics {
    @JSONField(name = "registration_count")
    private Integer registrationCount;
    @JSONField(name = "statistics_detail")
    private List<StatisticsDetail> statisticsDetail;
    private Department department;
}
