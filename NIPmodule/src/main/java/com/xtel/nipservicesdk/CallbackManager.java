package com.xtel.nipservicesdk;

import android.Manifest;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.xtel.nipservicesdk.callback.CallbackListenerActive;
import com.xtel.nipservicesdk.callback.CallbacListener;
import com.xtel.nipservicesdk.callback.CallbackLisenerRegister;
import com.xtel.nipservicesdk.callback.CallbackListenerReactive;
import com.xtel.nipservicesdk.callback.CallbackListenerReset;
import com.xtel.nipservicesdk.callback.ICmd;
import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.commons.Constants;
import com.xtel.nipservicesdk.model.LoginModel;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtel.nipservicesdk.model.entity.RESP_Login;
import com.xtel.nipservicesdk.model.entity.RESP_None;
import com.xtel.nipservicesdk.model.entity.RESP_Reactive;
import com.xtel.nipservicesdk.model.entity.RESP_Register;
import com.xtel.nipservicesdk.model.entity.RESP_Reset;
import com.xtel.nipservicesdk.model.entity.ReactiveNip;
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
    private CallbackLisenerRegister callbacListenerRegister;
    private Activity activity;
    private ArrayList<Object> object = new ArrayList<>();

    private ICmd iCmd = new ICmd() {
        @Override
        public void execute() {
            Log.e("TAG", "DFDFFDSFDSFDS      " + object.get(0));
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
            } else if ((Integer) object.get(0) == 2) {
                LoginModel.getInstance().postFacebookData2Server((String) object.get(1), (String) object.get(2), new ResponseHandle<RESP_Login>(RESP_Login.class) {
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
            } else if ((Integer) object.get(0) == 3){
                LoginModel.getInstance().postAccountKitData2Server((String) object.get(1), (String) object.get(2), new ResponseHandle<RESP_Login>(RESP_Login.class) {
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
            } else if ((Integer) object.get(0) == 4){
                LoginModel.getInstance().loginNipServices((String) object.get(1), (String) object.get(2), (String) object.get(3), new ResponseHandle<RESP_Login>(RESP_Login.class) {
                    @Override
                    public void onSuccess(RESP_Login obj) {
                        callbacListener.onSuccess(obj);
                    }

                    @Override
                    public void onError(Error error) {
                        callbacListener.onError(error);
                    }
                });
            } else if ((Integer) object.get(0) == 5){
                LoginModel.getInstance().registerAccountNip((String) object.get(1), (String) object.get(2), (String) object.get(3), (int) object.get(4), (String) object.get(5), (String) object.get(6), new ResponseHandle<RESP_Register>(RESP_Register.class) {
                    @Override
                    public void onSuccess(RESP_Register obj) {
                        SharedUtils.getInstance().putStringValue(Constants.USER_ACTIVATION_CODE, obj.getActivation_code());
                        callbacListenerRegister.onSuccess(obj);
                    }

                    @Override
                    public void onError(Error error) {
                        callbacListenerRegister.onError(error);
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

    public void LoginFaceook(String token_key, final CallbacListener callbacListener) {
        String service_code = LoginModel.getInstance().getServiceCode(activity);

        if (service_code == null || service_code.isEmpty()) {
            callbacListener.onError(new Error(-2, activity.getString(R.string.error), activity.getString(R.string.error_no_service_code)));
            return;
        }

        Log.e("TAG", "DFDFFDSFDSFDS");

        this.callbacListener = callbacListener;

        object.clear();
        object.add(2);
        object.add(service_code);
        object.add(token_key);


        if (checkPermission())
            iCmd.execute();
    }

    public void LoginAccountKit(String authorization_code, final CallbacListener callbacListener) {
        String service_code = LoginModel.getInstance().getServiceCode(activity);

        if (service_code == null || service_code.isEmpty()) {
            callbacListener.onError(new Error(-2, activity.getString(R.string.error), activity.getString(R.string.error_no_service_code)));
            return;
        }

        Log.e("TAG", "DFDFFDSFDSFDS");

        this.callbacListener = callbacListener;

        object.clear();
        object.add(3);
        object.add(service_code);
        object.add(authorization_code);


        if (checkPermission())
            iCmd.execute();
    }

    public void LoginNipAcc(Activity activity, String user_name, String password, final CallbacListener callbacListener) {
        String service_code = LoginModel.getInstance().getServiceCode(activity);
        if (service_code == null || service_code.isEmpty()) {
            callbacListener.onError(new Error(-2, activity.getString(R.string.error), activity.getString(R.string.error_no_service_code)));
            return;
        }

        Log.e("TAG", "DFDFFDSFDSFDS");

        this.callbacListener = callbacListener;

        object.clear();
        object.add(4);
        object.add(user_name);
        object.add(password);
        object.add(service_code);

        if (checkPermission())
            iCmd.execute();
    }

    public void registerNipService(String user_name, String password, String email, int sendMail, String type, final CallbackLisenerRegister callbackLisenerRegister) {
        String service_code = LoginModel.getInstance().getServiceCode(activity);
        if (service_code == null || service_code.isEmpty()) {
            callbacListenerRegister.onError(new Error(-2, activity.getString(R.string.error), activity.getString(R.string.error_no_service_code)));
            return;
        }

        Log.e("TAG", "DFDFFDSFDSFDS");

        this.callbacListenerRegister = callbackLisenerRegister;

        object.clear();
        object.add(5);
        object.add(user_name);
        object.add(password);
        object.add(email);
        object.add(sendMail);
        object.add(type);
        object.add(service_code);

        if (checkPermission())
            iCmd.execute();

    }

    public void resetNipAccount(String email, int sendEmail, final CallbackListenerReset callbacListener) {
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

    public void activeNipAccount(String authorization_code, String accountType, final CallbackListenerActive callbackListenerActive) {
        LoginModel.getInstance().activeAccount(authorization_code, accountType, new ResponseHandle<RESP_None>(RESP_None.class) {
            @Override
            public void onSuccess(RESP_None obj) {
                callbackListenerActive.onSuccess();
            }

            @Override
            public void onError(Error error) {
                callbackListenerActive.onError(error);
            }
        });
    }

    public void reactiveNipAccount(String user_name, int sendMail, int accountType, final CallbackListenerReactive callbacListener) {
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