package top.itcat.bean.diagnose.diagnostic.commonly;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.diagnose.CommonlyUsedDiagnostic;

import java.util.List;

public class AddCommonlyUsedDiagnosticsReq {
    private List<CommonlyUsedDiagnostic> list;

    public List<CommonlyUsedDiagnostic> getList() {
        return list;
    }

    @JsonSetter("list")
    public void setList(List<CommonlyUsedDiagnostic> list) {
        this.list = list;
    }
}
