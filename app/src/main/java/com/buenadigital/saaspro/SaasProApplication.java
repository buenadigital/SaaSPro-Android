package com.buenadigital.saaspro;

import android.app.Application;
import android.content.Context;

import com.buenadigital.saaspro.services.SettingsService;

import java.net.CookieHandler;
import java.net.CookieManager;


public class SaasProApplication extends Application {
    public static SaasProApplication sInstance;
    public static Context mContext;
    public static SettingsService mSettingsService;


    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        mContext = getApplicationContext();
        mSettingsService = new SettingsService();
        SettingsService.settings.token = null;
        SettingsService.settings.tokenExpirationDate = null;
        SettingsService.saveSettings();
        CookieHandler.setDefault(new CookieManager());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public static SaasProApplication getsInstance(){
        return sInstance;
    }
    public static Context getSaaSContext(){
        return sInstance.getApplicationContext();
    }
}
