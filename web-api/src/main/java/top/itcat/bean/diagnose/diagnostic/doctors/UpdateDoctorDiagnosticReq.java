package top.itcat.bean.diagnose.diagnostic.doctors;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.diagnose.DoctorDiagnostic;

public class UpdateDoctorDiagnosticReq {
    private DoctorDiagnostic doctorDiagnostic;

    public DoctorDiagnostic getDoctorDiagnostic() {
        return doctorDiagnostic;
    }

    @JsonSetter("doctor_diagnostic")
    public void setList(DoctorDiagnostic doctorDiagnostic) {
        this.doctorDiagnostic = doctorDiagnostic;
    }
}