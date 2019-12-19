package top.itcat.bean.charge.dayknot;

import top.itcat.bean.CommonSearchReq;

public class GetDayKnotItemReq extends CommonSearchReq {
    private Long dayKnotId;

    public Long getDayKnotId() {
        return dayKnotId;
    }

    public void setDayKnotId(Long dayKnotId) {
        this.dayKnotId = dayKnotId;
    }
}
