package top.itcat.entity.apply;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;
import top.itcat.rpc.service.model.ApplyCategory;

/**
 * <p>
 * 
 * </p>
 *
 * @author ITcathyh
 * @since 2019-06-04
 */
@Data
@Accessors(chain = true)
public class Apply extends Model<Apply> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("medical_record_no")
    private Long medicalRecordNo;
    private Long time;
    /**
     * 0  1 2
     */
    private Integer category;
    /**
     * id
     */
    @TableField("doctor_id")
    private Long doctorId;

    @TableField("nonmedical_charge_id")
    private Long nonmedicalChargeId;

    private Integer valid;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public static Apply convert(top.itcat.rpc.service.model.Apply rpcbean) {
        if (rpcbean == null) {
            return null;
        }
        Apply bean = new Apply();
        if (rpcbean.isSetId()) {
            bean.setId(rpcbean.getId());
        }
        if (rpcbean.isSetMedicalRecordNo()) {
            bean.setMedicalRecordNo(rpcbean.getMedicalRecordNo());
        }
        if (rpcbean.isSetTime()) {
            bean.setTime(rpcbean.getTime());
        }
        if (rpcbean.isSetCategory()) {
            bean.setCategory(rpcbean.getCategory().getValue());
        }
        if (rpcbean.isSetDoctorId()) {
            bean.setDoctorId(rpcbean.getDoctorId());
        }
        if (rpcbean.isSetValid()) {
            bean.setValid(rpcbean.getValid());
        }
//        if (rpcbean.isSetNonmedicalChargeId()) {
//            bean.setNonmedicalChargeId(rpcbean.getNonmedicalChargeId());
//        }
        if (rpcbean.isSetValid()) {
            bean.setValid(rpcbean.getValid());
        }

        return bean;
    }

    public static top.itcat.rpc.service.model.Apply convertRPCBean(Apply bean) {
        if (bean == null) {
            return null;
        }
        top.itcat.rpc.service.model.Apply rpcbean = new top.itcat.rpc.service.model.Apply();
        if (bean.getId() != null) {
            rpcbean.setId(bean.getId());
        }
        if (bean.getMedicalRecordNo() != null) {
            rpcbean.setMedicalRecordNo(bean.getMedicalRecordNo());
        }
        if (bean.getTime() != null) {
            rpcbean.setTime(bean.getTime());
        }
        if (bean.getCategory() != null) {
            rpcbean.setCategory(ApplyCategory.findByValue(bean.getCategory()));
        }
        if (bean.getDoctorId() != null) {
            rpcbean.setDoctorId(bean.getDoctorId());
        }
//        if (bean.getNonmedicalChargeId() != null) {
//            rpcbean.setNonmedicalChargeId(bean.getNonmedicalChargeId());
//        }
        if (bean.getValid() != null) {
            rpcbean.setValid(bean.getValid());
        }

        return rpcbean;
    }

}