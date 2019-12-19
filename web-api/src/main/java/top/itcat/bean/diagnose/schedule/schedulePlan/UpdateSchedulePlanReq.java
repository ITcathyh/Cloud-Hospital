package top.itcat.bean.diagnose.schedule.schedulePlan;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.diagnose.SchedulePlan;

public class UpdateSchedulePlanReq {
    private SchedulePlan schedulePlan;

    public SchedulePlan getSchedulePlan() {
        return schedulePlan;
    }

    @JsonSetter("plan")
    public void setSchedulePlan(SchedulePlan schedulePlan) {
        this.schedulePlan = schedulePlan;
    }
}