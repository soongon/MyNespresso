package com.mcjang.mynespresso.db.mysql;

import android.content.Context;

import com.mcjang.mynespresso.app.capsule.vo.Capsule;

/**
 * Created by mcjan on 2016-12-01.
 */

public interface CallbackReturnWithCapsule {

    public void callback(Context context, Object object, Capsule capsules);

}
