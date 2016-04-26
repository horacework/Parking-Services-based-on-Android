package com.example.horacechan.parking.util;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;

public class DeviceUtils {

    public static String getImieStatus(Activity activity) {
        TelephonyManager tm = (TelephonyManager)activity.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }
}
