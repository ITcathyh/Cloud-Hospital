package top.itcat.entity.user;

import top.itcat.rpc.service.model.User;

import java.io.Serializable;

public abstract class BaseUser implements BaseUserOpeartion, Serializable {
    public void converBaseUserInfo(User userInfo) {
        if (userInfo == null) {
            return;
        }

        if (userInfo.isSetRealName()) {
            setRealname(userInfo.getRealName());
        }

        if (userInfo.isSetCode()) {
            setCode(userInfo.getCode());
        }

        if (userInfo.isSetDepartId()) {
            setDepartmentId(userInfo.getDepartId());
        }

        if (userInfo.isSetId()) {
            setId(userInfo.getId());
        }
    }
}
