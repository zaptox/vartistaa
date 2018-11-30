package com.vartista.www.vartista.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
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
        this.user_id=user_id;
        this.context=context;
    }



String title[]=new String[]{"As a User","As a Service Provider"};
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                UsersFragment tab1 = new UsersFragment(user_id);
                MANDATORY_PAGE_LOCATION=0;
                return tab1;
            case 1:


                SharedPreferences ob =context.getSharedPreferences("Login", Context.MODE_PRIVATE);

                String sp_status=ob.getString("sp_status","0");
                MANDATORY_PAGE_LOCATION=1;

                if(sp_status.equals("0")||sp_status.equals("-1")){
                    Toast.makeText(context, ""+sp_status, Toast.LENGTH_SHORT).show();
                ConfigSettingsFragment tab3= new ConfigSettingsFragment();
                    return tab3;

                }
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


}