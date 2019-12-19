package top.itcat.bean.diagnose.medical.record.template;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.diagnose.MedicalRecordTemplate;

public class UpdateMedicalRecordTemplateReq {
    private MedicalRecordTemplate medicalRecordTemplate;

    public MedicalRecordTemplate getMedicalRecordTemplate() {
        return medicalRecordTemplate;
    }

    @JsonSetter("medical_record_template")
    public void setList(MedicalRecordTemplate medicalRecordTemplate) {
        this.medicalRecordTemplate = medicalRecordTemplate;
    }
}
