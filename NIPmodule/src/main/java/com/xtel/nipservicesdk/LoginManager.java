package com.xtel.nipservicesdk;

import com.xtel.nipservicesdk.callback.CallbacListener;
import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.commons.Constants;
import com.xtel.nipservicesdk.model.LoginModel;
import com.xtel.nipservicesdk.model.entity.AuthenNipModel;
import com.xtel.nipservicesdk.model.entity.DeviceObject;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtel.nipservicesdk.model.entity.RESP_Login;
import com.xtel.nipservicesdk.utils.JsonHelper;
import com.xtel.nipservicesdk.utils.SharedUtils;

/**
 * Created by vihahb on 1/4/2017
 */

public class LoginManager {
    private static LoginManager instance;

    public static LoginManager getInstance() {
        if (instance == null)
            instance = new LoginManager();
        return instance;
    }

    public static String getSession() {
        return LoginModel.getInstance().getSessiong();
    }

    public void LoginFaceook(String token_key, DeviceObject deviceObject, final CallbacListener callbacListener) {
        AuthenNipModel facebookModel = new AuthenNipModel();
        facebookModel.setAccess_token_key(token_key);
        facebookModel.getService_code();
        facebookModel.setDevInfo(deviceObject);

//        LoginModel.getInstance().postFacebookData2Server(JsonHelper.toJson(facebookModel), new ResponseHandle<RESP_Login>(RESP_Login.class) {
//            @Override
//            public void onSuccess(RESP_Login obj) {
//                SharedUtils.getInstance().putStringValue(Constants.SESSION, obj.getSession());
//                callbacListener.onSuccess(obj);
//            }
//
//            @Override
//            public void onError(Error error) {
//                callbacListener.onError(error);
//            }
//        });
    }

    public void LoginAccountKit(String authorization_code, DeviceObject deviceObject, final CallbacListener callbacListener){
        AuthenNipModel accountKitModel = new AuthenNipModel();
        accountKitModel.setAuthorization_code(authorization_code);
        accountKitModel.getService_code();
        accountKitModel.setDevInfo(deviceObject);

//        LoginModel.getInstance().postAccountKitData2Server(JsonHelper.toJson(accountKitModel), new ResponseHandle<RESP_Login>(RESP_Login.class) {
//            @Override
//            public void onSuccess(RESP_Login obj) {
//                SharedUtils.getInstance().putStringValue(Constants.SESSION, obj.getSession());
//                callbacListener.onSuccess(obj);
//            }
//
//            @Override
//            public void onError(Error error) {
//                callbacListener.onError(error);
//            }
//        });
    }

//    public static void getNewSesion(Activity activity, String authentication_id, DeviceObject deviceObject, final CallbacListener callbacListener) {
//        String service_code = null;
//
//        try {
//            ApplicationInfo ai = activity.getPackageManager().getApplicationInfo(activity.getPackageName(), PackageManager.GET_META_DATA);
//            Bundle bundle = ai.metaData;
//            service_code = bundle.getString("my_api_key");
//        } catch (PackageManager.NameNotFoundException | NullPointerException e) {
//            e.printStackTrace();
//        }
//
//        if (service_code == null || service_code.isEmpty()) {
//            callbacListener.onError(new Error(-2, activity.getString(R.string.error), activity.getString(R.string.error_no_service_code)));
//            return;
//        }
//
//        AuthenNip authenNip = new AuthenNip();
//        authenNip.setAuthenticationid(authentication_id);
//        authenNip.setService_code(service_code);
//        authenNip.setDevInfo(deviceObject);
//
//        LoginModel.getInstance().getNewSession(JsonHelper.toJson(authenNip), new ResponseHandle<RESP_Login>(RESP_Login.class) {
//            @Override
//            public void onSuccess(RESP_Login obj) {
//                callbacListener.onSuccess(obj);
//            }
//
//            @Override
//            public void onError(Error error) {
//                callbacListener.onError(error);
//            }
//        });
//    }
}