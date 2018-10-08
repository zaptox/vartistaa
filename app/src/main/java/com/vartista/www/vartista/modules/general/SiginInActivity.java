package com.vartista.www.vartista.modules.general;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vartista.www.vartista.R;
import com.vartista.www.vartista.restcalls.ApiClient;
import com.vartista.www.vartista.restcalls.ApiInterface;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.vartista.www.vartista.beans.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SiginInActivity extends AppCompatActivity {

    private Button signin;
    private TextView signup;
    private EditText email;
    private EditText password;
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
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        signin = findViewById(R.id.sign_in_button);
        signup = findViewById(R.id.t_signUp);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SiginInActivity.this, SignUpActivity.class));
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email1 = email.getText().toString();
                String password1 = password.getText().toString();
                addtosharedpreference(email1,password1);

                try {


                    User usergotten = perfromLogin(email1, password1);

//                    Toast.makeText(SiginInActivity.this, "Yes gotten " + usergotten, Toast.LENGTH_SHORT).show();


                } catch (Exception e) {

                   // Toast.makeText(SiginInActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }
        });

        //FOR CHECKING GITHUB WORKING

       // Toast.makeText(this, "Mehdi's Commit yayyyyyyyy", Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "THIS IS SAAD COMMIT AGAIN", Toast.LENGTH_LONG).show();
//        Toast.makeText(this, "THIS IS Xoni COMMIT ", Toast.LENGTH_LONG).show();


    }

    User userLoggedIn = null;

    public User perfromLogin(String email1, String password1) {

//        Toast.makeText(this, "in perform function", Toast.LENGTH_SHORT).show();
        Call<User> call = SiginInActivity.apiInterface.performUserLogin(email1, password1);
//        Call<User> call = SiginInActivity.apiInterface.performUserLogin();


        setUIToWait(true);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        call.enqueue(new Callback<User>() {
            @Override

            public void onResponse(Call<User> call, Response<User> response) {

//
                if (response.isSuccessful()) {
//                   // Toast.makeText(SiginInActivity.this, "In response", Toast.LENGTH_SHORT).show();
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

                    userLoggedIn = new User(id, name, email, password, image, status, contact, created_at, updated_at);
//                    Toast.makeText(SiginInActivity.this, "Response: " + response.body().getResponse() + "--name:" + name, Toast.LENGTH_SHORT).show();
                    setUIToWait(false);
//                    Toast.makeText(SiginInActivity.this, ""+userLoggedIn, Toast.LENGTH_SHORT).show();
                    //
                    Intent intent = new Intent(SiginInActivity.this, HomeActivity.class);
                    intent.putExtra("user", userLoggedIn);

                    startActivity(intent);
                    finish();
//
//
                } else if (response.body().getResponse().equals("failed")) {
                  //  Toast.makeText(SiginInActivity.this, "Login Failed.. Please try again", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(SiginInActivity.this, "", Toast.LENGTH_SHORT).show();
                    setUIToWait(false);

                }
//
                else {
                    setUIToWait(false);
                  //  Toast.makeText(SiginInActivity.this, "Response: " + response.body().getResponse(), Toast.LENGTH_SHORT).show();

                }

//                Toast.makeText(SiginInActivity.this, "In response's last line", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

              //  Toast.makeText(SiginInActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                setUIToWait(false);

            }
        });
        return userLoggedIn;
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

    public String getLocationFromAddress(String strAddress) throws IOException {

        Geocoder coder = new Geocoder(this);
       List<Address>is =coder.getFromLocationName(strAddress,5);

       Geocoder fwd = new Geocoder(this, Locale.US);
       String st="latifabad no 7";

       List<Address> loc=null;

       try{
           loc=fwd.getFromLocationName(st,10);

         //  Toast.makeText(this, ""+loc, Toast.LENGTH_SHORT).show();

            loc.get(0).getLongitude();
         //  Toast.makeText(this, "Lat: "+loc.get(0).getLatitude() + "long "+loc.get(0).getLongitude(), Toast.LENGTH_SHORT).show();

       }
       catch (Exception e){}


        return "";
    }

    public void addtosharedpreference(String email,String Password){

        SharedPreferences sharedPreferencespre =getSharedPreferences("Login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferencespre.edit();
        editor.putString("Email",email);
        editor.putString("Password",Password);
        editor.apply();
        editor.commit();





    }







}
