package top.itcat.bean.hospital;

import top.itcat.bean.CommonSearchReq;

public class GetDepartmentReq extends CommonSearchReq {
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
