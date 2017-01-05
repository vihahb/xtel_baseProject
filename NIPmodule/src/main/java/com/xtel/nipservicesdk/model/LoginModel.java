package com.xtel.nipservicesdk.model;

import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.commons.Constants;
import com.xtel.nipservicesdk.model.entity.RESP_Login;
import com.xtel.nipservicesdk.utils.SharedUtils;

/**
 * Created by Lê Công Long Vũ on 12/28/2016
 */

public class LoginModel extends BasicModel {
    String url_facebook = Constants.URL_NIP + Constants.API_FACEBOOK;
    String url_account_kit = Constants.URL_NIP + Constants.API_ACCOUNT_KIT;
    String url_sesion = Constants.URL_NIP + Constants.SESSION;
    private static LoginModel instance = new LoginModel();

    public static LoginModel getInstance() {
        return instance;
    }

    public void postFacebookData2Server(String jsonObject, ResponseHandle<RESP_Login> responseHandle){
        requestServer.postApi(url_facebook, jsonObject, null, responseHandle);
    }

    public void postAccountKitData2Server(String jsonObject, ResponseHandle<RESP_Login> responseHandle){
        requestServer.postApi(url_account_kit, jsonObject, null, responseHandle);
    }

    public void getNewSession(String jsonObject, ResponseHandle<RESP_Login> responseHandle){
        requestServer.postApi(url_sesion, jsonObject, null, responseHandle);
    }

    public String getSessiong() {
        return SharedUtils.getInstance().getStringValue(Constants.SESSION);
    }


}
