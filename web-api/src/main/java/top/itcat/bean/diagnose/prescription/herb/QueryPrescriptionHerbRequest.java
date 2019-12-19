package top.itcat.bean.diagnose.prescription.herb;

import lombok.Getter;
import lombok.Setter;
import top.itcat.bean.CommonSearchReq;

@Setter
@Getter
public class QueryPrescriptionHerbRequest extends CommonSearchReq {
    private Long medicalRecordNo;
    private Long doctorId;

    public void setMedicalRecordNo(Long medicalRecordNo) {
        this.medicalRecordNo = medicalRecordNo;
    }

    public Long getMedicalRecordNo() {
        return medicalRecordNo;
    }
}
