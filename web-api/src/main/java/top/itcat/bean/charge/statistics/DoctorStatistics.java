package top.itcat.bean.charge.statistics;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import top.itcat.rpc.service.model.User;

import java.util.List;

@Data
public class DoctorStatistics {
    @JSONField(name = "registration_count")
    private Integer registrationCount;
    @JSONField(name = "statistics_detail")
    private List<StatisticsDetail> statisticsDetail;
    @JSONField(name = "doctor_info")
    private User doctorInfo;
}
