package top.itcat.bean.charge.nonmedical;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.charge.NonmedicalCharge;

import java.util.List;

public class AddNonmedicalChargesReq {
    private List<NonmedicalCharge> list;

    public List<NonmedicalCharge> getList() {
        return list;
    }

    @JsonSetter("nonmedical_charges")
    public void setList(List<NonmedicalCharge> list) {
        this.list = list;
    }
}
