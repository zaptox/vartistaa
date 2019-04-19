package com.vartista.www.vartista.modules.general;

import android.app.Dialog;
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
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.valdesekamdem.library.mdtoast.MDToast;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.CreateRequest;
import com.vartista.www.vartista.beans.Doument_Upload_Nil;
import com.vartista.www.vartista.beans.NotificationsManager;
import com.vartista.www.vartista.modules.provider.DocumentUploadActivity;
import com.vartista.www.vartista.modules.user.AssignRatings;
import com.vartista.www.vartista.modules.user.UserAppointmentDetails;
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
    private TextView signup,login_sp;
    private TextView forgotpassword;
    private EditText email;
    private EditText password;
    ArrayList<User> list;
    private ProgressDialog progressDialog;
    Dialog signup_dialogue;


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
        list=new ArrayList<User>();
        signin = findViewById(R.id.sign_in_button);
        signup = findViewById(R.id.t_signUp);
        forgotpassword = findViewById(R.id.textView3);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
//        login_sp= findViewById(R.id.login_sp);
//
//        login_sp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(login_sp.getText().equals("SIGN IN AS SERVICE PROVIDER")) {
//                    signin.setText("Sign in as Service Provider");
//                    login_sp.setText("SIGN IN AS USER");
//                }
//                else{
//                    signin.setText("Sign in as User");
//                    login_sp.setText("SIGN IN AS SERVICE PROVIDER");
//                }
//
//            }
//        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SiginInActivity.this, SignUpActivity.class));

//                signup_dialogue = new Dialog(SiginInActivity.this);
//                signup_dialogue.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
//                signup_dialogue.setContentView(R.layout.signup_dialogue);
//                Button next = (Button)signup_dialogue.findViewById(R.id.next);
//                final RadioButton user_radio=signup_dialogue.findViewById(R.id.user_radio);
//                final RadioButton sp_radio=signup_dialogue.findViewById(R.id.sp_radio);
//
//                Button  cancel = (Button)signup_dialogue.findViewById(R.id.cancelbutton);
////                d_sp_name = signup_dialogue.findViewById(R.id.sp_name);
//
//
//
//                signup_dialogue.show();
//
//
//
//
//                cancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//
//
//                        signup_dialogue.cancel();
//                    }
//                });
//





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

                    String gender= response.body().getGender();

                    String sp_status= response.body().getSp_status();

                    int busystatus = response.body().getBusystatus();


                    userLoggedIn = new User(id, busystatus,name, email, password, image, status, contact, created_at, updated_at,gender,sp_status);
//                    Toast.makeText(SiginInActivity.this, "Response: " + response.body().getResponse() + "--name:" + name, Toast.LENGTH_SHORT).show();
                    addtosharedpreference(userLoggedIn.getId(),userLoggedIn.getBusystatus(),userLoggedIn.getEmail(),userLoggedIn.getPassword(),
                            userLoggedIn.getName(),userLoggedIn.getGender(),userLoggedIn.getSp_status(),
                            userLoggedIn.getContact(),image);
                    setUIToWait(false);
                    //
                    Intent intent = new Intent(SiginInActivity.this, HomeActivity.class);
                    intent.putExtra("user", userLoggedIn);
                    MDToast.makeText(SiginInActivity.this, "Login Sucessfully!...", MDToast.LENGTH_SHORT,MDToast.TYPE_SUCCESS).show();

                    startActivity(intent);
                    finish();
//
//
                } else if (response.body().getResponse().equals("failed")) {
                      MDToast.makeText(SiginInActivity.this, "Login Failed.. Please try again", MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();
                    setUIToWait(false);

                }
//
                else {
                    setUIToWait(false);
                    MDToast.makeText(SiginInActivity.this, "Login Failed.. Please try again", MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();

                }



            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                MDToast.makeText(SiginInActivity.this, "Login Failed.. Please try again", MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();
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

    public void addtosharedpreference(int user_id,int busystatus,String email,String Password,String name,String gender, String sp_status,String contact,String image){

        SharedPreferences sharedPreferencespre =getSharedPreferences("Login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferencespre.edit();
        editor.putInt("user_id",user_id);
        editor.putInt("busy_status",busystatus);
        editor.putString("contact",contact);
        editor.putString("Email",email);
        editor.putString("Password",Password);
        editor.putString("name",name);
        editor.putString("gender",gender);
        editor.putString("sp_status",sp_status);
        editor.putString("image",image);
        editor.apply();
        editor.commit();
        Toast.makeText(this, "Shared PReference are working "+user_id+"\n"+Password, Toast.LENGTH_SHORT).show();




    }

//public void upload_document(String Name,String Password,String ContactNo){
//    setUIToWait(true);
//    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//    Call<Doument_Upload_Nil> call=SiginInActivity.apiInterface.document_upload_nil(Name,Password,ContactNo);
//    call.enqueue(new Callback<Doument_Upload_Nil>() {
//        @Override
//        public void onResponse(Call <Doument_Upload_Nil> call, Response<Doument_Upload_Nil> response) {
//
//            if(response.body().getResponse().equals("ok")){
//                setUIToWait(false);
//
//                Toast.makeText(SiginInActivity.this,"Updated Successfully..",Toast.LENGTH_SHORT).show();
//
//            }else if(response.body().getResponse().equals("exist")){
//                setUIToWait(false);
//
//                Toast.makeText(SiginInActivity.this,"Same Data exists....",Toast.LENGTH_SHORT).show();
//
//            }
//            else if(response.body().getResponse().equals("error")){
//                setUIToWait(false);
//
//                Toast.makeText(SiginInActivity.this,"Something went wrong....",Toast.LENGTH_SHORT).show();
//
//            }
//
//            else{
//                setUIToWait(false);
//
//                Toast.makeText(SiginInActivity.this,"Something went wrong....",Toast.LENGTH_SHORT).show();
//
//            }
//            Intent intent = new Intent(SiginInActivity.this, SiginInActivity.class);
//            startActivity(intent);
//
//        }
//
//        @Override
//        public void onFailure(Call <Doument_Upload_Nil> call, Throwable t) {
//            setUIToWait(false);
//            Toast.makeText(SiginInActivity.this,"Update Failed",Toast.LENGTH_SHORT).show();
//        }
//    });
//
//
//}




}

