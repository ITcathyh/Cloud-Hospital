package top.itcat.entity.charge;

import lombok.Data;
import lombok.experimental.Accessors;

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
public class BillingCategory {

    private static final long serialVersionUID = 1L;

    /**
     * 结算类别id
     */
    private Long id;
    /**
     * 结算类别名称 自费、医保、新农合
     */
    private String name;
    /**
     * 结算类别折扣 单位%
     */
    private Double discount;
    /**
     * 结算类别有效性
     */
    private Integer valid;

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