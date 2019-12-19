package top.itcat.bean.charge;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.charge.ChargeSubject;

import java.util.List;

public class AddChargeSubjectsReq {
    private List<ChargeSubject> list;

    public List<ChargeSubject> getList() {
        return list;
    }

    @JsonSetter("list")
    public void setList(List<ChargeSubject> list) {
        this.list = list;
    }
}
