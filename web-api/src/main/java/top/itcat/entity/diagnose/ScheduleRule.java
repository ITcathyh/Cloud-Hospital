package top.itcat.entity.diagnose;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.experimental.Accessors;
import top.itcat.entity.user.OutpatientDoctor;
import top.itcat.rpc.service.model.WorkDayEnum;
import top.itcat.rpc.service.model.WorkTimeEnum;

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
public class ScheduleRule {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;
    private Integer day;
    @JSONField(name = "doctor_id")
    private Long doctorId;
    @JSONField(name = "department_id")
    private Long departmentId;
    @JSONField(name = "start_time")
    private Long startTime;
    @JSONField(name = "end_time")
    private Long endTime;
    @JSONField(name = "noon_break")
    private Integer noonBreak;
    @JSONField(name = "registration_level_id")
    private Long registrationLevelId;
    @JSONField(name = "limit_number")
    private Integer limitNumber;
    @JSONField(name = "operation_date")
    private Integer operationDate;
    private Integer valid;
    @JSONField(name = "doctor_name")
    private String doctorName;
    @JSONField(name = "depart_name")
    private String departName;
    @JSONField(name = "doctor")
    private OutpatientDoctor doctor;

    @JsonSetter("doctor_id")
    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    @JsonSetter("department_id")
    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    @JsonSetter("start_time")
    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    @JsonSetter("end_time")
    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    @JsonSetter("noon_break")
    public void setNoonBreak(Integer noonBreak) {
        this.noonBreak = noonBreak;
    }

    @JsonSetter("registration_level_id")
    public void setRegistrationLevelId(Long registrationLevelId) {
        this.registrationLevelId = registrationLevelId;
    }

    @JsonSetter("limit_number")
    public void setLimitNumber(Integer limitNumber) {
        this.limitNumber = limitNumber;
    }

    @JsonSetter("operation_date")
    public void setOperationDate(Integer operationDate) {
        this.operationDate = operationDate;
    }

    public static ScheduleRule convert(top.itcat.rpc.service.model.ScheduleRule rpcbean) {
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
        if (bean.getDoctorId() != null) {
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

        return rpcbean;
    }
}