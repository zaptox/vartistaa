package com.vartista.www.vartista.modules.provider;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.servicepaapointmentsitems;

public class AppointmentDetails extends AppCompatActivity {

    public TextView serviceprovidername,servicecharges,Date,Time,serviceDesc,serviceCat,serviceLoc;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);

         imageView = findViewById(R.id.profile_image);
        serviceprovidername=(TextView)findViewById(R.id.textViewname_user);
        servicecharges=(TextView)findViewById(R.id.servicedetail_user);
        Date=(TextView)findViewById(R .id.textViewdate_user);
        Time=(TextView)findViewById(R .id.textViewtime_user);
        serviceCat=(TextView)findViewById(R.id.service_category);
        serviceDesc=(TextView)findViewById(R.id.textView_service_description); 
        serviceLoc= (TextView)findViewById(R.id.textViewloc_user);

        Intent intent = getIntent();
        servicepaapointmentsitems ob = (servicepaapointmentsitems) intent.getSerializableExtra("object");

        Picasso.get().load(ob.getImage()).fit().centerCrop()
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile)
                .into(imageView);
        serviceprovidername.setText(ob.getUsername());
        servicecharges.setText(ob.getService_title()+" "+ob.getPrice());
        Date.setText(ob.getDate());
        Time.setText(ob.getTime());
        serviceCat.setText(ob.getName());
        serviceDesc.setText(ob.getService_description());
        serviceLoc.setText(ob.getLocation());



    }
}
