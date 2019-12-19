package top.itcat.bean.charge.nonmedical;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.charge.NonmedicalCharge;

public class UpdateNonmedicalChargeReq {
    private NonmedicalCharge nonmedicalCharge;

    public NonmedicalCharge getNonmedicalCharge() {
        return nonmedicalCharge;
    }

    @JsonSetter("nonmedical_charge")
    public void setNonmedicalCharge(NonmedicalCharge nonmedicalCharge) {
        this.nonmedicalCharge = nonmedicalCharge;
    }
}
