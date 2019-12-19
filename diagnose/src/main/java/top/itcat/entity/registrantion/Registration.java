package top.itcat.entity.registrantion;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;
import top.itcat.rpc.service.model.RegistrationSourceEnum;
import top.itcat.rpc.service.model.RegistrationStatusEnum;

import java.io.Serializable;

/**
 * <p>
 * 挂号
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-28
 */
@Data
@Accessors(chain = true)
public class Registration extends Model<Registration> {

    private static final long serialVersionUID = 1L;

    /**
     * 挂号id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 挂号病历号（2019052012345）
     */
    @TableField("medical_record_no")
    private Long medicalRecordNo;
    /**
     * 挂号患者身份证号
     */
    @TableField("identity_card_no")
    private String identityCardNo;
    /**
     * 挂号排班计划id
     */
    @TableField("schedule_plan_id")
    private Long schedulePlanId;
    /**
     * 挂号时间
     */
    @TableField("registration_time")
    private Long registrationTime;
    /**
     * 挂号就诊时间
     */
    @TableField("see_doctor_time")
    private Long seeDoctorTime;
    /**
     * 挂号来源 窗口、网络
     */
    @TableField("registration_source")
    private Integer registrationSource;
    /**
     * 挂号结算类别id
     */
    @TableField("billing_category_id")
    private Long billingCategoryId;
    /**
     * 挂号是否已看诊
     */
//    @TableField("seen_doctor")
//    private Integer seenDocator;
    /**
     * 挂号状态
     */
    private Integer status;
    /**
     * 挂号费用
     */
    private Double expense;
    /**
     * 顺序值
     */
    @TableField("sequence_number")
    private Long sequenceNumber;
    /**
     * 挂号有效性
     */
    private Integer valid;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public static Registration convert(top.itcat.rpc.service.model.Registration rpcbean) {
        Registration bean = new Registration();
        if (rpcbean.isSetId()) {
            bean.setId(rpcbean.getId());
        }
        if (rpcbean.isSetMedicalRecordNo()) {
            bean.setMedicalRecordNo(rpcbean.getMedicalRecordNo());
        }
        if (rpcbean.isSetIdentityCardNo()) {
            bean.setIdentityCardNo(rpcbean.getIdentityCardNo());
        }
        if (rpcbean.isSetSchedulePlanId()) {
            bean.setSchedulePlanId(rpcbean.getSchedulePlanId());
        }
        if (rpcbean.isSetRegistrationTime()) {
            bean.setRegistrationTime(rpcbean.getRegistrationTime());
        }
        if (rpcbean.isSetSeeDoctorTime()) {
            bean.setSeeDoctorTime(rpcbean.getSeeDoctorTime());
        }
        if (rpcbean.isSetRegistrationSource()) {
            bean.setRegistrationSource(rpcbean.getRegistrationSource().getValue());
        }
        if (rpcbean.isSetBillingCategoryId()) {
            bean.setBillingCategoryId(rpcbean.getBillingCategoryId());
        }
//        if (rpcbean.isSetSeenDocator()) {
//            bean.setSeenDocator(rpcbean.getSeenDocator());
//        }
        if (rpcbean.isSetStatus()) {
            bean.setStatus(rpcbean.getStatus().getValue());
        }
        if (rpcbean.isSetExpense()) {
            bean.setExpense(rpcbean.getExpense());
        }
        if (rpcbean.isSetSequenceNumber()) {
            bean.setSequenceNumber((long) rpcbean.getSequenceNumber());
        }

        if (rpcbean.isSetValid()) {
            bean.setValid(rpcbean.getValid());
        }

        return bean;
    }

    public static top.itcat.rpc.service.model.Registration convertRPCBean(Registration bean) {
        top.itcat.rpc.service.model.Registration rpcbean = new top.itcat.rpc.service.model.Registration();
        if (bean.getId() != null) {
            rpcbean.setId(bean.getId());
        }
        if (bean.getMedicalRecordNo() != null) {
            rpcbean.setMedicalRecordNo(bean.getMedicalRecordNo());
        }
        if (bean.getIdentityCardNo() != null) {
            rpcbean.setIdentityCardNo(bean.getIdentityCardNo());
        }
        if (bean.getSchedulePlanId() != null) {
            rpcbean.setSchedulePlanId(bean.getSchedulePlanId());
        }
        if (bean.getRegistrationTime() != null) {
            rpcbean.setRegistrationTime(bean.getRegistrationTime());
        }
        if (bean.getSeeDoctorTime() != null) {
            rpcbean.setSeeDoctorTime(bean.getSeeDoctorTime());
        }
        if (bean.getRegistrationSource() != null) {
            rpcbean.setRegistrationSource(RegistrationSourceEnum.findByValue(bean.getRegistrationSource()));
        }
        if (bean.getBillingCategoryId() != null) {
            rpcbean.setBillingCategoryId(bean.getBillingCategoryId());
        }
//        rpcbean.setSeenDoctor(bean.getSeenDocator());
        if (bean.getStatus() != null) {
            rpcbean.setStatus(RegistrationStatusEnum.findByValue(bean.getStatus()));
        }
        if (bean.getExpense() != null) {
            rpcbean.setExpense(bean.getExpense());
        }
        if (bean.getSequenceNumber() != null) {
            rpcbean.setSequenceNumber(bean.getSequenceNumber().intValue());
        }
        if (bean.getValid() != null) {
            rpcbean.setValid(bean.getValid());
        }

        return rpcbean;
    }

}