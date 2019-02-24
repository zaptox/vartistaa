package com.vartista.www.vartista.modules.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.CreateRequest;
import com.vartista.www.vartista.beans.servicepaapointmentsitems;
import com.vartista.www.vartista.modules.provider.AppointmentDetails;
import com.vartista.www.vartista.restcalls.ApiClient;
import com.vartista.www.vartista.restcalls.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAppointmentDetails extends AppCompatActivity {

    public TextView serviceprovidername,servicecharges,Date,Time,serviceDesc,serviceCat,serviceLoc;
    ImageView imageView;
    public static ApiInterface apiInterface;

    Button cancelButton , btn_paynow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_appointment_details);

        imageView = findViewById(R.id.profile_image);
        serviceprovidername=(TextView)findViewById(R.id.textViewname_user);
        servicecharges=(TextView)findViewById(R.id.servicedetail_user);
        Date=(TextView)findViewById(R .id.textViewdate_user);
        Time=(TextView)findViewById(R .id.textViewtime_user);
        serviceCat=(TextView)findViewById(R.id.service_category);
        serviceDesc=(TextView)findViewById(R.id.textView_service_description);
        serviceLoc= (TextView)findViewById(R.id.location);
        cancelButton = findViewById(R.id.cancelbuttonUser);
        btn_paynow= (Button)findViewById(R.id.PayButon);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        btn_paynow.setEnabled(false);

        Intent intent = getIntent();
        servicepaapointmentsitems ob = (servicepaapointmentsitems) intent.getSerializableExtra("object");

        Picasso.get().load(ob    .getImage()).fit().centerCrop()
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile)
                .into(imageView);
        serviceprovidername.setText(ob.getUsername());
        servicecharges.setText(ob.getService_title()+" "+ob.getPrice());
        Date.setText(ob.getDate());
        Time.setText(ob.getTime());
        serviceDesc.setText(ob.getService_description());
        serviceLoc.setText(ob.getLocation());

        if (ob.getRequest_status().equals("5")){
            cancelButton.setEnabled(false);
            btn_paynow.setEnabled(true);
        }
        final int requestservice_id = Integer.parseInt(ob.getRequest_status());

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            upaterequeststatus(requestservice_id);
            }
        });

    }

    public void upaterequeststatus(int id){
        Call<CreateRequest> call= UserAppointmentDetails.apiInterface.updaterequeststatus(id);
        call.enqueue(new Callback<CreateRequest>() {
            @Override
            public void onResponse(Call <CreateRequest> call, Response<CreateRequest> response) {

                if(response.body().getResponse().equals("ok")){


                    Toast.makeText(UserAppointmentDetails.this,"Updated Successfully..",Toast.LENGTH_SHORT).show();

                }else if(response.body().getResponse().equals("exist")){


                    Toast.makeText(UserAppointmentDetails.this,"Same Data exists....",Toast.LENGTH_SHORT).show();

                }
                else if(response.body().getResponse().equals("error")){


                    Toast.makeText(UserAppointmentDetails.this,"Something went wrong....",Toast.LENGTH_SHORT).show();

                }
                else{


                    Toast.makeText(UserAppointmentDetails.this,"Something went wrong....",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call <CreateRequest> call, Throwable t) {

                Toast.makeText(UserAppointmentDetails.this,"Update Failed",Toast.LENGTH_SHORT).show();

            }
        });
    }
}
