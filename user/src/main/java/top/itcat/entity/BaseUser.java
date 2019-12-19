package top.itcat.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.itcat.generator.CodeGenerator;
import top.itcat.rpc.service.model.User;
import top.itcat.util.EncryUtil;

import static top.itcat.constant.Const.USER_PWD_SALT;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class BaseUser<T extends Model> extends Model<T> implements BaseUserOpeartion {
    public void converBaseUserInfo(User userInfo) {
        if (userInfo == null) {
            return;
        }

        if (userInfo.isSetRealName()) {
            setRealname(userInfo.getRealName());
        }

//        if (userInfo.isSetCode()) {
//            setCode(userInfo.getCode());
//        }

        if (userInfo.isSetDepartId()) {
            setDepartmentId(userInfo.getDepartId());
        }

        if (userInfo.isSetId()) {
            setId(userInfo.getId());
        }

        if (userInfo.isSetPassword()) {
            setPassword(EncryUtil.SHA1(userInfo.getPassword(), USER_PWD_SALT));
        }
    }
}
