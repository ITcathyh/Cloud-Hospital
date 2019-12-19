package top.itcat.bean.diagnose.diagnostic.directory;

import top.itcat.bean.CommonSearchReq;

public class GetSecondDiagDirReq extends CommonSearchReq {
    private Long secondDircId;
    private Long firstDirecId;
    private Boolean withDetail;

    public Long getSecondDircId() {
        return secondDircId;
    }

    public void setSecondDircId(Long secondDircId) {
        this.secondDircId = secondDircId;
    }

    public Long getFirstDirecId() {
        return firstDirecId;
    }

    public void setFirstDirecId(Long firstDirecId) {
        this.firstDirecId = firstDirecId;
    }

    public Boolean getWithDetail() {
        return withDetail != null && withDetail;
    }

    public void setWithDetail(Boolean withDetail) {
        this.withDetail = withDetail;
    }
}
