package top.itcat.bean.diagnose.medical.record.template;

import top.itcat.bean.CommonSearchReq;

public class GetMedicalRecordTemplateReq extends CommonSearchReq {
    private Integer suitableRange;

    public Integer getSuitableRange() {
        return suitableRange;
    }

    public void setSuitableRange(Integer suitableRange) {
        this.suitableRange = suitableRange;
    }
}
