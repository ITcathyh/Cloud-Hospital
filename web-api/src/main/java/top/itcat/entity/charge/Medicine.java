package top.itcat.entity.charge;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.experimental.Accessors;
import top.itcat.rpc.service.model.MedicineCategoryEnum;
import top.itcat.rpc.service.model.MedicineUsageEnum;

@Data
@Accessors(chain = true)
public class Medicine {
    private static final long serialVersionUID = 1;

    /**
     * 药品id
     */
    @JSONField(name = "id")
    private Long id;

    /**
     * 药品编码
     */
    @JSONField(name = "code")
    private String code;

    /**
     * 药品名称
     */
    @JSONField(name = "name")
    private String name;

    /**
     * 药品拼音首字母
     */
    @JSONField(name = "phonetic")
    private String phonetic;

    /**
     * 药品规格
     */
    @JSONField(name = "specification")
    private String specification;

    /**
     * 药品剂型
     */
    @JSONField(name = "form")
    private String form;

    /**
     * 药品包装单位 g、ml……
     */
    @JSONField(name = "package_unit")
    private String packageUnit;

    /**
     * 药品包装数量
     */
    @JSONField(name = "package_num")
    private Integer packageNum;

    /**
     * 药品单价
     */
    @JSONField(name = "price")
    private Double price;

    /**
     * 药品厂家
     */
    @JSONField(name = "factory")
    private String factory;

    /**
     * 药品类型 西药、中成药、中草药
     */
    @JSONField(name = "category")
    private Integer category;

    /**
     * 药品用法    口服、静脉滴注、皮下注射……
     */
    @JSONField(name = "usage")
    private Integer usage;

    /**
     * 药品用量
     */
    @JSONField(name = "dosage")
    private Double dosage;

    /**
     * 药品用量单位 g、ml……
     */
    @JSONField(name = "unit")
    private String unit;

    /**
     * 药品频次 一日一次、一日两次、一日三次……
     */
    @JSONField(name = "frequency")
    private String frequency;

    /**
     * 药品用药嘱托
     */
    @JSONField(name = "advice")
    private String advice;

    /**
     * 药品有效性
     */
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

    @JsonSetter("phonetic")
    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    @JsonSetter("specification")
    public void setSpecification(String specification) {
        this.specification = specification;
    }

    @JsonSetter("form")
    public void setForm(String form) {
        this.form = form;
    }

    @JsonSetter("package_unit")
    public void setPackageUnit(String packageUnit) {
        this.packageUnit = packageUnit;
    }

    @JsonSetter("package_num")
    public void setPackageNum(Integer packageNum) {
        this.packageNum = packageNum;
    }

    @JsonSetter("price")
    public void setPrice(Double price) {
        this.price = price;
    }

    @JsonSetter("factory")
    public void setFactory(String factory) {
        this.factory = factory;
    }

    @JsonSetter("category")
    public void setCategory(Integer category) {
        this.category = category;
    }

    @JsonSetter("usage")
    public void setUsage(Integer usage) {
        this.usage = usage;
    }

    @JsonSetter("dosage")
    public void setDosage(Double dosage) {
        this.dosage = dosage;
    }

    @JsonSetter("unit")
    public void setUnit(String unit) {
        this.unit = unit;
    }

    @JsonSetter("frequency")
    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    @JsonSetter("advice")
    public void setAdvice(String advice) {
        this.advice = advice;
    }

    @JsonSetter("valid")
    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public static Medicine convert(top.itcat.rpc.service.model.Medicine rpcbean) {
        Medicine bean = new Medicine();
        if (rpcbean.isSetId()) {
            bean.setId(rpcbean.getId());
        }
        if (rpcbean.isSetCode()) {
            bean.setCode(rpcbean.getCode());
        }
        if (rpcbean.isSetName()) {
            bean.setName(rpcbean.getName());
        }
        if (rpcbean.isSetPinyin()) {
            bean.setPhonetic(rpcbean.getPinyin());
        }
        if (rpcbean.isSetSpecification()) {
            bean.setSpecification(rpcbean.getSpecification());
        }
        if (rpcbean.isSetForm()) {
            bean.setForm(rpcbean.getForm());
        }
        if (rpcbean.isSetPackageUnit()) {
            bean.setPackageUnit(rpcbean.getPackageUnit());
        }
        if (rpcbean.isSetPackageNum()) {
            bean.setPackageNum(rpcbean.getPackageNum());
        }
        if (rpcbean.isSetPrice()) {
            bean.setPrice(rpcbean.getPrice());
        }
        if (rpcbean.isSetFactory()) {
            bean.setFactory(rpcbean.getFactory());
        }
        if (rpcbean.isSetCategory()) {
            bean.setCategory(rpcbean.getCategory().getValue());
        }
        if (rpcbean.isSetUsage()) {
            bean.setUsage(rpcbean.getUsage().getValue());
        }
        if (rpcbean.isSetDosage()) {
            bean.setDosage(rpcbean.getDosage());
        }
        if (rpcbean.isSetUnit()) {
            bean.setUnit(rpcbean.getUnit());
        }
        if (rpcbean.isSetFrequency()) {
            bean.setFrequency(rpcbean.getFrequency());
        }
        if (rpcbean.isSetAdvice()) {
            bean.setAdvice(rpcbean.getAdvice());
        }
        if (rpcbean.isSetValid()) {
            bean.setValid(rpcbean.getValid());
        }
        return bean;
    }

    public static top.itcat.rpc.service.model.Medicine convertRPCBean(Medicine bean) {
        top.itcat.rpc.service.model.Medicine rpcbean = new top.itcat.rpc.service.model.Medicine();
        if (bean.getId() != null) {
            rpcbean.setId(bean.getId());
        }
        rpcbean.setCode(bean.getCode());
        rpcbean.setName(bean.getName());
        rpcbean.setPinyin(bean.getPhonetic());
        if (bean.getSpecification() != null) {
            rpcbean.setSpecification(bean.getSpecification());
        }
        if (bean.getForm() != null) {
            rpcbean.setForm(bean.getForm());
        }
        if (bean.getPackageUnit() != null) {
            rpcbean.setPackageUnit(bean.getPackageUnit());
        }
        if (bean.getPackageNum() != null) {
            rpcbean.setPackageNum(bean.getPackageNum());
        }
        if (bean.getPrice() != null) {
            rpcbean.setPrice(bean.getPrice());
        }
        rpcbean.setFactory(bean.getFactory());
        if (bean.getCategory() != null) {
            rpcbean.setCategory(MedicineCategoryEnum.findByValue(bean.getCategory()));
        }
        if (bean.getUsage() != null) {
            rpcbean.setUsage(MedicineUsageEnum.findByValue(bean.getUsage()));
        }
        rpcbean.setDosage(bean.getDosage());
        if (bean.getUnit() != null) {
            rpcbean.setUnit(bean.getUnit());
        }
        if (bean.getFrequency() != null) {
            rpcbean.setFrequency(bean.getFrequency());
        }
        rpcbean.setAdvice(bean.getAdvice());
        return rpcbean;
    }
}