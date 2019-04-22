package com.vartista.www.vartista.modules.user;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.valdesekamdem.library.mdtoast.MDToast;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.adapters.MyRequestsServicesListAdapter;
import com.vartista.www.vartista.beans.AllNotificationBean;
import com.vartista.www.vartista.beans.CreateRequest;
import com.vartista.www.vartista.beans.NotificationsManager;
import com.vartista.www.vartista.beans.ServiceRequets;
import com.vartista.www.vartista.beans.servicepaapointmentsitems;
import com.vartista.www.vartista.modules.general.AppSettings;
import com.vartista.www.vartista.modules.general.HomeActivity;
import com.vartista.www.vartista.modules.provider.AppointmentDetails;
import com.vartista.www.vartista.restcalls.ApiClient;
import com.vartista.www.vartista.restcalls.ApiInterface;
import com.vartista.www.vartista.restcalls.SendNotificationApiInterface;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAppointmentDetails extends AppCompatActivity {

    public TextView serviceprovidername,servicecharges,date_view,Time,serviceDesc,serviceCat,serviceLoc,d_servicename,d_sp_name,d_payment;
    ImageView imageView;
    public static ApiInterface apiInterface;
    Dialog payment_dialogue, cancel_service;
    public static SendNotificationApiInterface sendNotificationApiInterface;

    Button cancelButton , btn_paynow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_appointment_details);
        Date currentTime = Calendar.getInstance().getTime();

        sendNotificationApiInterface = ApiClient.getApiClient().create(SendNotificationApiInterface.class);

        imageView = findViewById(R.id.profile_image);
        serviceprovidername=(TextView)findViewById(R.id.textViewname_user);
        servicecharges=(TextView)findViewById(R.id.servicedetail_user);
        date_view=(TextView)findViewById(R .id.textViewdate_user);
        Time=(TextView)findViewById(R .id.textViewtime_user);
        serviceCat=(TextView)findViewById(R.id.service_category);
        serviceDesc=(TextView)findViewById(R.id.textView_service_description);
        serviceLoc= (TextView)findViewById(R.id.location);
        cancelButton = findViewById(R.id.cancelbuttonUser);
        btn_paynow= (Button)findViewById(R.id.PayButon);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        btn_paynow.setEnabled(true);

        Intent intent = getIntent();
        final servicepaapointmentsitems ob = (servicepaapointmentsitems) intent.getSerializableExtra("object");

        if(Integer.parseInt(ob.getRequest_status())==6){
            btn_paynow.setText("Payment Under Verification");
            btn_paynow.setEnabled(false);
        }

        Picasso.get().load(ob    .getImage()).fit().centerCrop()
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile)
                .into(imageView);
        serviceprovidername.setText("Service Provider : "+ob.getUsername());
        servicecharges.setText("Service : "+ob.getService_title()+" "+ob.getPrice()+"£");
        date_view.setText("Date : "+ob.getDate());
        Time.setText("Time : "+ob.getTime());
        serviceDesc.setText(ob.getService_description());
        serviceLoc.setText("Location : "+ob.getLocation());

        if (ob.getRequest_status().equals("5")){
            cancelButton.setEnabled(true);
            btn_paynow.setEnabled(true);
        }
        final int requestservice_id = Integer.parseInt(ob.getRequestservice_id());

        btn_paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                payment_dialogue = new Dialog(UserAppointmentDetails.this);
                payment_dialogue.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
                payment_dialogue.setContentView(R.layout.paynow_dialogue);
                Button paynow = (Button)payment_dialogue.findViewById(R.id.paynow);
                final RadioButton cash_pay=payment_dialogue.findViewById(R.id.cash_pay);
                final RadioButton online_pay=payment_dialogue.findViewById(R.id.online_pay);

                Button  cancel = (Button)payment_dialogue.findViewById(R.id.cancelbutton);
                d_sp_name = payment_dialogue.findViewById(R.id.sp_name);
                d_payment = payment_dialogue.findViewById(R.id.to_pay);
                d_servicename = payment_dialogue.findViewById(R.id.service_name);

                d_sp_name.setText("Service Provider: "+ob.getUsername());
                d_payment.setText("Payment: "+ob.getPrice()+"£");
                d_servicename.setText("Service:"+ob.getService_title());

                payment_dialogue.show();



                paynow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    if(cash_pay.isChecked()){
                        //ye  toast mat hatana
                        MDToast.makeText(UserAppointmentDetails.this, "Verification by Service Provider Under Process...", MDToast.LENGTH_LONG,MDToast.TYPE_INFO).show();
                        int customer_id=Integer.parseInt(ob.getService_provider_id());
                        String body="Have you Received the payment from "+ob.getUsername();
                        String title="Cash Payment Verification";
                        insertNotification(title,body,Integer.parseInt(ob.getUser_customer_id()),customer_id,1,get_Current_Date());
                        payment_received_function(Integer.parseInt(ob.getRequestservice_id()));

                        Call<NotificationsManager> callNotification = UserAppointmentDetails.sendNotificationApiInterface
                                .sendPushNotification(customer_id,
                                        body,title);
                        callNotification.enqueue(new Callback<NotificationsManager>() {
                            @Override
                            public void onResponse(Call<NotificationsManager> call, Response<NotificationsManager> response) {
                                if(response.isSuccessful()){}


                            }


                            @Override
                            public void onFailure(Call<NotificationsManager> call, Throwable t) {

                            }
                        });

                        payment_dialogue.cancel();


                    }
                    else if(online_pay.isChecked()){
                        MDToast.makeText(UserAppointmentDetails.this, "Online Payment", MDToast.LENGTH_SHORT,MDToast.TYPE_INFO).show();

                    }

                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        payment_dialogue.cancel();
                    }
                });


            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel_service = new Dialog(UserAppointmentDetails.this);
                cancel_service.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
                cancel_service.setContentView(R.layout.cancel_service_dialogue);
                Button yes_cancel = (Button)cancel_service.findViewById(R.id.yes_cancel);

                Button  cancel = (Button)cancel_service.findViewById(R.id.cancelbutton);
                d_sp_name = cancel_service.findViewById(R.id.sp_name);
                d_payment = cancel_service.findViewById(R.id.to_pay);
                d_servicename = cancel_service.findViewById(R.id.service_name);
//                TextView penalty_text=cancel_service.findViewById(R.id.penalty_text);
//                penalty_text.setVisibility(View.INVISIBLE);
//                if(ob.getRequest_status().equals("5")){
//                    penalty_text.setVisibility(View.VISIBLE);
//                }
//
                d_sp_name.setText("Service Provider: "+ob.getUsername());
                d_payment.setText("Payment: "+ob.getPrice()+"£");
                d_servicename.setText("Service:"+ob.getService_title());


                cancel_service.show();

                yes_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        upaterequeststatus(requestservice_id);

                        SharedPreferences current_user = getSharedPreferences("Login", Context.MODE_PRIVATE);

                        String current_username = current_user.getString("name", "user-undefined");

                        int customer_id=Integer.parseInt(ob.getService_provider_id());
                        String body=current_username+" has cancelled your service, unfortunately the service "+ob.getService_title()+" will not be provided.";
                        String title="'"+ob.getService_title()+"' service is cancelled";
                        insertNotification(title,body,Integer.parseInt(ob.getUser_customer_id()),customer_id,1,get_Current_Date());


                        Call<NotificationsManager> callNotification = UserAppointmentDetails.sendNotificationApiInterface
                                .sendPushNotification(customer_id,
                                        body,title);
                        callNotification.enqueue(new Callback<NotificationsManager>() {
                            @Override
                            public void onResponse(Call<NotificationsManager> call, Response<NotificationsManager> response) {
                                if(response.isSuccessful()){}

//                                if(response.isSuccessful())
//                                    Toast.makeText(getContext(), "Request Accepted",Toast.LENGTH_SHORT).show();

                            }


                            @Override
                            public void onFailure(Call<NotificationsManager> call, Throwable t) {

                            }
                        });



                        Intent intent = new Intent(UserAppointmentDetails.this, MyServiceMeetings.class);
                        startActivity(intent);

                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        cancel_service.cancel();
                    }
                });




            }
        });

    }

    public void upaterequeststatus(int id){
        Call<CreateRequest> call= UserAppointmentDetails.apiInterface.updaterequeststatus(id);
        call.enqueue(new Callback<CreateRequest>() {
            @Override
            public void onResponse(Call <CreateRequest> call, Response<CreateRequest> response) {



                if(response.body().getResponse().equals("ok")){


                    MDToast.makeText(UserAppointmentDetails.this,"Updated Successfully..",MDToast.LENGTH_SHORT,MDToast.TYPE_SUCCESS).show();

                }else if(response.body().getResponse().equals("exist")){


                    MDToast.makeText(UserAppointmentDetails.this,"Same Data exists....",MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();

                }
                else if(response.body().getResponse().equals("error")){


                    MDToast.makeText(UserAppointmentDetails.this,"Something went wrong....",MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();

                }
                else{


                }

            }

            @Override
            public void onFailure(Call <CreateRequest> call, Throwable t) {

                MDToast.makeText(UserAppointmentDetails.this,"Update Failed",MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();

            }
        });
    }



    public String get_Current_Date(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = sdf.getDateTimeInstance().format(new Date());
        return currentDate;
    }



    public  void insertNotification(String title , String message, int sender_id , int receiver_id , int status , String created_at){
//         setUIToWait(true);
        Call<AllNotificationBean> call=UserAppointmentDetails.apiInterface.Insert_Notification(title,message,sender_id,receiver_id,status,created_at);
        call.enqueue(new Callback<AllNotificationBean>() {
            @Override
            public void onResponse(Call <AllNotificationBean> call, Response<AllNotificationBean> response) {

                if(response.body().getResponse().equals("ok")){
//                     setUIToWait(false);

                }
                else if(response.body().getResponse().equals("exist")){
//                     setUIToWait(false);

                }
                else if(response.body().getResponse().equals("error")){
//                     setUIToWait(false);


                }

                else{
//                     setUIToWait(false);


                }

            }

            @Override
            public void onFailure(Call <AllNotificationBean> call, Throwable t) {

            }
        });


    }

    public void payment_received_function(int id){
        Call<ServiceRequets> call= UserAppointmentDetails.apiInterface.updateOnClickRequests(6,id);
        call.enqueue(new Callback<ServiceRequets>() {
            @Override
            public void onResponse(Call <ServiceRequets> call, Response<ServiceRequets> response) {

                if(response.body().getResponse().equals("ok")){


                }else if(response.body().getResponse().equals("exist")){


                }
                else if(response.body().getResponse().equals("error")){



                }
                else{


                }

            }

            @Override
            public void onFailure(Call <ServiceRequets> call, Throwable t) {

//                MDToast.makeText(AppointmentDetails.this,"Update Failed",MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();

            }
        });
    }

}
