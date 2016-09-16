package com.buenadigital.saaspro.services;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.buenadigital.saaspro.Constants;
import com.buenadigital.saaspro.R;
import com.buenadigital.saaspro.models.DialogTypeModel;
import com.buenadigital.saaspro.tools.Utils;


public class DialogService{
    public String simpleMessage;
    public String simpleMessageTitle;

    private static Dialog dialog;
    private Context mContext;

    private TextView mTitleTextView;
    private TextView mDescriptionTextView;
    private TextView mCancelTextView;
    private TextView mOkTextView;
    private LinearLayout mMainLayout;
    private SettingsService mSettingsService;
    public Runnable okButtonRunnable;


    public void createDialog(final DialogTypeModel id, final Context context) {
        mContext = context;
        final Context mContext = context;
        Handler mainHandler = new Handler(context.getMainLooper());

        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                if(dialog != null && dialog.isShowing())
                    return;

                dialog = new Dialog(mContext);
                final int mMaxScreenWidth = (int)(mContext.getResources().getDisplayMetrics().widthPixels/context.getResources().getDisplayMetrics().density);
                final int dialogSideMargin = Utils.dpToPx(Constants.DIALOG_MARGIN_IN_DP);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                switch(id) {
                    case DIALOG_QUESTION_YES_NO:
                        dialog.setContentView(R.layout.dialog_exit_application);
                        mTitleTextView = (TextView) dialog.findViewById(R.id.title);
                        mDescriptionTextView = (TextView) dialog.findViewById(R.id.description);
                        mCancelTextView = (TextView) dialog.findViewById(R.id.dialog_cancel_button);
                        mOkTextView = (TextView) dialog.findViewById(R.id.dialog_ok_button);

                        mTitleTextView.setTypeface(Constants.TYPE_FACE_FONT_ROBOTO_MEDIUM);
                        mDescriptionTextView.setTypeface(Constants.TYPE_FACE_FONT_ROBOTO_MEDIUM);
                        mCancelTextView.setTypeface(Constants.TYPE_FACE_FONT_ROBOTO_MEDIUM);
                        mOkTextView.setTypeface(Constants.TYPE_FACE_FONT_ROBOTO_MEDIUM);

                        mTitleTextView.setText(simpleMessageTitle);
                        mDescriptionTextView.setText(simpleMessage);
                        mMainLayout = (LinearLayout) dialog.findViewById(R.id.dialog_main_layout);
                        mMainLayout.setMinimumWidth(Utils.dpToPx(mMaxScreenWidth - dialogSideMargin));
                        mCancelTextView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        mOkTextView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                okButtonRunnable.run();
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                        break;
                    case DIALOG_SIMPLE_MESSAGE:
                        dialog.setContentView(R.layout.dialog_simple_message);
                        mDescriptionTextView = (TextView) dialog.findViewById(R.id.description);
                        mOkTextView = (TextView) dialog.findViewById(R.id.dialog_ok_button);

                        mDescriptionTextView.setTypeface(Constants.TYPE_FACE_FONT_ROBOTO_MEDIUM);
                        mOkTextView.setTypeface(Constants.TYPE_FACE_FONT_ROBOTO_MEDIUM);

                        mDescriptionTextView.setText(simpleMessage);
                        mMainLayout = (LinearLayout) dialog.findViewById(R.id.dialog_main_layout);
                        mMainLayout.setMinimumWidth(Utils.dpToPx(mMaxScreenWidth - dialogSideMargin));
                        mOkTextView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                        break;
                    case DIALOG_SIMPLE_ERROR_MESSAGE:
                        dialog.setContentView(R.layout.dialog_custom_simple);
                        mTitleTextView = (TextView) dialog.findViewById(R.id.title);
                        mDescriptionTextView = (TextView) dialog.findViewById(R.id.description);
                        mOkTextView = (TextView) dialog.findViewById(R.id.dialog_ok_button);
                        mTitleTextView.setText(mContext.getResources().getString(R.string.error));
                        mDescriptionTextView.setText(simpleMessage);
                        mMainLayout = (LinearLayout) dialog.findViewById(R.id.dialog_main_layout);
                        mMainLayout.setMinimumWidth(Utils.dpToPx(mMaxScreenWidth - dialogSideMargin));
                        mOkTextView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                        break;




                }
            }
        };

        mainHandler.post(myRunnable);
    }



    public void exitApplication(){
        System.exit(0);
    }

}
