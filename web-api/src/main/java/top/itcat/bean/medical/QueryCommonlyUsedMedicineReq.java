package top.itcat.bean.medical;

import top.itcat.bean.CommonSearchReq;

public class QueryCommonlyUsedMedicineReq extends CommonSearchReq {
    private Integer suitableRange;
    private Integer catalog;

    public Integer getCatalog() {
        return catalog;
    }

    public void setCatalog(Integer catalog) {
        this.catalog = catalog;
    }

    public Integer getSuitableRange() {
        return suitableRange;
    }

    public void setSuitableRange(Integer suitableRange) {
        this.suitableRange = suitableRange;
    }
}
