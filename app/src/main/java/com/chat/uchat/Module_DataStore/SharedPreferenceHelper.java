package com.chat.uchat.Module_DataStore;

import android.content.Context;
import android.content.SharedPreferences;

import com.chat.uchat.Module_DataModel.MDL_User;


public class SharedPreferenceHelper {
    private static SharedPreferenceHelper instance = null;
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;
    private static String SHARE_USER_INFO = "userinfo";
    private static String SHARE_KEY_NAME = "name";
    private static String SHARE_KEY_EMAIL = "email";
    private static String SHARE_KEY_AVATA = "avata";
    private static String SHARE_KEY_UID = "uid";


    private SharedPreferenceHelper() {}

    public static SharedPreferenceHelper getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferenceHelper();
            preferences = context.getSharedPreferences(SHARE_USER_INFO, Context.MODE_PRIVATE);
            editor = preferences.edit();
        }
        return instance;
    }

    public void saveUserInfo(MDL_User MDLUser) {
        editor.putString(SHARE_KEY_NAME, MDLUser.name);
        editor.putString(SHARE_KEY_EMAIL, MDLUser.email);
        editor.putString(SHARE_KEY_AVATA, MDLUser.avata);
        editor.putString(SHARE_KEY_UID, StaticConfig.UID);
        editor.apply();
    }

    public MDL_User getUserInfo(){
        String userName = preferences.getString(SHARE_KEY_NAME, "");
        String email = preferences.getString(SHARE_KEY_EMAIL, "");
        String avatar = preferences.getString(SHARE_KEY_AVATA, "default");

        MDL_User MDLUser = new MDL_User();
        MDLUser.name = userName;
        MDLUser.email = email;
        MDLUser.avata = avatar;

        return MDLUser;
    }

    public String getUID(){
        return preferences.getString(SHARE_KEY_UID, "");
    }

}
