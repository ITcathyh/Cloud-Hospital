package top.itcat.bean.charge;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.charge.ChargeSubject;

public class UpdateChargeSubjectReq {
    private ChargeSubject ChargeSubject;

    public top.itcat.entity.charge.ChargeSubject getChargeSubject() {
        return ChargeSubject;
    }

    @JsonSetter("bean")
    public void setChargeSubject(ChargeSubject ChargeSubject) {
        this.ChargeSubject = ChargeSubject;
    }
}
