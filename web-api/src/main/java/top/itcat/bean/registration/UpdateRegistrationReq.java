package top.itcat.bean.registration;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.registration.Registration;

public class UpdateRegistrationReq {
    private Registration registration;

    public Registration getRegistration() {
        return registration;
    }

    @JsonSetter("registration")
    public void setRegistration(Registration registration) {
        this.registration = registration;
    }
}

