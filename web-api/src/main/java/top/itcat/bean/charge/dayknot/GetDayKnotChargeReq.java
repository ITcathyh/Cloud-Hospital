package top.itcat.bean.charge.dayknot;

import top.itcat.bean.CommonSearchReq;

public class GetDayKnotChargeReq extends CommonSearchReq {
    private Long endTime;

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }
}
