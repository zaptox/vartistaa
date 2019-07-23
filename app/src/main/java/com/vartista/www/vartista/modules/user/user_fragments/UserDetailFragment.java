package com.vartista.www.vartista.modules.user.user_fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rilixtech.CountryCodePicker;
import com.squareup.picasso.Picasso;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserDetailFragment extends Fragment {


    public UserDetailFragment() {
        // Required empty public constructor
    }

    User user_got;
    TextView name,email,contact,gender,header_name;
    ImageView img;
    CheckBox active,notyet;



    @SuppressLint("ValidFragment")
    public  UserDetailFragment(User u){
        this.user_got=u;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_detail, container, false);
        name = (TextView)view.findViewById(R.id.name1);
        header_name = (TextView)view.findViewById(R.id.header_name);
        email = (TextView)view.findViewById(R.id.email1);
        contact = (TextView)view.findViewById(R.id.contact1);
        gender = (TextView)view.findViewById(R.id.gender1);
        img = (ImageView)view.findViewById(R.id.profile_image);
        active = (CheckBox)view.findViewById(R.id.checkBoxActive);
        notyet = (CheckBox)view.findViewById(R.id.checkBoxNotYet);


        name.setText(user_got.getName());
        header_name.setText(user_got.getName());
        email.setText(user_got.getEmail());
        contact.setText(user_got.getContact());
        gender.setText(user_got.getGender());


        if(user_got.getSp_status().equals("1")){
            Toast.makeText(view.getContext(), "Sp Status is "+user_got.getSp_status(), Toast.LENGTH_SHORT).show();
            active.setChecked(true);
            notyet.setEnabled(false);
            if (active.isChecked()){
               active.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                   @Override
                   public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                       if(b==true){
                           active.setChecked(true);

                       }
                       else{
                           active.setChecked(true);

                       }
                   }
               });

            }
        }
        else{
            notyet.setChecked(true);
            active.setEnabled(false);
            if (notyet.isChecked()){
                notyet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                   @Override
                   public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                       if(b==true){
                           notyet.setChecked(true);

                       }
                       else{
                           notyet.setChecked(true);

                       }
                   }
               });

            }
        }
        Picasso.get().load(user_got.getImage()).fit().centerCrop()
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile)
                .into(img);
        // Inflate the layout for this fragment
        return view;

    }

}
