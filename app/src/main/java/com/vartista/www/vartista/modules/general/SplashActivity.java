package com.vartista.www.vartista.modules.general;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.view.WindowManager;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.User;
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
        startActivity(new Intent(SplashActivity.this,SiginInActivity.class));
        finish();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
//
//        if((ob.getString("Email","").equals(user.getEmail()) && ob.getString("Password","").equals(user.getPassword()))){
//            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
//            intent.putExtra("user", userLoggedIn);
//            startActivity(intent);
//            finivnsh();
//
//            }
//        else{


//            Toast.makeText(SplashActivity.this,""+ob.getString("Email",""),Toast.LENGTH_SHORT);
//            startActivity(new Intent(SplashActivity.this,SiginInActivity.class));
//            finish();
//        }


        SharedPreferences ob =getSharedPreferences("Login", Context.MODE_PRIVATE);

        String email_shared=ob.getString("Email","");
        String pass_shared=ob.getString("Password","");

        if(email_shared.equals("") && pass_shared.equals("")){

            startActivity(new Intent(SplashActivity.this,SiginInActivity.class));
//            startActivity(new Intent(SplashActivity.this,SiginInActivity.class));

        }
        else {
            perfromLogin(email_shared, pass_shared);
        }


    }


    User userLoggedIn = null;

    public User perfromLogin(String email1, String password1) {

//        Toast.makeText(this, "in perform function", Toast.LENGTH_SHORT).show();
        Call<User> call = SplashActivity.apiInterface.performUserLogin(email1, password1);
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

                    userLoggedIn = new User(id, name, email, password, image, status, contact, created_at, updated_at,gender,sp_status);
//                    Toast.makeText(SiginInActivity.this, "Response: " + response.body().getResponse() + "--name:" + name, Toast.LENGTH_SHORT).show();
                    Toast.makeText(SplashActivity.this, "in b/w "+userLoggedIn, Toast.LENGTH_SHORT).show();


                    setUIToWait(false);
//                    Toast.makeText(SiginInActivity.this, ""+userLoggedIn, Toast.LENGTH_SHORT).show();
                    //

//                    SharedPreferences ob =getSharedPreferences("Login", Context.MODE_PRIVATE);
//
//                    String email_shared=ob.getString("Email","");
//                    String pass_shared=ob.getString("Password","");
//
//                    if(email_shared.equals("") && pass_shared.equals("")){
//
//                        startActivity(new Intent(SplashActivity.this,SiginInActivity.class));
//
//                    }
//                    else {
//
                    SharedPreferences sharedPreferencespre =getSharedPreferences("Login", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferencespre.edit();
                    editor.putInt("user_id",userLoggedIn.getId());
                    editor.putString("Email",userLoggedIn.getEmail());
                    editor.putString("Password",userLoggedIn.getPassword());
                    editor.putString("name",userLoggedIn.getName());
                    editor.putString("gender",userLoggedIn.getGender());
                    editor.putString("sp_status",userLoggedIn.getSp_status());

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
        Toast.makeText(this, "in last"+userLoggedIn, Toast.LENGTH_SHORT).show();
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

