package top.itcat.bean.registration.level;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.registration.RegistrationLevel;

public class UpdateRegistrationLevelReq {
    private RegistrationLevel registrationLevel;

    public RegistrationLevel getRegistrationLevel() {
        return registrationLevel;
    }

    @JsonSetter("registration_level")
    public void setRegistrationLevel(RegistrationLevel registrationLevel) {
        this.registrationLevel = registrationLevel;
    }
}
