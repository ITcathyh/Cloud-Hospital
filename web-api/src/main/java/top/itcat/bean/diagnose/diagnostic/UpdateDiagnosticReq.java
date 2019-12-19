package top.itcat.bean.diagnose.diagnostic;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.diagnose.Diagnostic;

public class UpdateDiagnosticReq {
    private Diagnostic diagnostic;

    public Diagnostic getDiagnostic() {
        return diagnostic;
    }

    @JsonSetter("diagnostic")
    public void setDiagnostic(Diagnostic diagnostic) {
        this.diagnostic = diagnostic;
    }
}

