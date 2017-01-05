package com.xtel.nipservicesdk.model.entity;

import com.google.gson.annotations.Expose;

/**
 * Created by vihahb on 1/4/2017.
 */

public class RegisterModel extends LoginNipModel {

    @Expose
    private String email;

    @Expose
    private int sendEmail;

    @Expose
    private String accountType;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSendEmail() {
        return sendEmail;
    }

    public void setSendEmail(int sendEmail) {
        this.sendEmail = sendEmail;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @Override
    public String toString() {
        return "RegisterModel{" +
                "email='" + email + '\'' +
                ", sendEmail=" + sendEmail +
                ", accountType='" + accountType + '\'' +
                '}';
    }
}
