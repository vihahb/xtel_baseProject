package com.xtel.nipservicesdk.model.entity;

import com.google.gson.annotations.Expose;

/**
 * Created by vihahb on 1/4/2017.
 */

public class LoginNipModel extends AuthenNipModel {
    @Expose
    private String user_name;

    @Expose
    private String password;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
