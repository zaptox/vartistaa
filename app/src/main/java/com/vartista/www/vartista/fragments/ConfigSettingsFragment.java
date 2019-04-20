package com.vartista.www.vartista.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.vartista.www.vartista.R;
import com.vartista.www.vartista.modules.provider.CreateServiceActivity;
import com.vartista.www.vartista.modules.provider.DocumentUploadActivity;
import com.vartista.www.vartista.modules.provider.MyServiceRequests;
import com.vartista.www.vartista.modules.provider.MyServicesListActivity;
import com.vartista.www.vartista.modules.provider.ProviderFragments.DocumentUploadFragment;
import com.vartista.www.vartista.modules.provider.ProviderFragments.UploadDocListFragment;
import com.vartista.www.vartista.modules.user.AssignRatings;

/**
 * Created by Dell on 2018-10-08.
 */

public class ConfigSettingsFragment extends Fragment {

    Button become_sp;
    static int user_id;

    private FragmentActivity myContext;

    @SuppressLint("ValidFragment")
    public ConfigSettingsFragment(int user_id) {
        this.user_id=user_id;
    }

    public ConfigSettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_config, container, false);
        become_sp=(Button)view.findViewById(R.id.become_sp);

        become_sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent=new Intent(getContext(),DocumentUploadActivity.class);
//                intent.putExtra("userId",user_id);
//                startActivity(intent);

                FragmentManager manager = myContext.getSupportFragmentManager();
                manager.beginTransaction().remove(manager.findFragmentById(R.id.viewpager)).replace(R.id.fragment_frame_layout, new DocumentUploadFragment()).addToBackStack("TAG").commit();



            }
        });


        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }


}


