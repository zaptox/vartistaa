package com.vartista.www.vartista.fragments;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.vartista.www.vartista.modules.general.HomeActivity;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.utilities.CONST;


/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceProviderFragment extends Fragment {
    LinearLayout btnCreateServices,btnMyServices,reqalert,btnUploadDoc,earnings;
     static int user_id;
    private FragmentActivity myContext;


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
        earnings=(LinearLayout) view.findViewById(R.id.earnings);

        btnUploadDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getContext(),HomeActivity.class);
                intent.putExtra("fragment_Flag", CONST.UPLOAD_DOC_LIST_FRAGMENT);
                startActivity(intent);




            }
        });
        reqalert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),HomeActivity.class);
                intent.putExtra("fragment_Flag", CONST.MY_SERVICE_REQUEST_FRAGMENT);
                startActivity(intent);


            }
        });
        earnings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),HomeActivity.class);
                intent.putExtra("fragment_Flag",CONST.EARNING_FRAGMENT);
                startActivity(intent);
                }
        });
        btnMyServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent=new Intent(getContext(),HomeActivity.class);
                intent.putExtra("fragment_Flag",CONST.MY_SERVICES_LIST_FRAGMENT);
                startActivity(intent);
//

            }
        });
        btnCreateServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),HomeActivity.class);
                intent.putExtra("fragment_Flag",CONST.CREATE_SERVICE_FRAGMENT);
                startActivity(intent);


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
