package top.itcat.bean.diagnose.diagnostic.commonly;

import top.itcat.bean.CommonSearchReq;

public class GetCommonlyUsedDiagnosticReq extends CommonSearchReq {
    private Integer suitableRange;

    public Integer getSuitableRange() {
        return suitableRange;
    }

    public void setSuitableRange(Integer suitableRange) {
        this.suitableRange = suitableRange;
    }
}
