package com.buenadigital.saaspro.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.buenadigital.saaspro.Constants;
import com.buenadigital.saaspro.R;
import com.buenadigital.saaspro.customModels.MaterialEditTextMedium;
import com.buenadigital.saaspro.services.SettingsService;
import com.buenadigital.saaspro.services.WebService;
import com.buenadigital.saaspro.tools.AnimationUtils;
import com.buenadigital.saaspro.tools.Messages;
import com.buenadigital.saaspro.tools.Utils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private Response.Listener<String> mApiLoginHandler;

    private Context mContext;
    private ImageView mLogoImageView;
    private TextView mText_S;
    private TextView mText_a;
    private TextView mText_a2;
    private TextView mText_S2;
    private TextView mText_P;
    private TextView mText_r;
    private TextView mText_o;

    private TextView mPageLabel;

    private MaterialEditTextMedium mEditTextAccount;
    private MaterialEditTextMedium mEditTextUsername;
    private MaterialEditTextMedium mEditTextPassword;
    private Button mLoginButton;
    private CheckBox mCheckboxRememberMe;



    private WebService mWebService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.animation_fade_in, R.anim.animation_fade_out);
        setContentView(R.layout.activity_login);
        mLogoImageView = (ImageView) findViewById(R.id.logo_image);
        mText_S = (TextView) findViewById(R.id.logo_text_S);
        mText_a = (TextView) findViewById(R.id.logo_text_a);
        mText_a2 = (TextView) findViewById(R.id.logo_text_a2);
        mText_S2 = (TextView) findViewById(R.id.logo_text_S2);
        mText_P = (TextView) findViewById(R.id.logo_text_P);
        mText_r = (TextView) findViewById(R.id.logo_text_r);
        mText_o = (TextView) findViewById(R.id.logo_text_o);

        mPageLabel = (TextView) findViewById(R.id.login_page_label);
        mEditTextAccount = (MaterialEditTextMedium) findViewById(R.id.login_page_account);
        mEditTextUsername = (MaterialEditTextMedium) findViewById(R.id.login_page_username);
        mEditTextPassword = (MaterialEditTextMedium) findViewById(R.id.login_page_password);
        mLoginButton = (Button) findViewById(R.id.login_page_button_login);
        mCheckboxRememberMe = (CheckBox) findViewById(R.id.login_page_remember_me_checkbox);
        mPageLabel.setVisibility(View.INVISIBLE);
        mEditTextAccount.setVisibility(View.INVISIBLE);
        mEditTextUsername.setVisibility(View.INVISIBLE);
        mEditTextPassword.setVisibility(View.INVISIBLE);
        mLoginButton.setVisibility(View.INVISIBLE);
        mCheckboxRememberMe.setVisibility(View.INVISIBLE);


        mText_S.setTypeface(Constants.TYPE_FACE_FONT_ANTENNA_MEDIUM);
        mText_a.setTypeface(Constants.TYPE_FACE_FONT_ANTENNA_MEDIUM);
        mText_a2.setTypeface(Constants.TYPE_FACE_FONT_ANTENNA_MEDIUM);
        mText_S2.setTypeface(Constants.TYPE_FACE_FONT_ANTENNA_MEDIUM);
        mText_P.setTypeface(Constants.TYPE_FACE_FONT_ANTENNA_MEDIUM);
        mText_r.setTypeface(Constants.TYPE_FACE_FONT_ANTENNA_MEDIUM);
        mText_o.setTypeface(Constants.TYPE_FACE_FONT_ANTENNA_MEDIUM);
        mEditTextAccount.setTypeface(Constants.TYPE_FACE_FONT_ANTENNA_MEDIUM);
        mEditTextUsername.setTypeface(Constants.TYPE_FACE_FONT_ANTENNA_MEDIUM);
        mEditTextPassword.setTypeface(Constants.TYPE_FACE_FONT_ANTENNA_MEDIUM);
        mLoginButton.setTypeface(Constants.TYPE_FACE_FONT_ANTENNA_MEDIUM);
        mCheckboxRememberMe.setTypeface(Constants.TYPE_FACE_FONT_ANTENNA_MEDIUM);

        mPageLabel.setTypeface(Constants.TYPE_FACE_FONT_ANTENNA_MEDIUM);


        if(SettingsService.settings.saveCredentials){
            if(SettingsService.settings.email != null && SettingsService.settings.password != null && SettingsService.settings.account != null){

            }
            mEditTextAccount.setText(SettingsService.settings.account);
            mEditTextUsername.setText(SettingsService.settings.email);
            mEditTextPassword.setText(SettingsService.settings.password);
            mCheckboxRememberMe.setChecked(true);
        }else{
            mCheckboxRememberMe.setChecked(false);
        }

        mContext = this;
        int myTimer = 500;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                applyStartAnimations();
            }
        }, myTimer);
    }



    @Override
    protected void onResume() {
        super.onResume();
        mWebService = new WebService(mContext);

    }

    public Button getLoginButton(){
        return mLoginButton;
    }

    public void applyStartAnimations(){

        //AnimationUtils.translateAnimation(mContext, mLogoImageView, 0, Utils.dpToPx(50));

        AnimationUtils.applyAnimationRelocation(this, mLogoImageView, R.anim.animation_move_top, 200, 0, 0, 0, 240);
        AnimationUtils.applyAnimationRelocation(this, mText_S, R.anim.animation_move_top, 220, 0, 0, 0, 240);
        AnimationUtils.applyAnimationRelocation(this, mText_a, R.anim.animation_move_top, 240, 0, 0, 0, 240);
        AnimationUtils.applyAnimationRelocation(this, mText_a2, R.anim.animation_move_top, 260, 0, 0, 0, 240);
        AnimationUtils.applyAnimationRelocation(this, mText_S2, R.anim.animation_move_top, 280, 0, 0, 0, 240);
        AnimationUtils.applyAnimationRelocation(this, mText_P, R.anim.animation_move_top, 300, 0, 0, 0, 240);
        AnimationUtils.applyAnimationRelocation(this, mText_r, R.anim.animation_move_top, 320, 0, 0, 0, 240);
        AnimationUtils.applyAnimationRelocation(this, mText_o, R.anim.animation_move_top, 340, 0, 0, 0, 240);

        AnimationUtils.applyAnimationWithFinalShow(this, mPageLabel, R.anim.animation_fade_out, 460);
        AnimationUtils.applyAnimationWithFinalShow(this, mEditTextAccount, R.anim.animation_fade_out, 580);
        AnimationUtils.applyAnimationWithFinalShow(this, mEditTextUsername, R.anim.animation_appear_from_nothing_tiny, 700);
        AnimationUtils.applyAnimationWithFinalShow(this, mEditTextPassword, R.anim.animation_appear_from_nothing_tiny, 820);
        AnimationUtils.applyAnimationWithFinalShow(this, mCheckboxRememberMe, R.anim.animation_appear_from_nothing_tiny, 940);
        AnimationUtils.applyAnimationWithFinalShow(this, mLoginButton, R.anim.animation_appear_from_nothing_tiny, 1060);
        setupListeners();
    }

    public void setupListeners() {
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            AnimationUtils.applyAnimation(mContext, mLoginButton, R.anim.animation_tap, 0);
            if(mEditTextUsername.getText().toString().length()>0 && mEditTextPassword.getText().toString().length()>0){
                mLoginButton.setEnabled(false);
                Utils.hideSoftKeyboard(mContext);
                SettingsService.settings.account = mEditTextAccount.getText().toString();
                SettingsService.saveSettings();
                mWebService.apiLogin(mContext,mApiLoginHandler,mEditTextUsername.getText().toString(),mEditTextPassword.getText().toString());
            }

            }
        });
        mEditTextAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationUtils.applyAnimation(mContext, mEditTextAccount, R.anim.animation_tap, 0);
            }
        });
        mEditTextPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationUtils.applyAnimation(mContext, mEditTextPassword, R.anim.animation_tap, 0);
            }
        });
        mEditTextUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationUtils.applyAnimation(mContext, mEditTextUsername, R.anim.animation_tap, 0);
            }
        });

        mApiLoginHandler = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Boolean success;
                JSONObject json;
                try {
                    mLoginButton.setEnabled(true);
                    success = false;
                    // Get JSONObject from response;
                    json = new JSONObject(response);
                    // First check our errorCode information from the response.
                    if (json.has("success") && json.getBoolean("success") == true)
                        success = true;

                    // Let's display any returned errors.
                    if (!success) {
                        if (json.has("error")) {
                            Messages.errorMessage(mContext, json.getJSONObject("error"));
                        }
                        Utils.hideLoadingProgress(mContext);
                    } else {
                        if (json.has("data")) {
                            SettingsService.settings.email = mEditTextUsername.getText().toString();
                            SettingsService.settings.password = mEditTextPassword.getText().toString();
                            SettingsService.settings.token = json.getJSONObject("data").getString("token");
                            SettingsService.settings.tokenExpirationDate = json.getJSONObject("data").getString("expirationDate");
                            SettingsService.settings.securityQuestion = json.getJSONObject("data").getString("securityQuestion");
                            SettingsService.settings.securityQuestionValidated = false;
                            if(mCheckboxRememberMe.isChecked())
                                SettingsService.settings.saveCredentials = true;
                            else
                                SettingsService.settings.saveCredentials = false;

                            SettingsService.saveSettings();
                            AnimationUtils.applyAnimationWithRunnable(mContext, mPageLabel, R.anim.animation_list_item_clear, 100, new Runnable() {
                                @Override
                                public void run() {
                                    mPageLabel.setVisibility(View.INVISIBLE);
                                    navigateToQuestionCheck();
                                }
                            });
                            AnimationUtils.applyAnimationWithFinalHide(mContext, mEditTextAccount, R.anim.animation_list_item_clear, 80);
                            AnimationUtils.applyAnimationWithFinalHide(mContext, mEditTextUsername, R.anim.animation_list_item_clear, 60);
                            AnimationUtils.applyAnimationWithFinalHide(mContext, mEditTextPassword, R.anim.animation_list_item_clear, 40);
                            AnimationUtils.applyAnimationWithFinalHide(mContext, mCheckboxRememberMe, R.anim.animation_list_item_clear, 20);
                            AnimationUtils.applyAnimationWithFinalHide(mContext, mLoginButton, R.anim.animation_list_item_clear, 0);
                            Utils.hideLoadingProgress(mContext);

                        }
                    }
                } catch (JSONException e) {
                    Messages.errorMessage(mContext, "JSONException");
                    Utils.hideLoadingProgress(mContext);
                } finally {
                    mLoginButton.setEnabled(true);
                }

            }
        };
//        mApiGetSecurityQuestionHandler = new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Boolean success;
//                JSONObject json;
//                try {
//                    mLoginButton.setEnabled(true);
//                    success = false;
//                    // Get JSONObject from response;
//                    json = new JSONObject(response);
//                    // First check our errorCode information from the response.
//                    if (json.has("success") || json.has("Success"))
//                        success = true;
//
//                    // Let's display any returned errors.
//                    if (!success) {
//                        if (json.has("error")) {
//                            Messages.errorMessage(mContext, json.getJSONObject("error"));
//                        }
//                    } else {
//                        if (json.has("data")) {
//                            SettingsService.settings.securityQuestion = json.getString("data");
//                            SettingsService.saveSettings();
//                            AnimationUtils.applyAnimationWithRunnable(mContext, mEditTextUsername, R.anim.animation_list_item_clear, 60, new Runnable() {
//                                @Override
//                                public void run() {
//                                    mEditTextUsername.setVisibility(View.INVISIBLE);
//                                    navigateToQuestionCheck();
//                                }
//                            });
//                            AnimationUtils.applyAnimationWithFinalHide(mContext, mEditTextPassword, R.anim.animation_list_item_clear, 40);
//                            AnimationUtils.applyAnimationWithFinalHide(mContext, mCheckboxRememberMe, R.anim.animation_list_item_clear, 20);
//                            AnimationUtils.applyAnimationWithFinalHide(mContext, mLoginButton, R.anim.animation_list_item_clear, 0);
//
//                        }
//                    }
//                } catch (JSONException e) {
//                    Messages.errorMessage(mContext, "JSONException");
//                } finally {
//                    Utils.hideLoadingProgress(mContext);
//                    mLoginButton.setEnabled(true);
//                }
//
//            }
//        };
    }

    public void navigateToQuestionCheck(){
        startActivity(new Intent(this, QuestionCheckActivity.class));
        finish();
    }

}
