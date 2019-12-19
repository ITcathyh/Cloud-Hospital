package top.itcat.bean.diagnose.prescription.pack;

import com.fasterxml.jackson.annotation.JsonSetter;

public class PackPrescriptionReq {
    private Long medicalRecordNo;

    public Long getMedicalRecordNo() {
        return medicalRecordNo;
    }

    @JsonSetter("medical_record_no")
    public void setMedicalRecordNo(Long medicalRecordNo) {
        this.medicalRecordNo = medicalRecordNo;
    }
}
