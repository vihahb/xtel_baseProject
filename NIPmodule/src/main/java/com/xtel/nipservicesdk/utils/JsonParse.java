package com.xtel.nipservicesdk.utils;

import android.view.View;

import com.xtel.nipservicesdk.MyApplication;
import com.xtel.nipservicesdk.R;

import org.json.JSONObject;

/**
 * Created by Lê Công Long Vũ on 11/9/2016
 */

public class JsonParse {

    public static boolean checkJsonObject(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getCodeError(View view, int code, String content) {

        return content;
    }

    public static String getCodeMessage(int code, String content) {

        if (code == 2) {
            return MyApplication.context.getString(R.string.error_session_invalid);
        } else if (code == 1) {
            return MyApplication.context.getString(R.string.error_input_invalid);
        } else if (code == 4) {
            return MyApplication.context.getString(R.string.error_system);
        } else if (code == 401) {
            return MyApplication.context.getString(R.string.error_activation_failed);
        } else if (code == 100) {
            return MyApplication.context.getString(R.string.error_service_not_valid);
        } else if (code == 101) {
            return MyApplication.context.getString(R.string.error_service_in_valid);
        } else if (code == 102) {
            return MyApplication.context.getString(R.string.error_email_already);
        } else if (code == 103) {
            return MyApplication.context.getString(R.string.error_user_name_already);
        } else if (code == 104) {
            return MyApplication.context.getString(R.string.error_activation_wrong);
        } else if (code == 105) {
            return MyApplication.context.getString(R.string.error_account_already_activated);
        } else if (code == 106) {
            return MyApplication.context.getString(R.string.error_url_invalid);
        }else if (code == 108) {
            return MyApplication.context.getString(R.string.error_user_invalid);
        }else if (code == 109) {
            return MyApplication.context.getString(R.string.error_email_invalid);
        }else if (code == 110) {
            return MyApplication.context.getString(R.string.error_service_not_support_device);
        }else if (code == 111) {
            return MyApplication.context.getString(R.string.error_user_or_password_wrong);
        }else if (code == 112) {
            return MyApplication.context.getString(R.string.error_account_not_active);
        }else if (code == 113) {
            return MyApplication.context.getString(R.string.error_authentication_id_wrong);
        }else if (code == 114) {
            return MyApplication.context.getString(R.string.error_authentication_id_invalid);
        }else if (code == 117) {
            return MyApplication.context.getString(R.string.error_facebook_access_token_invalid);
        }else if (code == 118) {
            return MyApplication.context.getString(R.string.error_phone_number_wrong);
        } else {
            return content;
        }

    }
}