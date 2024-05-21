package com.test.speechtotext.utility;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;
import com.test.speechtotext.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class ErrorMessage {
    public static void I(Context cx, Class<?> startActivity, Bundle data) {
        Intent i = new Intent(cx, startActivity);
        if (data != null)
            i.putExtras(data);
        cx.startActivity(i);
        onAnim((Activity) cx);
    }


    public static void I_clear(Context cx, Class<?> startActivity, Bundle data) {
        Intent i = new Intent(cx, startActivity);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        if (data != null)
            i.putExtras(data);
        cx.startActivity(i);
        onAnim((Activity) cx);
    }

    public static void E(String msg) {
    if (true)
            Log.e("Log.E By Burhan ", msg);
    }


    public static Dialog initProgressDialog(Context c) {
        try{
        Dialog dialog = new Dialog(c);
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
        return dialog;}
        catch (Exception e){
            return null;

        }

    }

    public static void T(Context c, String msg) {
        Toast.makeText(c, msg, Toast.LENGTH_SHORT).show();
    }

    public static boolean isSDCardPresent() {
        if (Environment.getExternalStorageState().equals(

                Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

   /* public static void success_message(Context c, String Title, String Content) {
        new SweetAlertDialog(c, SweetAlertDialog.SUCCESS_TYPE).setTitleText(Title).setConfirmText(Content).show();


    }

    public static void error(Context c, String Title ) {
        new SweetAlertDialog(c,
                SweetAlertDialog.ERROR_TYPE).setTitleText("Oops...").
                setContentText(Title).show();
    }*/
    public static void setSnackBar(View root, String snackTitle) {
        Snackbar snackbar = Snackbar.make(root, snackTitle, Snackbar.LENGTH_SHORT);
        snackbar.show();
        View view = snackbar.getView();
        TextView txtv = (TextView) view.findViewById(CoordinatorLayout.generateViewId());
        txtv.setGravity(Gravity.CENTER_HORIZONTAL);
    }
    public static void onAnim(Activity activity) {
        activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public static void Alert(Context c,String title, String msg) {
        AlertDialog alertDialog = new AlertDialog.Builder(c).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
    public static void openWebPage(Context context,String url) {
       try {
           ErrorMessage.E("url>>>>" + url);
           Uri webpage = Uri.parse(url);
           Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

           // Verify if there's a web browser app available to handle the intent
           if (intent.resolveActivity(context.getPackageManager()) != null) {
               ErrorMessage.E("if is working>>>");
               context.startActivity(intent);
           } else {
               ErrorMessage.E("Else is working>>>");
               context.startActivity(intent);
           }
       }catch (Exception e){}
    }



        public static String ChangeDateFormate(String inputDate,Context context) {

            String inputFormat = "yyyy-MM-dd HH:mm:ss";
            String outputFormat = "dd MMM yyyy, hh:mm a";
            String formattedDate="";
            SimpleDateFormat inputFormatter = new SimpleDateFormat(inputFormat, Locale.getDefault());

            try {
                Date date = inputFormatter.parse(inputDate);

                if (date != null) {
                    SimpleDateFormat outputFormatter = new SimpleDateFormat(outputFormat, Locale.getDefault());
                     formattedDate = outputFormatter.format(date);
                    System.out.println("Formatted date: " + formattedDate);
                } else {
                    System.out.println("Error parsing the date.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return  formattedDate;
        }


}