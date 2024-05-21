package com.test.speechtotext;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.IntDef;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public class MyApplication extends MultiDexApplication {

    private static MyApplication mInstance;
    //private RequestQueue mRequestQueue;
    public static final String TAG = MyApplication.class.getSimpleName();
//    private AppLocationListener mListener;

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef(
            {GRANTED, DENIED, BLOCKED})
    public @interface PermissionStatus {
    }

    public static final int GRANTED = 0;
    public static final int DENIED = 1;
    public static final int BLOCKED = 2;

    @PermissionStatus
    public static int getPermissionStatus(Activity activity, String androidPermissionName) {
        if (ContextCompat.checkSelfPermission(activity, androidPermissionName) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, androidPermissionName)) {
                return BLOCKED;
            }
            return DENIED;
        }
        return GRANTED;
    }


    @Override
    public void onCreate() {
        MultiDex.install(this);
        super.onCreate();
        mInstance = this;

    }

}

