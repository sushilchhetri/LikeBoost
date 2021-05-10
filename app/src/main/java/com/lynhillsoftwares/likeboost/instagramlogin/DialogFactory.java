package com.lynhillsoftwares.likeboost.instagramlogin;

import android.app.ProgressDialog;
import android.content.Context;

import com.lynhillsoftwares.likeboost.R;

/**
 * Created by Sushil Chhetri on 07,May,2021
 */
public final class DialogFactory {
    private DialogFactory() {
    }

    public static ProgressDialog createLoadingDialog(Context context) {
        ProgressDialog loadingDialog = new ProgressDialog(context, ProgressDialog.THEME_HOLO_LIGHT);
        loadingDialog.setCancelable(false);
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setMessage(context.getString(R.string.sa_loading));
        return loadingDialog;
    }
}
