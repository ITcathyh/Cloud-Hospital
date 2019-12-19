package top.itcat.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;
import top.itcat.rpc.service.model.NonmedicalChargeCategoryEnum;

import java.io.Serializable;

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
@TableName("nonmedical_charge")
public class NonmedicalCharge extends Model<NonmedicalCharge> {

    private static final long serialVersionUID = 1L;

    /**
     * 非药品收费项目id
     */
    @TableId(value = "id", type = IdType.AUTO)
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
     * 非药品收费项目名�?
     */
    private String name;
    /**
     * 非药品收费项目规�?
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
    @TableField("charge_subject_id")
    private Long chargeSubjectId;
    /**
     * 非药品收费项目执行科室id
     */
    @TableField("department_id")
    private Long departmentId;
    /**
     * 非药品收费项目描述
     */
    private String description;
    /**
     * 非药品收费项目有效性
     */
    private Integer valid;


    @Override
    protected Serializable pkVal() {
        return this.id;
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
            bean.setChargeSubjectId(rpcbean.getChargeSubjectId());
        }
        if (rpcbean.isSetDepartmentId()) {
            bean.setDepartmentId(rpcbean.getDepartmentId());
        }
        if (rpcbean.isSetDescription()) {
            bean.setDescription(rpcbean.getDescription());
        }
        if (rpcbean.isSetPinyin()) {
            bean.setPhonetic(rpcbean.getPinyin());
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
        rpcbean.setName(bean.getName());
        rpcbean.setFormat(bean.getFormat());
        if (bean.getPrice() != null) {
            rpcbean.setPrice(bean.getPrice());
        }
        if (bean.getCategory() != null) {
            rpcbean.setCategory(NonmedicalChargeCategoryEnum.findByValue(bean.getCategory()));
        }
        if (bean.getChargeSubjectId() != null) {
            rpcbean.setChargeSubjectId(bean.getChargeSubjectId());
        }
        if (bean.getDepartmentId() != null) {
            rpcbean.setDepartmentId(bean.getDepartmentId());
        }
        rpcbean.setDescription(bean.getDescription());
        rpcbean.setPinyin(bean.getPhonetic());
        rpcbean.setValid(bean.getValid());

        if (bean.getChargeSubjectId() != null) {
            rpcbean.setChargeSubjectId(bean.getChargeSubjectId());
        }

        return rpcbean;
    }
}
