package com.example.qr_go_gotta_scan_em_all;import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;

public class DeviceIdHelper {
    private Context mContext;

    public DeviceIdHelper(Context context) {
        mContext = context;
    }

    public String getDeviceId() {
        TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = telephonyManager.getDeviceId();

        if (deviceId == null) {
            // If the device ID is not available, use the Android ID instead
            deviceId = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
        }

        return deviceId;
    }
}