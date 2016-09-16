package com.buenadigital.saaspro.tools;

import android.content.Context;

import com.buenadigital.saaspro.models.DialogTypeModel;
import com.buenadigital.saaspro.services.DialogService;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dn on 11/11/2015.
 */
public class Messages {
    private static DialogService mDialogService;
    private Messages(Context context){
        mDialogService = new DialogService();
    }


    public static void checkDialogService(){
        if(mDialogService == null){
            mDialogService = new DialogService();
        }
    }

    public static void errorMessage(Context context, String message){
        checkDialogService();
        mDialogService.simpleMessage = message;
        mDialogService.createDialog(DialogTypeModel.DIALOG_SIMPLE_MESSAGE, context);
    }

    public static void errorMessage(Context context, JSONObject errorStruct){
        if(errorStruct != null && errorStruct.has("errorCode") && errorStruct.has("errorMessage")){
            checkDialogService();
            try {
                mDialogService.simpleMessage = errorStruct.getString("errorMessage");
                mDialogService.createDialog(DialogTypeModel.DIALOG_SIMPLE_ERROR_MESSAGE, context);
            } catch (JSONException e) {

            }

        }

    }



}
