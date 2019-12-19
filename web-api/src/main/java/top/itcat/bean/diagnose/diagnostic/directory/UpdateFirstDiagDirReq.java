package top.itcat.bean.diagnose.diagnostic.directory;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.diagnose.FirstDiagDir;

public class UpdateFirstDiagDirReq {
    private FirstDiagDir diagnosticDirectory;

    public FirstDiagDir getDiagnosticDirectory() {
        return diagnosticDirectory;
    }

    @JsonSetter("diagnostic_directory")
    public void setDiagnosticDirectory(FirstDiagDir diagnosticDirectory) {
        this.diagnosticDirectory = diagnosticDirectory;
    }
}

