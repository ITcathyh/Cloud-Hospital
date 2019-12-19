package top.itcat.bean.diagnose.apply;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.diagnose.Apply;

public class UpdateApplyReq {
    private Apply apply;

    public Apply getApply() {
        return apply;
    }

    @JsonSetter("apply")
    public void setList(Apply apply) {
        this.apply = apply;
    }
}
