package com.vartista.www.vartista.modules.general;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.WindowManager;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.User;
import com.vartista.www.vartista.modules.provider.AssignRatingsToUser;
import com.vartista.www.vartista.restcalls.ApiClient;
import com.vartista.www.vartista.restcalls.ApiInterface;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AwesomeSplash {
    private ProgressDialog progressDialog;
    public static ApiInterface apiInterface;
    User user=null;
    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash);
//    }

    @Override
    public void initSplash(ConfigSplash configSplash) {




        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

        }



        ActionBar actionBar= getSupportActionBar();
//        actionBar.hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );


        //Background

        configSplash.setBackgroundColor(R.color.colorPrimaryDark); //any color you want form colors.xml

        configSplash.setAnimCircularRevealDuration(1000); //int ms
        configSplash.setRevealFlagX(Flags.REVEAL_RIGHT);  //or Flags.REVEAL_LEFT
        configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM); //or Flags.REVEAL_TOP
        //means start from left-end bottom


        //Choose LOGO OR PATH; if you don't provide String value for path it's logo by default

        //Customize Logo
        configSplash.setLogoSplash(R.drawable.forsplash); //or any other drawable
        configSplash.setAnimLogoSplashDuration(1000); //int ms
        configSplash.setAnimLogoSplashTechnique(Techniques.FadeIn); //choose one form Techniques (ref: https://github.com/daimajia/AndroidViewAnimations)


        //Customize Path
        configSplash.setPathSplash(""); //set path String
        configSplash.setOriginalHeight(200); //in relation to your svg (path) resource
        configSplash.setOriginalWidth(200); //in relation to your svg (path) resource
        configSplash.setAnimPathStrokeDrawingDuration(500);
        configSplash.setPathSplashStrokeSize(3); //I advise value be <5
        configSplash.setPathSplashStrokeColor(R.color.color_good); //any color you want form colors.xml
        configSplash.setAnimPathFillingDuration(500);
        configSplash.setPathSplashFillColor(R.color.color_splash); //path object filling color

        //Title
        configSplash.setTitleSplash("");
        configSplash.setTitleTextColor(R.color.color_good);
        configSplash.setTitleTextSize(20f); //float value
        configSplash.setAnimTitleDuration(500);
        configSplash.setAnimTitleTechnique(Techniques.FadeIn);
//        configSplash.setTitleFont("fonts/myfont.ttf"); //provide string to your font located in assets/fonts/


    }

    @Override
    public void animationsFinished() {
//        startActivity(new Intent(SplashActivity.this,SiginInActivity.class));
//        finish();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);



        SharedPreferences ob =getSharedPreferences("Login", Context.MODE_PRIVATE);

        String email_shared=ob.getString("Email","");
        String pass_shared=ob.getString("Password","");
        int user_id_shared=ob.getInt("user_id",0);
        String name_shared=ob.getString("name","");
        String gender_shared=ob.getString("gender","");
        String sp_status_shared=ob.getString("sp_status","");
        String contact_shared=ob.getString("contact","");
        String image_shared=ob.getString("image","");
        Boolean assignrating = ob.getBoolean("RatingsToUser",false);


        userLoggedIn = new User(user_id_shared, name_shared, email_shared, pass_shared, image_shared, sp_status_shared, contact_shared, "", "",gender_shared,sp_status_shared);

        if(email_shared.equals("") && pass_shared.equals("")){

            startActivity(new Intent(SplashActivity.this,SiginInActivity.class));
            finish();
        }
        else {
//            perfromLogin(email_shared, pass_shared);

            if (sp_status_shared.contains("1")){
                 if(assignrating){
                     Intent intent = new Intent(SplashActivity.this, AssignRatingsToUser.class);
                     intent.putExtra("assignratings",1);
                     startActivity(intent);
                     finish();
                 }
                 else{
                     Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
//                    Intent intent = new Intent(SplashActivity.this, CheckActivity.class);

                     intent.putExtra("user", userLoggedIn);


                     startActivity(intent);
                     finish();
                 }



            } else {
                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
//                    Intent intent = new Intent(SplashActivity.this, CheckActivity.class);

                intent.putExtra("user", userLoggedIn);


                startActivity(intent);
                finish();
            }

        }


    }


    User userLoggedIn = null;

    public User perfromLogin(String email1, String password1) {

        Call<User> call = SplashActivity.apiInterface.performUserLogin(email1, password1);


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


                    setUIToWait(false);

                    SharedPreferences sharedPreferencespre =getSharedPreferences("Login", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferencespre.edit();
                    editor.putInt("user_id",userLoggedIn.getId());
                    editor.putString("Email",userLoggedIn.getEmail());
                    editor.putString("Password",userLoggedIn.getPassword());
                    editor.putString("name",userLoggedIn.getName());
                    editor.putString("gender",userLoggedIn.getGender());
                    editor.putString("sp_status",userLoggedIn.getSp_status());
                    editor.putInt("busy_status",userLoggedIn.getBusystatus());
                    editor.putString("contact",contact);
                    editor.putString("image",image);


                    editor.apply();
                    editor.commit();



                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
//                    Intent intent = new Intent(SplashActivity.this, CheckActivity.class);

                    intent.putExtra("user", userLoggedIn);


                    startActivity(intent);
                    finish();


                    //                    }
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
//            progressDialog.setContentView(new ProgressBar(this));
            progressDialog.setContentView(R.layout.loader);

        } else {
            progressDialog.dismiss();
        }

    }



















}

