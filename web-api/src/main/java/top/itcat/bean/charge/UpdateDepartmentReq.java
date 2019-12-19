package top.itcat.bean.charge;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.hospital.Department;

public class UpdateDepartmentReq {
    private Department department;

    public Department getDepartment() {
        return department;
    }

    @JsonSetter("department")
    public void setDepartment(Department department) {
        this.department = department;
    }
}
