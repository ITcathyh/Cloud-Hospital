package top.itcat.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
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
@TableName("day_knot")
public class DayKnot extends Model<DayKnot> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * �?始时�?
     */
    @TableField("start_time")
    private Long startTime;
    /**
     * 截止时间
     */
    @TableField("end_time")
    private Long endTime;
    /**
     * 总金�?
     */
    @TableField("charge_amount")
    private Double chargeAmount;
    /**
     * 是否审核通过
     */
    @TableField("check_through")
    private Integer checkThrough;
    /**
     * 操作员id
     */
    @TableField("operator_id")
    private Long operatorId;
    /**
     * 是否有效
     */
    private Integer valid;


    @Override
    protected Serializable pkVal() {
        return this.id;
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