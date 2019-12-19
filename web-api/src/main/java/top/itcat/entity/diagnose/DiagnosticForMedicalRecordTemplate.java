package top.itcat.entity.diagnose;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.experimental.Accessors;
import top.itcat.rpc.service.model.DoctorDiagnosticCatalogEnum;

@Data
@Accessors(chain = true)
public class DiagnosticForMedicalRecordTemplate {

    private static final long serialVersionUID = 1L;

    /**
     * 诊断id
     */
    @JSONField(name = "id")
    private Long id;

    /**
     * 医生id
     */
    @JSONField(name = "doctor_id")
    private Long doctorId;

    /**
     * 诊断id(具体某一个病的id)
     */
    @JSONField(name = "diagnostic_id")
    private Long diagnosticId;
    private Diagnostic diagnostic;

    /**
     * 号
     */
    @JSONField(name = "medical_record_template_id")
    private Long medicalRecordTemplateId;

    /**
     * 0中医诊断1西医诊断
     */
    @JSONField(name = "catalog")
    private Integer catalog;

    @JsonSetter("id")
    public void setId(Long id) {
        this.id = id;
    }

    @JsonSetter("doctor_id")
    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    @JsonSetter("diagnostic_id")
    public void setDiagnosticId(Long diagnosticId) {
        this.diagnosticId = diagnosticId;
    }

    @JsonSetter("medical_record_template_id")
    public void setMedicalRecordTemplateId(Long medicalRecordTemplateId) {
        this.medicalRecordTemplateId = medicalRecordTemplateId;
    }

    @JsonSetter("catalog")
    public void setCatalog(Integer catalog) {
        this.catalog = catalog;
    }

    public static DiagnosticForMedicalRecordTemplate convert(top.itcat.rpc.service.model.DiagnosticForMedicalRecordTemplate rpcbean) {
        DiagnosticForMedicalRecordTemplate bean = new DiagnosticForMedicalRecordTemplate();
        if (rpcbean.isSetId()) {
            bean.setId(rpcbean.getId());
        }
        if (rpcbean.isSetDoctorId()) {
            bean.setDoctorId(rpcbean.getDoctorId());
        }
        if (rpcbean.isSetDiagnosticId()) {
            bean.setDiagnosticId(rpcbean.getDiagnosticId());
        }
        if (rpcbean.isSetMedicalRecordTemplateId()) {
            bean.setMedicalRecordTemplateId(rpcbean.getMedicalRecordTemplateId());
        }
        if (rpcbean.isSetCatalog()) {
            bean.setCatalog(rpcbean.getCatalog().getValue());
        }

        return bean;
    }

    public static top.itcat.rpc.service.model.DiagnosticForMedicalRecordTemplate convertRPCBean(DiagnosticForMedicalRecordTemplate bean) {
        top.itcat.rpc.service.model.DiagnosticForMedicalRecordTemplate rpcbean = new top.itcat.rpc.service.model.DiagnosticForMedicalRecordTemplate();
        if (bean.getId() != null) {
            rpcbean.setId(bean.getId());
        }
//        if (bean.getDoctorId() != null) {
//            rpcbean.setDoctorId(bean.getDoctorId());
//        }
        if (bean.getDiagnosticId() != null) {
            rpcbean.setDiagnosticId(bean.getDiagnosticId());
        }
        if (bean.getMedicalRecordTemplateId() != null) {
            rpcbean.setMedicalRecordTemplateId(bean.getMedicalRecordTemplateId());
        }
        if (bean.getCatalog() != null) {
            rpcbean.setCatalog(DoctorDiagnosticCatalogEnum.findByValue(bean.getCatalog()));
        }
//        if (bean.getDiagnostic() != null && bean.getDiagnostic().getId() != null) {
//            rpcbean.setDiagnosticId(bean.getDiagnostic().getId());
//        }

        return rpcbean;
    }

}
