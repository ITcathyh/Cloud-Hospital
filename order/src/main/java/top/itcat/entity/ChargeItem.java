package top.itcat.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;
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
@TableName("charge_item")
public class ChargeItem extends Model<ChargeItem> implements Cloneable {
    private static final long serialVersionUID = 1L;

    /**
     * 收费项目id
     */
    @TableId(value = "id", type = IdType.AUTO)
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
    @TableField("unit_price")
    private Double unitPrice;
    /**
     * 数量
     */
    private Integer amount;
    /**
     * 单位 （克，次，支�?
     */
    @TableField("measure_word")
    private String measureWord;
    /**
     * 应付金额
     */
    private Double payable;
    /**
     * 实付
     */
    @TableField("actually_paid")
    private Double actuallyPaid;
    /**
     * 执行科室
     */
    @TableField("department_id")
    private Long departmentId;

    @TableField("create_department_id")
    private Long createDepartmentId;

    /**
     * 病历�?
     */
    @TableField("medical_record_no")
    private Long medicalRecordNo;
    /**
     * 收费状态（0：未缴，1：已缴，2：已红冲，3：是红冲，4：已作废
     */
    private Integer status;
    /**
     * 结算类别id
     */
    @TableField("billing_category_id")
    private Long billingCategoryId;

    @TableField("creator_id")
    private Long creatorId;

    /**
     * 收费员id
     */
    @TableField("operator_id")
    private Long operatorId;
    /**
     * 操作时间
     */
    @TableField("operation_time")
    private Long operationTime;
    /**
     * 是否日结
     */
    @TableField("daily_knot")
    private Integer dailyKnot;
    /**
     * 费用科目id
     */
    @TableField("charge_subject_id")
    private Long chargeSubjectId;
    /**
     * 具体项目id（�?�过费用科目id判断此项目是药品，还是非药品�?
     */
    @TableField("project_id")
    private Long projectId;
    /**
     * 是否有效
     */
    private Integer valid;


    transient private Double total;


    @Override
    protected Serializable pkVal() {
        return this.id;
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
        if (rpcbean.isSetValid()) {
            bean.setValid(rpcbean.getValid());
        }

        return bean;
    }

    public static top.itcat.rpc.service.model.ChargeItem convertRPCBean(ChargeItem bean) {
        top.itcat.rpc.service.model.ChargeItem rpcbean = new top.itcat.rpc.service.model.ChargeItem();
        if (bean.getId() != null) {
            rpcbean.setId(bean.getId());
        }
        rpcbean.setName(bean.getName());
        rpcbean.setSpecification(bean.getSpecification());

        if (bean.getUnitPrice() != null) {
            rpcbean.setUnitPrice(bean.getUnitPrice());
        }

        if (bean.getUnitPrice() != null) {
            rpcbean.setUnitPrice(bean.getUnitPrice());
        }
        rpcbean.setMeasureWord(bean.getMeasureWord());

        if (bean.getPayable() != null) {
            rpcbean.setPayable(bean.getPayable());
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
        }
        if (bean.getBillingCategoryId() != null) {
            rpcbean.setBillingCategoryId(bean.getBillingCategoryId());
        }
        if (bean.getOperatorId() != null) {
            rpcbean.setOperatorId(bean.getOperatorId());
        }
        if (bean.getOperationTime() != null) {
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
        if (bean.getCreateDepartmentId() != null) {
            rpcbean.setCreateDepartmentId(bean.getCreateDepartmentId());
        }
        if (bean.getValid() != null) {
            rpcbean.setValid(bean.getValid());
        }
        if (bean.getAmount() != null) {
            rpcbean.setAmount(bean.getAmount());
        }
        if (bean.getCreatorId() != null) {
            rpcbean.setCreatorId(bean.getCreatorId());
        }

        return rpcbean;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
