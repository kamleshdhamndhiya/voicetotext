package com.test.speechtotext.utility;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.test.speechtotext.R;


public class DialogBox {


    private static Dialog loadingDialog;


   /* public static void showDialog(Context context, String error) {
        try {
            androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(context, R.style.MyDialogTheme);
            alertDialogBuilder.setMessage(error)
                    .setCancelable(false)
                    .setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });

            androidx.appcompat.app.AlertDialog alert = alertDialogBuilder.create();
            alert.show();
            alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }catch (Exception w){}
    }

    public static void showDialogtoFinishActity(final Context context, String error, final boolean isFinish) {
        androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(context, R.style.MyDialogTheme);
        alertDialogBuilder.setMessage(error)
                .setCancelable(false)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();

                            }
                        });

        androidx.appcompat.app.AlertDialog alert = alertDialogBuilder.create();
        alert.show();
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.colorPrimary));
    }
*/

    /*public static Dialog showLoader(Context mActivity, boolean isCancellable) {
        loadingDialog = new Dialog(mActivity);
        loadingDialog.setCancelable(isCancellable);
        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loadingDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loadingDialog.setContentView(R.layout.progress_dialog);
        avi = loadingDialog.findViewById(R.id.avi);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = loadingDialog.getWindow();
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        avi.show();
        loadingDialog.show();
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        return loadingDialog;
    }

    public static void hide() {
        loadingDialog.dismiss();
        avi.hide();
    }*/


    public static void ShowAlertDialogs(final Context context, String error) {
        androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(error)
                .setCancelable(false)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });

        androidx.appcompat.app.AlertDialog alert = alertDialogBuilder.create();
        alert.show();
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.black));
    }


    public static void showMsgAlert(View view, String msg) {
        try {
            Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
        } catch (Exception e) {

        }
    }

}
