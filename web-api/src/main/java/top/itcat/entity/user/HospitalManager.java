package top.itcat.entity.user;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import top.itcat.rpc.service.model.User;

/**
 * <p>
 * 医院管理员
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-27
 */
@Data
public class HospitalManager extends BaseUser {

    private static final long serialVersionUID = 1L;

    /**
     * 医院管理员id
     */
    private Long id;
    /**
     * 医院管理员编号
     */
    private String code;
    /**
     * 医院管理员登录密码
     */
    private String password;
    /**
     * 医院管理员真实姓名
     */
    private String realname;
    /**
     * 医院管理员科室id
     */
    @JSONField(name = "department_id")
    private Long departmentId;
    /**
     * 医院管理员有效性
     */
    private Integer valid;


    public static HospitalManager convertHospitalManager(top.itcat.rpc.service.model.HospitalManager rpcUser) {
        HospitalManager hospitalManager = new HospitalManager();
        User user = rpcUser.getUser();

        if (!rpcUser.isSetUser()) {
            return null;
        }

        hospitalManager.converBaseUserInfo(user);

        return hospitalManager;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    @JsonSetter("department_id")
    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }
}
