package com.test.speechtotext.utility;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.cardview.widget.CardView;

import com.google.android.material.snackbar.Snackbar;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;



public class AppUtil {

    private static int widthPixels = 0;

    public static boolean isNetworkAvailable(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static Bitmap decodeBase64(String input) {
        try {
            byte[] decodedByte = Base64.decode(input, 0);
            return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
        } catch (Exception e) {
            return null;
        }
    }

    public static void hideSoftKeyboard(Context context) {
        Activity activity = (Activity) context;
        if (activity.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static void showSoftKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 1);
    }

    public static void shareIntent(Context context, String msg) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
//                sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey check out my app at: https://play.google.com/store/apps/details?id=com.google.android.apps.plus");
        sendIntent.putExtra(Intent.EXTRA_TEXT, msg);
        sendIntent.setType("text/plain");
        sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(sendIntent);
    }

    public static void showMsgAlert(View view, String msg) {
        try {
            Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
        } catch (Exception e) {

        }
    }

    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }

    public static RelativeLayout.LayoutParams setImageSizeForPopularVIew(View img, Activity context, int widthRatio, int heightRatio) {
        Log.d("ratio", "width : " + widthRatio + " height " + heightRatio);
        int width = getDeviceWidth(context);
        Log.d("ratio", "screen width : " + width);

        if (widthRatio != 0) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, ((((width * heightRatio) / widthRatio) * 100) / 100) + 85);
            img.setLayoutParams(params);
            Log.d("ratio", "calculate width : " + width + " calculate height : " + (((width * heightRatio) / widthRatio) * 100) / 100);
            return params;
        } else {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, (((width * 1) / 1) * 100) / 100);
            img.setLayoutParams(params);
            return params;
        }
    }

    public static RelativeLayout.LayoutParams setImageSizeForDescriptionView(View img, Activity context, int widthRatio, int heightRatio) {
        Log.d("ratio", "width : " + widthRatio + " height " + heightRatio);
        int width = getDeviceWidth(context);
        Log.d("ratio", "screen width : " + width);

        if (widthRatio != 0) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, ((((width * heightRatio) / widthRatio) * 100) / 100));
            img.setLayoutParams(params);
            Log.d("ratio", "calculate width : " + width + " calculate height : " + (((width * heightRatio) / widthRatio) * 100) / 100);
            return params;
        } else {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, (((width * 1) / 1) * 100) / 100);
            img.setLayoutParams(params);
            return params;
        }
    }


    public static LinearLayout.LayoutParams setImageSizeForHomeView(View img, Activity context, int widthRatio, int heightRatio) {
        Log.d("ratio", "width : " + widthRatio + " height " + heightRatio);
        int width = getDeviceWidth(context);
        Log.d("ratio", "screen width : " + width);

        if (widthRatio != 0) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, ((((width * heightRatio) / widthRatio) * 100) / 100) + 85);
            img.setLayoutParams(params);
            Log.d("ratio", "calculate width : " + width + " calculate height : " + (((width * heightRatio) / widthRatio) * 100) / 100);
            return params;
        } else {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, (((width * 1) / 1) * 100) / 100);
            img.setLayoutParams(params);
            return params;
        }
    }

    public static CardView.LayoutParams setCardViewSize(View img, Activity context, int widthRatio, int heightRatio) {
        Log.d("ratio", "width : " + widthRatio + " height " + heightRatio);
        int width = getDeviceWidth(context);
        Log.d("ratio", "screen width : " + width);

        if (widthRatio != 0) {
            CardView.LayoutParams params = new CardView.LayoutParams(width - 40, (((width * heightRatio) / widthRatio) * 100) / 100);
            params.setMargins(20, 0, 20, 15);
            img.setLayoutParams(params);
            Log.d("ratio", "calculate width : " + width + " calculate height : " + (((width * heightRatio) / widthRatio) * 100) / 100);
            return params;
        } else {
            CardView.LayoutParams params = new CardView.LayoutParams(width, (((width * 1) / 1) * 100) / 100);
            img.setLayoutParams(params);
            return params;
        }
    }

    public static int getDeviceWidth(Activity context) {
        if (widthPixels == 0) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            widthPixels = displayMetrics.widthPixels;
            Log.e("device width", widthPixels + "");
        }
        return widthPixels;
    }



    public static boolean isForeground(Context context) {//String PackageName
        // Get the Activity Manager
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        // Get a list of running tasks, we are only interested in the last one,
        // the top most so we give a 1 as parameter so we only get the topmost.
        List<ActivityManager.RunningTaskInfo> task = manager.getRunningTasks(1);
        // Get the info we need for comparison.
        ComponentName componentInfo;
        try {
            componentInfo = task.get(0).topActivity;
        } catch (Exception e) {
            componentInfo = null;
        }
        // Check if it matches our package name.
        return componentInfo != null && componentInfo.getPackageName().equals("com.zecast.dealdio");
        // If not then our app is not on the foreground.
    }

    public static ShapeDrawable drawCircle(Context context, int width, int height, int color) {

        //////Drawing oval & Circle programmatically /////////////

        ShapeDrawable oval = new ShapeDrawable(new OvalShape());
        oval.setIntrinsicHeight(height);
        oval.setIntrinsicWidth(width);
        oval.getPaint().setColor(color);
        return oval;
    }

}
