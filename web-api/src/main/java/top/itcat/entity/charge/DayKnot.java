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
public class DayKnot {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;
    /**
     * �?始时�?
     */
    @JSONField(name = "start_time")
    private Long startTime;
    /**
     * 截止时间
     */
    @JSONField(name = "end_time")
    private Long endTime;
    /**
     * 总金额
     */
    @JSONField(name = "charge_amount")
    private Double chargeAmount;
    /**
     * 是否审核通过
     */
    @JSONField(name = "check_through")
    private Integer checkThrough;
    /**
     * 操作员id
     */
    @JSONField(name = "operator_id")
    private Long operatorId;
    @JSONField(name = "operator_name")
    private String operatorName;
    /**
     * 是否有效
     */
    private Integer valid;

    @JsonSetter("start_time")
    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    @JsonSetter("end_time")
    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    @JsonSetter("charge_amount")
    public void setChargeAmount(Double chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    @JsonSetter("check_through")
    public void setCheckThrough(Integer checkThrough) {
        this.checkThrough = checkThrough;
    }

    @JsonSetter("operator_id")
    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public static DayKnot convert(top.itcat.rpc.service.model.DayKnot rpcbean) {
        DayKnot bean = new DayKnot();
        if (rpcbean.isSetId()) {
            bean.setId(rpcbean.getId());
        }
        if (rpcbean.isSetStartTime()) {
            bean.setStartTime(rpcbean.getStartTime());
        }
        if (rpcbean.isSetEndTime()) {
            bean.setEndTime(rpcbean.getEndTime());
        }
        if (rpcbean.isSetChargeAmount()) {
            bean.setChargeAmount(rpcbean.getChargeAmount());
        }
        if (rpcbean.isSetCheckThrough()) {
            bean.setCheckThrough(rpcbean.getCheckThrough());
        }
        if (rpcbean.isSetOperatorId()) {
            bean.setOperatorId(rpcbean.getOperatorId());
        }

        if (rpcbean.isSetValid()) {
            bean.setValid(rpcbean.getValid());
        }

        return bean;
    }

    public static top.itcat.rpc.service.model.DayKnot convertRPCBean(DayKnot bean) {
        top.itcat.rpc.service.model.DayKnot rpcbean = new top.itcat.rpc.service.model.DayKnot();
        if (bean.getId() != null) {
            rpcbean.setId(bean.getId());
        }
        if (bean.getStartTime() != null) {
            rpcbean.setStartTime(bean.getStartTime());
        }
        if (bean.getEndTime() != null) {
            rpcbean.setEndTime(bean.getEndTime());
        }
        if (bean.getChargeAmount() != null) {
            rpcbean.setChargeAmount(bean.getChargeAmount());
        }
        if (bean.getCheckThrough() != null) {
            rpcbean.setCheckThrough(bean.getCheckThrough());
        }
        if (bean.getOperatorId() != null) {
            rpcbean.setOperatorId(bean.getOperatorId());
        }
        if (bean.getValid() != null) {
            rpcbean.setValid(bean.getValid());
        }

        return rpcbean;
    }

}