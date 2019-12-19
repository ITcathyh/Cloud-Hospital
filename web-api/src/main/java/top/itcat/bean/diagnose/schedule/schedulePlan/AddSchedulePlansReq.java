package top.itcat.bean.diagnose.schedule.schedulePlan;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.diagnose.SchedulePlan;

import java.util.List;

public class AddSchedulePlansReq {
    private List<SchedulePlan> list;

    public List<SchedulePlan> getList() {
        return list;
    }

    @JsonSetter("list")
    public void setList(List<SchedulePlan> list) {
        this.list = list;
    }
}

