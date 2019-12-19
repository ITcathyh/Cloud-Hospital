package top.itcat.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import top.itcat.rpc.service.model.User;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-22
 */
@TableName("doctor")
@Data
public class Doctor extends BaseUser<Doctor> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String code;
    private String password;
    private String realname;
    /**
     * department_id
     */
    @TableField("department_id")
    private Long departmentId;
    /**
     * job_title
     */
    @TableField("job_title")
    private Integer jobTitle;
    /**
     * is_in_scheduling
     */
    @TableField("in_scheduling")
    private Integer inScheduling;
    private String description;
    private Integer role;
    private Integer valid;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public static Doctor convertMedicalDoctor(top.itcat.rpc.service.model.MedicalDoctor rpcUser) {
        Doctor medicalDoctor = new Doctor();
        User user = rpcUser.getUser();

        if (!rpcUser.isSetUser()) {
            return null;
        }

        medicalDoctor.converBaseUserInfo(user);

        if (rpcUser.isSetTitle()) {
            medicalDoctor.setJobTitle(rpcUser.getTitle().getValue());
        }

        if (rpcUser.isSetDescription()) {
            medicalDoctor.setDescription(rpcUser.getDescription());
        }

//        if (rpcUser.isSetInSchedual()) {
//            medicalDoctor.setInScheduling(rpcUser.inSchedual ? 1 : 0);
//        }

        if (rpcUser.isSetValid()) {
            medicalDoctor.setValid(rpcUser.getValid());
        }

        return medicalDoctor;
    }

    public static Doctor convertOutpatientDoctor(top.itcat.rpc.service.model.OutpatientDoctor rpcUser) {
        Doctor doctor = new Doctor();
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
            doctor.setInScheduling(rpcUser.isInSchedual() ? 1 : 0);
        }

        if (rpcUser.isSetValid()) {
            doctor.setValid(rpcUser.getValid());
        }

        return doctor;
    }
}
