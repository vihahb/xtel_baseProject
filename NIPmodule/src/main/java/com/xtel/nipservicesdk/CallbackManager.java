package com.xtel.nipservicesdk;

import android.Manifest;
import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.xtel.nipservicesdk.callback.CallbacListener;
import com.xtel.nipservicesdk.callback.ICmd;
import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.commons.Constants;
import com.xtel.nipservicesdk.model.LoginModel;
import com.xtel.nipservicesdk.model.entity.AuthenNipModel;
import com.xtel.nipservicesdk.model.entity.DeviceObject;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtel.nipservicesdk.model.entity.RESP_Login;
import com.xtel.nipservicesdk.utils.JsonHelper;
import com.xtel.nipservicesdk.utils.PermissionHelper;
import com.xtel.nipservicesdk.utils.SharedUtils;

import java.util.ArrayList;

/**
 * Created by Lê Công Long Vũ on 1/5/2017
 */

public class CallbackManager {
    public final int REQUEST_PERMISSION = 111;
    private CallbacListener callbacListener;
    private Activity activity;
    private ArrayList<Object> object = new ArrayList<>();

    private ICmd iCmd = new ICmd() {
        @Override
        public void execute() {
            if ((Integer) object.get(0) == 1) {

                LoginModel.getInstance().getNewSession((String) object.get(1), new ResponseHandle<RESP_Login>(RESP_Login.class) {
                    @Override
                    public void onSuccess(RESP_Login obj) {
                        callbacListener.onSuccess(obj);
                    }

                    @Override
                    public void onError(Error error) {
                        callbacListener.onError(error);
                    }
                });
            }
        }
    };

    private CallbackManager(Activity activity) {
        this.activity = activity;
    }

    public static CallbackManager create(Activity activity) {
        return new CallbackManager(activity);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION) {
            iCmd.execute();
        }
    }

    public void LoginFaceook(String token_key, DeviceObject deviceObject, final CallbacListener callbacListener) {
        AuthenNipModel facebookModel = new AuthenNipModel();
        facebookModel.setAccess_token_key(token_key);
        facebookModel.getService_code();
        facebookModel.setDevInfo(deviceObject);

        LoginModel.getInstance().postFacebookData2Server(JsonHelper.toJson(facebookModel), new ResponseHandle<RESP_Login>(RESP_Login.class) {
            @Override
            public void onSuccess(RESP_Login obj) {
                SharedUtils.getInstance().putStringValue(Constants.USER_AUTH_ID, obj.getAuthenticationid());
                SharedUtils.getInstance().putStringValue(Constants.SESSION, obj.getSession());
                callbacListener.onSuccess(obj);
            }

            @Override
            public void onError(Error error) {
                callbacListener.onError(error);
            }
        });
    }

    public void LoginAccountKit(String authorization_code, DeviceObject deviceObject, final CallbacListener callbacListener) {
        AuthenNipModel accountKitModel = new AuthenNipModel();
        accountKitModel.setAuthorization_code(authorization_code);
        accountKitModel.getService_code();
        accountKitModel.setDevInfo(deviceObject);

        LoginModel.getInstance().postAccountKitData2Server(JsonHelper.toJson(accountKitModel), new ResponseHandle<RESP_Login>(RESP_Login.class) {
            @Override
            public void onSuccess(RESP_Login obj) {
                SharedUtils.getInstance().putStringValue(Constants.USER_AUTH_ID, obj.getAuthenticationid());
                SharedUtils.getInstance().putStringValue(Constants.SESSION, obj.getSession());
                callbacListener.onSuccess(obj);
            }

            @Override
            public void onError(Error error) {
                callbacListener.onError(error);
            }
        });
    }

    public static String getSession() {
        return LoginModel.getInstance().getSessiong();
    }

    public void getNewSesion(final CallbacListener callbacListener) {
        String service_code = LoginModel.getInstance().getServiceCode(activity);

        if (service_code == null || service_code.isEmpty()) {
            callbacListener.onError(new Error(-2, activity.getString(R.string.error), activity.getString(R.string.error_no_service_code)));
            return;
        }

        this.callbacListener = callbacListener;

        object.clear();
        object.add(1);
        object.add(service_code);

        if (checkPermission())
            iCmd.execute();
    }

    private boolean checkPermission() {
        return PermissionHelper.checkOnlyPermission(Manifest.permission.READ_PHONE_STATE, activity, REQUEST_PERMISSION);
    }
}