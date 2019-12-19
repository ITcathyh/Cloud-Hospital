package top.itcat.bean.charge.dayknot;

import top.itcat.bean.CommonSearchReq;

public class GetDayKnotReq extends CommonSearchReq {
    private Long endTime;
    private Long startTime;

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }
}
