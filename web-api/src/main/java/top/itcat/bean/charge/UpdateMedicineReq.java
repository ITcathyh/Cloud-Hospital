package top.itcat.bean.charge;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.medical.Medicine;

public class UpdateMedicineReq {
    private Medicine medicine;

    public Medicine getMedicine() {
        return medicine;
    }

    @JsonSetter("bean")
    public void setList(Medicine medicine) {
        this.medicine = medicine;
    }
}