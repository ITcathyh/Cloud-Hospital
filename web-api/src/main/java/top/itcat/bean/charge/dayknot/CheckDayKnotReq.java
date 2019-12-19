package top.itcat.bean.charge.dayknot;

import com.fasterxml.jackson.annotation.JsonSetter;

public class CheckDayKnotReq {
    private Long dayKnotId;

    public Long getDayKnotId() {
        return dayKnotId;
    }

    @JsonSetter("day_knot_id")
    public void setDayKnotId(Long dayKnotId) {
        this.dayKnotId = dayKnotId;
    }
}
