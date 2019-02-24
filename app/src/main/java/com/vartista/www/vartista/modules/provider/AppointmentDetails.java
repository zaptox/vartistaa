package com.vartista.www.vartista.modules.provider;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.TextAppearanceSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kennyc.bottomsheet.BottomSheet;
import com.kennyc.bottomsheet.BottomSheetListener;
import com.squareup.picasso.Picasso;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.CreateRequest;
import com.vartista.www.vartista.beans.EarningsBean;
import com.vartista.www.vartista.beans.servicepaapointmentsitems;
import com.vartista.www.vartista.modules.user.Service_user_cancel;
import com.vartista.www.vartista.restcalls.ApiClient;
import com.vartista.www.vartista.restcalls.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentDetails extends AppCompatActivity {

    public TextView serviceprovidername,servicecharges,Date,Time,serviceDesc,serviceCat,serviceLoc;
    ImageView imageView;
    Button cancelButton,PaymentReceivedButon;
    public static ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);

         imageView = findViewById(R.id.profile_image);
        serviceprovidername=(TextView)findViewById(R.id.textViewname_user);
        servicecharges=(TextView)findViewById(R.id.servicedetail_user);
        Date=(TextView)findViewById(R .id.textViewdate_user);
        Time=(TextView)findViewById(R .id.textViewtime_user);
        serviceDesc=(TextView)findViewById(R.id.textView_service_description);
        serviceLoc= (TextView)findViewById(R.id.location);
        cancelButton = findViewById(R.id.cancelbuttonUser);
        PaymentReceivedButon = findViewById(R.id.PaymentReceivedButon);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
//        PaymentReceivedButon.setEnabled(false);
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

        final int requestservice_id = Integer.parseInt(ob.getRequest_status());

//        if (ob.getRequest_status().equals("5")){
//            PaymentReceivedButon.setEnabled(true);
//        }

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               upaterequeststatus(requestservice_id);
            }
        });

        PaymentReceivedButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BottomSheet.Builder(v.getContext())
                        .setMessage("Have You Received You payment?  ")
                        .setTitle("Payment Received?")
                        .setPositiveButton("Yes")
                        .setNegativeButton("No")
                        .show();
            }
        });

    }



    public void insertEarnings(int sp_id,int user_id,int serviceid , int requestservice_id,double total_ammout,double admin_tax,double discount,double user_bones,double sp_earning, double admin_earning,String date){
        Call<EarningsBean> call= AppointmentDetails.apiInterface.Insert_Earnings(sp_id,user_id,serviceid,requestservice_id,total_ammout,admin_tax,discount,user_bones,sp_earning,admin_earning,date);
        call.enqueue(new Callback<EarningsBean>() {
            @Override
            public void onResponse(Call <EarningsBean> call, Response<EarningsBean> response) {

                if(response.body().getResponse().equals("ok")){


                    Toast.makeText(AppointmentDetails.this,"Updated Successfully..",Toast.LENGTH_SHORT).show();

                }else if(response.body().getResponse().equals("exist")){


                    Toast.makeText(AppointmentDetails.this,"Same Data exists....",Toast.LENGTH_SHORT).show();

                }
                else if(response.body().getResponse().equals("error")){


                    Toast.makeText(AppointmentDetails.this,"Something went wrong....",Toast.LENGTH_SHORT).show();

                }
                else{


                    Toast.makeText(AppointmentDetails.this,"Something went wrong....",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call <EarningsBean> call, Throwable t) {

                Toast.makeText(AppointmentDetails.this,"Update Failed",Toast.LENGTH_SHORT).show();

            }
        });
    }



    public void  upaterequeststatus(int requestservice_id){
        Call<CreateRequest> call= AppointmentDetails.apiInterface.updaterequeststatus(requestservice_id);
        call.enqueue(new Callback<CreateRequest>() {
            @Override
            public void onResponse(Call <CreateRequest> call, Response<CreateRequest> response) {

                if(response.body().getResponse().equals("ok")){


                    Toast.makeText(AppointmentDetails.this,"Updated Successfully..",Toast.LENGTH_SHORT).show();

                }else if(response.body().getResponse().equals("exist")){


                    Toast.makeText(AppointmentDetails.this,"Same Data exists....",Toast.LENGTH_SHORT).show();

                }
                else if(response.body().getResponse().equals("error")){


                    Toast.makeText(AppointmentDetails.this,"Something went wrong....",Toast.LENGTH_SHORT).show();

                }
                else{


                    Toast.makeText(AppointmentDetails.this,"Something went wrong....",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call <CreateRequest> call, Throwable t) {

                Toast.makeText(AppointmentDetails.this,"Update Failed",Toast.LENGTH_SHORT).show();

            }
        });
    }



}
