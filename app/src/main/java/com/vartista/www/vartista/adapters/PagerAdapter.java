package com.vartista.www.vartista.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.vartista.www.vartista.fragments.ServiceProviderFragment;
import com.vartista.www.vartista.fragments.UsersFragment;

/**
 * Created by Vksoni on 1/20/2018.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    static int user_id;
    public PagerAdapter(FragmentManager fm, int user_id) {
        super(fm);
        this.user_id=user_id;

    }
String title[]=new String[]{"As a User","As a Service Providers"};
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                UsersFragment tab1 = new UsersFragment(user_id);
                return tab1;
            case 1:
                ServiceProviderFragment tab2 = new ServiceProviderFragment(user_id);
                return tab2;

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
}