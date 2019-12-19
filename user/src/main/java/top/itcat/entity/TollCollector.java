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
 * 挂号收费员
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-27
 */
@Data
@TableName("toll_collector")
public class TollCollector extends BaseUser<TollCollector> {

    private static final long serialVersionUID = 1L;

    /**
     * 挂号收费员id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 挂号收费员编号
     */
    private String code;
    /**
     * 挂号收费员登录密码
     */
    private String password;
    /**
     * 挂号收费员真实姓名
     */
    private String realname;
    /**
     * 挂号收费员科室id
     */
    @TableField("department_id")
    private Long departmentId;
    /**
     * 挂号收费员有效性
     */
    private Integer valid;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public static TollCollector convertTollCollector(top.itcat.rpc.service.model.TollCollector rpcUser) {
        TollCollector tollCollector = new TollCollector();
        User user = rpcUser.getUser();

        if (!rpcUser.isSetUser()) {
            return null;
        }

        tollCollector.converBaseUserInfo(user);

        if (rpcUser.isSetValid()) {
            tollCollector.setValid(rpcUser.getValid());
        }

        return tollCollector;
    }
}
