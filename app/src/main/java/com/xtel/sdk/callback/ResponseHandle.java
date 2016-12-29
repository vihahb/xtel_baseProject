package com.xtel.sdk.callback;


import com.xtel.basicsample.model.Model;
import com.xtel.basicsample.model.entity.Error;
import com.xtel.basicsample.model.entity.RESP_Basic;
import com.xtel.sdk.utils.JsonHelper;

import java.io.IOException;

/**
 * Created by Vũ Hà Vi on 12/27/2016.
 */

public abstract class ResponseHandle<T extends RESP_Basic> {
    private Class<T> clazz;

    protected ResponseHandle(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void onSuccess(String result) {
        try {
            boolean isJson;
            isJson = !(result == null || result.isEmpty());

            if (!isJson) {
                onSuccess((T) new Model());
            } else {
                T t = JsonHelper.getObjectNoException(result, clazz);
                if (t.getError() != null) {
                    onError(t.getError());
                } else {
                    onSuccess(t);
                }
            }
        } catch (Exception e) {
            onError(new Error(-1, "ERROR_PARSER_RESPONSE", e.getMessage()));
        }
    }

    public void onError(IOException error) {
        onError(new Error(-1, "ERROR_PARSER_RESPONSE", error.getMessage()));
    }

    public abstract void onSuccess(T obj);

    public abstract void onError(Error error);
}
