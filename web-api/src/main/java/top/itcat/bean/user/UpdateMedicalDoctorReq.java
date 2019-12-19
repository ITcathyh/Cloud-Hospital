package top.itcat.bean.user;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.user.MedicalDoctor;

public class UpdateMedicalDoctorReq {
    private MedicalDoctor doctor;

    public MedicalDoctor getDoctor() {
        return doctor;
    }

    @JsonSetter("doctor")
    public void setDoctor(MedicalDoctor doctor) {
        this.doctor = doctor;
    }
}
