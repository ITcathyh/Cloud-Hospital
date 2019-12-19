package top.itcat.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import top.itcat.rpc.service.model.DoctorDiagnosticCatalogEnum;

/**
 * <p>
 * 诊断
 * </p>
 *
 * @author ITcathyh
 * @since 2019-06-11
 */
@Data
@Accessors(chain = true)
@TableName("diagnostic_for_medical_record_template")
public class DiagnosticForMedicalRecordTemplate extends Model<DiagnosticForMedicalRecordTemplate> {
    private static final long serialVersionUID = 1L;

    /**
     * 诊断id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 医生id
     */
    @TableField("doctor_id")
    private Long doctorId;
    /**
     * 诊断id(具体某一个病的id)
     */
    @TableField("diagnostic_id")
    private Long diagnosticId;
    /**
     * �?
     */
    @TableField("medical_record_template_id")
    private Long medicalRecordTemplateId;
    /**
     * 0中医诊断1西医诊断
     */
    private Integer catalog;
    /**
     * 病历有效�?
     */
    private Integer valid;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public static DiagnosticForMedicalRecordTemplate convert(top.itcat.rpc.service.model.DiagnosticForMedicalRecordTemplate rpcbean) {
        if (rpcbean == null) {
            return null;
        }
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
        if (rpcbean.isSetValid()) {
            bean.setValid(rpcbean.getValid());
        }

        return bean;
    }

    public static top.itcat.rpc.service.model.DiagnosticForMedicalRecordTemplate convertRPCBean(DiagnosticForMedicalRecordTemplate bean) {
        if (bean == null) {
            return null;
        }
        top.itcat.rpc.service.model.DiagnosticForMedicalRecordTemplate rpcbean = new top.itcat.rpc.service.model.DiagnosticForMedicalRecordTemplate();
        if (bean.getId() != null) {
            rpcbean.setId(bean.getId());
        }
        if (bean.getDoctorId() != null) {
            rpcbean.setDoctorId(bean.getDoctorId());
        }
        if (bean.getDiagnosticId() != null) {
            rpcbean.setDiagnosticId(bean.getDiagnosticId());
        }
        if (bean.getMedicalRecordTemplateId() != null) {
            rpcbean.setMedicalRecordTemplateId(bean.getMedicalRecordTemplateId());
        }
        if (bean.getCatalog() != null) {
            rpcbean.setCatalog(DoctorDiagnosticCatalogEnum.findByValue(bean.getCatalog()));
        }
        if (bean.getValid() != null) {
            rpcbean.setValid(bean.getValid());
        }

        return rpcbean;
    }

}