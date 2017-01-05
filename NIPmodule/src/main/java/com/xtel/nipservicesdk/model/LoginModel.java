package com.xtel.nipservicesdk.model;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.commons.Constants;
import com.xtel.nipservicesdk.model.entity.AuthenNip;
import com.xtel.nipservicesdk.model.entity.RESP_Login;
import com.xtel.nipservicesdk.model.entity.RESP_Reactive;
import com.xtel.nipservicesdk.model.entity.RESP_Register;
import com.xtel.nipservicesdk.model.entity.RESP_Reset;
import com.xtel.nipservicesdk.utils.DeviceInfo;
import com.xtel.nipservicesdk.utils.JsonHelper;
import com.xtel.nipservicesdk.utils.SharedUtils;

/**
 * Created by Lê Công Long Vũ on 12/28/2016
 */

public class LoginModel extends BasicModel {
    private static LoginModel instance = new LoginModel();
    String url_facebook = Constants.URL_NIP + Constants.API_FACEBOOK;
    String url_account_kit = Constants.URL_NIP + Constants.API_ACCOUNT_KIT;
    String url_sesion_authenticate = Constants.URL_NIP + Constants.API_SESSION_AUTHENTICATE;
    String url_reg_nip_acc = Constants.URL_NIP + Constants.API_REGISTER_NIP;
    String url_reactive = Constants.URL_NIP + Constants.API_RE_ACTIVE_ACC_NIP;
    String url_reset_password = Constants.URL_NIP + Constants.API_RESET_ACC_NIP;
    String url_login = Constants.URL_NIP + Constants.API_LOGIN_ACC_NIP;

    public static LoginModel getInstance() {
        return instance;
    }

    public void postFacebookData2Server(String jsonObject, ResponseHandle<RESP_Login> responseHandle) {
        requestServer.postApi(url_facebook, jsonObject, null, responseHandle);
    }

    public void postAccountKitData2Server(String jsonObject, ResponseHandle<RESP_Login> responseHandle) {
        requestServer.postApi(url_account_kit, jsonObject, null, responseHandle);
    }


    public void registerAccountNip(String jsonObject, ResponseHandle<RESP_Register> responseHandle) {
        requestServer.postApi(url_reg_nip_acc, jsonObject, null, responseHandle);
    }

    public void loginNipServices(String jsonObject, ResponseHandle<RESP_Login> responseHandle) {
        requestServer.postApi(url_login, jsonObject, null, responseHandle);
    }

    public void resetPassworf(String jsonObject, ResponseHandle<RESP_Reset> responseHandle) {
        requestServer.putApi(url_reset_password, jsonObject, null, responseHandle);
    }

    public void reactiveNipAccoint(String jsonObject, ResponseHandle<RESP_Reactive> responseHandle) {
        requestServer.putApi(url_reactive, jsonObject, null, responseHandle);
    }


    public String getSessiong() {
        return SharedUtils.getInstance().getStringValue(Constants.SESSION);
    }

    public void getNewSession(String service_code, ResponseHandle<RESP_Login> responseHandle) {
        AuthenNip authenNip = new AuthenNip();
        authenNip.setAuthenticationid(SharedUtils.getInstance().getStringValue(Constants.USER_AUTH_ID));
        authenNip.setService_code(service_code);
        authenNip.setDevInfo(DeviceInfo.getDeviceObject());

        String url_authen = Constants.URL_NIP + Constants.API_SESSION_AUTHENTICATE;
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
