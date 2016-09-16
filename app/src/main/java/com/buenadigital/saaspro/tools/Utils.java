package com.buenadigital.saaspro.tools;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.buenadigital.saaspro.R;
import com.buenadigital.saaspro.SaasProApplication;
import com.buenadigital.saaspro.activities.LoginActivity;
import com.buenadigital.saaspro.activities.MainActivity;
import com.buenadigital.saaspro.activities.QuestionCheckActivity;
import com.buenadigital.saaspro.activities.SplashScreen;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by elser on 10.11.2015.
 */
public class Utils {
    private static Typeface mTypeFace;


    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static String getAppVersion(Context context)
    {
        android.content.pm.PackageInfo appversion = null;

        try {
            appversion = context.getPackageManager().getPackageInfo(context.getPackageName(),0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (appversion != null)
            return  appversion.versionName;
        else
            return "";

    }


    public static String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return s;
    }

    public static int dpToPx(int dp) {
        return (int)(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, SaasProApplication.getSaaSContext().getResources().getDisplayMetrics()));
    }

    public static String decimalWithCommas(String decimalStringValue){
        if(!decimalStringValue.contains("."))
            decimalStringValue = decimalStringValue + ".";
        decimalStringValue = decimalStringValue.replaceFirst("(\\d)(\\d)(\\d)(\\d)(?=\\.)","$1,$2$3$4");
        while(!decimalStringValue.equals(decimalStringValue.replaceFirst("(\\d)(\\d)(\\d)(\\d)(?=\\,)","$1,$2$3$4"))){
            decimalStringValue = decimalStringValue.replaceFirst("(\\d)(\\d)(\\d)(\\d)(?=\\,)","$1,$2$3$4");
        }
        if(decimalStringValue.substring(decimalStringValue.length()-1).equals(".")){
            decimalStringValue = decimalStringValue.substring(0,decimalStringValue.length()-1);
        }
        decimalStringValue = decimalStringValue.replaceFirst("\\.(\\d)$",".$1"+"0");

        return decimalStringValue;
    }

    public static String formatPhone(String phone){
        if(phone.length()<2){
            phone = "+1";
            return phone;
        }else{
            if(!(phone.contains("+"))){
                phone = "+1" + phone;
            }
            phone = phone.replaceAll("-","");
            phone = phone.replaceAll("([^\\d\\-\\+]+)","");
            phone = phone.replaceFirst("^\\+1(\\d{1,3})","+1-$1");
            phone = phone.replaceFirst("^\\+1-(\\d{3})(\\d{1,3})","+1-$1-$2");
            phone = phone.replaceFirst("^\\+1-(\\d{3})-(\\d{3})(\\d{1,4})","+1-$1-$2-$3");
            if(phone.length()>15){
                phone = phone.substring(0,15);
            }
        }


        //+1-541-754-3010
        return phone;
    }
    public static boolean phoneNumberCorrect(String phone){
        if(phone.equals(phone.replaceFirst("^\\+1-(\\d{3})-(\\d{3})-(\\d{4})$",""))){
            return false;
        }
        return true;
    }

    public static boolean isEmailValid(CharSequence email) {
        boolean asdfsadf = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static String getCreditCardType(String ccNumber){

        String visaRegex        = "^4[0-9]{12}(?:[0-9]{3})?$";
        String masterRegex      = "^5[1-5][0-9]{14}$";
        String amexRegex        = "^3[47][0-9]{13}$";
        String dinersClubrRegex = "^3(?:0[0-5]|[68][0-9])[0-9]{11}$";
        String discoverRegex    = "^6(?:011|5[0-9]{2})[0-9]{12}$";
        String jcbRegex         = "^(?:2131|1800|35\\d{3})\\d{11}$";
        String commonRegex      = "^(?:4[0-9]{12}(?:[0-9]{3})?|5[1-5][0-9]{14}|6(?:011|5[0-9][0-9])[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35\\d{3})\\d{11})$";

        try {
            ccNumber = ccNumber.replaceAll("\\D", "");
            return (ccNumber.matches(visaRegex) ? "VISA" :
                    ccNumber.matches(masterRegex) ? "MASTERCARD" :
                            ccNumber.matches(amexRegex) ? "AMEX" :
                                    ccNumber.matches(dinersClubrRegex) ? "DINER" :
                                            ccNumber.matches(discoverRegex) ? "DISCOVER"  :
                                                    ccNumber.matches(jcbRegex) ? "JCB":null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String cardWithSpaces(String cardStringValue){
        cardStringValue = cardStringValue.replaceFirst("^(\\d{4})(\\d{4})(\\d{4})(\\d{1,4})$","$1 $2 $3 $4");
        cardStringValue = cardStringValue.replaceFirst("^(\\d{4})(\\d{4})(\\d{1,4})$","$1 $2 $3");
        cardStringValue = cardStringValue.replaceFirst("^(\\d{4})(\\d{1,4})$","$1 $2");
        return cardStringValue;
    }

    public static String cardHideVisual(String cardStringValue){
        cardStringValue = cardStringValue.replaceFirst("^(\\d{4})(\\d{4})(\\d{4})(\\d{1,4})$","**** **** **** $4");
        return cardStringValue;
    }

    public static String expirationDateWithSlash(String expStringValue){
        expStringValue = expStringValue.replaceFirst("^(\\d{2})(\\d{1,2})$","$1/$2");
        if(expStringValue.contains("/")){
            String monthCheck = expStringValue.substring(0,expStringValue.indexOf("/"));
            if(Integer.parseInt(monthCheck)>12){
                expStringValue = expStringValue.replaceFirst("^(\\d{2})/","12/");
            }
        }
        return expStringValue;
    }

    public static String firstUpperOtherLowerCase(String inString){
        if(inString != null) {
            inString = inString.substring(0,1).toUpperCase() + inString.substring(1).toLowerCase();
        }
        return inString;
    }



    public static void hideSoftKeyboard(Context context) {
        View currentFocus = getMainContainerCurrentFocus(context);

        if(currentFocus!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
    }

    public static void showSoftKeyboard(EditText textEdit, Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(textEdit, InputMethodManager.SHOW_IMPLICIT);
    }


    public static boolean passwordProperEntered (String password){
        boolean proper = true;
        if(password.length() < 4)
            proper = false;
        if(password.equals(password.replaceAll("([A-Z]+)","")))
            proper = false;
        if(password.equals(password.replaceAll("([a-z]+)","")))
            proper = false;
        if(password.equals(password.replaceAll("([0-9]+)","")))
            proper = false;

        return proper;
    }

    public static SpannableString MakeSubStringClickable(String fullString, String subString, ClickableSpan clickableSpan) {
        if (fullString.indexOf(subString) < 0) {
            return null;
        } else {
            SpannableString ss = new SpannableString(fullString);

            SubStringIndices subStringIndices = GetSubStringIndices(fullString, subString);
            ss.setSpan(clickableSpan, subStringIndices.startIndex, subStringIndices.endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            return ss;
        }
    }

    public static SubStringIndices GetSubStringIndices(String fullString, String subString) {
        int startIndex = fullString.indexOf(subString);
        int endIndex = startIndex + subString.length();
        return new SubStringIndices(startIndex, endIndex);
    }

    private static class SubStringIndices {
        private Integer startIndex;
        private Integer endIndex;

        private SubStringIndices(Integer a, Integer b) {
            this.startIndex = a;
            this.endIndex = b;
        }
    }

    public static RelativeLayout getMainContainerLayout(final Context context) {
        RelativeLayout mainContainerLayout = null;
        if (context instanceof SplashScreen)
            mainContainerLayout = (RelativeLayout) ((SplashScreen) context).getWindow().getDecorView().findViewById(R.id.main_saaspro_layout);
        if (context instanceof LoginActivity)
            mainContainerLayout = (RelativeLayout) ((LoginActivity) context).getWindow().getDecorView().findViewById(R.id.main_saaspro_layout);
        if (context instanceof QuestionCheckActivity)
            mainContainerLayout = (RelativeLayout) ((QuestionCheckActivity) context).getWindow().getDecorView().findViewById(R.id.main_saaspro_layout);
        if (context instanceof MainActivity)
            mainContainerLayout = (RelativeLayout) ((MainActivity) context).getWindow().getDecorView().findViewById(R.id.main_saaspro_layout);
        return mainContainerLayout;
    }

    public static View getMainContainerCurrentFocus(final Context context) {
        View mainContainerFocus = null;
        if (context instanceof SplashScreen)
            mainContainerFocus = ((SplashScreen) context).getCurrentFocus();
        if (context instanceof LoginActivity)
            mainContainerFocus = ((LoginActivity) context).getCurrentFocus();
        if (context instanceof QuestionCheckActivity)
            mainContainerFocus = ((QuestionCheckActivity) context).getCurrentFocus();
        if (context instanceof MainActivity)
            mainContainerFocus = ((MainActivity) context).getCurrentFocus();


        return mainContainerFocus;
    }

    public static void showLoadingProgress(Context context, String message){
        RelativeLayout mainContainerLayout = Utils.getMainContainerLayout(context);

        if(mainContainerLayout != null){
            LinearLayout checkForErrorLayoutExists = (LinearLayout) mainContainerLayout.findViewById(R.id.saas_progress_layout);
            if(checkForErrorLayoutExists == null){
                View progressLayout = View.inflate(context,R.layout.custom_progress_layout, null);
                TextView progressText = (TextView) progressLayout.findViewById(R.id.progress_info_text);
                progressText.setText(message);
                mainContainerLayout.addView(progressLayout);
                progressText.clearAnimation();
                AnimationUtils.applyAnimationRepeatless(context,progressText,R.anim.animation_progress_text,0);
            }else{
                checkForErrorLayoutExists.setVisibility(View.VISIBLE);
                TextView progressText = (TextView) checkForErrorLayoutExists.findViewById(R.id.progress_info_text);
                progressText.setText(message);
                progressText.clearAnimation();
                AnimationUtils.applyAnimationRepeatless(context, progressText, R.anim.animation_progress_text, 0);
            }

        }

    }

    public static void hideLoadingProgress(Context context){
        RelativeLayout mainContainerLayout = Utils.getMainContainerLayout(context);
        LinearLayout checkForErrorLayoutExists = (LinearLayout) mainContainerLayout.findViewById(R.id.saas_progress_layout);
        if(checkForErrorLayoutExists != null){
            checkForErrorLayoutExists.setVisibility(View.GONE);
        }

    }



//    public static String moneyWithSign(Double value){
//        value = round(value,2);
//        return String.format(SaasProApplication.getSaaSContext().getString(R.string.money_sign), Utils.decimalWithCommas(value+"")).toString();
//    }
//    public static String moneyWithSign(String value){
//        value = round(Double.parseDouble(value),2)+"";
//        return String.format(SaasProApplication.getSaaSContext().getString(R.string.money_sign), Utils.decimalWithCommas(value)).toString();
//    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static String dateVisualExtract(String date){
        return date.replaceAll("^(\\d+)\\-(\\d+)\\-(\\d+)T(.+)$","$2/$3/$1");
    }

    public static float displayWidth(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();

        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        return displayMetrics.widthPixels / displayMetrics.density;
    }

    public static float displayHeight(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();

        return displayMetrics.heightPixels / displayMetrics.density;
    }



}
