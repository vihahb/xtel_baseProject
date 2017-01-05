package com.xtel.nipservicesdk.model;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.commons.Constants;
import com.xtel.nipservicesdk.model.entity.AuthenNip;
import com.xtel.nipservicesdk.model.entity.DeviceObject;
import com.xtel.nipservicesdk.model.entity.RESP_Login;
import com.xtel.nipservicesdk.utils.DeviceInfo;
import com.xtel.nipservicesdk.utils.JsonHelper;
import com.xtel.nipservicesdk.utils.SharedUtils;

/**
 * Created by Lê Công Long Vũ on 12/28/2016
 */

public class LoginModel extends BasicModel {
    String url_facebook = Constants.URL_NIP + Constants.API_FACEBOOK;
    String url_account_kit = Constants.URL_NIP + Constants.API_ACCOUNT_KIT;


    private static LoginModel instance = new LoginModel();

    public static LoginModel getInstance() {
        return instance;
    }

    public void postFacebookData2Server(String jsonObject, ResponseHandle<RESP_Login> responseHandle) {
        requestServer.postApi(url_facebook, jsonObject, null, responseHandle);
    }

    public void postAccountKitData2Server(String jsonObject, ResponseHandle<RESP_Login> responseHandle) {
        requestServer.postApi(url_account_kit, jsonObject, null, responseHandle);
    }

    public String getSessiong() {
        return SharedUtils.getInstance().getStringValue(Constants.SESSION);
    }

    public void getNewSession(String service_code, ResponseHandle<RESP_Login> responseHandle) {
        AuthenNip authenNip = new AuthenNip();
        authenNip.setAuthenticationid(SharedUtils.getInstance().getStringValue(Constants.USER_AUTH_ID));
        authenNip.setService_code(service_code);
        authenNip.setDevInfo(DeviceInfo.getDeviceObject());

        String url_authen = Constants.URL_NIP + Constants.AUTHEN_AUTHENTICATE;
        requestServer.postApi(url_authen, JsonHelper.toJson(authenNip), null, responseHandle);
    }

    public String getServiceCode(Activity activity) {
        try {
            ApplicationInfo ai = activity.getPackageManager().getApplicationInfo(activity.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            return bundle.getString(Constants.META_DATA_NAME);
        } catch (PackageManager.NameNotFoundException | NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }
}
