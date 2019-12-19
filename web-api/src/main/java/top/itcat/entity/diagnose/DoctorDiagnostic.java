package top.itcat.entity.diagnose;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.experimental.Accessors;
import top.itcat.rpc.service.model.DoctorDiagnosticCatalogEnum;

/**
 * <p>
 *
 * </p>
 *
 * @author ITcathyh
 * @since 2019-06-03
 */
@Data
@Accessors(chain = true)
public class DoctorDiagnostic {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;
    @JSONField(name = "doctor_id")
    private Long doctorId;
    @JSONField(name = "diagnostic_id")
    private Long diagnosticId;
    @JSONField(name = "medical_record_no")
    private Long medicalRecordNo;
    private Integer catalog;
    private Integer main;
    private Integer suspect;
    private Integer valid;
    private Diagnostic diagnostic;

    @JsonSetter("doctor_id")
    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    @JsonSetter("diagnostic_id")
    public void setDiagnosticId(Long diagnosticId) {
        this.diagnosticId = diagnosticId;
    }

    @JsonSetter("medical_record_no")
    public void setMedicalRecordNo(Long medicalRecordNo) {
        this.medicalRecordNo = medicalRecordNo;
    }

    public static DoctorDiagnostic convert(top.itcat.rpc.service.model.DoctorDiagnostic rpcbean) {
        DoctorDiagnostic bean = new DoctorDiagnostic();
        if (rpcbean.isSetId()) {
            bean.setId(rpcbean.getId());
        }
        if (rpcbean.isSetDoctorId()) {
            bean.setDoctorId(rpcbean.getDoctorId());
        }
        if (rpcbean.isSetDiagnosticId()) {
            bean.setDiagnosticId(rpcbean.getDiagnosticId());
        }
        if (rpcbean.isSetMedicalRecordNo()) {
            bean.setMedicalRecordNo(rpcbean.getMedicalRecordNo());
        }
        if (rpcbean.isSetCatalog()) {
            bean.setCatalog(rpcbean.getCatalog().getValue());
        }
        if (rpcbean.isSetMain()) {
            bean.setMain(rpcbean.isMain() ? 1 : 0);
        }
        if (rpcbean.isSetSuspect()) {
            bean.setSuspect(rpcbean.isSuspect() ? 1 : 0);
        }

        return bean;
    }

    public static top.itcat.rpc.service.model.DoctorDiagnostic convertRPCBean(DoctorDiagnostic bean) {
        top.itcat.rpc.service.model.DoctorDiagnostic rpcbean = new top.itcat.rpc.service.model.DoctorDiagnostic();
        if (bean.getId() != null) {
            rpcbean.setId(bean.getId());
        }
        if (bean.getDoctorId() != null) {
            rpcbean.setDoctorId(bean.getDoctorId());
        }
        if (bean.getDiagnosticId() != null) {
            rpcbean.setDiagnosticId(bean.getDiagnosticId());
        }
        if (bean.getMedicalRecordNo() != null) {
            rpcbean.setMedicalRecordNo(bean.getMedicalRecordNo());
        }
        if (bean.getCatalog() != null) {
            rpcbean.setCatalog(DoctorDiagnosticCatalogEnum.findByValue(bean.getCatalog()));
        }
        if (bean.getMain() != null) {
            rpcbean.setMain(bean.getMain() == 1);
        }
        if (bean.getSuspect() != null) {
            rpcbean.setSuspect(bean.getSuspect() == 1);
        }
        if (bean.getDiagnostic() != null && bean.getDiagnostic().getId() != null) {
            rpcbean.setDiagnosticId(bean.getDiagnostic().getId());
        }

        return rpcbean;
    }

}