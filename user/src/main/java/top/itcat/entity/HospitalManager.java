package top.itcat.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import top.itcat.rpc.service.model.User;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 医院管理员
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-27
 */
@Data
@TableName("hospital_manager")
public class HospitalManager extends BaseUser<HospitalManager> {

    private static final long serialVersionUID = 1L;

    /**
     * 医院管理员id
     */
    @TableId(value = "id", type = IdType.AUTO)
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
    @TableField("department_id")
    private Long departmentId;
    /**
     * 医院管理员有效性
     */
    private Integer valid;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public static HospitalManager convertHospitalManager(top.itcat.rpc.service.model.HospitalManager rpcUser) {
        HospitalManager hospitalManager = new HospitalManager();
        User user = rpcUser.getUser();

        if (!rpcUser.isSetUser()) {
            return null;
        }

        hospitalManager.converBaseUserInfo(user);

        if (rpcUser.isSetValid()) {
            hospitalManager.setValid(rpcUser.getValid());
        }

        return hospitalManager;
    }
}
