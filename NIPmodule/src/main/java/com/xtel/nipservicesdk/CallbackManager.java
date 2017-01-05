package com.xtel.nipservicesdk;

import android.support.annotation.NonNull;

/**
 * Created by Lê Công Long Vũ on 1/5/2017
 */

public class CallbackManager {

    public static CallbackManager create() {
        return new CallbackManager();
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    }
}