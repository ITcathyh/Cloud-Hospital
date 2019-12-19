package top.itcat.bean.user;

import lombok.Getter;
import lombok.ToString;
import top.itcat.rpc.service.model.DoctorTitleEnum;
import top.itcat.rpc.service.model.PackedUser;
import top.itcat.rpc.service.model.RoleEnum;

import java.io.Serializable;

@Getter
@ToString
public class ApiUser implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private String realname;
    private RoleEnum role;
    private DoctorTitleEnum title;
    private long departmentId;
    private String code;
    private String description;

    public ApiUser(PackedUser user) {
        id = user.getUser().getId();
        realname = user.getUser().getRealName();
        role = user.getUser().getRole();
        title = user.getTitle();
        departmentId = user.getUser().getDepartId();
        code = user.getUser().getCode();
        description = user.getDescription();
    }

    public void setRoles(RoleEnum role) {
        this.role = role;
    }
}

