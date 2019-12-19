package top.itcat.bean.diagnose.diagnostic.directory;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.diagnose.FirstDiagDir;

import java.util.List;

public class AddFirstDiagDirsReq {
    private List<FirstDiagDir> list;

    public List<FirstDiagDir> getList() {
        return list;
    }

    @JsonSetter("list")
    public void setList(List<FirstDiagDir> list) {
        this.list = list;
    }
}
