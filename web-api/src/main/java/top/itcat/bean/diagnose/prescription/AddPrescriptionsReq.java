package top.itcat.bean.diagnose.prescription;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.medical.Prescription;

import java.util.List;


public class AddPrescriptionsReq {
    private List<Prescription> list;

    public List<Prescription> getList() {
        return list;
    }

    @JsonSetter("list")
    public void setList(List<Prescription> list) {
        this.list = list;
    }
}
