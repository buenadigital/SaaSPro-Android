package com.buenadigital.saaspro.services;

import android.content.Context;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.buenadigital.saaspro.Constants;
import com.buenadigital.saaspro.activities.LoginActivity;
import com.buenadigital.saaspro.activities.QuestionCheckActivity;
import com.buenadigital.saaspro.tools.Messages;
import com.buenadigital.saaspro.tools.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


public class WebService {

    private static final String HTTP_CONTENT_TYPE = "application/json";

    private static String BASE_API_ADDRESS = Constants.BASE_API_ADDRESS;

    private static final int TIMEOUT_IN_MILLISECONDS = 60000;

    private static String API_LOGIN = BASE_API_ADDRESS + "/auth/login";
    private static String API_GET_SECURITY_QUESTION = BASE_API_ADDRESS + "/auth/get-security-question";
    private static String API_VALIDATE_SECURITY_QUESTION = BASE_API_ADDRESS + "/auth/validate-security-answer";


    private DefaultRetryPolicy mRetryPolicy = new DefaultRetryPolicy(
            TIMEOUT_IN_MILLISECONDS,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);


    public static final String TAG = WebService.class.getSimpleName();

    public static final String mAccess = "";

    private static RequestQueue mVolleyApiClient;

    private static String mSessionCode = "";

    private Response.ErrorListener errorListener;

    private VolleyServiceSingleton mVolleySingleton;
    private static ProgressBar mProgressBar;

    // Double attempt to load on network error
    private static VolleyStringRequest mLastDoneVolleyRequest;
    private static VolleyJsonObjectRequest mLastDoneVolleyJsonRequest;
    private static VolleyStringRequest mLastDoneVolleyRequestSaved;
    private int mNumberOfRetriesDone = 0;

    // Session auto restart Handler
    private static Response.Listener<String> mLastSavedListenerHandler;
    private static Response.Listener<String> mValidateUserHandler;
    private static String mEmail;
    private static String mPassword;

    private static Context mContext;
    private static WebService mInstance = null;


    public WebService(Context context){
        mVolleySingleton = VolleyServiceSingleton.getInstance();
        mVolleyApiClient = mVolleySingleton.gerRequestQueue();
        errorListenerSetup(context);
        mContext = context;
        mInstance = this;
        validateUserHandler();
    }

    public void validateUserHandler(){
        mValidateUserHandler = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Boolean success;
                JSONObject json;
                try {
                    success = false;
                    // Get JSONObject from response;
                    json = new JSONObject(response);
                    // First check our errorCode information from the response.
                    if(response.contains("you are logged"))
                        success = true;

                    // Let's display any returned errors.
                    if (!success) {
                        if(json.has("Error"))
                            Messages.errorMessage(mContext, "Error on request");
                    } else {

                        VolleyStringRequest request = new VolleyStringRequest(mLastDoneVolleyRequest.getMethod(), mLastDoneVolleyRequest.getUrl(), mLastSavedListenerHandler, errorListener, mLastDoneVolleyRequest.getParams());
                        mVolleyApiClient.add(request);

                    }
                } catch (JSONException e) {
                    Messages.errorMessage(mContext, "JSONException");
                } finally {
                    //mWebService.hideProgressDialog();
                }

            }
        };

    }

    public void apiLogin(Context context, Response.Listener<String> responseHandler, String email, String password){
        mLastSavedListenerHandler = responseHandler;
        showProgressDialog(context,"Logging in..");
        HashMap<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("Email", email);
        paramMap.put("Password", password);

        VolleyStringRequest request = new VolleyStringRequest(Request.Method.POST, getFinalUrl(API_LOGIN, null), responseHandler, errorListener, paramMap);
        request.setRetryPolicy(mRetryPolicy);
        mLastDoneVolleyRequest = request;
        mVolleyApiClient.add(request);

    }

    public void apiValidateSecurityQuestion(Context context, Response.Listener<String> responseHandler, String answer){
        mLastSavedListenerHandler = responseHandler;
        showProgressDialog(context, "Security question check..");
        HashMap<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("Answer", answer);
        VolleyStringRequest request = new VolleyStringRequest(Request.Method.POST, getFinalUrl(API_VALIDATE_SECURITY_QUESTION, null), responseHandler, errorListener, paramMap);
        request.setRetryPolicy(mRetryPolicy);
        mLastDoneVolleyRequest = request;
        mVolleyApiClient.add(request);

    }


//    public void getSecurityQuestion(Context context, Response.Listener<String> responseHandler){
//        mLastSavedListenerHandler = responseHandler;
//        showProgressDialog(context,"Logging in..");
//        VolleyStringRequest request = new VolleyStringRequest(Request.Method.GET, getFinalUrl(API_GET_SECURITY_QUESTION, null), responseHandler, errorListener, null);
//        request.setRetryPolicy(mRetryPolicy);
//        mLastDoneVolleyRequest = request;
//        mVolleyApiClient.add(request);
//
//    }




//    public void restoreSessionWithLastCredentials(){
//        if(SettingsService.settings.loginPassword != null && SettingsService.settings.loginPassword.length()>0 &&
//                SettingsService.settings.loginEmail != null && SettingsService.settings.loginEmail.length()>0){
//            apiFastLogin(mContext, mValidateUserHandler, SettingsService.settings.loginEmail,
//                    SettingsService.settings.loginPassword, mProgressBar);
//        }
//
//    }




    public String getParametersString(HashMap<String, String> hashMap){
        if(hashMap != null && hashMap.entrySet().size()>0){
            String parameters="?";
            for(Map.Entry<String, String> entry : hashMap.entrySet()) {
                String parameter = entry.getKey();
                String value = URLEncoder.encode(entry.getValue());

                parameters += parameter + "=" + value + "&";
            }
            return parameters.substring(0,parameters.length()-1);
        }else{
            return "";
        }
    }

    public void errorListenerSetup(final Context context){
        final Context mContext = context;
        errorListener = new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgressDialog();
                if(context instanceof LoginActivity){
                    ((LoginActivity)context).getLoginButton().setEnabled(true);
                }
                if(context instanceof QuestionCheckActivity){
                    ((QuestionCheckActivity)context).getContinueButton().setEnabled(true);
                }

//                if((mLastDoneVolleyRequest != null || mLastDoneVolleyJsonRequest != null) && mNumberOfRetriesDone < Constants.NUMBER_OF_ATTEMPTS_ON_NETWORK_ERROR){
//                    mNumberOfRetriesDone++;
//                    //showProgressDialog();
//                    mVolleySingleton.gerRequestQueue().cancelAll("");
//                    if(mLastDoneVolleyRequest != null){
//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                mVolleyApiClient.add(mLastDoneVolleyRequest);
//                            }
//                        }).run();
//
//                    }else if(mLastDoneVolleyJsonRequest != null){
//                        mVolleyApiClient.add(mLastDoneVolleyJsonRequest);
//
//                    }
//
//                }else{


                mNumberOfRetriesDone = 0;
                //Otherwise handle the errors;
                if( error instanceof NoConnectionError) {
                    //ToastErrorInfo.toastErrorInfo(mContext, "No internet connection " + error.getLocalizedMessage());
                    //showCurrentTopActivityRetryAlert(); //TODO: make choice display this message or not
//                    if(mContext instanceof LoginActivity){
//                        if(SettingsService.settings.loginEmail.equals(mEmail) && SettingsService.settings.loginPassword.equals(mPassword)){
//                            ((LoginActivity)mContext).runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    ((LoginActivity) mContext).navigateToMainActivityWithNoInternet();
//                                }
//                            });
//                        }else{
//                            if(SettingsService.settings.noInternetMessages)
//                                Messages.errorMessage(mContext, "No internet connection " + error.getLocalizedMessage());
//                        }
//                    }

                } else if( error instanceof ServerError) {

                    Messages.errorMessage(mContext, "Server error " + error.getLocalizedMessage());
                } else if( error instanceof AuthFailureError) {
                    Messages.errorMessage(mContext, "Auth Failure Error " + error.getLocalizedMessage());
                } else if( error instanceof ParseError) {
                    Messages.errorMessage(mContext, "Parse Error " + error.getLocalizedMessage());
                } else if( error instanceof NetworkError) {
                    Messages.errorMessage(mContext, "Network Error " + error.getLocalizedMessage());
                } else if( error instanceof TimeoutError) {
                    Messages.errorMessage(mContext, "Timeout Error " + error.getLocalizedMessage());
                }
                hideProgressDialog();
                //}
            }
        };
    }


    public String getFinalUrl(String apiUrl, HashMap<String, String> parametersMap){
        if(SettingsService.settings.account != null){
            apiUrl = apiUrl.replace("account_placeholderQ123", SettingsService.settings.account);
        }

        return apiUrl + getParametersString(parametersMap);
    }

    public ImageLoader getImageLoader(){
        return mVolleySingleton.getImageLoader();
    }

    public void hideProgressDialog(){
        Utils.hideLoadingProgress(mContext);
    }

    public void showProgressDialog(Context context, String infoLoading){
        Utils.showLoadingProgress(context, infoLoading);
    }


    class VolleyJsonObjectRequest extends JsonObjectRequest
    {
        public VolleyJsonObjectRequest(int method, String url, JSONObject jsonRequest, Response.Listener listener, Response.ErrorListener errorListener)
        {
            super(method, url, jsonRequest, listener, errorListener);
        }
        @Override
        public Map getHeaders() throws AuthFailureError {
            Map headers = new HashMap();
            headers.put("Accept", HTTP_CONTENT_TYPE);
            if(SettingsService.settings.token != null){
                headers.put("Authorization", "ApiSessionKey "+SettingsService.settings.token);
            }
            return headers;
        }

    }

    class VolleyStringRequest extends StringRequest
    {
        private Map<String, String> mParams;
        public VolleyStringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener, Map<String, String> requestParams) {
            super(method, url, listener, errorListener);
            mParams = requestParams;
        }



        @Override
        public RetryPolicy getRetryPolicy() {
            DefaultRetryPolicy retryPolicy = new DefaultRetryPolicy(
                    TIMEOUT_IN_MILLISECONDS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            return retryPolicy;
        }

        @Override
        public Map getHeaders() throws AuthFailureError {
            Map headers = new HashMap();
            headers.put("Accept", HTTP_CONTENT_TYPE);
            if(SettingsService.settings.token != null){
                headers.put("Authorization", "ApiSessionKey "+SettingsService.settings.token);
            }
            return headers;
        }

        @Override
        public Map<String, String> getParams() {
            return mParams;
        }

    }



}
