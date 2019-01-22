package com.vartista.www.vartista.modules.general;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.valdesekamdem.library.mdtoast.MDToast;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.CreateRequest;
import com.vartista.www.vartista.beans.Doument_Upload_Nil;
import com.vartista.www.vartista.modules.provider.DocumentUploadActivity;
import com.vartista.www.vartista.modules.user.AssignRatings;
import com.vartista.www.vartista.restcalls.ApiClient;
import com.vartista.www.vartista.restcalls.ApiInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.vartista.www.vartista.beans.User;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SiginInActivity extends AppCompatActivity {

    private Button signin;
    private TextView signup;
    private TextView forgotpassword;
    private EditText email;
    private EditText password;
    ArrayList<User> list;
    private ProgressDialog progressDialog;

    //    public static PrefConfig prefConfig;
    public static ApiInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sigin_in);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        try {
            getLocationFromAddress("latifabad no 7");
        } catch (IOException e) {
//            e.printStackTrace();
        }
        list=new ArrayList<User>();
        signin = findViewById(R.id.sign_in_button);
        signup = findViewById(R.id.t_signUp);
        forgotpassword = findViewById(R.id.textView3);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SiginInActivity.this, SignUpActivity.class));
            }
        });

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SiginInActivity.this, ForgotPasswordActivity.class));
            }
        });






        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email1 = email.getText().toString();
                String password1 = password.getText().toString();

                try {


                    User usergotten = perfromLogin(email1, password1);



                } catch (Exception e) {

                   // Toast.makeText(SiginInActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }
        });



    }

    User userLoggedIn = null;

    public User perfromLogin(String email1, String password1) {

        Call<User> call = SiginInActivity.apiInterface.performUserLogin(email1, password1);


        setUIToWait(true);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        call.enqueue(new Callback<User>() {
            @Override

            public void onResponse(Call<User> call, Response<User> response) {

//
                if (response.isSuccessful()) {
                    setUIToWait(false);
                }
//

                if (response.body().getResponse().equals("ok")) {
                    int id = response.body().getId();

                    String name = response.body().getName();

                    String email = response.body().getEmail();

                    String password = response.body().getPassword();

                    String image = response.body().getImage();

                    String status = response.body().getStatus();

                    String contact = response.body().getContact();

                    String created_at = response.body().getCreatedAt();

                    String updated_at = response.body().getUpdatedAt();

                    String gender= response.body().getGender();

                    String sp_status= response.body().getSp_status();


                    userLoggedIn = new User(id, name, email, password, image, status, contact, created_at, updated_at,gender,sp_status);
                    addtosharedpreference(userLoggedIn.getId(),userLoggedIn.getEmail(),userLoggedIn.getPassword(),
                            userLoggedIn.getName(),userLoggedIn.getGender(),userLoggedIn.getSp_status(),
                            userLoggedIn.getContact());
//
                    setUIToWait(false);
                    //
                    Intent intent = new Intent(SiginInActivity.this, HomeActivity.class);
                    intent.putExtra("user", userLoggedIn);

                    startActivity(intent);
                    finish();
//
//
                } else if (response.body().getResponse().equals("failed")) {

                    setUIToWait(false);

                }
//
                else {
                    setUIToWait(false);

                }



            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                setUIToWait(false);

            }
        });
        return userLoggedIn;
    }

    private void setUIToWait(boolean wait) {

        if (wait) {
            progressDialog = ProgressDialog.show(this, null, null, true, true);
            progressDialog.setContentView(R.layout.loader);

        } else {
            progressDialog.dismiss();
        }

    }

    public String getLocationFromAddress(String strAddress) throws IOException {

        Geocoder coder = new Geocoder(this);
       List<Address>is =coder.getFromLocationName(strAddress,5);

       Geocoder fwd = new Geocoder(this, Locale.US);
       String st="latifabad no 7";

       List<Address> loc=null;

       try{
           loc=fwd.getFromLocationName(st,10);


            loc.get(0).getLongitude();

       }
       catch (Exception e){}


        return "";
    }

    public void addtosharedpreference(int user_id,String email,String Password,String name,String gender, String sp_status,String contact){

        SharedPreferences sharedPreferencespre =getSharedPreferences("Login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferencespre.edit();
        editor.putInt("user_id",user_id);
        editor.putString("contact",contact);
        editor.putString("Email",email);
        editor.putString("Password",Password);
        editor.putString("name",name);
        editor.putString("gender",gender);
        editor.putString("sp_status",sp_status);

        editor.apply();
        editor.commit();




    }





}
