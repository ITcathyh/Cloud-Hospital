package top.itcat.entity.diagnose;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.experimental.Accessors;
import top.itcat.rpc.service.model.SuitableRangeEnum;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Accessors(chain = true)
public class MedicalRecordTemplate {

    private static final long serialVersionUID = 1L;

    /**
     * 病历模板id
     */
    @JSONField(name = "id")
    private Long id;

    /**
     * 病历模板编号
     */
    @JSONField(name = "code")
    private String code;

    /**
     * 病历模板名称
     */
    @JSONField(name = "name")
    private String name;

    /**
     * 创建人id
     */
    @JSONField(name = "doctor_id")
    private Long doctorId;

    @JSONField(name = "department_id")
    private Long departmentId;

    /**
     * 病历模板适用范围  个人、科室、全院
     */
    @JSONField(name = "suitable_range")
    private Integer suitableRange;

    /**
     * 病历模板主诉
     */
    @JSONField(name = "complain")
    private String complain;

    /**
     * 病历模板现病史
     */
    @JSONField(name = "current_medical_history")
    private String currentMedicalHistory;

    /**
     * 病历模板体格检查
     */
    @JSONField(name = "physical_check_up")
    private String physicalCheckUp;

    /**
     * 病历模板初步诊断（西医）
     */
    @JSONField(name = "preliminary_diagnosis_western")
    private String preliminaryDiagnosisWestern;

    /**
     * 病历模板初步诊断（中医）
     */
    @JSONField(name = "preliminary_diagnosis_chinese")
    private String preliminaryDiagnosisChinese;
    @JSONField(name = "doctor_diagnostic")
    public List<DiagnosticForMedicalRecordTemplate> doctorDiagnostics;

    @JsonSetter("doctor_diagnostic")
    public void setDoctorDiagnostics(List<DiagnosticForMedicalRecordTemplate> doctorDiagnostics) {
        this.doctorDiagnostics = doctorDiagnostics;
    }

    @JsonSetter("id")
    public void setId(Long id) {
        this.id = id;
    }

    @JsonSetter("code")
    public void setCode(String code) {
        this.code = code;
    }

    @JsonSetter("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonSetter("doctor_id")
    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    @JsonSetter("department_id")
    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    @JsonSetter("suitable_range")
    public void setSuitableRange(Integer suitableRange) {
        this.suitableRange = suitableRange;
    }

    @JsonSetter("complain")
    public void setComplain(String complain) {
        this.complain = complain;
    }

    @JsonSetter("current_medical_history")
    public void setCurrentMedicalHistory(String currentMedicalHistory) {
        this.currentMedicalHistory = currentMedicalHistory;
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

    public static MedicalRecordTemplate convert(top.itcat.rpc.service.model.MedicalRecordTemplate rpcbean) {
        MedicalRecordTemplate bean = new MedicalRecordTemplate();
        if (rpcbean.isSetId()) {
            bean.setId(rpcbean.getId());
        }
        if (rpcbean.isSetCode()) {
            bean.setCode(rpcbean.getCode());
        }
        if (rpcbean.isSetName()) {
            bean.setName(rpcbean.getName());
        }
        if (rpcbean.isSetDoctorId()) {
            bean.setDoctorId(rpcbean.getDoctorId());
        }
        if (rpcbean.isSetSuitableRange()) {
            bean.setSuitableRange(rpcbean.getSuitableRange().getValue());
        }
        if (rpcbean.isSetComplain()) {
            bean.setComplain(rpcbean.getComplain());
        }
        if (rpcbean.isSetCurrentMedicalHistory()) {
            bean.setCurrentMedicalHistory(rpcbean.getCurrentMedicalHistory());
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
        if (rpcbean.isSetDoctorDiagnostics()) {
            bean.setDoctorDiagnostics(rpcbean.getDoctorDiagnostics().parallelStream()
                    .map(DiagnosticForMedicalRecordTemplate::convert)
                    .collect(Collectors.toList()));
        }
        if (rpcbean.isSetDepartmentId()) {
            bean.setDepartmentId(rpcbean.getDepartmentId());
        }
        return bean;
    }

    public static top.itcat.rpc.service.model.MedicalRecordTemplate convertRPCBean(MedicalRecordTemplate bean) {
        top.itcat.rpc.service.model.MedicalRecordTemplate rpcbean = new top.itcat.rpc.service.model.MedicalRecordTemplate();
        if (bean.getId() != null) {
            rpcbean.setId(bean.getId());
        }
        rpcbean.setCode(bean.getCode());
        rpcbean.setName(bean.getName());
        if (bean.getDoctorId() != null) {
            rpcbean.setDoctorId(bean.getDoctorId());
        }
        if (bean.getSuitableRange() != null) {
            rpcbean.setSuitableRange(SuitableRangeEnum.findByValue(bean.getSuitableRange()));
        }
        rpcbean.setComplain(bean.getComplain());
        rpcbean.setCurrentMedicalHistory(bean.getCurrentMedicalHistory());
        rpcbean.setPhysicalCheckUp(bean.getPhysicalCheckUp());
        rpcbean.setPreliminaryDiagnosisWestern(bean.getPreliminaryDiagnosisWestern());
        rpcbean.setPreliminaryDiagnosisChinese(bean.getPreliminaryDiagnosisChinese());

//        if (bean.getDepartmentId() != null) {
//            rpcbean.setDepartmentId(bean.getDepartmentId());
//        }
        if (bean.getDoctorDiagnostics() != null) {
            rpcbean.setDoctorDiagnostics(bean.getDoctorDiagnostics().
                    parallelStream().map(DiagnosticForMedicalRecordTemplate::convertRPCBean).
                    collect(Collectors.toList()));
        }
        return rpcbean;
    }
}
