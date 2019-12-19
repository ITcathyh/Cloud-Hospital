package top.itcat.bean.charge.statistics;

import lombok.Data;

import java.util.List;

@Data
public class GetDoctorStatisticsReq {
    private List<Long> ids;
    private Long departmentId;
    private Boolean all;
    private Long startTime;
    private Long endTime;
}
