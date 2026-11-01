package com.musicinstrumentstore.network_service;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenManager {

    private static TokenManager instance;
    private SharedPreferences settings;


    private static final String PREFS_NAME = "auth_prefs";
    private static final String KEY_ACCESS_TOKEN = "ACCESS";
    private static final String KEY_REFRESH_TOKEN = "REFRESH";


    private TokenManager(Context context) {
        settings = context.getApplicationContext()
                .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized TokenManager getInstance(Context context) {
        if (instance == null) {
            instance = new TokenManager(context);
        }
        return instance;
    }

    public void saveTokens(String accessToken, String refreshToken) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(KEY_ACCESS_TOKEN, accessToken);
        editor.putString(KEY_REFRESH_TOKEN, refreshToken);
        editor.apply();
    }

    public String getAccessToken() {
        return settings.getString(KEY_ACCESS_TOKEN, null);
    }

    public String getRefreshToken() {
        return settings.getString(KEY_REFRESH_TOKEN, null);
    }

    public boolean hasTokens() {
        String accessToken = getAccessToken();
        String refreshToken = getRefreshToken();
        return accessToken != null && !accessToken.isEmpty()
                && refreshToken != null && !refreshToken.isEmpty();
    }

    public void clearTokens() {
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(KEY_ACCESS_TOKEN);
        editor.remove(KEY_REFRESH_TOKEN);
        editor.apply();
    }
}