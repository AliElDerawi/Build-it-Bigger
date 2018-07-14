package com.udacity.gradle.builditbigger.util;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import com.udacity.gradle.builditbigger.R;


public class WaitingDialog {

    private AlertDialog alertDialog = null;


    private Activity activity = null;

    public WaitingDialog(Activity activity) {
        this.activity = activity;

        alertDialog = new AlertDialog.Builder(activity, R.style.AlertDialogStyle)
                .setCancelable(false)
                .setView(LayoutInflater.from(activity).inflate(R.layout.dialog_waiting_layout, null))
                .create();
    }

    public void showDialog() {
        if (activity == null) {
            return;
        }

        if (alertDialog != null && !alertDialog.isShowing()) {
            Context context = ((ContextWrapper) alertDialog.getContext()).getBaseContext();
            if (context instanceof Activity) {
                if (!((Activity) context).isFinishing()) {
                    alertDialog.show();
                }
            } else {
                alertDialog.show();
            }
        }
    }

    public void dismissDialog() {
        if (activity == null) {
            return;
        }

        if (alertDialog != null && alertDialog.isShowing()) {
            Context context = ((ContextWrapper) alertDialog.getContext()).getBaseContext();
            if (context instanceof Activity) {
                if (!((Activity) context).isFinishing()) {
                    alertDialog.dismiss();
                }
            } else {
                alertDialog.dismiss();
            }
        }
    }
}
