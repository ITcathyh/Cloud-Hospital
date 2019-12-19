package top.itcat.entity.user;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import top.itcat.rpc.service.model.User;

/**
 * <p>
 *
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-22
 */
@Data
public class OutpatientDoctor extends BaseUser {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;
    private String code;
    private String password;
    private String realname;
    /**
     * department_id
     */
    @JSONField(name = "department_id")
    private Long departmentId;
    /**
     * job_title
     */
    @JSONField(name = "title")
    private Integer jobTitle;
    /**
     * is_in_scheduling
     */
    @JSONField(name = "in_schedule")
    private Integer inScheduling;
    private String description;


    public static OutpatientDoctor convertOutpatientDoctor(top.itcat.rpc.service.model.OutpatientDoctor rpcUser) {
        OutpatientDoctor doctor = new OutpatientDoctor();
        User user = rpcUser.getUser();

        if (!rpcUser.isSetUser()) {
            return null;
        }

        doctor.converBaseUserInfo(user);

        if (rpcUser.isSetTitle()) {
            doctor.setJobTitle(rpcUser.getTitle().getValue());
        }

        if (rpcUser.isSetDescription()) {
            doctor.setDescription(rpcUser.getDescription());
        }

        if (rpcUser.isSetInSchedual()) {
            doctor.setInScheduling(rpcUser.inSchedual ? 1 : 0);
        }

        return doctor;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    @JsonSetter("department_id")
    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    @JsonSetter("title")
    public void setJobTitle(Integer jobTitle) {
        this.jobTitle = jobTitle;
    }

    @JsonSetter("in_schedule")
    public void setInScheduling(Integer inScheduling) {
        this.inScheduling = inScheduling;
    }
}
