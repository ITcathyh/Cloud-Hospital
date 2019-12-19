package top.itcat.bean.user;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.user.MedicalDoctor;

import java.util.List;

public class AddMedicalDoctorsReq {
    private List<MedicalDoctor> medicalDoctors;

    public List<MedicalDoctor> getMedicalDoctors() {
        return medicalDoctors;
    }

    @JsonSetter("doctors")
    public void setMedicalDoctors(List<MedicalDoctor> medicalDoctors) {
        this.medicalDoctors = medicalDoctors;
    }
}
