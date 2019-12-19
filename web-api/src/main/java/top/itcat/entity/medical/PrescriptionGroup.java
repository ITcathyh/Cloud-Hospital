package top.itcat.entity.medical;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.experimental.Accessors;
import top.itcat.rpc.service.model.DocumentCategory;
import top.itcat.rpc.service.model.PrescriptionGroupCatalogEnum;
import top.itcat.rpc.service.model.ServiceObject;
import top.itcat.rpc.service.model.SuitableRangeEnum;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Accessors(chain = true)
public class PrescriptionGroup implements Serializable {

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

    @JSONField(name = "catalog")
    private Integer catalog;

    /**
     * 组套单据分类 0普诊1专家
     */
    @JSONField(name = "document_category")
    private Integer documentCategory;

    /**
     * 组套服务对象 0门诊……
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
     * 组套备注
     */
    @JSONField(name = "remark")
    private String remark;
    @JSONField(name = "items")
    private List<PrescriptionItemTemplate> items;

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

    @JsonSetter("remark")
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @JsonSetter("catalog")
    public void setCatalog(Integer catalog) {
        this.catalog = catalog;
    }

    public static PrescriptionGroup convert(top.itcat.rpc.service.model.PrescriptionGroup rpcbean) {
        PrescriptionGroup bean = new PrescriptionGroup();
        if (rpcbean.isSetId()) {
            bean.setId(rpcbean.getId());
        }
        if (rpcbean.isSetCode()) {
            bean.setCode(rpcbean.getCode());
        }
        if (rpcbean.isSetName()) {
            bean.setName(rpcbean.getName());
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
        if (rpcbean.isSetRemark()) {
            bean.setRemark(rpcbean.getRemark());
        }
        if (rpcbean.isSetItems()) {
            bean.setItems(rpcbean.getItems().parallelStream()
                    .map(PrescriptionItemTemplate::convert)
                    .collect(Collectors.toList()));
        }
        if (rpcbean.isSetCatalog()) {
            bean.setCatalog(rpcbean.getCatalog().getValue());
        }
        return bean;
    }

    public static top.itcat.rpc.service.model.PrescriptionGroup convertRPCBean(PrescriptionGroup bean) {
        top.itcat.rpc.service.model.PrescriptionGroup rpcbean = new top.itcat.rpc.service.model.PrescriptionGroup();
        if (bean.getId() != null) {
            rpcbean.setId(bean.getId());
        }
        rpcbean.setCode(bean.getCode());
        rpcbean.setName(bean.getName());
        if (bean.getDocumentCategory() != null) {
            rpcbean.setDocumentCategory(DocumentCategory.findByValue(bean.getDocumentCategory()));
        }
        if (bean.getServiceObject() != null) {
            rpcbean.setServiceObject(ServiceObject.findByValue(bean.getServiceObject()));
        }
        if (bean.getSuitableRange() != null) {
            rpcbean.setSuitableRange(SuitableRangeEnum.findByValue(bean.getSuitableRange()));
        }
        if (bean.getCreatorId() != null && bean.getCreatorId() != 0) {
            rpcbean.setCreatorId(bean.getCreatorId());
        }
        if (bean.getDepartmentId() != null && bean.getDepartmentId() != 0) {
            rpcbean.setDepartmentId(bean.getDepartmentId());
        }
        if (bean.getCreateTime() != null) {
            rpcbean.setCreateTime(bean.getCreateTime());
        }
        rpcbean.setRemark(bean.getRemark());

        if (bean.getItems() != null) {
            rpcbean.setItems(bean.getItems()
                    .parallelStream()
                    .map(PrescriptionItemTemplate::convertRPCBean)
                    .collect(Collectors.toList()));
        }

        if (bean.getCatalog() != null) {
            rpcbean.setCatalog(PrescriptionGroupCatalogEnum.findByValue(bean.getCatalog()));
        }
        return rpcbean;
    }

}
