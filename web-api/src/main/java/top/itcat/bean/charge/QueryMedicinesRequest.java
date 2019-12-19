package top.itcat.bean.charge;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.ToString;
import top.itcat.bean.CommonSearchReq;

public class QueryMedicinesRequest extends CommonSearchReq {
    private Integer category;

    @JsonSetter("category")
    public void setCategory(Integer category) {
        this.category = category;
    }

    public Integer getCategory() {
        return category;
    }
}
