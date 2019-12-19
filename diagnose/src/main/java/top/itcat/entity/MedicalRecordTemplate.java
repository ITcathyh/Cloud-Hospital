package top.itcat.entity;

import com.alibaba.fastjson.annotation.JSONField;
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
import top.itcat.rpc.service.model.SuitableRangeEnum;

/**
 * <p>
 * 病历模板
 * </p>
 *
 * @author ITcathyh
 * @since 2019-06-11
 */
@Data
@Accessors(chain = true)
@TableName("medical_record_template")
public class MedicalRecordTemplate extends Model<MedicalRecordTemplate> {

    private static final long serialVersionUID = 1L;

    /**
     * 病历模板id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 病历模板编号
     */
    private String code;
    /**
     * 病历模板名称
     */
    private String name;
    /**
     * 创建人id
     */
    @TableField("doctor_id")
    private Long doctorId;
    /**
     * 病历模板适用范围  个人、科室�?�全�?
     */
    @TableField("suitable_range")
    private Integer suitableRange;
    /**
     * 病历模板主诉
     */
    private String complain;
    /**
     * 病历模板现病�?
     */
    @TableField("current_medical_history")
    private String currentMedicalHistory;
    /**
     * 病历模板体格�?�?
     */
    @TableField("physical_check_up")
    private String physicalCheckUp;
    /**
     * 病历模板初步诊断（西医）
     */
    @TableField("preliminary_diagnosis_western")
    private String preliminaryDiagnosisWestern;
    /**
     * 病历模板初步诊断（中医）
     */
    @TableField("preliminary_diagnosis_chinese")
    private String preliminaryDiagnosisChinese;
    /**
     * 病历模板有效性
     */
    private Integer valid;
    @TableField("department_id")
    private Long departmentId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public static MedicalRecordTemplate convert(top.itcat.rpc.service.model.MedicalRecordTemplate rpcbean) {
        if (rpcbean == null) {
            return null;
        }
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
        if (rpcbean.isSetValid()) {
            bean.setValid(rpcbean.getValid());
        }
        if (rpcbean.isSetDepartmentId()) {
            bean.setDepartmentId(rpcbean.getDepartmentId());
        }
        if (rpcbean.isSetValid()) {
            bean.setValid(rpcbean.getValid());
        }

        return bean;
    }

    public static top.itcat.rpc.service.model.MedicalRecordTemplate convertRPCBean(MedicalRecordTemplate bean) {
        if (bean == null) {
            return null;
        }
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
        if (bean.getValid() != null) {
            rpcbean.setValid(bean.getValid());
        }
        if (bean.getDepartmentId() != null) {
            rpcbean.setDepartmentId(bean.getDepartmentId());
        }
        if (bean.getValid() != null) {
            rpcbean.setValid(bean.getValid());
        }

        return rpcbean;
    }

}