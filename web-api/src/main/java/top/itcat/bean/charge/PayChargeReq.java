package top.itcat.bean.charge;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class PayChargeReq {
    private long medicalRecordNo;

    @JsonSetter("medical_record_no")
    public void setMedicalRecordNo(long medicalRecordNo) {
        this.medicalRecordNo = medicalRecordNo;
    }
}
