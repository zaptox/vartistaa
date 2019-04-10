package com.vartista.www.vartista.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabItem;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Toast;

import com.vartista.www.vartista.fragments.ConfigSettingsFragment;
import com.vartista.www.vartista.fragments.ServiceProviderFragment;
import com.vartista.www.vartista.fragments.UsersFragment;
import com.vartista.www.vartista.modules.general.HomeActivity;

/**
 * Created by Vksoni on 1/20/2018.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;
    static int user_id;
    public static int MANDATORY_PAGE_LOCATION;
    public Context context;

    public PagerAdapter(FragmentManager fm, int user_id, Context context) {
        super(fm);
        this.user_id = user_id;
        this.context = context;
    }



String title[]=new String[]{"As a User","As a Service Provider"};

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:


                UsersFragment tab1 = new UsersFragment(user_id);

                MANDATORY_PAGE_LOCATION=0;
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                restartApp();
                return tab1;
            case 1:
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

                SharedPreferences ob =context.getSharedPreferences("Login", Context.MODE_PRIVATE);
                String sp_status=ob.getString("sp_status","0");
                MANDATORY_PAGE_LOCATION=1;
                if(sp_status.equals("0")||sp_status.equals("-1")){
                ConfigSettingsFragment tab3= new ConfigSettingsFragment();
//                restartApp();
                    return tab3;
                }
//                else if(HomeActivity.changed_from_notif.equals("1")){
//
//                ServiceProviderFragment tab2 = new ServiceProviderFragment(user_id);
//                return tab2;
//
//                }
                else{
                    ServiceProviderFragment tab2 = new ServiceProviderFragment(user_id);
                    return tab2;

                }

            default:
                return null;
        }
    }




    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    public void restartApp(){
        Intent i=new Intent(context,HomeActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(i);

    }

}