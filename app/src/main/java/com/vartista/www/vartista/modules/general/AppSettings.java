package com.vartista.www.vartista.modules.general;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.oss.licenses.OssLicensesMenuActivity;
import com.valdesekamdem.library.mdtoast.MDToast;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.User;
import com.vartista.www.vartista.restcalls.ApiClient;
import com.vartista.www.vartista.restcalls.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppSettings extends AppCompatActivity {
    TextView accountsetting,changepassword,deleteaccount,help,license,about;
    Dialog changepassworddialog;
    EditText OldPassword,NewPassword,ConfirmNewPassword;
    public static ApiInterface apiInterface;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_settings);
        accountsetting = (TextView)findViewById(R.id.AccountSetting);
        changepassword = (TextView)findViewById(R.id.ChangePasswordappsetting);
        deleteaccount = (TextView)findViewById(R.id.DeleteAccount);
//        help = (TextView)findViewById(R.id.Help);
        license = (TextView)findViewById(R.id.License);
        about = (TextView)findViewById(R.id.About);
        progressDialog = new ProgressDialog(this);
        apiInterface= ApiClient.getApiClient().create(ApiInterface.class);
        accountsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppSettings.this,SettingsActivity.class);
                startActivity(intent);
            }
        });

        license.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppSettings.this, OssLicensesMenuActivity.class));
            }
        });

        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changepassworddialog = new Dialog(AppSettings.this);
                changepassworddialog.setContentView(R.layout.changepassword_dialoguebox);
                Button savepassword = (Button)changepassworddialog.findViewById(R.id.savepassword);
                Button  cancel = (Button)changepassworddialog.findViewById(R.id.cancelbutton);
                OldPassword = (EditText)changepassworddialog.findViewById(R.id.oldPasswordedittext);
                NewPassword = (EditText)changepassworddialog.findViewById(R.id.newpasswordedittext);
                ConfirmNewPassword = (EditText)changepassworddialog.findViewById(R.id.confirmpasswordedittext);
                changepassworddialog.show();

                savepassword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences ob =getSharedPreferences("Login", Context.MODE_PRIVATE);
                        int id=HomeActivity.user_id;
                        String username=ob.getString("name","");
                        String email=ob.getString("Email","");
                        String contact=ob.getString("contact","");
                        String oldpassword = OldPassword.getText().toString();
                        String newpassword = NewPassword.getText().toString();
                        String confirmnewpassword = ConfirmNewPassword.getText().toString();
                        if(newpassword.equals(confirmnewpassword)){
                            updatedata(id,username,email,contact,confirmnewpassword);
                            changepassworddialog.cancel();
                        }

                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changepassworddialog.cancel();
                    }
                });



            }
        });


        deleteaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppSettings.this,DeleteMyAccount.class);
                startActivity(intent);
            }
        });












    }

    public void updatedata(int id,String name,String email,String contact,String password){
//        setUIToWait(true);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//    Call<User> call=SettingsActivity.apiInterface.updateUserSettings(id,name,email,password,user.getImage(),1,contact,0,0);
        Call<User> call = AppSettings.apiInterface.updateUser(name, email, password, id);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call <User> call, Response<User> response) {

                if(response.body().getResponse().equals("ok")){
                    setUIToWait(false);

                    MDToast.makeText(AppSettings.this,"Updated Successfully..",MDToast.LENGTH_SHORT,MDToast.TYPE_SUCCESS).show();

                }else if(response.body().getResponse().equals("exist")){
                    setUIToWait(false);

                    MDToast.makeText(AppSettings.this,"Same Data exists....",MDToast.LENGTH_SHORT,MDToast.TYPE_WARNING).show();

                }
                else if(response.body().getResponse().equals("error")){
                    setUIToWait(false);

                    MDToast.makeText(AppSettings.this,"Something went wrong....",MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();

                }
                else{
                    setUIToWait(false);

                    MDToast.makeText(AppSettings.this,"Something went wrong....",MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();
                }

            }

            @Override
            public void onFailure(Call <User> call, Throwable t) {
                setUIToWait(false);

            }
        });
    }



    private void setUIToWait(boolean wait) {

        if (wait) {
            progressDialog = ProgressDialog.show(this, null, null, true, true);
            progressDialog.setContentView(R.layout.loader);

        } else {
            progressDialog.dismiss();
        }

    }

















}
