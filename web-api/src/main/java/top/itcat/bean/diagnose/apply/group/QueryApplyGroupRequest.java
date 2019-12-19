package top.itcat.bean.diagnose.apply.group;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.bean.CommonSearchReq;

public class QueryApplyGroupRequest extends CommonSearchReq {
    private Integer category;
    private Long doctorId;
    private Integer suitableRange;

    public Integer getCategory() {
        return category;
    }

    @JsonSetter("category")
    public void setCategory(Integer category) {
        this.category = category;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    @JsonSetter("creater_id")
    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getSuitableRange() {
        return suitableRange;
    }

    @JsonSetter("suitable_range")
    public void setSuitableRange(Integer suitableRange) {
        this.suitableRange = suitableRange;
    }
}
