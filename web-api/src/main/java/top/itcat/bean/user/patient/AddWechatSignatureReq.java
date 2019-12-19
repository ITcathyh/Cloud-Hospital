package top.itcat.bean.user.patient;

import com.fasterxml.jackson.annotation.JsonSetter;

public class AddWechatSignatureReq {
    private String signature;
    private String identityCardNo;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getIdentityCardNo() {
        return identityCardNo;
    }

    @JsonSetter("identity_card_no")
    public void setIdentityCardNo(String identityCardNo) {
        this.identityCardNo = identityCardNo;
    }
}
