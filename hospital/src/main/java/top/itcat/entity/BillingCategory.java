package top.itcat.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 结算类别
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-28
 */
@Data
@Accessors(chain = true)
@TableName("billing_category")
public class BillingCategory extends Model<BillingCategory> {

    private static final long serialVersionUID = 1L;

    /**
     * 结算类别id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 结算类别名称 自费、医保、新农合
     */
    private String name;
    /**
     * 结算类别折扣
     */
    private Double discount;
    /**
     * 结算类别有效性?
     */
    private Integer valid;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public static BillingCategory convert(top.itcat.rpc.service.model.BillingCategory rpcbean) {
        BillingCategory bean = new BillingCategory();
        if (rpcbean.isSetId()) {
            bean.setId(rpcbean.getId());
        }
        if (rpcbean.isSetName()) {
            bean.setName(rpcbean.getName());
        }
        if (rpcbean.isSetDiscount()) {
            bean.setDiscount(rpcbean.getDiscount());
        }
        if (rpcbean.isSetValid()) {
            bean.setValid(rpcbean.getValid());
        }

        return bean;
    }

    public static top.itcat.rpc.service.model.BillingCategory convertRPCBean(BillingCategory bean) {
        top.itcat.rpc.service.model.BillingCategory rpcbean = new top.itcat.rpc.service.model.BillingCategory();
        if (bean.getId() != null) {
            rpcbean.setId(bean.getId());
        }
        rpcbean.setName(bean.getName());
        if (bean.getDiscount() != null) {
            rpcbean.setDiscount(bean.getDiscount());
        }
        if (bean.getValid() != null) {
            rpcbean.setValid(bean.getValid());
        }

        return rpcbean;
    }

}