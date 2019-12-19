package top.itcat.bean.charge;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.charge.BillingCategory;

import java.util.List;

public class AddBillingCategorysReq {
    private List<BillingCategory> list;

    public List<BillingCategory> getList() {
        return list;
    }

    @JsonSetter("list")
    public void setList(List<BillingCategory> list) {
        this.list = list;
    }
}
