package top.itcat.bean.user;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.user.PharmacyManager;

public class UpdatePharmacyManagerReq {
    private PharmacyManager pharmacyManager;

    public PharmacyManager getPharmacyManager() {
        return pharmacyManager;
    }

    @JsonSetter("pharmacy_manager")
    public void setPharmacyManager(PharmacyManager pharmacyManager) {
        this.pharmacyManager = pharmacyManager;
    }
}
