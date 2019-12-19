package top.itcat.bean.diagnose.diagnostic.commonly;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.diagnose.CommonlyUsedDiagnostic;

public class UpdateCommonlyUsedDiagnosticReq {
    private CommonlyUsedDiagnostic commonlyUsedDiagnostic;

    public CommonlyUsedDiagnostic getCommonlyUsedDiagnostic() {
        return commonlyUsedDiagnostic;
    }

    @JsonSetter("commonly_used_diagnostic")
    public void setList(CommonlyUsedDiagnostic commonlyUsedDiagnostic) {
        this.commonlyUsedDiagnostic = commonlyUsedDiagnostic;
    }
}
