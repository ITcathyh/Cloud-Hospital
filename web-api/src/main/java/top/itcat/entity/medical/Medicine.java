package top.itcat.entity.medical;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;
import top.itcat.rpc.service.model.MedicineCategoryEnum;
import top.itcat.rpc.service.model.MedicineUsageEnum;

import java.io.Serializable;

/**
 * <p>
 * 药品
 * </p>
 *
 * @author ITcathyh
 * @since 2019-06-05
 */
@Data
@Accessors(chain = true)
public class Medicine implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 药品id
     */
    private Long id;
    /**
     * 药品编码
     */
    private String code;
    /**
     * 药品名称
     */
    private String name;
    /**
     * 药品拼音首字母
     */
    @JSONField(name = "pinyin")
    private String phonetic;
    /**
     * 药品规格
     */
    private String specification;
    /**
     * 药品剂型
     */
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
    private Double price;
    /**
     * 药品厂家
     */
    private String factory;
    /**
     * 药品类型 西药、中成药、中草药
     */
    private Integer category;
    /**
     * 药品用法  口服、静脉滴注、皮下注射……
     */
    private Integer usage;

    @JSONField(name = "day_usage")
    private Integer dayUsage;
    /**
     * 药品用量
     */
    private Double dosage;
    /**
     * 药品用量单位 g、ml……
     */
    private String unit;
    /**
     * 药品频次 一日一次、一日两次、一日三次……
     */
    private String frequency;
    /**
     * 药品用药嘱托
     */
    private String advice;
    /**
     * 药品有效性
     */
    private Integer valid;


    public static Medicine convert(top.itcat.rpc.service.model.Medicine rpcbean) {
        if (rpcbean == null) {
            return null;
        }
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
        if (rpcbean.isSetDayUsage()) {
            bean.setDayUsage(rpcbean.getDayUsage());
        }
        if (rpcbean.isSetPinyin()) {
            bean.setPhonetic(rpcbean.getPinyin());
        }

        return bean;
    }

    public static top.itcat.rpc.service.model.Medicine convertRPCBean(Medicine bean) {
        if (bean == null) {
            return null;
        }
        top.itcat.rpc.service.model.Medicine rpcbean = new top.itcat.rpc.service.model.Medicine();
        if (bean.getId() != null) {
            rpcbean.setId(bean.getId());
        }
        rpcbean.setCode(bean.getCode());
        rpcbean.setName(bean.getName());
        rpcbean.setPinyin(bean.getPhonetic());
        rpcbean.setSpecification(bean.getSpecification());
        rpcbean.setForm(bean.getForm());
        rpcbean.setPackageUnit(bean.getPackageUnit());
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
        if (bean.getDosage() != null) {
            rpcbean.setDosage(bean.getDosage());
        }
        rpcbean.setUnit(bean.getUnit());
        rpcbean.setFrequency(bean.getFrequency());
        rpcbean.setAdvice(bean.getAdvice());
        if (bean.getValid() != null) {
            rpcbean.setValid(bean.getValid());
        }

        return rpcbean;
    }

}