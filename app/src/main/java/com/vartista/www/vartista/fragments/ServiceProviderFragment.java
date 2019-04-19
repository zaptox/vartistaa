package com.vartista.www.vartista.fragments;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
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
import com.vartista.www.vartista.modules.provider.EarningActivity;
import com.vartista.www.vartista.modules.provider.MyServiceRequests;
import com.vartista.www.vartista.modules.provider.MyServicesListActivity;
import com.vartista.www.vartista.modules.provider.ProviderFragments.CreateServiceFragment;
import com.vartista.www.vartista.modules.provider.ProviderFragments.EarningFragment;
import com.vartista.www.vartista.modules.provider.ProviderFragments.MyServiceRequestsFragment;
import com.vartista.www.vartista.modules.provider.ProviderFragments.MyServicesListFragment;
import com.vartista.www.vartista.modules.provider.UploadDocListActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceProviderFragment extends Fragment {
    LinearLayout btnCreateServices,btnMyServices,reqalert,btnUploadDoc,earnings;
     static int user_id;
    private FragmentActivity myContext;
    TabLayout tabLayout;


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
        tabLayout = (TabLayout) getActivity().findViewById(R.id.tabs);
        tabLayout.setVisibility(View.VISIBLE);

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
//                Intent intent=new Intent(getContext(),MyServiceRequests.class);
//                intent.putExtra("userId",user_id);
//                startActivity(intent);

                FragmentManager manager = myContext.getSupportFragmentManager();
                manager.beginTransaction().remove(manager.findFragmentById(R.id.viewpager)).replace(R.id.fragment_frame_layout, new MyServiceRequestsFragment(user_id,tabLayout)).addToBackStack("TAG").commit();


            }
        });
        earnings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(getContext(),EarningActivity.class);
//                intent.putExtra("userId",user_id);
//                startActivity(intent);

                FragmentManager manager = myContext.getSupportFragmentManager();
                manager.beginTransaction().remove(manager.findFragmentById(R.id.viewpager)).replace(R.id.fragment_frame_layout, new EarningFragment(user_id,tabLayout)).addToBackStack("TAG").commit();




            }
        });
        btnMyServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager manager = myContext.getSupportFragmentManager();
                manager.beginTransaction().remove(manager.findFragmentById(R.id.viewpager)).replace(R.id.fragment_frame_layout, new MyServicesListFragment(user_id,tabLayout)).addToBackStack("TAG").commit();

//                FragmentTransaction ft= getChildFragmentManager().beginTransaction().replace(R.id.viewpager,new MyServicesListFragment(user_id)).addToBackStack("TA")

//                Intent intent=new Intent(getContext(),MyServicesListActivity.class);
//                intent.putExtra("userId",user_id);
//                startActivity(intent);
//

            }
        });
        btnCreateServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(getContext(),CreateServiceActivity.class);
//                intent.putExtra("userId",user_id);
//                startActivity(intent);


                FragmentManager manager = myContext.getSupportFragmentManager();
                manager.beginTransaction().remove(manager.findFragmentById(R.id.viewpager)).replace(R.id.fragment_frame_layout, new CreateServiceFragment(user_id,tabLayout)).addToBackStack("TAG").commit();

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
