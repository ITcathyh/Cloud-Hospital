package top.itcat.entity.registration;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.experimental.Accessors;
import top.itcat.entity.Patient;
import top.itcat.entity.charge.ChargeItem;
import top.itcat.entity.diagnose.SchedulePlan;
import top.itcat.rpc.service.model.RegistrationSourceEnum;
import top.itcat.rpc.service.model.RegistrationStatusEnum;

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
public class Registration {

    private static final long serialVersionUID = 1L;

    /**
     * 挂号id
     */
    private Long id;
    /**
     * 挂号病历号（2019052012345）
     */
    @JSONField(name = "medical_record_no")
    private Long medicalRecordNo;
    /**
     * 挂号患者身份证号
     */
    @JSONField(name = "identity_card_no")
    private String identityCardNo;
    /**
     * 挂号排班计划id
     */
    @JSONField(name = "schedule_plan_id")
    private Long schedulePlanId;
    /**
     * 挂号时间
     */
    @JSONField(name = "registration_time")
    private Long registrationTime;
    /**
     * 挂号就诊时间
     */
    @JSONField(name = "see_doctor_time")
    private Long seeDoctorTime;
    /**
     * 挂号来源 窗口、网络
     */
    @JSONField(name = "registration_source")
    private Integer registrationSource;
    /**
     * 挂号结算类别id
     */
    @JSONField(name = "billing_category_id")
    private Long billingCategoryId;
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
    @JSONField(name = "sequence_number")
    private Long sequenceNumber;
    /**
     * 挂号有效性
     */
    private Integer valid;
    @JSONField(name = "patient_info")
    private Patient patient;
    @JSONField(name = "charge_item")
    private ChargeItem chargeItem;
    private SchedulePlan plan;

    @JsonSetter("medical_record_no")
    public void setMedicalRecordNo(Long medicalRecordNo) {
        this.medicalRecordNo = medicalRecordNo;
    }

    @JsonSetter("identity_card_no")
    public void setIdentityCardNo(String identityCardNo) {
        this.identityCardNo = identityCardNo;
    }

    @JsonSetter("schedule_plan_id")
    public void setSchedulePlanId(Long schedulePlanId) {
        this.schedulePlanId = schedulePlanId;
    }

    @JsonSetter("registration_time")
    public void setRegistrationTime(Long registrationTime) {
        this.registrationTime = registrationTime;
    }

    @JsonSetter("see_doctor_time")
    public void setSeeDoctorTime(Long seeDoctorTime) {
        this.seeDoctorTime = seeDoctorTime;
    }

    @JsonSetter("registration_source")
    public void setRegistrationSource(Integer registrationSource) {
        this.registrationSource = registrationSource;
    }

    @JsonSetter("billing_category_id")
    public void setBillingCategoryId(Long billingCategoryId) {
        this.billingCategoryId = billingCategoryId;
    }

    @JsonSetter("sequence_number")
    public void setSequenceNumber(Long sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
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
        if (bean.getStatus() != null) {
            rpcbean.setStatus(RegistrationStatusEnum.findByValue(bean.getStatus()));
        }
        rpcbean.setExpense(bean.getExpense());
        if (bean.getSequenceNumber() != null) {
            rpcbean.setSequenceNumber(bean.getSequenceNumber().intValue());
        }
        rpcbean.setValid(bean.getValid());

        return rpcbean;
    }

}