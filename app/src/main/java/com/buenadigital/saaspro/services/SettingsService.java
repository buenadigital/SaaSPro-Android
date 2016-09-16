package com.buenadigital.saaspro.services;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.buenadigital.saaspro.Constants;
import com.buenadigital.saaspro.SaasProApplication;
import com.buenadigital.saaspro.models.SettingsModel;
import com.google.gson.Gson;


public class SettingsService {
    private static String KEY = Constants.SETTINGS_KEY;
    private static Context mContext;

    public static SettingsModel settings;

    public SettingsService(){
        mContext = SaasProApplication.getSaaSContext();
        //if(settings == null){
        settings = getSettings();
        //}
    }

    public static void saveSettings(){
        if(settings == null){
            settings = new SettingsModel();
        }
        SharedPreferences.Editor editor = mContext
                .getSharedPreferences(KEY, Activity.MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String jsonSettings = gson.toJson(settings);
        editor.putString("settings", jsonSettings );
        editor.commit();
        settings = getSettings();
    }


    public static SettingsModel getSettings() {
        SharedPreferences editor = mContext.getSharedPreferences(KEY,
                Activity.MODE_PRIVATE);
        try{
            String jsonSettings = editor.getString("settings","settings");
            Gson gson = new Gson();
            settings = gson.fromJson(jsonSettings, SettingsModel.class);
        }catch (Exception e){
            settings = new SettingsModel();
        }

        return settings;
    }


}
