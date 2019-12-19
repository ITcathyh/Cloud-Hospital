package top.itcat.mq.bean;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;
import top.itcat.rpc.service.model.ChargeItemStatusEnum;

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
public class ChargeItem {

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
    private Double unitPrice;
    /**
     * 数量
     */
    private Integer amount;
    /**
     * 单位 （克，次，支�?
     */
    private String measureWord;
    /**
     * 应付金额
     */
    private Double payable;
    /**
     * 实付
     */
    private Double actuallyPaid;
    /**
     * 执行科室
     */
    private Long departmentId;
    /**
     * 创建科室
     */
    private Long createDepartmentId;
    /**
     * 病历号
     */
    private Long medicalRecordNo;
    /**
     * 收费状态（0：未缴，1：已缴，2：已红冲，3：是红冲，4：已作废
     */
    private Integer status;
    /**
     * 结算类别id
     */
    private Long billingCategoryId;

    /**
     * 收费员id
     */
    private Long operatorId;
    /**
     * 操作时间
     */
    private Long operationTime;
    /**
     * 是否日结
     */
    private Integer dailyKnot;
    /**
     * 费用科目id
     */
    private Long chargeSubjectId;

    private Long creatorId;

    /**
     * 具体项目id（通过费用科目id判断此项目是药品，还是非药品）
     */
    private Long projectId;
    /**
     * 是否有效
     */
    private Integer valid;
    private String departName;

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
