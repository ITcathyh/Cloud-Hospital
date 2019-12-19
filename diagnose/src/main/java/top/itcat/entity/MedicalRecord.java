package top.itcat.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;
import top.itcat.rpc.service.model.MedicalRecordStatusEnum;

import java.io.Serializable;

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
@TableName("medical_record")
public class MedicalRecord extends Model<MedicalRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("medical_record_no")
    private Long medicalRecordNo;
    @TableField("doctor_id")
    private Long doctorId;
    private Long time;
    private String complain;
    @TableField("current_medical_history")
    private String currentMedicalHistory;
    @TableField("current_medical_treatment")
    private String currentMedicalTreatment;
    @TableField("allergy_history")
    private String allergyHistory;
    @TableField("past_medical_history")
    private String pastMedicalHistory;
    @TableField("physical_check_up")
    private String physicalCheckUp;
    @TableField("preliminary_diagnosis_western")
    private String preliminaryDiagnosisWestern;
    @TableField("preliminary_diagnosis_chinese")
    private String preliminaryDiagnosisChinese;
    private Integer status;
    private Integer valid;


    @Override
    protected Serializable pkVal() {
        return this.id;
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
        if (rpcbean.isSetValid()) {
            bean.setValid(rpcbean.getValid());
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
        if (bean.getValid() != null) {
            rpcbean.setValid(bean.getValid());
        }

        return rpcbean;
    }

}