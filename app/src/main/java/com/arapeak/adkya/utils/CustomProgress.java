package com.arapeak.adkya.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.TextView;

import com.arapeak.adkya.R;
import com.github.loadingview.LoadingView;

public class CustomProgress {

    public static CustomProgress customProgress = null;
    private Dialog mDialog;
    LoadingView mProgressBar;

    public static CustomProgress getInstance() {
        if (customProgress == null) {
            customProgress = new CustomProgress();
        }
        return customProgress;
    }

    public void showProgress(Context context, String message, boolean cancelable) {
        mDialog = new Dialog(context);
        // no tile for the dialog
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.my_custom_prograss_bar_dialog);
        mProgressBar = (LoadingView) mDialog.findViewById(R.id.loadingView);
        //  mProgressBar.getIndeterminateDrawable().setColorFilter(context.getResources()
        // .getColor(R.color.material_blue_gray_500), PorterDuff.Mode.SRC_IN);
        TextView progressText = (TextView) mDialog.findViewById(R.id.progress_text);
        progressText.setText("" + message);
        // you can change or add this line according to your need
//        mProgressBar.setIndeterminate(true);
        mDialog.setCancelable(cancelable);
        mDialog.setCanceledOnTouchOutside(cancelable);
        mProgressBar.start();
        mDialog.show();
    }

    public void hideProgress( ) {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

}
