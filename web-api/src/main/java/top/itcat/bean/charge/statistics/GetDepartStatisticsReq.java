package top.itcat.bean.charge.statistics;

import lombok.Data;

import java.util.List;

@Data
public class GetDepartStatisticsReq {
    private List<Long> ids;
    private Boolean all;
    private Long startTime;
    private Long endTime;
}


