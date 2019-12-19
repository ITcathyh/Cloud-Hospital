package top.itcat.entity.registration;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 挂号级别
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-28
 */
@Data
@Accessors(chain = true)
public class RegistrationLevel {

    private static final long serialVersionUID = 1L;

    /**
     * 挂号级别id
     */
    private Long id;
    /**
     * 挂号级别编号
     */
    private String code;
    /**
     * 挂号级别名称 普通号、专家号、急诊号?
     */
    private String name;
    /**
     * 挂号级别是否默认
     */
    @JSONField(name = "default")
    private Integer showDefault;

    /**
     * 挂号级别显示顺序性
     */
    @JSONField(name = "display_seq_num")
    private Long displaySeqNum;
    /**
     * 挂号级别价格
     */
    private Double price;
    /**
     * 挂号级别有效性
     */
    private Integer valid;

    @JsonSetter("is_default")
    public void setShowDefault(Integer showDefault) {
        this.showDefault = showDefault;
    }

    @JsonSetter("display_seq_num")
    public void setDisplaySeqNum(Long displaySeqNum) {
        this.displaySeqNum = displaySeqNum;
    }

    public static RegistrationLevel convert(top.itcat.rpc.service.model.RegisterationLevel rpcbean) {
        RegistrationLevel bean = new RegistrationLevel();
        if (rpcbean.isSetId()) {
            bean.setId(rpcbean.getId());
        }
        if (rpcbean.isSetCode()) {
            bean.setCode(rpcbean.getCode());
        }
        if (rpcbean.isSetName()) {
            bean.setName(rpcbean.getName());
        }
        if (rpcbean.isSetIsDefault()) {
            bean.setShowDefault(rpcbean.isIsDefault() ? 1 : 0);
        }
        if (rpcbean.isSetDisplaySeqNum()) {
            bean.setDisplaySeqNum((long) rpcbean.getDisplaySeqNum());
        }
        if (rpcbean.isSetPrice()) {
            bean.setPrice(rpcbean.getPrice());
        }

        return bean;
    }

    public static top.itcat.rpc.service.model.RegisterationLevel convertRPCBean(RegistrationLevel bean) {
        top.itcat.rpc.service.model.RegisterationLevel rpcbean = new top.itcat.rpc.service.model.RegisterationLevel();

        if (bean.getId() != null) {
            rpcbean.setId(bean.getId());
        }

        rpcbean.setCode(bean.getCode());
        rpcbean.setName(bean.getName());
        if (bean.getShowDefault() != null) {
            rpcbean.setIsDefault(bean.getShowDefault() == 1);
        }
        if (bean.getDisplaySeqNum() != null) {
            rpcbean.setDisplaySeqNum(bean.getDisplaySeqNum().intValue());
        }
        if (bean.getPrice() != null) {
            rpcbean.setPrice(bean.getPrice());
        }

        return rpcbean;
    }

}