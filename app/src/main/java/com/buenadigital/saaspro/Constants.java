package com.buenadigital.saaspro;

import android.graphics.Typeface;


public class Constants {
    // Currently used Api Url
    // release
    public static String BASE_API_ADDRESS = "https://account_placeholderQ123.saaspro.net/api";

    //test database
    // public static String BASE_API_ADDRESS = "";

    //Number of attempts to try load data
    public static final int NUMBER_OF_ATTEMPTS_ON_NETWORK_ERROR = 2;

    public static final String PREFERENCES_LEARNDRAWER_KEY = "SaaSProDrawer";
    public static final String PREFERENCES_FILE_NAME = "prefs";

    public static final String SETTINGS_KEY = "SaaSProSettingsKey";

    public static final int DIALOG_MARGIN_IN_DP = 40;

    public static final Typeface TYPE_FACE_FONT_ANTENNA_MEDIUM = Typeface.createFromAsset(SaasProApplication.getSaaSContext().getAssets(),"fonts/antennamedium.ttf");
    public static final Typeface TYPE_FACE_FONT_ROBOTO_BLACK = Typeface.createFromAsset(SaasProApplication.getSaaSContext().getAssets(),"fonts/robotoblack.ttf");
    public static final Typeface TYPE_FACE_FONT_ROBOTO_MEDIUM = Typeface.createFromAsset(SaasProApplication.getSaaSContext().getAssets(),"fonts/robotomedium.ttf");
    public static final Typeface TYPE_FACE_FONT_ROBOTO_CONDENSED = Typeface.createFromAsset(SaasProApplication.getSaaSContext().getAssets(),"fonts/robotocondensed.ttf");

}
