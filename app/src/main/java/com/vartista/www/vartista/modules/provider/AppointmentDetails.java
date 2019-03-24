package com.vartista.www.vartista.modules.provider;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.TextAppearanceSpan;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.kennyc.bottomsheet.BottomSheet;
import com.kennyc.bottomsheet.BottomSheetListener;
import com.squareup.picasso.Picasso;
import com.valdesekamdem.library.mdtoast.MDToast;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.adapters.MyRequestsServicesListAdapter;
import com.vartista.www.vartista.adapters.servicepappointmentsadapter;
import com.vartista.www.vartista.appconfig.App;
import com.vartista.www.vartista.beans.CreateRequest;
import com.vartista.www.vartista.beans.EarningsBean;
import com.vartista.www.vartista.beans.NotificationsManager;
import com.vartista.www.vartista.beans.ServiceRequets;
import com.vartista.www.vartista.beans.servicepaapointmentsitems;
import com.vartista.www.vartista.modules.general.HomeActivity;
import com.vartista.www.vartista.modules.user.MyServiceMeetings;
import com.vartista.www.vartista.modules.user.Service_user_cancel;
import com.vartista.www.vartista.modules.user.UserAppointmentDetails;
import com.vartista.www.vartista.restcalls.ApiClient;
import com.vartista.www.vartista.restcalls.ApiInterface;
import com.vartista.www.vartista.restcalls.SendNotificationApiInterface;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static okhttp3.internal.http.HttpDate.format;

public class AppointmentDetails extends AppCompatActivity {

    public TextView serviceprovidername,servicecharges,Date,Time,serviceDesc,serviceCat,serviceLoc,d_servicename,d_sp_name,d_payment;
    ImageView imageView;
    Button cancelButton,PaymentReceivedButon;
    public static ApiInterface apiInterface;
    Dialog payment_received_dialogue, cancel_service;
    Double admin_tax,discount,penalty;
    public static SendNotificationApiInterface sendNotificationApiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);
        sendNotificationApiInterface = ApiClient.getApiClient().create(SendNotificationApiInterface.class);



        new AppointmentDetails.Conncetion(AppointmentDetails.this).execute();



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
        final servicepaapointmentsitems ob = (servicepaapointmentsitems) intent.getSerializableExtra("object");

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

        final int requestservice_id = Integer.parseInt(ob.getRequestservice_id());

//        if (ob.getRequest_status().equals("5")){
//            PaymentReceivedButon.setEnabled(true);
//        }

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel_service = new Dialog(AppointmentDetails.this);
                cancel_service.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
                cancel_service.setContentView(R.layout.cancel_service_dialogue);
                Button yes_cancel = (Button)cancel_service.findViewById(R.id.yes_cancel);

                Button  cancel = (Button)cancel_service.findViewById(R.id.cancelbutton);
                d_sp_name = cancel_service.findViewById(R.id.sp_name);
                d_payment = cancel_service.findViewById(R.id.to_pay);
                d_servicename = cancel_service.findViewById(R.id.service_name);

                d_sp_name.setText("Service Provider: "+ob.getUsername());
                d_payment.setText("Payment: "+ob.getPrice());
                d_servicename.setText("Service:"+ob.getService_title());

                cancel_service.show();

                yes_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        upaterequeststatus(requestservice_id);

                        Intent intent = new Intent(AppointmentDetails.this, MyAppointments.class);
                        startActivity(intent);
                        finish();

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

        PaymentReceivedButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                payment_received_dialogue = new Dialog(AppointmentDetails.this);
                payment_received_dialogue.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
                payment_received_dialogue.setContentView(R.layout.payment_received_dialogue);
                Button yes_paid = (Button)payment_received_dialogue.findViewById(R.id.yes_paid);

                Button  cancel = (Button)payment_received_dialogue.findViewById(R.id.cancelbutton);
                d_sp_name = payment_received_dialogue.findViewById(R.id.sp_name);
                d_payment = payment_received_dialogue.findViewById(R.id.to_pay);
                d_servicename = payment_received_dialogue.findViewById(R.id.service_name);

                d_sp_name.setText("Service Provider: "+ob.getUsername());
                d_payment.setText("Payment: "+ob.getPrice());
                d_servicename.setText("Service:"+ob.getService_title());

                payment_received_dialogue.show();

                yes_paid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

//                        upaterequeststatus(requestservice_id);

                        insertEarnings(Integer.parseInt(ob.getService_provider_id()),Integer.parseInt(ob.getUser_customer_id()),ob.getService_id(), Integer.parseInt(ob.getRequestservice_id()),Double.parseDouble(ob.getPrice()),admin_tax,discount,0.0,ob.getSpname());
//                        insertEarnings();
                        Intent intent = new Intent(AppointmentDetails.this, MyAppointments.class);
                        startActivity(intent);
                        finish();

                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        payment_received_dialogue.cancel();
                    }
                });




            }
        });

    }



    @TargetApi(Build.VERSION_CODES.N)
    public void insertEarnings(int sp_id, final int user_id, int serviceid , final int requestservice_id, double total_ammout, double admin_tax, double discount, double user_bones, final String sp_name){

        double admin_earning=(total_ammout *admin_tax)/100;
        double sp_earning= total_ammout-admin_earning;
        String date=getDateTime();

//        System.out.println("Current time => " + c);,

        Call<EarningsBean> call= AppointmentDetails.apiInterface.Insert_Earnings(sp_id,user_id,serviceid,requestservice_id,total_ammout,admin_tax,discount,user_bones,sp_earning,admin_earning,date);
        call.enqueue(new Callback<EarningsBean>() {
            @Override
            public void onResponse(Call <EarningsBean> call, Response<EarningsBean> response) {

                if(response.body().getResponse().equals("ok")){

                    payment_received_function(requestservice_id,user_id,sp_name);
                    MDToast.makeText(AppointmentDetails.this,"Updated Successfully..",MDToast.LENGTH_SHORT,MDToast.TYPE_SUCCESS).show();

                }else if(response.body().getResponse().equals("exist")){


                    MDToast.makeText(AppointmentDetails.this,"Same Data exists....",MDToast.LENGTH_SHORT,MDToast.TYPE_WARNING).show();

                }
                else if(response.body().getResponse().equals("error")){


                    MDToast.makeText(AppointmentDetails.this,"Something went wrong....",MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();

                }
                else{


                    MDToast.makeText(AppointmentDetails.this,"Something went wrong....",MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();
                }

            }

            @Override
            public void onFailure(Call <EarningsBean> call, Throwable t) {

                MDToast.makeText(AppointmentDetails.this,"Update Failed",MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();

            }
        });
    }



    public void  upaterequeststatus(int requestservice_id){


        Call<CreateRequest> call= AppointmentDetails.apiInterface.updaterequeststatus(requestservice_id);
        call.enqueue(new Callback<CreateRequest>() {
            @Override
            public void onResponse(Call <CreateRequest> call, Response<CreateRequest> response) {

                if(response.body().getResponse().equals("ok")){


                    MDToast.makeText(AppointmentDetails.this,"Updated Successfully..",MDToast.LENGTH_SHORT,MDToast.TYPE_SUCCESS).show();

                }else if(response.body().getResponse().equals("exist")){


                    MDToast.makeText(AppointmentDetails.this,"Same Data exists....",MDToast.LENGTH_SHORT,MDToast.TYPE_WARNING).show();

                }
                else if(response.body().getResponse().equals("error")){


                    MDToast.makeText(AppointmentDetails.this,"Something went wrong....",MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();

                }
                else{


                    MDToast.makeText(AppointmentDetails.this,"Something went wrong....",MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();
                }

            }

            @Override
            public void onFailure(Call <CreateRequest> call, Throwable t) {

                MDToast.makeText(AppointmentDetails.this,"Update Failed",MDToast.LENGTH_SHORT).show();

            }
        });
    }

    public void payment_received_function(int id, final int customer_id, final String sp_name){
        Call<ServiceRequets> call= AppointmentDetails.apiInterface.updateOnClickRequests(3,id);
        call.enqueue(new Callback<ServiceRequets>() {
            @Override
            public void onResponse(Call <ServiceRequets> call, Response<ServiceRequets> response) {

                if(response.body().getResponse().equals("ok")){

                String title="Wanna rate "+sp_name+"?";
                String body="Make sure to rate and let us know about service provider's behavior.";

                    MDToast.makeText(AppointmentDetails.this,"Service Completed Successfully..",MDToast.LENGTH_SHORT,MDToast.TYPE_SUCCESS).show();

                    //notification to user for ratings
                    Call<NotificationsManager> callNotification = AppointmentDetails.sendNotificationApiInterface
                            .sendPushNotification(customer_id,
                                    body,title);
                    callNotification.enqueue(new Callback<NotificationsManager>() {
                        @Override
                        public void onResponse(Call<NotificationsManager> call, Response<NotificationsManager> response) {
                            if(response.isSuccessful()){}

//                            if(response.isSuccessful())
//                                MDToast.makeText(view.getContext(), "Request Accepted",MDToast.LENGTH_SHORT).show();

                        }


                        @Override
                        public void onFailure(Call<NotificationsManager> call, Throwable t) {

                        }
                    });


                }else if(response.body().getResponse().equals("exist")){


                    MDToast.makeText(AppointmentDetails.this,"Same Data exists....",MDToast.LENGTH_SHORT,MDToast.TYPE_WARNING).show();

                }
                else if(response.body().getResponse().equals("error")){


                    MDToast.makeText(AppointmentDetails.this,"Something went wrong....",MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();

                }
                else{


                    MDToast.makeText(AppointmentDetails.this,"Something went wrong....",MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();
                }

            }

            @Override
            public void onFailure(Call <ServiceRequets> call, Throwable t) {

                MDToast.makeText(AppointmentDetails.this,"Update Failed",MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();

            }
        });
    }

    class Conncetion extends AsyncTask<String,String ,String > {
        private int service_id;
        private ProgressDialog dialog;

        public Conncetion(Context activity) {
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Retriving data Please Wait..");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {


            String result = "";

            final String BASE_URL = "http://vartista.com/vartista_app/retreive_admin_cost.php";
            try {
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(BASE_URL));
                HttpResponse response = client.execute(request);
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuffer stringBuffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    stringBuffer.append(line);
                    break;
                }
                reader.close();
                result = stringBuffer.toString();
            } catch (URISyntaxException e) {
                e.printStackTrace();
                return new String("Exception is " + e.getMessage());
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            try {


                JSONObject jsonResult = new JSONObject(result);

                int success = jsonResult.getInt("success");

                if (success == 1) {
                    JSONArray services = jsonResult.getJSONArray("services");
                    for (int j = 0; j < services.length(); j++) {
                        JSONObject ser1 = services.getJSONObject(j);
                         discount= ser1.getDouble("discount");
                        admin_tax = ser1.getDouble("admin_tax");
                        penalty = ser1.getDouble("penalty");

//                          MDToast.makeText(MyAppointments.this, ""+myappointments, MDToast.LENGTH_SHORT).show();
                    }
                }

                else {
                    //   MDToast.makeText(getApplicationContext(),"no data",MDToast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                MDToast.makeText(AppointmentDetails.this, "Connection Problem!", MDToast.LENGTH_SHORT,MDToast.TYPE_WARNING).show();
                // MDToast.makeText(getApplicationContext(),e.getMessage(),MDToast.LENGTH_LONG).show();
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        Date date = new Date();
        return dateFormat.format(date);
    }




}
