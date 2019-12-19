package top.itcat.entity.apply;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;
import top.itcat.rpc.service.model.ApplyCategory;
import top.itcat.rpc.service.model.DocumentCategory;
import top.itcat.rpc.service.model.ServiceObject;
import top.itcat.rpc.service.model.SuitableRangeEnum;

/**
 * <p>
 * 组套
 * </p>
 *
 * @author ITcathyh
 * @since 2019-06-11
 */
@Data
@Accessors(chain = true)
@TableName("apply_group")
public class ApplyGroup extends Model<ApplyGroup> {

    private static final long serialVersionUID = 1L;

    /**
     * 组套id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 组套编码
     */
    private String code;
    /**
     * 组套名称
     */
    private String name;
    /**
     * 组套业务分类0 �?�? 1�?�? 2处置
     */
    private Integer category;
    /**
     * 组套单据分类 超声…�??/普诊…�??
     */
    @TableField("document_category")
    private Integer documentCategory;
    /**
     * 组套服务对象 门诊…�??
     */
    @TableField("service_object")
    private Integer serviceObject;
    /**
     * 组套适用范围 0个人�?1科室�?2全院
     */
    @TableField("suitable_range")
    private Integer suitableRange;
    /**
     * 组套创建医生id
     */
    @TableField("creator_id")
    private Long creatorId;
    /**
     * 组套创建科室id
     */
    @TableField("department_id")
    private Long departmentId;
    /**
     * 组套创建时间
     */
    @TableField("create_time")
    private Long createTime;
    /**
     * 组套临床印象
     */
    @TableField("clinical_impression")
    private String clinicalImpression;
    /**
     * 组套临床诊断
     */
    @TableField("clinical_diagnosis")
    private String clinicalDiagnosis;
    /**
     * 组套目标和要�?
     */
    @TableField("goal_and_requirement")
    private String goalAndRequirement;
    /**
     * 组套备注
     */
    private String remark;
    /**
     * 组套有效性
     */
    private Integer valid;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public static ApplyGroup convert(top.itcat.rpc.service.model.ApplyGroup rpcbean) {
        if (rpcbean == null) {
            return null;
        }
        ApplyGroup bean = new ApplyGroup();
        if (rpcbean.isSetId()) {
            bean.setId(rpcbean.getId());
        }
        if (rpcbean.isSetCode()) {
            bean.setCode(rpcbean.getCode());
        }
        if (rpcbean.isSetName()) {
            bean.setName(rpcbean.getName());
        }
        if (rpcbean.isSetCategory()) {
            bean.setCategory(rpcbean.getCategory().getValue());
        }
        if (rpcbean.isSetDocumentCategory()) {
            bean.setDocumentCategory(rpcbean.getDocumentCategory().getValue());
        }
        if (rpcbean.isSetServiceObject()) {
            bean.setServiceObject(rpcbean.getServiceObject().getValue());
        }
        if (rpcbean.isSetSuitableRange()) {
            bean.setSuitableRange(rpcbean.getSuitableRange().getValue());
        }
        if (rpcbean.isSetCreatorId()) {
            bean.setCreatorId(rpcbean.getCreatorId());
        }
        if (rpcbean.isSetDepartmentId()) {
            bean.setDepartmentId(rpcbean.getDepartmentId());
        }
        if (rpcbean.isSetCreateTime()) {
            bean.setCreateTime(rpcbean.getCreateTime());
        }
        if (rpcbean.isSetClinicalImpression()) {
            bean.setClinicalImpression(rpcbean.getClinicalImpression());
        }
        if (rpcbean.isSetClinicalDiagnosis()) {
            bean.setClinicalDiagnosis(rpcbean.getClinicalDiagnosis());
        }
        if (rpcbean.isSetGoalAndRequirement()) {
            bean.setGoalAndRequirement(rpcbean.getGoalAndRequirement());
        }
        if (rpcbean.isSetRemark()) {
            bean.setRemark(rpcbean.getRemark());
        }
        if (rpcbean.isSetValid()) {
            bean.setValid(rpcbean.getValid());
        }

        return bean;
    }

    public static top.itcat.rpc.service.model.ApplyGroup convertRPCBean(ApplyGroup bean) {
        if (bean == null) {
            return null;
        }
        top.itcat.rpc.service.model.ApplyGroup rpcbean = new top.itcat.rpc.service.model.ApplyGroup();
        if (bean.getId() != null) {
            rpcbean.setId(bean.getId());
        }
        rpcbean.setCode(bean.getCode());
        rpcbean.setName(bean.getName());
        if (bean.getCategory() != null) {
            rpcbean.setCategory(ApplyCategory.findByValue(bean.getCategory()));
        }
        if (bean.getDocumentCategory() != null) {
            rpcbean.setDocumentCategory(DocumentCategory.findByValue(bean.getDocumentCategory()));
        }
        if (bean.getServiceObject() != null) {
            rpcbean.setServiceObject(ServiceObject.findByValue(bean.getServiceObject()));
        }
        if (bean.getSuitableRange() != null) {
            rpcbean.setSuitableRange(SuitableRangeEnum.findByValue(bean.getSuitableRange()));
        }
        if (bean.getCreatorId() != null) {
            rpcbean.setCreatorId(bean.getCreatorId());
        }
        if (bean.getDepartmentId() != null) {
            rpcbean.setDepartmentId(bean.getDepartmentId());
        }
        if (bean.getCreateTime() != null) {
            rpcbean.setCreateTime(bean.getCreateTime());
        }
        rpcbean.setClinicalImpression(bean.getClinicalImpression());
        rpcbean.setClinicalDiagnosis(bean.getClinicalDiagnosis());
        rpcbean.setGoalAndRequirement(bean.getGoalAndRequirement());
        rpcbean.setRemark(bean.getRemark());
        if (bean.getValid() != null) {
            rpcbean.setValid(bean.getValid());
        }

        return rpcbean;
    }

}