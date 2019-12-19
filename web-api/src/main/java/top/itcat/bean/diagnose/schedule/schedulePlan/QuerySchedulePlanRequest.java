package top.itcat.bean.diagnose.schedule.schedulePlan;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.bean.CommonSearchReq;

public class QuerySchedulePlanRequest extends CommonSearchReq {
    private Long startTime;
    private Long doctorId;
    private Long endTime;
    private Long departId;

    public Long getDoctorId() {
        return doctorId;
    }

    @JsonSetter("creater_id")
    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getStartTime() {
        return startTime;
    }

    @JsonSetter("start_time")
    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    @JsonSetter("end_time")
    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Long getDepartId() {
        return departId;
    }

    @JsonSetter("depart_id")
    public void setDepartId(Long departId) {
        this.departId = departId;
    }

}
