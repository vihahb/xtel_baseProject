package com.xtel.nipservicesdk.callback;

import com.xtel.nipservicesdk.model.entity.Error;
import com.xtel.nipservicesdk.model.entity.RESP_Basic;

/**
 * Created by vihahb on 1/4/2017
 */

public interface CallbacListener {

    void onSuccess(RESP_Basic success);
    void onError(Error error);
}
