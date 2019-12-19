package top.itcat.bean.charge.nonmedical;

import top.itcat.bean.CommonSearchReq;

public class GetNonmedicalChargeReq extends CommonSearchReq {
    private Integer category;

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }
}
