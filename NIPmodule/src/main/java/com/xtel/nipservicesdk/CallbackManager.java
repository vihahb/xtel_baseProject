package com.xtel.nipservicesdk;

import android.Manifest;
import android.app.Activity;
import android.support.annotation.NonNull;

import com.xtel.nipservicesdk.callback.CallbacListener;
import com.xtel.nipservicesdk.callback.ICmd;
import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.commons.Constants;
import com.xtel.nipservicesdk.model.LoginModel;
import com.xtel.nipservicesdk.model.entity.AuthenNipModel;
import com.xtel.nipservicesdk.model.entity.DeviceObject;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtel.nipservicesdk.model.entity.LoginNipModel;
import com.xtel.nipservicesdk.model.entity.RESP_Login;
import com.xtel.nipservicesdk.model.entity.RESP_Reactive;
import com.xtel.nipservicesdk.model.entity.RESP_Register;
import com.xtel.nipservicesdk.model.entity.RESP_Reset;
import com.xtel.nipservicesdk.model.entity.ReactiveNip;
import com.xtel.nipservicesdk.model.entity.RegisterModel;
import com.xtel.nipservicesdk.model.entity.ResetEntity;
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

    public static String getSession() {
        return LoginModel.getInstance().getSessiong();
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION) {
            iCmd.execute();
        }
    }

    public void LoginFaceook(String token_key, DeviceObject deviceObject, final CallbacListener callbacListener) {
        String service_code = LoginModel.getInstance().getServiceCode(activity);
        AuthenNipModel facebookModel = new AuthenNipModel();
        facebookModel.setAccess_token_key(token_key);
        facebookModel.setService_code(service_code);
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
        String service_code = LoginModel.getInstance().getServiceCode(activity);
        AuthenNipModel accountKitModel = new AuthenNipModel();
        accountKitModel.setAuthorization_code(authorization_code);
        accountKitModel.setService_code(service_code);
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

    public void LoginNipAcc(Activity activity, String user_name, String password, DeviceObject deviceObject, final CallbacListener callbacListener) {
        String service_code = LoginModel.getInstance().getServiceCode(activity);
        LoginNipModel loginNipModel = new LoginNipModel();
        loginNipModel.setUser_name(user_name);
        loginNipModel.setPassword(password);
        loginNipModel.setService_code(service_code);
        loginNipModel.setDevInfo(deviceObject);

        LoginModel.getInstance().loginNipServices(JsonHelper.toJson(loginNipModel), new ResponseHandle<RESP_Login>(RESP_Login.class) {
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

    public void registerNipService(String user_name, String password, String email, int sendMail, int type, final CallbacListener callbacListener) {
        RegisterModel registerNipModel = new RegisterModel();
        registerNipModel.setUser_name(user_name);
        registerNipModel.setPassword(password);
        registerNipModel.setEmail(email);
        registerNipModel.setSendEmail(sendMail);
        if (type == 0) {
            registerNipModel.setAccountType("EMAIL");
        } else {
            registerNipModel.setAccountType("PHONE-NUMBER");
        }

        LoginModel.getInstance().registerAccountNip(JsonHelper.toJson(registerNipModel), new ResponseHandle<RESP_Register>(RESP_Register.class) {
            @Override
            public void onSuccess(RESP_Register obj) {
                callbacListener.onSuccess(obj);
            }

            @Override
            public void onError(Error error) {
                callbacListener.onError(error);
            }
        });
    }

    public void resetNipAccount(String email, int sendEmail, final CallbacListener callbacListener) {
        String service_code = LoginModel.getInstance().getServiceCode(activity);
        ResetEntity resetEntity = new ResetEntity();
        resetEntity.setEmail(email);
        if (sendEmail == 1) {
            resetEntity.setSendEmail(1);
        } else
            resetEntity.setSendEmail(0);

        LoginModel.getInstance().resetPassworf(JsonHelper.toJson(resetEntity), new ResponseHandle<RESP_Reset>(RESP_Reset.class) {
            @Override
            public void onSuccess(RESP_Reset obj) {
                callbacListener.onSuccess(obj);
            }

            @Override
            public void onError(Error error) {
                callbacListener.onError(error);
            }
        });
    }

    public void reactiveNipAccount(String user_name, int sendMail, int accountType, final CallbacListener callbacListener) {
        String service_code = LoginModel.getInstance().getServiceCode(activity);
        ReactiveNip reactiveNip = new ReactiveNip();
        reactiveNip.setUsername(user_name);
        reactiveNip.setService_code(service_code);
        if (sendMail == 1) {
            reactiveNip.setSendMail(1);
        } else
            reactiveNip.setSendMail(0);

        if (accountType == 1) {
            reactiveNip.setAccountType("EMAIL");
        } else
            reactiveNip.setAccountType("PHONE-NUMBER");

        LoginModel.getInstance().reactiveNipAccoint(JsonHelper.toJson(reactiveNip), new ResponseHandle<RESP_Reactive>(RESP_Reactive.class) {
            @Override
            public void onSuccess(RESP_Reactive obj) {
                callbacListener.onSuccess(obj);
            }

            @Override
            public void onError(Error error) {
                callbacListener.onError(error);
            }
        });
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