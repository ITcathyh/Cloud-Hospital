package top.itcat.bean.user;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.user.PharmacyManager;

import java.util.List;

public class AddPharmacyManagersReq {
    private List<PharmacyManager> PharmacyManagers;

    public List<PharmacyManager> getPharmacyManagers() {
        return PharmacyManagers;
    }

    @JsonSetter("pharmacy_managers")
    public void setPharmacyManagers(List<PharmacyManager> PharmacyManagers) {
        this.PharmacyManagers = PharmacyManagers;
    }
}
