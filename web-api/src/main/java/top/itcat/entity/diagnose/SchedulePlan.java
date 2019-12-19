package top.itcat.entity.diagnose;

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
 * @since 2019-05-26
 */
@Data
@Accessors(chain = true)
public class SchedulePlan {

    private static final long serialVersionUID = 1L;

    private Long id;
    @JSONField(name = "schedule_id")
    private Long scheduleId;
    @JSONField(name = "start_time")
    private Long startTime;
    @JSONField(name = "end_time")
    private Long endTime;
    private Integer remain;
    private Integer valid;
    @JSONField(name = "rule")
    private ScheduleRule scheduleRule;

    @JsonSetter("rule")
    public void setScheduleRule(ScheduleRule scheduleRule) {
        this.scheduleRule = scheduleRule;
    }

    @JsonSetter("schedule_id")
    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    @JsonSetter("start_time")
    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    @JsonSetter("end_time")
    public void setEndTime(Long endTime) {
        this.endTime = endTime;
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

        return rpcbean;
    }
}