package top.itcat.entity.charge;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.experimental.Accessors;
import top.itcat.rpc.service.model.NonmedicalChargeCategoryEnum;

/**
 * <p>
 * 非药品收费项目
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-28
 */
@Data
@Accessors(chain = true)
public class NonmedicalCharge {

    private static final long serialVersionUID = 1L;

    /**
     * 非药品收费项目id
     */
    private Long id;
    /**
     * 非药品收费项目编号
     */
    private String code;
    /**
     * 非药品收费项目拼音首字母
     */
    private String phonetic;
    /**
     * 非药品收费项目名称
     */
    private String name;
    /**
     * 非药品收费项目规格
     */
    private String format;
    /**
     * 非药品收费项目价格
     */
    private Double price;
    /**
     * 非药品收费项目分类 检查检验、处置
     */
    private Integer category;
    /**
     * 非药品收费项目费用科目id
     */
    @JSONField(name = "charge_subject_id")
    private Long chargeRecordId;
    /**
     * 非药品收费项目执行科室id
     */
    @JSONField(name = "department_id")
    private Long departmentId;
    /**
     * 非药品收费项目描述
     */
    private String description;
    /**
     * 非药品收费项目有效性
     */
    private Integer valid;

    @JsonSetter("charge_subject_id")
    public void setChargeRecordId(Long chargeRecordId) {
        this.chargeRecordId = chargeRecordId;
    }

    @JsonSetter("department_id")
    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public static NonmedicalCharge convert(top.itcat.rpc.service.model.NonmedicalCharge rpcbean) {
        NonmedicalCharge bean = new NonmedicalCharge();
        if (rpcbean.isSetId()) {
            bean.setId(rpcbean.getId());
        }
        if (rpcbean.isSetCode()) {
            bean.setCode(rpcbean.getCode());
        }
        if (rpcbean.isSetPinyin()) {
            bean.setPhonetic(rpcbean.getPinyin());
        }
        if (rpcbean.isSetName()) {
            bean.setName(rpcbean.getName());
        }
        if (rpcbean.isSetFormat()) {
            bean.setFormat(rpcbean.getFormat());
        }
        if (rpcbean.isSetPrice()) {
            bean.setPrice(rpcbean.getPrice());
        }
        if (rpcbean.isSetCategory()) {
            bean.setCategory(rpcbean.getCategory().getValue());
        }
        if (rpcbean.isSetChargeSubjectId()) {
            bean.setChargeRecordId(rpcbean.getChargeSubjectId());
        }
        if (rpcbean.isSetDepartmentId()) {
            bean.setDepartmentId(rpcbean.getDepartmentId());
        }
        if (rpcbean.isSetDescription()) {
            bean.setDescription(rpcbean.getDescription());
        }
        if (rpcbean.isSetValid()) {
            bean.setValid(rpcbean.getValid());
        }

        return bean;
    }

    public static top.itcat.rpc.service.model.NonmedicalCharge convertRPCBean(NonmedicalCharge bean) {
        top.itcat.rpc.service.model.NonmedicalCharge rpcbean = new top.itcat.rpc.service.model.NonmedicalCharge();
        if (bean.getId() != null) {
            rpcbean.setId(bean.getId());
        }
        rpcbean.setCode(bean.getCode());
        rpcbean.setPinyin(bean.getPhonetic());
        rpcbean.setName(bean.getName());
        if (bean.getFormat() != null) {
            rpcbean.setFormat(bean.getFormat());
        }
        rpcbean.setPrice(bean.getPrice());
        if (bean.getCategory() != null) {
            rpcbean.setCategory(NonmedicalChargeCategoryEnum.findByValue(bean.getCategory()));
        }

        if (bean.getChargeRecordId() != null) {
            rpcbean.setChargeSubjectId(bean.getChargeRecordId());
        }

        if (bean.getDepartmentId() != null) {
            rpcbean.setDepartmentId(bean.getDepartmentId());
        }

        rpcbean.setDescription(bean.getDescription());

        if (bean.getValid() != null) {
            rpcbean.setValid(bean.getValid());

        }

        return rpcbean;
    }
}