package top.itcat.bean.charge;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.hospital.Department;

import java.util.List;

public class AddDepartmentsReq {
    private List<Department> list;

    public List<Department> getList() {
        return list;
    }

    @JsonSetter("list")
    public void setList(List<Department> list) {
        this.list = list;
    }
}
