package top.itcat.bean.charge;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.medical.Medicine;

import java.util.List;

public class AddMedicinesReq {
    private List<Medicine> list;

    public List<Medicine> getList() {
        return list;
    }

    @JsonSetter("list")
    public void setList(List<Medicine> list) {
        this.list = list;
    }
}