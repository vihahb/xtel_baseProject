package com.xtel.nipservicesdk;

import android.app.Application;
import android.content.Context;

/**
 * Created by Lê Công Long Vũ on 1/4/2017
 */

public class MyApplication extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}
