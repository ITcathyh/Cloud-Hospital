package top.itcat.bean.medical;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.medical.CommonlyUsedMedicine;

import java.util.List;

public class AddCommonlyUsedMedicineReq {
    private List<CommonlyUsedMedicine> list;

    public List<CommonlyUsedMedicine> getList() {
        return list;
    }

    @JsonSetter("list")
    public void setList(List<CommonlyUsedMedicine> list) {
        this.list = list;
    }
}
