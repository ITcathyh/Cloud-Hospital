package top.itcat.bean.diagnose.medical.record;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.diagnose.MedicalRecord;

public class UpdateMedicalRecordReq {
    private MedicalRecord medicalRecord;

    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    @JsonSetter("medical_record")
    public void setList(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
    }
}
