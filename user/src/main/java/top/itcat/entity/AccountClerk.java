package top.itcat.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import top.itcat.entity.BaseUser;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import top.itcat.rpc.service.model.User;

import java.io.Serializable;

/**
 * <p>
 * 财务管理员
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-27
 */
@Data
@TableName("account_clerk")
public class AccountClerk extends BaseUser<AccountClerk> {

    private static final long serialVersionUID = 1L;

    /**
     * 财务管理员id
     */
    @TableId(value = "id", type = IdType.AUTO)
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
    @TableField("department_id")
    private Long departmentId;
    /**
     * 财务管理员有效性
     */
    private Integer valid;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public static AccountClerk convertAccountClerk(top.itcat.rpc.service.model.AccountClerk rpcUser) {
        AccountClerk accountClerk = new AccountClerk();
        User user = rpcUser.getUser();

        if (!rpcUser.isSetUser()) {
            return null;
        }

        accountClerk.converBaseUserInfo(user);

        if (rpcUser.isSetValid()) {
            accountClerk.setValid(rpcUser.getValid());
        }

        return accountClerk;
    }
}
