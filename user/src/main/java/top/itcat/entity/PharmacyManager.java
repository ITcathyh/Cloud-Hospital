package top.itcat.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import top.itcat.rpc.service.model.User;

import java.io.Serializable;


/**
 * <p>
 * 药房操作员
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-27
 */
@Data
@TableName("pharmacy_manager")
public class PharmacyManager extends BaseUser<PharmacyManager> {

    private static final long serialVersionUID = 1L;

    /**
     * 药房操作员id
     */
    @TableId(value = "id", type = IdType.AUTO)
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
    @TableField("department_id")
    private Long departmentId;
    /**
     * 药房操作员有效性
     */
    private Integer valid;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public static PharmacyManager convertPharmacyManager(top.itcat.rpc.service.model.PharmacyManager rpcUser) {
        PharmacyManager pharmacyManager = new PharmacyManager();
        User user = rpcUser.getUser();

        if (!rpcUser.isSetUser()) {
            return null;
        }

        pharmacyManager.converBaseUserInfo(user);

        if (rpcUser.isSetValid()) {
            pharmacyManager.setValid(rpcUser.getValid());
        }

        return pharmacyManager;
    }
}
