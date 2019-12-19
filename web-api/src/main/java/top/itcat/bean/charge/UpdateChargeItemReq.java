package top.itcat.bean.charge;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.charge.ChargeItem;

public class UpdateChargeItemReq {
    private ChargeItem ChargeItem;

    public ChargeItem getChargeItem() {
        return ChargeItem;
    }

    @JsonSetter("charge_item")
    public void setChargeItem(ChargeItem ChargeItem) {
        this.ChargeItem = ChargeItem;
    }
}
