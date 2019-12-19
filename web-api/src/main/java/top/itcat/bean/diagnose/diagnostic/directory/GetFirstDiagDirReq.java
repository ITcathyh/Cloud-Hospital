package top.itcat.bean.diagnose.diagnostic.directory;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.bean.CommonSearchReq;

public class GetFirstDiagDirReq extends CommonSearchReq {
    private Boolean withSecond;

    public Boolean getWithSecond() {
        return withSecond;
    }

    @JsonSetter("with_second")
    public void setWithSecond(Boolean withSecond) {
        this.withSecond = withSecond;
    }
}
