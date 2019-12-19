package top.itcat.entity.user;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import top.itcat.rpc.service.model.User;

/**
 * <p>
 * 财务管理员
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-27
 */
@Data
public class AccountClerk extends BaseUser {

    private static final long serialVersionUID = 1L;

    /**
     * 财务管理员id
     */
    private Long id;
    /**
     * 财务管理员编号
     */
    private String code;
    /**
     * 财务管理员登录密码
     */
    private String password;
    /**
     * 财务管理员真名
     */
    private String realname;
    /**
     * 财务管理员科室id
     */
    @JSONField(name = "department_id")
    private Long departmentId;
    /**
     * 财务管理员有效性
     */
    private Integer valid;


    public static AccountClerk convertAccountClerk(top.itcat.rpc.service.model.AccountClerk rpcUser) {
        AccountClerk accountClerk = new AccountClerk();
        User user = rpcUser.getUser();

        if (!rpcUser.isSetUser()) {
            return null;
        }

        accountClerk.converBaseUserInfo(user);

        return accountClerk;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    @JsonSetter("department_id")
    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }
}
