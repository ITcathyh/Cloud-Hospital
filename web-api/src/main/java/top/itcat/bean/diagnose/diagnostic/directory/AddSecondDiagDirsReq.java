package top.itcat.bean.diagnose.diagnostic.directory;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.diagnose.SecondDiagDir;

import java.util.List;

public class AddSecondDiagDirsReq {
    private List<SecondDiagDir> list;

    public List<SecondDiagDir> getList() {
        return list;
    }

    @JsonSetter("list")
    public void setList(List<SecondDiagDir> list) {
        this.list = list;
    }
}
