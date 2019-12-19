package top.itcat.bean.diagnose.schedule.scheduleRule;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.diagnose.ScheduleRule;

import java.util.List;

public class AddScheduleRulesReq {
    private List<ScheduleRule> list;

    public List<ScheduleRule> getList() {
        return list;
    }

    @JsonSetter("list")
    public void setList(List<ScheduleRule> list) {
        this.list = list;
    }
}

