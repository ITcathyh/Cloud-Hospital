package top.itcat.bean.diagnose.apply.group;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.diagnose.ApplyGroup;

public class UpdateApplyGroupReq {
    private ApplyGroup applyGroup;

    public ApplyGroup getApplyGroup() {
        return applyGroup;
    }

    @JsonSetter("apply_group")
    public void setList(ApplyGroup applyGroup) {
        this.applyGroup = applyGroup;
    }
}