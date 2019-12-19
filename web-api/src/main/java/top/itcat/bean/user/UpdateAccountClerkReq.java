package top.itcat.bean.user;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.user.AccountClerk;

public class UpdateAccountClerkReq {
    private AccountClerk accountClerk;

    public AccountClerk getAccountClerk() {
        return accountClerk;
    }

    @JsonSetter("account_clerk")
    public void setAccountClerk(AccountClerk accountClerk) {
        this.accountClerk = accountClerk;
    }
}
