package top.itcat.bean.user;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.user.HospitalManager;

import java.util.List;

public class AddHospitalManagersReq {
    private List<HospitalManager> HospitalManagers;

    public List<HospitalManager> getHospitalManagers() {
        return HospitalManagers;
    }

    @JsonSetter("hospital_managers")
    public void setHospitalManagers(List<HospitalManager> HospitalManagers) {
        this.HospitalManagers = HospitalManagers;
    }

}
