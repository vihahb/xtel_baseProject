package com.xtel.nipservicesdk.model.entity;

import com.google.gson.annotations.Expose;

/**
 * Created by vihahb on 1/5/2017.
 */

public class RESP_Reactive extends RESP_Basic {
    @Expose
    private String active_url;

    public String getActive_url() {
        return active_url;
    }

    public void setActive_url(String active_url) {
        this.active_url = active_url;
    }

    @Override
    public String toString() {
        return "RESP_Reactive{" +
                "active_url='" + active_url + '\'' +
                '}';
    }
}
