package com.vartista.www.vartista.fragments;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.vartista.www.vartista.beans.DocUploadList;
import com.vartista.www.vartista.modules.general.HomeActivity;
import com.vartista.www.vartista.modules.provider.CreateServiceActivity;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.modules.provider.MyServiceRequests;
import com.vartista.www.vartista.modules.provider.MyServicesListActivity;
import com.vartista.www.vartista.modules.provider.UploadDocListActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceProviderFragment extends Fragment {
    LinearLayout btnCreateServices,btnMyServices,reqalert,btnUploadDoc;
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
        btnCreateServices=(LinearLayout) view.findViewById(R.id.btnCreateService);
        reqalert=(LinearLayout) view.findViewById(R.id.btnRequestAlert);
        btnMyServices=(LinearLayout)view.findViewById(R.id.btnMyServices);
        btnUploadDoc=(LinearLayout)view.findViewById(R.id.btnUploadDoc);
        btnUploadDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),UploadDocListActivity.class);
                intent.putExtra("userId",user_id);
                startActivity(intent);
            }
        });
        reqalert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),MyServiceRequests.class);
                intent.putExtra("userId",user_id);
                startActivity(intent);
            }
        });
        btnMyServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),MyServicesListActivity.class);
                intent.putExtra("userId",user_id);
                startActivity(intent);
            }
        });
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
