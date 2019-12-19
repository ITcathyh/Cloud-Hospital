package top.itcat.entity.charge;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-29
 */
@Data
@Accessors(chain = true)
public class DayKnotItem {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;
    /**
     * 收费项目id
     */
    @JSONField(name = "charge_item_id")
    private Long chargeItemId;
    @JSONField(name = "charge_item")
    private ChargeItem chargeItem;
    /**
     * 日结单id
     */
    @JSONField(name = "day_knot_id")
    private Long dayKnotId;
    /**
     * 费用科目id
     */
    @JSONField(name = "charge_subject_id")
    private Long chargeSubjectId;
    /**
     * 是否有效
     */
    private Integer valid;

    @JsonSetter("charge_item_id")
    public void setChargeItemId(Long chargeItemId) {
        this.chargeItemId = chargeItemId;
    }

    @JsonSetter("day_knot_id")
    public void setDayKnotId(Long dayKnotId) {
        this.dayKnotId = dayKnotId;
    }

    @JsonSetter("charge_subject_id")
    public void setChargeSubjectId(Long chargeSubjectId) {
        this.chargeSubjectId = chargeSubjectId;
    }

    public static DayKnotItem convert(top.itcat.rpc.service.model.DayKnotItem rpcbean) {
        DayKnotItem bean = new DayKnotItem();
        if (rpcbean.isSetId()) {
            bean.setId(rpcbean.getId());
        }
        if (rpcbean.isSetChargeItemId()) {
            bean.setChargeItemId(rpcbean.getChargeItemId());
        }
        if (rpcbean.isSetDayKnotId()) {
            bean.setDayKnotId(rpcbean.getDayKnotId());
        }
        if (rpcbean.isSetChargeSubjectId()) {
            bean.setChargeSubjectId(rpcbean.getChargeSubjectId());
        }
        if (rpcbean.isSetValid()) {
            bean.setValid(rpcbean.getValid());
        }

        return bean;
    }

    public static top.itcat.rpc.service.model.DayKnotItem convertRPCBean(DayKnotItem bean) {
        top.itcat.rpc.service.model.DayKnotItem rpcbean = new top.itcat.rpc.service.model.DayKnotItem();
        if (bean.getId() != null) {
            rpcbean.setId(bean.getId());
        }
        if (bean.getChargeItemId() != null) {
            rpcbean.setChargeItemId(bean.getChargeItemId());
        }
        if (bean.getDayKnotId() != null) {
            rpcbean.setDayKnotId(bean.getDayKnotId());
        }
        if (bean.getChargeSubjectId() != null) {
            rpcbean.setChargeSubjectId(bean.getChargeSubjectId());
        }
        if (bean.getValid() != null) {
            rpcbean.setValid(bean.getValid());
        }

        return rpcbean;
    }

}