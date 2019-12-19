package top.itcat.bean.diagnose.diagnostic.directory;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.diagnose.SecondDiagDir;

public class UpdateSecondDiagDirReq {
    private SecondDiagDir diagnosticDirectory;

    public SecondDiagDir getDiagnosticDirectory() {
        return diagnosticDirectory;
    }

    @JsonSetter("diagnostic_directory")
    public void setDiagnosticDirectory(SecondDiagDir diagnosticDirectory) {
        this.diagnosticDirectory = diagnosticDirectory;
    }
}

