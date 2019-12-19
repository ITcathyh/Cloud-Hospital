package top.itcat.entity.registrantion;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;
import top.itcat.rpc.service.model.WorkDayEnum;
import top.itcat.rpc.service.model.WorkTimeEnum;

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
@TableName("schedule_rule")
public class ScheduleRule extends Model<ScheduleRule> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Integer day;
    /**
     * id
     */
    @TableField("doctor_id")
    private Long doctorId;
    /**
     * id
     */
    @TableField("department_id")
    private Long departmentId;
    @TableField("start_time")
    private Long startTime;
    @TableField("end_time")
    private Long endTime;
    @TableField("noon_break")
    private Integer noonBreak;
    /**
     * id
     */
    @TableField("registration_level_id")
    private Long registrationLevelId;
    @TableField("limit_number")
    private Integer limitNumber;
    @TableField("operation_date")
    private Integer operationDate;
    private Integer valid;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }


    public static ScheduleRule convert(top.itcat.rpc.service.model.ScheduleRule rpcbean) {
        if (rpcbean == null) {
            return null;
        }

        ScheduleRule bean = new ScheduleRule();
        if (rpcbean.isSetId()) {
            bean.setId(rpcbean.getId());
        }
        if (rpcbean.isSetDay()) {
            bean.setDay(rpcbean.getDay().getValue());
        }
        if (rpcbean.isSetDoctorId()) {
            bean.setDoctorId(rpcbean.getDoctorId());
        }
        if (rpcbean.isSetDepartmentId()) {
            bean.setDepartmentId(rpcbean.getDepartmentId());
        }
        if (rpcbean.isSetStartTime()) {
            bean.setStartTime(rpcbean.getStartTime());
        }
        if (rpcbean.isSetEndTime()) {
            bean.setEndTime(rpcbean.getEndTime());
        }
        if (rpcbean.isSetNoonBreak()) {
            bean.setNoonBreak(rpcbean.getNoonBreak().getValue());
        }
        if (rpcbean.isSetRegistrationLevelId()) {
            bean.setRegistrationLevelId(rpcbean.getRegistrationLevelId());
        }
        if (rpcbean.isSetLimitNumber()) {
            bean.setLimitNumber(rpcbean.getLimitNumber());
        }
        if (rpcbean.isSetOperationDate()) {
            bean.setOperationDate(rpcbean.getOperationDate());
        }
        if (rpcbean.isSetValid()) {
            bean.setValid(rpcbean.getValid());
        }

        return bean;
    }

    public static top.itcat.rpc.service.model.ScheduleRule convertRPCBean(ScheduleRule bean) {
        top.itcat.rpc.service.model.ScheduleRule rpcbean = new top.itcat.rpc.service.model.ScheduleRule();
        if (bean.getId() != null) {
            rpcbean.setId(bean.getId());
        }
        if (bean.getDay() != null) {
            rpcbean.setDay(WorkDayEnum.findByValue(bean.getDay()));
        }
        if (bean.getId() != null) {
            rpcbean.setDoctorId(bean.getDoctorId());
        }
        if (bean.getDepartmentId() != null) {
            rpcbean.setDepartmentId(bean.getDepartmentId());
        }
        if (bean.getStartTime() != null) {
            rpcbean.setStartTime(bean.getStartTime());
        }
        if (bean.getEndTime() != null) {
            rpcbean.setEndTime(bean.getEndTime());
        }
        if (bean.getNoonBreak() != null) {
            rpcbean.setNoonBreak(WorkTimeEnum.findByValue(bean.getNoonBreak()));
        }
        if (bean.getRegistrationLevelId() != null) {
            rpcbean.setRegistrationLevelId(bean.getRegistrationLevelId());
        }
        if (bean.getLimitNumber() != null) {
            rpcbean.setLimitNumber(bean.getLimitNumber());
        }
        if (bean.getOperationDate() != null) {
            rpcbean.setOperationDate(bean.getOperationDate());
        }
        if (bean.getValid() != null) {
            rpcbean.setValid(bean.getValid());
        }

        return rpcbean;
    }
}