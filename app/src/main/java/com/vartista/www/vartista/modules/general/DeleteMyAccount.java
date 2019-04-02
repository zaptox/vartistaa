package com.vartista.www.vartista.modules.general;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.valdesekamdem.library.mdtoast.MDToast;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.User;
import com.vartista.www.vartista.restcalls.ApiClient;
import com.vartista.www.vartista.restcalls.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteMyAccount extends AppCompatActivity {

    Button delete,cancel;
    private ProgressDialog progressDialog;
    public static ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_my_account);
        delete = (Button)findViewById(R.id.delete);
        cancel = (Button)findViewById(R.id.cancel);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setUIToWait(true);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


                SharedPreferences ob =getSharedPreferences("Login", Context.MODE_PRIVATE);
//                int user_id= Integer.parseInt(ob.getString("user_id","").toString());
                int user_id= ob.getInt("user_id",-1);
                Call<User> call=DeleteMyAccount.apiInterface.UpdateUserStatus(user_id);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call <User> call, Response<User> response) {

                        if(response.body().getResponse().equals("ok")){
                            setUIToWait(false);

                            MDToast.makeText(DeleteMyAccount.this,"Updated Successfully..",MDToast.LENGTH_SHORT,MDToast.TYPE_SUCCESS).show();

                        }else if(response.body().getResponse().equals("exist")){
                            setUIToWait(false);

                            MDToast.makeText(DeleteMyAccount.this,"Same Data exists....",MDToast.LENGTH_SHORT,MDToast.TYPE_WARNING).show();

                        }
                        else if(response.body().getResponse().equals("error")){
                            setUIToWait(false);

                            MDToast.makeText(DeleteMyAccount.this,"Something went wrong....",MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();

                        }

                        else{
                            setUIToWait(false);

                            MDToast.makeText(DeleteMyAccount.this,"Something went wrong....",MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();

                        }
//                        user_name.setText("response ");
                        Intent intent = new Intent(DeleteMyAccount.this, SiginInActivity.class);
                        startActivity(intent);

                    }

                    @Override
                    public void onFailure(Call <User> call, Throwable t) {
                        setUIToWait(false);
                        MDToast.makeText(DeleteMyAccount.this,"Update Failed",MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();

//
                    }
                });

            }
        });
    }

    private void setUIToWait(boolean wait) {

        if (wait) {
            progressDialog = ProgressDialog.show(this, null, null, true, true);
//            progressDialog.setContentView(new ProgressBar(this));
            progressDialog.setContentView(R.layout.loader);

        } else {
            progressDialog.dismiss();
        }

    }


}
