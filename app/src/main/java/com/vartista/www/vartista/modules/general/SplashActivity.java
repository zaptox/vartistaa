package com.vartista.www.vartista.modules.general;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.view.WindowManager;

import com.daimajia.androidanimations.library.Techniques;
import com.vartista.www.vartista.R;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

public class SplashActivity extends AwesomeSplash {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash);
//    }

    @Override
    public void initSplash(ConfigSplash configSplash) {


        ActionBar actionBar= getSupportActionBar();
        actionBar.hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );


        //Background

        configSplash.setBackgroundColor(R.color.colorPrimaryDark); //any color you want form colors.xml

        configSplash.setAnimCircularRevealDuration(1000); //int ms
        configSplash.setRevealFlagX(Flags.REVEAL_RIGHT);  //or Flags.REVEAL_LEFT
        configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM); //or Flags.REVEAL_TOP
        //means start from left-end bottom


        //Choose LOGO OR PATH; if you don't provide String value for path it's logo by default

        //Customize Logo
        configSplash.setLogoSplash(R.drawable.logosplash); //or any other drawable
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

    }
}
