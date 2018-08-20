package com.vartista.www.vartista.fragments;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.vartista.www.vartista.CreateServiceActivity;
import com.vartista.www.vartista.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceProviderFragment extends Fragment {
    Button btnCreateServices;
     static int user_id;
    @SuppressLint("ValidFragment")
    public ServiceProviderFragment(int user_id) {
      this.user_id=user_id;
    }

    public ServiceProviderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_service, container, false);
        btnCreateServices=(Button)view.findViewById(R.id.buttonCreateService);

        btnCreateServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),CreateServiceActivity.class);
                intent.putExtra("userId",user_id);
                startActivity(intent);
            }
        });
   return view;
    }

}
