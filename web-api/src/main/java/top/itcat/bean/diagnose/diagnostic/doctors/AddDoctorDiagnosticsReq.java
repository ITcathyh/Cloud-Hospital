package top.itcat.bean.diagnose.diagnostic.doctors;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.diagnose.DoctorDiagnostic;

import java.util.List;

;

public class AddDoctorDiagnosticsReq {
    private List<DoctorDiagnostic> list;

    public List<DoctorDiagnostic> getList() {
        return list;
    }

    @JsonSetter("list")
    public void setList(List<DoctorDiagnostic> list) {
        this.list = list;
    }
}