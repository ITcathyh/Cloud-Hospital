package top.itcat.entity.user;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import top.itcat.rpc.service.model.User;


/**
 * <p>
 * 药房操作员
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-27
 */
@Data
public class PharmacyManager extends BaseUser {

    private static final long serialVersionUID = 1L;

    /**
     * 药房操作员id
     */
    private Long id;
    /**
     * 药房操作员编号
     */
    private String code;
    /**
     * 药房操作员登录密码
     */
    private String password;
    /**
     * 药房操作员真实姓名
     */
    private String realname;
    /**
     * 药房操作员科室id
     */
    @JSONField(name = "department_id")
    private Long departmentId;
    /**
     * 药房操作员有效性
     */
    private Integer valid;


    public static PharmacyManager convertPharmacyManager(top.itcat.rpc.service.model.PharmacyManager rpcUser) {
        PharmacyManager pharmacyManager = new PharmacyManager();
        User user = rpcUser.getUser();

        if (!rpcUser.isSetUser()) {
            return null;
        }

        pharmacyManager.converBaseUserInfo(user);

        return pharmacyManager;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    @JsonSetter("department_id")
    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }
}
