package top.itcat.entity.user;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import top.itcat.rpc.service.model.User;

/**
 * <p>
 * 挂号收费员
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-27
 */
@Data
public class TollCollector extends BaseUser {

    private static final long serialVersionUID = 1L;

    /**
     * 挂号收费员id
     */
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
    @JSONField(name = "real_name")
    private String realname;
    /**
     * 挂号收费员科室id
     */
    @JSONField(name = "depart_id")
    private Long departmentId;
    /**
     * 挂号收费员有效性
     */
    private Integer valid;


    public static TollCollector convertTollCollector(top.itcat.rpc.service.model.TollCollector rpcUser) {
        TollCollector tollCollector = new TollCollector();
        User user = rpcUser.getUser();

        if (!rpcUser.isSetUser()) {
            return null;
        }

        tollCollector.converBaseUserInfo(user);

        return tollCollector;
    }

    @JsonSetter("department_id")
    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }
}
