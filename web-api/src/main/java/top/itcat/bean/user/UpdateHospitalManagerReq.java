package top.itcat.bean.user;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.user.HospitalManager;

public class UpdateHospitalManagerReq {
    private HospitalManager hospitalManager;

    public HospitalManager getHospitalManager() {
        return hospitalManager;
    }

    @JsonSetter("hospital_manager")
    public void setHospitalManager(HospitalManager hospitalManager) {
        this.hospitalManager = hospitalManager;
    }
}
