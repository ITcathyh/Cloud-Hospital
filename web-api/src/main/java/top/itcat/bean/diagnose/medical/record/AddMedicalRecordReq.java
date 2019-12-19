package top.itcat.bean.diagnose.medical.record;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.diagnose.MedicalRecord;

public class AddMedicalRecordReq {
    private MedicalRecord bean;

    public MedicalRecord getBean() {
        return bean;
    }

    @JsonSetter("bean")
    public void setList(MedicalRecord bean) {
        this.bean = bean;
    }
}
