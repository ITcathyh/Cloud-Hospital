package top.itcat.bean.diagnose.apply.group;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.diagnose.ApplyGroup;

import java.util.List;


public class AddApplyGroupsReq {
    private List<ApplyGroup> list;

    public List<ApplyGroup> getList() {
        return list;
    }

    @JsonSetter("list")
    public void setList(List<ApplyGroup> list) {
        this.list = list;
    }
}
