package top.itcat.entity.diagnose;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.experimental.Accessors;
import top.itcat.entity.Patient;
import top.itcat.rpc.service.model.MedicalRecordStatusEnum;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Accessors(chain = true)
public class MedicalRecord {

    private static final long serialVersionUID = 1L;

    /**
     * 病历id
     */
    @JSONField(name = "id")
    private Long id;

    /**
     * 病历号
     */
    @JSONField(name = "medical_record_no")
    private Long medicalRecordNo;

    /**
     * 医生id
     */
    @JSONField(name = "doctor_id")
    private Long doctorId;

    /**
     * 病历时间
     */
    @JSONField(name = "time")
    private Long time;

    /**
     * 病历主诉
     */
    @JSONField(name = "complain")
    private String complain;

    /**
     * 病历现病史
     */
    @JSONField(name = "current_medical_history")
    private String currentMedicalHistory;

    /**
     * 病历现病治疗情况
     */
    @JSONField(name = "current_medical_treatment")
    private String currentMedicalTreatment;

    /**
     * 病历过敏史
     */
    @JSONField(name = "allergy_history")
    private String allergyHistory;

    /**
     * 病历既往史
     */
    @JSONField(name = "past_medical_history")
    private String pastMedicalHistory;

    /**
     * 病历体格检查
     */
    @JSONField(name = "physical_check_up")
    private String physicalCheckUp;

    /**
     * 病历初步诊断（西医）
     */
    @JSONField(name = "preliminary_diagnosis_western")
    private String preliminaryDiagnosisWestern;

    /**
     * 病历初步诊断（中医）
     */
    @JSONField(name = "preliminary_diagnosis_chinese")
    private String preliminaryDiagnosisChinese;

    /**
     * 病历状态0.未看诊，1.确诊,2诊毕
     */
    @JSONField(name = "status")
    private Integer status;
    /**
     * 病历状态0.未看诊，1.确诊,2诊毕
     */
    @JSONField(name = "list")
    private List<DoctorDiagnostic> list;

    @JSONField(name = "patient_info")
    private Patient patient;

    @JsonSetter("id")
    public void setId(Long id) {
        this.id = id;
    }

    @JsonSetter("medical_record_no")
    public void setMedicalRecordNo(Long medicalRecordNo) {
        this.medicalRecordNo = medicalRecordNo;
    }

    @JsonSetter("doctor_id")
    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    @JsonSetter("time")
    public void setTime(Long time) {
        this.time = time;
    }

    @JsonSetter("complain")
    public void setComplain(String complain) {
        this.complain = complain;
    }

    @JsonSetter("current_medical_history")
    public void setCurrentMedicalHistory(String currentMedicalHistory) {
        this.currentMedicalHistory = currentMedicalHistory;
    }

    @JsonSetter("current_medical_treatment")
    public void setCurrentMedicalTreatment(String currentMedicalTreatment) {
        this.currentMedicalTreatment = currentMedicalTreatment;
    }

    @JsonSetter("allergy_history")
    public void setAllergyHistory(String allergyHistory) {
        this.allergyHistory = allergyHistory;
    }

    @JsonSetter("past_medical_history")
    public void setPastMedicalHistory(String pastMedicalHistory) {
        this.pastMedicalHistory = pastMedicalHistory;
    }

    @JsonSetter("physical_check_up")
    public void setPhysicalCheckUp(String physicalCheckUp) {
        this.physicalCheckUp = physicalCheckUp;
    }

    @JsonSetter("preliminary_diagnosis_western")
    public void setPreliminaryDiagnosisWestern(String preliminaryDiagnosisWestern) {
        this.preliminaryDiagnosisWestern = preliminaryDiagnosisWestern;
    }

    @JsonSetter("preliminary_diagnosis_chinese")
    public void setPreliminaryDiagnosisChinese(String preliminaryDiagnosisChinese) {
        this.preliminaryDiagnosisChinese = preliminaryDiagnosisChinese;
    }

    @JsonSetter("status")
    public void setStatus(Integer status) {
        this.status = status;
    }

    public static MedicalRecord convert(top.itcat.rpc.service.model.MedicalRecord rpcbean) {
        MedicalRecord bean = new MedicalRecord();
        if (rpcbean.isSetId()) {
            bean.setId(rpcbean.getId());
        }
        if (rpcbean.isSetMedicalRecordNo()) {
            bean.setMedicalRecordNo(rpcbean.getMedicalRecordNo());
        }
        if (rpcbean.isSetDoctorId()) {
            bean.setDoctorId(rpcbean.getDoctorId());
        }
        if (rpcbean.isSetTime()) {
            bean.setTime(rpcbean.getTime());
        }
        if (rpcbean.isSetComplain()) {
            bean.setComplain(rpcbean.getComplain());
        }
        if (rpcbean.isSetCurrentMedicalHistory()) {
            bean.setCurrentMedicalHistory(rpcbean.getCurrentMedicalHistory());
        }
        if (rpcbean.isSetCurrentMedicalTreatment()) {
            bean.setCurrentMedicalTreatment(rpcbean.getCurrentMedicalTreatment());
        }
        if (rpcbean.isSetAllergyHistory()) {
            bean.setAllergyHistory(rpcbean.getAllergyHistory());
        }
        if (rpcbean.isSetPastMedicalHistory()) {
            bean.setPastMedicalHistory(rpcbean.getPastMedicalHistory());
        }
        if (rpcbean.isSetPhysicalCheckUp()) {
            bean.setPhysicalCheckUp(rpcbean.getPhysicalCheckUp());
        }
        if (rpcbean.isSetPreliminaryDiagnosisWestern()) {
            bean.setPreliminaryDiagnosisWestern(rpcbean.getPreliminaryDiagnosisWestern());
        }
        if (rpcbean.isSetPreliminaryDiagnosisChinese()) {
            bean.setPreliminaryDiagnosisChinese(rpcbean.getPreliminaryDiagnosisChinese());
        }
        if (rpcbean.isSetStatus()) {
            bean.setStatus(rpcbean.getStatus().getValue());
        }
        if (rpcbean.isSetDoctorDiagnostics()) {
            bean.setList(rpcbean.getDoctorDiagnostics().parallelStream()
                    .map(DoctorDiagnostic::convert)
                    .collect(Collectors.toList()));
        }
        return bean;
    }

    public static top.itcat.rpc.service.model.MedicalRecord convertRPCBean(MedicalRecord bean) {
        top.itcat.rpc.service.model.MedicalRecord rpcbean = new top.itcat.rpc.service.model.MedicalRecord();
        if (bean.getId() != null) {
            rpcbean.setId(bean.getId());
        }
        if (bean.getMedicalRecordNo() != null) {
            rpcbean.setMedicalRecordNo(bean.getMedicalRecordNo());
        }
        if (bean.getDoctorId() != null) {
            rpcbean.setDoctorId(bean.getDoctorId());
        }
        if (bean.getTime() != null) {
            rpcbean.setTime(bean.getTime());
        }
        rpcbean.setComplain(bean.getComplain());
        rpcbean.setCurrentMedicalHistory(bean.getCurrentMedicalHistory());
        rpcbean.setCurrentMedicalTreatment(bean.getCurrentMedicalTreatment());
        rpcbean.setAllergyHistory(bean.getAllergyHistory());
        rpcbean.setPastMedicalHistory(bean.getPastMedicalHistory());
        rpcbean.setPhysicalCheckUp(bean.getPhysicalCheckUp());
        rpcbean.setPreliminaryDiagnosisWestern(bean.getPreliminaryDiagnosisWestern());
        rpcbean.setPreliminaryDiagnosisChinese(bean.getPreliminaryDiagnosisChinese());
        if (bean.getStatus() != null) {
            rpcbean.setStatus(MedicalRecordStatusEnum.findByValue(bean.getStatus()));
        }
        rpcbean.setDoctorDiagnostics(bean.getList()
                .parallelStream()
                .map(DoctorDiagnostic::convertRPCBean)
                .collect(Collectors.toList()));

        return rpcbean;
    }

}
