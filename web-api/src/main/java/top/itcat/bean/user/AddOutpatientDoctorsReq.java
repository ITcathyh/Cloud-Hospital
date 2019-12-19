package top.itcat.bean.user;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.user.OutpatientDoctor;

import java.util.List;

public class AddOutpatientDoctorsReq {
    private List<OutpatientDoctor> outpatientDoctors;

    public List<OutpatientDoctor> getOutpatientDoctors() {
        return outpatientDoctors;
    }

    @JsonSetter("doctors")
    public void setOutpatientDoctors(List<OutpatientDoctor> outpatientDoctors) {
        this.outpatientDoctors = outpatientDoctors;
    }
}
