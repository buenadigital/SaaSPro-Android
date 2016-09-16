package com.buenadigital.saaspro.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

import org.json.JSONException;
import org.json.JSONObject;

public class QuestionCheckActivity extends AppCompatActivity {

    private Response.Listener<String> mApiValidateSecurityQuestionHandler;

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

    private TextView mQuestionTextView;
    private MaterialEditTextMedium mAnswerEditText;
    private Button mContinueButton;

    private WebService mWebService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.animation_fade_in, R.anim.animation_fade_out);
        setContentView(R.layout.activity_question_check);
        mContext = this;

        mContinueButton = (Button) findViewById(R.id.question_page_button_continue);
        mAnswerEditText = (MaterialEditTextMedium) findViewById(R.id.question_page_answer);
        mQuestionTextView = (TextView) findViewById(R.id.question_page_question);
        mContinueButton.setVisibility(View.INVISIBLE);
        mAnswerEditText.setVisibility(View.INVISIBLE);
        mQuestionTextView.setVisibility(View.INVISIBLE);
        if(SettingsService.settings.securityQuestion != null)
            mQuestionTextView.setText(getString(R.string.question) + SettingsService.settings.securityQuestion);
        mLogoImageView = (ImageView) findViewById(R.id.logo_image);
        mText_S = (TextView) findViewById(R.id.logo_text_S);
        mText_a = (TextView) findViewById(R.id.logo_text_a);
        mText_a2 = (TextView) findViewById(R.id.logo_text_a2);
        mText_S2 = (TextView) findViewById(R.id.logo_text_S2);
        mText_P = (TextView) findViewById(R.id.logo_text_P);
        mText_r = (TextView) findViewById(R.id.logo_text_r);
        mText_o = (TextView) findViewById(R.id.logo_text_o);

        mPageLabel = (TextView) findViewById(R.id.question_page_label);

        mText_S.setTypeface(Constants.TYPE_FACE_FONT_ANTENNA_MEDIUM);
        mText_a.setTypeface(Constants.TYPE_FACE_FONT_ANTENNA_MEDIUM);
        mText_a2.setTypeface(Constants.TYPE_FACE_FONT_ANTENNA_MEDIUM);
        mText_S2.setTypeface(Constants.TYPE_FACE_FONT_ANTENNA_MEDIUM);
        mText_P.setTypeface(Constants.TYPE_FACE_FONT_ANTENNA_MEDIUM);
        mText_r.setTypeface(Constants.TYPE_FACE_FONT_ANTENNA_MEDIUM);
        mText_o.setTypeface(Constants.TYPE_FACE_FONT_ANTENNA_MEDIUM);
        mPageLabel.setTypeface(Constants.TYPE_FACE_FONT_ANTENNA_MEDIUM);

        AnimationUtils.applyAnimationWithFinalShow(this, mPageLabel, R.anim.animation_appear_from_nothing_tiny, 0);
        AnimationUtils.applyAnimationWithFinalShow(this, mQuestionTextView, R.anim.animation_appear_from_nothing_tiny, 120);
        AnimationUtils.applyAnimationWithFinalShow(this, mAnswerEditText, R.anim.animation_appear_from_nothing_tiny, 240);
        AnimationUtils.applyAnimationWithFinalShow(this, mContinueButton, R.anim.animation_appear_from_nothing_tiny, 360);


        setupListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebService = new WebService(mContext);
    }

    public Button getContinueButton(){
        return mContinueButton;
    }

    public void setupListeners() {
        mContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.hideSoftKeyboard(mContext);
                AnimationUtils.applyAnimation(mContext, mContinueButton, R.anim.animation_tap, 0);
                if (mAnswerEditText.getText().toString().length() > 0) {
                    mContinueButton.setEnabled(false);
                    mWebService.apiValidateSecurityQuestion(mContext, mApiValidateSecurityQuestionHandler, mAnswerEditText.getText().toString());
                }

            }
        });

        mAnswerEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationUtils.applyAnimation(mContext, mAnswerEditText, R.anim.animation_tap, 0);
            }
        });

        mApiValidateSecurityQuestionHandler = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Boolean success;
                JSONObject json;
                try {
                    mContinueButton.setEnabled(true);
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

                        SettingsService.settings.securityQuestionValidated = true;
                        SettingsService.saveSettings();
                        AnimationUtils.applyAnimationWithRunnable(mContext, mPageLabel, R.anim.animation_list_item_clear, 60, new Runnable() {
                            @Override
                            public void run() {
                                mPageLabel.setVisibility(View.INVISIBLE);
                                navigateToMainActivity();
                            }
                        });
                        AnimationUtils.applyAnimationWithFinalHide(mContext, mAnswerEditText, R.anim.animation_list_item_clear, 40);
                        AnimationUtils.applyAnimationWithFinalHide(mContext, mQuestionTextView, R.anim.animation_list_item_clear, 20);
                        AnimationUtils.applyAnimationWithFinalHide(mContext, mContinueButton, R.anim.animation_list_item_clear, 0);
                        Utils.hideLoadingProgress(mContext);

                    }
                } catch (JSONException e) {
                    Messages.errorMessage(mContext, "JSONException");
                    Utils.hideLoadingProgress(mContext);
                } finally {
                    mContinueButton.setEnabled(true);
                }

            }
        };

    }

    public void navigateToMainActivity(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}
