package com.vartista.www.vartista.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.vartista.www.vartista.R;
import com.vartista.www.vartista.modules.provider.CreateServiceActivity;
import com.vartista.www.vartista.modules.provider.MyServiceRequests;
import com.vartista.www.vartista.modules.provider.MyServicesListActivity;
import com.vartista.www.vartista.modules.user.AssignRatings;

/**
 * Created by Dell on 2018-10-08.
 */

public class ConfigSettingsFragment extends Fragment {

    Button become_sp;
    static int user_id;
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

                Intent intent=new Intent(getContext(),AssignRatings.class);
                intent.putExtra("userId",user_id);
                startActivity(intent);

                Toast.makeText(getContext(), " Now you are a service provider", Toast.LENGTH_SHORT).show();

            }
        });


        return view;
    }



}
