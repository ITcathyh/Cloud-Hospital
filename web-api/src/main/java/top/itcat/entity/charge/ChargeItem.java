package top.itcat.entity.charge;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.experimental.Accessors;
import top.itcat.entity.Patient;
import top.itcat.rpc.service.model.ChargeItemStatusEnum;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-29
 */
@Data
@Accessors(chain = true)
public class ChargeItem implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 收费项目id
     */
    private Long id;
    /**
     * 项目名称
     */
    private String name;
    /**
     * 规格 10ml/L�?
     */
    private String specification;
    /**
     * 单价
     */
    @JSONField(name = "unit_price")
    private Double unitPrice;
    /**
     * 数量
     */
    private Integer amount;
    /**
     * 单位 （克，次，支�?
     */
    @JSONField(name = "measure_word")
    private String measureWord;
    /**
     * 应付金额
     */
    private Double payable;
    /**
     * 实付
     */
    @JSONField(name = "actually_paid")
    private Double actuallyPaid;
    /**
     * 执行科室
     */
    @JSONField(name = "department_id")
    private Long departmentId;
    /**
     * 创建科室
     */
    @JSONField(name = "create_department_id")
    private Long createDepartmentId;
    /**
     * 病历号
     */
    @JSONField(name = "medical_record_no")
    private Long medicalRecordNo;
    /**
     * 收费状态（0：未缴，1：已缴，2：已红冲，3：是红冲，4：已作废
     */
    private Integer status;
    /**
     * 结算类别id
     */
    @JSONField(name = "billing_category_id")
    private Long billingCategoryId;
    /**
     * 结算类别id
     */
    @JSONField(name = "billing_category")
    private BillingCategory billingCategory;
    /**
     * 收费员id
     */
    @JSONField(name = "operator_id")
    private Long operatorId;
    /**
     * 操作时间
     */
    @JSONField(name = "operation_time")
    private Long operationTime;
    /**
     * 是否日结
     */
    @JSONField(name = "daily_knot")
    private Integer dailyKnot;
    /**
     * 费用科目id
     */
    @JSONField(name = "charge_subject_id")
    private Long chargeSubjectId;

    @JSONField(name = "creator_id")
    private Long creatorId;

    /**
     * 具体项目id（通过费用科目id判断此项目是药品，还是非药品）
     */
    @JSONField(name = "project_id")
    private Long projectId;
    /**
     * 是否有效
     */
    private Integer valid;
    @JSONField(name = "depart_name")
    private String departName;
    @JSONField(name = "patient_info")
    private Patient patient;
//    private Integer canRefund;

    @JsonSetter("unit_price")
    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @JsonSetter("measure_word")
    public void setMeasureWord(String measureWord) {
        this.measureWord = measureWord;
    }

    @JsonSetter("actually_paid")
    public void setActuallyPaid(Double actuallyPaid) {
        this.actuallyPaid = actuallyPaid;
    }

    @JsonSetter("department_id")
    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    @JsonSetter("medical_record_no")
    public void setMedicalRecordNo(Long medicalRecordNo) {
        this.medicalRecordNo = medicalRecordNo;
    }

    @JsonSetter("billing_category_id")
    public void setBillingCategoryId(Long billingCategoryId) {
        this.billingCategoryId = billingCategoryId;
    }

    @JsonSetter("operator_id")
    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    @JsonSetter("operation_time")
    public void setOperationTime(Long operationTime) {
        this.operationTime = operationTime;
    }

    @JsonSetter("daily_knot")
    public void setDailyKnot(Integer dailyKnot) {
        this.dailyKnot = dailyKnot;
    }

    @JsonSetter("charge_subject_id")
    public void setChargeSubjectId(Long chargeSubjectId) {
        this.chargeSubjectId = chargeSubjectId;
    }

    @JsonSetter("project_id")
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    @JsonSetter("creator_id")
    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    @JsonSetter("create_department_id")
    public void setCreateDepartmentId(Long createDepartmentId) {
        this.createDepartmentId = createDepartmentId;
    }

    public static ChargeItem convert(top.itcat.rpc.service.model.ChargeItem rpcbean) {
        ChargeItem bean = new ChargeItem();
        if (rpcbean.isSetId()) {
            bean.setId(rpcbean.getId());
        }
        if (rpcbean.isSetName()) {
            bean.setName(rpcbean.getName());
        }
        if (rpcbean.isSetSpecification()) {
            bean.setSpecification(rpcbean.getSpecification());
        }
        if (rpcbean.isSetUnitPrice()) {
            bean.setUnitPrice(rpcbean.getUnitPrice());
        }
        if (rpcbean.isSetAmount()) {
            bean.setAmount((int) rpcbean.getAmount());
        }
        if (rpcbean.isSetMeasureWord()) {
            bean.setMeasureWord(rpcbean.getMeasureWord());
        }
        if (rpcbean.isSetPayable()) {
            bean.setPayable(rpcbean.getPayable());
        }
        if (rpcbean.isSetActuallyPaid()) {
            bean.setActuallyPaid(rpcbean.getActuallyPaid());
        }
        if (rpcbean.isSetDepartmentId()) {
            bean.setDepartmentId(rpcbean.getDepartmentId());
        }
        if (rpcbean.isSetMedicalRecordNo()) {
            bean.setMedicalRecordNo(rpcbean.getMedicalRecordNo());
        }
        if (rpcbean.isSetStatus()) {
            bean.setStatus(rpcbean.getStatus().getValue());
        }
        if (rpcbean.isSetBillingCategoryId()) {
            bean.setBillingCategoryId(rpcbean.getBillingCategoryId());
        }
        if (rpcbean.isSetOperatorId()) {
            bean.setOperatorId(rpcbean.getOperatorId());
        }
        if (rpcbean.isSetOperationTime()) {
            bean.setOperationTime(rpcbean.getOperationTime());
        }
        if (rpcbean.isSetDailyKnot()) {
            bean.setDailyKnot(rpcbean.isDailyKnot() ? 1 : 0);
        }
        if (rpcbean.isSetChargeSubjectId()) {
            bean.setChargeSubjectId(rpcbean.getChargeSubjectId());
        }
        if (rpcbean.isSetProjectId()) {
            bean.setProjectId(rpcbean.getProjectId());
        }
        if (rpcbean.isSetValid()) {
            bean.setValid(rpcbean.getValid());
        }
        if (rpcbean.isSetCreatorId()) {
            bean.setCreatorId(rpcbean.getCreatorId());
        }
        if (rpcbean.isSetCreateDepartmentId()) {
            bean.setCreateDepartmentId(rpcbean.getCreateDepartmentId());
        }
        return bean;
    }

    public static top.itcat.rpc.service.model.ChargeItem convertRPCBean(ChargeItem bean) {
        top.itcat.rpc.service.model.ChargeItem rpcbean = new top.itcat.rpc.service.model.ChargeItem();
        if (bean.getId() != null) {
            rpcbean.setId(bean.getId());
        }
        rpcbean.setName(bean.getName());
        if (bean.getSpecification() != null) {
            rpcbean.setSpecification(bean.getSpecification());
        }
        if (bean.getUnitPrice() != null) {
            rpcbean.setUnitPrice(bean.getUnitPrice());
        }
        if (bean.getAmount() != null) {
            rpcbean.setAmount(bean.getAmount());
        }
        rpcbean.setMeasureWord(bean.getMeasureWord());
        if (bean.getPayable() != null) {
            rpcbean.setPayable(bean.getPayable());
            rpcbean.setActuallyPaid(bean.getPayable());
        }
        if (bean.getActuallyPaid() != null) {
            rpcbean.setActuallyPaid(bean.getActuallyPaid());
        }
        if (bean.getDepartmentId() != null) {
            rpcbean.setDepartmentId(bean.getDepartmentId());
        }
        if (bean.getMedicalRecordNo() != null) {
            rpcbean.setMedicalRecordNo(bean.getMedicalRecordNo());
        }
        if (bean.getStatus() != null) {
            rpcbean.setStatus(ChargeItemStatusEnum.findByValue(bean.getStatus()));
        } else {
            rpcbean.setStatus(ChargeItemStatusEnum.Unpaid);
        }
        if (bean.getBillingCategoryId() != null) {
            rpcbean.setBillingCategoryId(bean.getBillingCategoryId());
        }
        if (bean.getOperatorId() != null) {
            rpcbean.setOperatorId(bean.getOperatorId());
        }
        if (bean.getOperatorId() != null) {
            rpcbean.setOperationTime(bean.getOperationTime());
        }
        if (bean.getDailyKnot() != null) {
            rpcbean.setDailyKnot(bean.getDailyKnot() == 1);
        }
        if (bean.getChargeSubjectId() != null) {
            rpcbean.setChargeSubjectId(bean.getChargeSubjectId());
        }
        if (bean.getProjectId() != null) {
            rpcbean.setProjectId(bean.getProjectId());
        }
        if (bean.getCreatorId() != null) {
            rpcbean.setCreatorId(bean.getCreatorId());
        }
        if (bean.getCreateDepartmentId() != null) {
            rpcbean.setCreateDepartmentId(bean.getCreateDepartmentId());
        }
        if (bean.getValid() != null) {
            rpcbean.setValid(bean.getValid());
        }
        return rpcbean;
    }

}
