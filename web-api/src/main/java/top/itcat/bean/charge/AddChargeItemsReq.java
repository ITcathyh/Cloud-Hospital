package top.itcat.bean.charge;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.charge.ChargeItem;

import java.util.List;

public class AddChargeItemsReq {
    private List<ChargeItem> list;

    public List<ChargeItem> getList() {
        return list;
    }

    @JsonSetter("charge_items")
    public void setList(List<ChargeItem> list) {
        this.list = list;
    }
}
