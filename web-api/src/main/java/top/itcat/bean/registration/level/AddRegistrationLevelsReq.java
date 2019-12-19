package top.itcat.bean.registration.level;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.registration.RegistrationLevel;

import java.util.List;

public class AddRegistrationLevelsReq {
    private List<RegistrationLevel> list;

    public List<RegistrationLevel> getList() {
        return list;
    }

    @JsonSetter("list")
    public void setList(List<RegistrationLevel> list) {
        this.list = list;
    }
}
