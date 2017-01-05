package com.xtel.nipservicesdk.model.entity;

import com.google.gson.annotations.Expose;

/**
 * Created by vihahb on 1/5/2017.
 */

public class ResetEntity {

    @Expose
    private String email;

    @Expose
    private String service_code;

    @Expose
    private int sendEmail;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getService_code() {
        return service_code;
    }

    public void setService_code(String service_code) {
        this.service_code = service_code;
    }

    public int getSendEmail() {
        return sendEmail;
    }

    public void setSendEmail(int sendEmail) {
        this.sendEmail = sendEmail;
    }

    @Override
    public String toString() {
        return "ResetEntity{" +
                "email='" + email + '\'' +
                ", service_code='" + service_code + '\'' +
                ", sendEmail=" + sendEmail +
                '}';
    }
}
