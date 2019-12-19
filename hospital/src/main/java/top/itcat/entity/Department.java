package top.itcat.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;
import top.itcat.rpc.service.model.DepartClassificationEnum;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-27
 */
@Data
@Accessors(chain = true)
public class Department extends Model<Department> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String code;
    private String name;
    /**
     *
     */
    private Integer classification;
    /**
     *
     */
    private String category;
    private Integer valid;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public static Department convert(top.itcat.rpc.service.model.Department rpcbean) {
        Department bean = new Department();
        if (rpcbean.isSetId()) {
            bean.setId(rpcbean.getId());
        }
        if (rpcbean.isSetCode()) {
            bean.setCode(rpcbean.getCode());
        }
        if (rpcbean.isSetName()) {
            bean.setName(rpcbean.getName());
        }
        if (rpcbean.isSetClassification()) {
            bean.setClassification(rpcbean.getClassification().getValue());
        }
        if (rpcbean.isSetCategory()) {
            bean.setCategory(rpcbean.getCategory());
        }
        if (rpcbean.isSetValid()) {
            bean.setValid(rpcbean.getValid());
        }

        return bean;
    }

    public static top.itcat.rpc.service.model.Department convertRPCBean(Department bean) {
        top.itcat.rpc.service.model.Department rpcbean = new top.itcat.rpc.service.model.Department();
        if (bean.getId() != null) {
            rpcbean.setId(bean.getId());
        }
        rpcbean.setCode(bean.getCode());
        rpcbean.setName(bean.getName());
        if (bean.getClassification() != null) {
            rpcbean.setClassification(DepartClassificationEnum.findByValue(bean.getClassification()));
        }
        if (bean.getCategory() != null) {
            rpcbean.setCategory(bean.getCategory());
        }
        if (bean.getValid() != null) {
            rpcbean.setValid(bean.getValid());
        }

        return rpcbean;
    }
}