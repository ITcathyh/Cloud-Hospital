package top.itcat.entity.diagnose;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.experimental.Accessors;
import top.itcat.rpc.service.model.ApplyCategory;
import top.itcat.rpc.service.model.DocumentCategory;
import top.itcat.rpc.service.model.ServiceObject;
import top.itcat.rpc.service.model.SuitableRangeEnum;

import java.util.List;
import java.util.stream.Collectors;


@Data
@Accessors(chain = true)
public class ApplyGroup {

    private static final long serialVersionUID = 1L;

    /**
     * 组套id
     */
    @JSONField(name = "id")
    private Long id;

    /**
     * 组套编码
     */
    @JSONField(name = "code")
    private String code;

    /**
     * 组套名称
     */
    @JSONField(name = "name")
    private String name;

    /**
     * 组套业务分类0 检查 1检验 2处置
     */
    @JSONField(name = "category")
    private Integer category;

    /**
     * 组套单据分类 超声……/普诊……
     */
    @JSONField(name = "document_category")
    private Integer documentCategory;

    /**
     * 组套服务对象 门诊……
     */
    @JSONField(name = "service_object")
    private Integer serviceObject;

    /**
     * 组套适用范围 0个人、1科室、2全院
     */
    @JSONField(name = "suitable_range")
    private Integer suitableRange;

    /**
     * 组套创建医生id
     */
    @JSONField(name = "creator_id")
    private Long creatorId;

    /**
     * 组套创建科室id
     */
    @JSONField(name = "department_id")
    private Long departmentId;

    /**
     * 组套创建时间
     */
    @JSONField(name = "create_time")
    private Long createTime;

    /**
     * 组套临床印象
     */
    @JSONField(name = "clinical_impression")
    private String clinicalImpression;

    /**
     * 组套临床诊断
     */
    @JSONField(name = "clinical_diagnosis")
    private String clinicalDiagnosis;

    /**
     * 组套目标和要求
     */
    @JSONField(name = "goal_and_requirement")
    private String goalAndRequirement;

    @JSONField(name = "items")
    private List<ApplyItemTemplate> items;
    /**
     * 组套备注
     */
    @JSONField(name = "remark")
    private String remark;

    @JSONField(name = "valid")
    private Integer valid;

    @JsonSetter("id")
    public void setId(Long id) {
        this.id = id;
    }

    @JsonSetter("code")
    public void setCode(String code) {
        this.code = code;
    }

    @JsonSetter("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonSetter("category")
    public void setCategory(Integer category) {
        this.category = category;
    }

    @JsonSetter("document_category")
    public void setDocumentCategory(Integer documentCategory) {
        this.documentCategory = documentCategory;
    }

    @JsonSetter("service_object")
    public void setServiceObject(Integer serviceObject) {
        this.serviceObject = serviceObject;
    }

    @JsonSetter("suitable_range")
    public void setSuitableRange(Integer suitableRange) {
        this.suitableRange = suitableRange;
    }

    @JsonSetter("creator_id")
    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    @JsonSetter("department_id")
    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    @JsonSetter("create_time")
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    @JsonSetter("clinical_impression")
    public void setClinicalImpression(String clinicalImpression) {
        this.clinicalImpression = clinicalImpression;
    }

    @JsonSetter("clinical_diagnosis")
    public void setClinicalDiagnosis(String clinicalDiagnosis) {
        this.clinicalDiagnosis = clinicalDiagnosis;
    }

    @JsonSetter("goal_and_requirement")
    public void setGoalAndRequirement(String goalAndRequirement) {
        this.goalAndRequirement = goalAndRequirement;
    }

    @JsonSetter("valid")
    public void setValid(Integer valid) {
        this.valid = valid;
    }

    @JsonSetter("remark")
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static ApplyGroup convert(top.itcat.rpc.service.model.ApplyGroup rpcbean) {
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
        if (rpcbean.isSetItems()) {
            bean.setItems(rpcbean.getItems().parallelStream()
                    .map(ApplyItemTemplate::convert)
                    .collect(Collectors.toList()));
        }
        if (rpcbean.isSetValid()) {
            bean.setValid(rpcbean.getValid());
        }
        return bean;
    }

    public static top.itcat.rpc.service.model.ApplyGroup convertRPCBean(ApplyGroup bean) {
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
        if (bean.getServiceObject() != null) {
            rpcbean.setSuitableRange(SuitableRangeEnum.findByValue(bean.getSuitableRange()));
        }
        if (bean.getDepartmentId() != null && bean.getDepartmentId() != 0) {
            rpcbean.setDepartmentId(bean.getDepartmentId());
        }

        if (bean.getCreatorId() != null && bean.getCreatorId() != 0) {
            rpcbean.setCreatorId(bean.getCreatorId());
        }

        if (bean.getCreateTime() != null) {
            rpcbean.setCreateTime(bean.getCreateTime());
        }
        rpcbean.setClinicalImpression(bean.getClinicalImpression());
        rpcbean.setClinicalDiagnosis(bean.getClinicalDiagnosis());
        rpcbean.setGoalAndRequirement(bean.getGoalAndRequirement());
        rpcbean.setRemark(bean.getRemark());
        if (bean.getItems() != null) {
            rpcbean.setItems(bean.getItems().
                    parallelStream().map(ApplyItemTemplate::convertRPCBean).
                    collect(Collectors.toList()));
        }
        if (bean.getValid() != null) {
            rpcbean.setValid(bean.getValid());
        }
        if (bean.getSuitableRange() != null) {
            rpcbean.setSuitableRange(SuitableRangeEnum.findByValue(bean.getSuitableRange()));
        }
        return rpcbean;
    }

}
