package top.itcat.bean.registration;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.registration.Registration;

public class AddRegistrationReq {
    private Registration registration;

    public Registration getRegistration() {
        return registration;
    }

    @JsonSetter("registrations")
    public void setRegistration(Registration registration) {
        this.registration = registration;
    }
}

