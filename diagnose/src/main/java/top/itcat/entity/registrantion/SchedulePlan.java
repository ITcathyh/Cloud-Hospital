package top.itcat.entity.registrantion;

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
 *
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-26
 */
@Data
@Accessors(chain = true)
@TableName("schedule_plan")
public class SchedulePlan extends Model<SchedulePlan> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * id
     */
    @TableField("schedule_id")
    private Long scheduleId;
    @TableField("start_time")
    private Long startTime;
    @TableField("end_time")
    private Long endTime;
    private Integer remain;
    private Integer valid;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public static SchedulePlan convert(top.itcat.rpc.service.model.SchedulePlan rpcbean) {
        SchedulePlan bean = new SchedulePlan();
        if (rpcbean.isSetId()) {
            bean.setId(rpcbean.getId());
        }
        if (rpcbean.isSetScheduleId()) {
            bean.setScheduleId(rpcbean.getScheduleId());
        }
        if (rpcbean.isSetStartTime()) {
            bean.setStartTime(rpcbean.getStartTime());
        }
        if (rpcbean.isSetEndTime()) {
            bean.setEndTime(rpcbean.getEndTime());
        }
        if (rpcbean.isSetRemain()) {
            bean.setRemain(rpcbean.getRemain());
        }
        if (rpcbean.isSetValid()) {
            bean.setValid(rpcbean.getValid());
        }

        return bean;
    }

    public static top.itcat.rpc.service.model.SchedulePlan convertRPCBean(SchedulePlan bean) {
        top.itcat.rpc.service.model.SchedulePlan rpcbean = new top.itcat.rpc.service.model.SchedulePlan();
        if (bean.getId() != null) {
            rpcbean.setId(bean.getId());
        }
        if (bean.getScheduleId() != null) {
            rpcbean.setScheduleId(bean.getScheduleId());
        }
        if (bean.getStartTime() != null) {
            rpcbean.setStartTime(bean.getStartTime());
        }
        if (bean.getEndTime() != null) {
            rpcbean.setEndTime(bean.getEndTime());
        }
        if (bean.getRemain() != null) {
            rpcbean.setRemain(bean.getRemain());
        }
        if (bean.getValid() != null) {
            rpcbean.setValid(bean.getValid());
        }

        return rpcbean;
    }
}