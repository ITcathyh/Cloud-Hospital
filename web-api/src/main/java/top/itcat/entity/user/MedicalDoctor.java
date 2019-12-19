package top.itcat.entity.user;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import top.itcat.rpc.service.model.User;

/**
 * <p>
 * 医技医生
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-27
 */
@Data
public class MedicalDoctor extends BaseUser {

    private static final long serialVersionUID = 1L;

    /**
     * 医技医生id
     */
    private Long id;
    /**
     * 医技医生编号
     */
    private String code;
    /**
     * 医技医生登录密码
     */
    private String password;
    /**
     * 医技医生真实姓名
     */
    private String realname;
    /**
     * 医技医生科室id
     */
    @JSONField(name = "department_id")
    private Long departmentId;
    /**
     * 医技医生职称 主任医师、副主任医师、主治医师、住院医师
     */
    @JSONField(name = "title")
    private Integer jobTitle;
//    /**
//     * 医技医生是否参与排班
//     */
//    @TableField("in_scheduling")
//    private Integer inScheduling;
    /**
     * 医技医生描述
     */
    private String description;
    /**
     * 医技医生有效性
     */
    private Integer valid;


    public static MedicalDoctor convertMedicalDoctor(top.itcat.rpc.service.model.MedicalDoctor rpcUser) {
        MedicalDoctor medicalDoctor = new MedicalDoctor();
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

        return medicalDoctor;
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
}
