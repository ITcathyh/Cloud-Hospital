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
 *
 * </p>
 *
 * @author ITcathyh
 * @since 2019-06-03
 */
@Data
@Accessors(chain = true)
@TableName("doctor_diagnostic")
public class DoctorDiagnostic extends Model<DoctorDiagnostic> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * id
     */
    @TableField("doctor_id")
    private Long doctorId;
    /**
     * id(id)
     */
    @TableField("diagnostic_id")
    private Long diagnosticId;
    @TableField("medical_record_no")
    private Long medicalRecordNo;
    /**
     * 01
     */
    private Integer catalog;
    /**
     * 01
     */
    private Integer main;
    /**
     * 01
     */
    private Integer suspect;
    private Integer valid;


    @Override
    protected Serializable pkVal() {
        return this.id;
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
        if (rpcbean.isSetValid()) {
            bean.setValid(rpcbean.getValid());
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
        if (bean.getValid() != null) {
            rpcbean.setValid(bean.getValid());
        }

        return rpcbean;
    }

}