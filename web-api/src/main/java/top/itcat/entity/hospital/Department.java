package top.itcat.entity.hospital;

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
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;
    private String code;
    private String name;
    /**
     * 分类
     */
    private Integer classification;
    /**
     * 类别
     */
    private String category;
    private Integer valid;

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