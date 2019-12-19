package top.itcat.bean.diagnose.schedule.scheduleRule;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.diagnose.ScheduleRule;

public class UpdateScheduleRuleReq {
    private ScheduleRule scheduleRule;

    public ScheduleRule getScheduleRule() {
        return scheduleRule;
    }

    @JsonSetter("rule")
    public void setScheduleRule(ScheduleRule scheduleRule) {
        this.scheduleRule = scheduleRule;
    }
}

