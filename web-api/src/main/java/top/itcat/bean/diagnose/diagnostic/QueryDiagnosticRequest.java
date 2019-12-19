package top.itcat.bean.diagnose.diagnostic;

import top.itcat.bean.CommonSearchReq;

public class QueryDiagnosticRequest extends CommonSearchReq {
    private Long secondDirecId;

    public Long getSecondDirecId() {
        return secondDirecId;
    }

    public void setSecondDirecId(Long secondDirecId) {
        this.secondDirecId = secondDirecId;
    }
}