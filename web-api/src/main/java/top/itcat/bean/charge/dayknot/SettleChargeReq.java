package top.itcat.bean.charge.dayknot;

import com.fasterxml.jackson.annotation.JsonSetter;

public class SettleChargeReq {
    private Long endTime;

    public Long getEndTime() {
        return endTime;
    }

    @JsonSetter("end_time")
    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }
}
