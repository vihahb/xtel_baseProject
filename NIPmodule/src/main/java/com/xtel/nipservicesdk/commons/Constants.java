package com.xtel.nipservicesdk.commons;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Lê Công Long Vũ on 12/27/2016
 */

public class Constants {
    public static final String SHARED_NAME = "nip_service_sdk";
    public static final String SESSION = "session";
    public static final String CODE = "code";
    public static final String TYPE = "type";
    public static final String MESSAGE = "message";
    public static final String ERROR = "error";
    public static final String SERVER_UPLOAD = "replace to server upload image";

//    URL Base & API
    public static final String URL_NIP = "http://124.158.5.112:9180/nipum/";
    public static final String API_FACEBOOK = "v1.0/m/user/fb/login";
    public static final String API_ACCOUNT_KIT = "v1.0/m/user/accountkit/login";
    public static final String API_SESSION_AUTHENTICATE = "v1.0/m/user/authenticate";
}