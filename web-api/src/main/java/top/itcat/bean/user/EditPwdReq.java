package top.itcat.bean.user;

import com.fasterxml.jackson.annotation.JsonSetter;

public class EditPwdReq {
    private String oldPwd;
    private String pwd;

    public String getOldPwd() {
        return oldPwd;
    }

    @JsonSetter("old_pwd")
    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
