package top.itcat.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
@TableName("registration_level")
public class RegistrationLevel extends Model<RegistrationLevel> {

    private static final long serialVersionUID = 1L;

    /**
     * 挂号级别id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 挂号级别编号
     */
    private String code;
    /**
     * 挂号级别名称 普通号、专家号、急诊号
     */
    private String name;
    /**
     * 挂号级别是否默认
     */
    @TableField("default")
    private Integer showDefault;
    /**
     * 挂号级别显示顺序号
     */
    @TableField("display_seq_num")
    private Long displaySeqNum;
    /**
     * 挂号级别价格
     */
    private Double price;
    /**
     * 挂号级别有效性
     */
    private Integer valid;


    @Override
    protected Serializable pkVal() {
        return this.id;
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
        if (rpcbean.isSetValid()) {
            bean.setValid(rpcbean.getValid());
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
        if (bean.getValid() != null) {
            rpcbean.setValid(bean.getValid());
        }

        return rpcbean;
    }

}