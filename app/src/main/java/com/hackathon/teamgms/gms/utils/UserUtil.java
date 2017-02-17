package com.hackathon.teamgms.gms.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hackathon.teamgms.gms.Config;

/**
 * Created by yunjeonghwang on 2017. 2. 18..
 */

public class UserUtil {
    public static String uid;

    public static String loadUserFirebaseUid(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if(sharedPreferences != null)
            uid = sharedPreferences.getString(Config.SHARED_UID, null);
        return uid;
    }
}
