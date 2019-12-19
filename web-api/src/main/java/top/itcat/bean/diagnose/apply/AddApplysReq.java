package top.itcat.bean.diagnose.apply;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.diagnose.Apply;

import java.util.List;

;

public class AddApplysReq {
    private List<Apply> list;

    public List<Apply> getList() {
        return list;
    }

    @JsonSetter("list")
    public void setList(List<Apply> list) {
        this.list = list;
    }
}
