package top.itcat.bean.user;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.user.OutpatientDoctor;

public class UpdateOutpatientDoctorReq {
    private OutpatientDoctor doctor;

    public OutpatientDoctor getDoctor() {
        return doctor;
    }

    @JsonSetter("doctor")
    public void setDoctor(OutpatientDoctor doctor) {
        this.doctor = doctor;
    }
}
