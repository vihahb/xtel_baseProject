package com.xtel.nipservicesdk;

import com.xtel.nipservicesdk.model.LoginModel;

/**
 * Created by Lê Công Long VŨ on 1/10/2017
 */

public class LoginManager {

    public static String getCurrentSessiong() {
        return LoginModel.getInstance().getSessiong();
    }

    public static void LogOut() {
        LoginModel.getInstance().logout();
    }
}