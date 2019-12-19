package top.itcat.bean.user;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.user.AccountClerk;

import java.util.List;

public class AddAccountClerksReq {
    private List<AccountClerk> accountClerks;

    public List<AccountClerk> getAccountClerks() {
        return accountClerks;
    }

    @JsonSetter("account_clerks")
    public void setAccountClerks(List<AccountClerk> accountClerks) {
        this.accountClerks = accountClerks;
    }
}
