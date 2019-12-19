package top.itcat.bean.diagnose.diagnostic;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.diagnose.Diagnostic;

import java.util.List;

public class AddDiagnosticsReq {
    private List<Diagnostic> list;

    public List<Diagnostic> getList() {
        return list;
    }

    @JsonSetter("list")
    public void setList(List<Diagnostic> list) {
        this.list = list;
    }
}

