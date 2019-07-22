package com.vartista.www.vartista.utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class Utils {
    String message;
    Activity activity;


    public Utils(Activity activity)
    {
        this.activity = activity;

    }

    public Utils(Context context){
        this.activity = (Activity) activity;

    }

    public Boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void alterDialog (final String msg)  {

        message = msg;
        final AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
        builder1.setMessage(msg);
        builder1.setCancelable(false);
        builder1.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        builder1.setCancelable(true);
                        dialog.cancel();

                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }



    public void hideSoftKeyboard(Activity activity, EditText editText) {
        if (activity.getCurrentFocus() != null)
        {
            InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            editText.setFocusable(false);
            //  editText.setFocusableInTouchMode(false);
            editText.requestFocus();
        }
    }

    public  void showSoftKeyboard(Activity activity, EditText editText, View v) {

        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    public void hidekeyboard(View view){
        view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public Boolean checkVersion(){

        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M) {

            return false;

        }else {

            return true;
        }

    }

//    public void CallServices_GET(Activity activity, String url, String type, String loadingtype){
//
//        CustomAsyncTask customAsyncTask = new CustomAsyncTask(activity,url,type,loadingtype);
//        customAsyncTask.execute();
//    }
//
//    public void CallServices_GET(Context activity, String url, String type, String loadingtype){
//
//        CustomAsyncTask customAsyncTask = new CustomAsyncTask((Activity) activity,url,type,loadingtype);
//        customAsyncTask.execute();
//    }
//
//    public void CallService_POST(Activity activity, String url, String type, String parameters, String loadingtype){
//        CustomAsyncTask customAsyncTask = new CustomAsyncTask(activity,url,type,parameters,loadingtype);
//        customAsyncTask.execute();
//    }
//
//
//
//    public void Call_SecondService(Class SecondActivity){
//        activity.startActivity(new Intent(activity,SecondActivity));
//        activity.finish();
//    }
//
    public void ChangeFont(TextView change_text){
        Typeface font = Typeface.createFromAsset(activity.getAssets(), "roboto_regular.ttf");
        change_text.setTypeface(font);
    }

    public String RemoveZero(String price){

        if(price.contains(".")) {
            String[] split = price.split("\\.");
            double checkzero = Double.parseDouble(split[1]);
            if (checkzero == 0) {
                return split[0];
            } else
            {
                return price;
            }
        }else {
            return price;
        }

    }

}
