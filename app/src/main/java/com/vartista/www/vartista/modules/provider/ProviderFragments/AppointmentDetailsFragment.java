package com.vartista.www.vartista.modules.provider.ProviderFragments;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.valdesekamdem.library.mdtoast.MDToast;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.AllNotificationBean;
import com.vartista.www.vartista.beans.CreateRequest;
import com.vartista.www.vartista.beans.EarningsBean;
import com.vartista.www.vartista.beans.EarningsDuesBean;
import com.vartista.www.vartista.beans.NotificationsManager;
import com.vartista.www.vartista.beans.ServiceRequets;
import com.vartista.www.vartista.beans.servicepaapointmentsitems;
import com.vartista.www.vartista.modules.provider.AppointmentDetails;
import com.vartista.www.vartista.modules.provider.MyAppointments;
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
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AppointmentDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppointmentDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //activity code
    public TextView serviceprovidername,servicecharges,Date,Time,serviceDesc,serviceCat,serviceLoc,d_servicename,d_sp_name,d_payment;
    ImageView imageView;
    Button cancelButton,PaymentReceivedButon;
    public static ApiInterface apiInterface;
    Dialog payment_received_dialogue, cancel_service;
    Double admin_tax,discount,penalty;
    public static SendNotificationApiInterface sendNotificationApiInterface;


    private OnFragmentInteractionListener mListener;

    public AppointmentDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AppointmentDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AppointmentDetailsFragment newInstance(String param1, String param2) {
        AppointmentDetailsFragment fragment = new AppointmentDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_appointment_details, container, false);

        sendNotificationApiInterface = ApiClient.getApiClient().create(SendNotificationApiInterface.class);



        new AppointmentDetailsFragment.Conncetion(getContext()).execute();



        imageView = view.findViewById(R.id.profile_image);
        serviceprovidername=(TextView)view.findViewById(R.id.textViewname_user);
        servicecharges=(TextView)view.findViewById(R.id.servicedetail_user);
        Date=(TextView)view.findViewById(R .id.textViewdate_user);
        Time=(TextView)view.findViewById(R .id.textViewtime_user);
        serviceDesc=(TextView)view.findViewById(R.id.textView_service_description);
        serviceLoc= (TextView)view.findViewById(R.id.location);
        cancelButton = view.findViewById(R.id.cancelbuttonUser);
        PaymentReceivedButon = view.findViewById(R.id.PaymentReceivedButon);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
//        PaymentReceivedButon.setEnabled(false);
        Intent intent = getActivity().getIntent();
        final servicepaapointmentsitems ob = (servicepaapointmentsitems) intent.getSerializableExtra("object");

        Picasso.get().load(ob    .getImage()).fit().centerCrop()
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile)
                .into(imageView);

        serviceprovidername.setText("Service Provider : "+ob.getUsername());
        servicecharges.setText(ob.getService_title()+" "+ob.getPrice());
        Date.setText(ob.getDate());
        Time.setText(ob.getTime());
        serviceDesc.setText(ob.getService_description());
        serviceLoc.setText("Location : "+ob.getLocation());

        final int requestservice_id = Integer.parseInt(ob.getRequestservice_id());

//        if (ob.getRequest_status().equals("5")){
//            PaymentReceivedButon.setEnabled(true);
//        }

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel_service = new Dialog(getContext());
                cancel_service.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
                cancel_service.setContentView(R.layout.cancel_service_dialogue);
                Button yes_cancel = (Button)cancel_service.findViewById(R.id.yes_cancel);

                Button  cancel = (Button)cancel_service.findViewById(R.id.cancelbutton);
                d_sp_name = cancel_service.findViewById(R.id.sp_name);
                d_payment = cancel_service.findViewById(R.id.to_pay);
                d_servicename = cancel_service.findViewById(R.id.service_name);

                d_sp_name.setText("Service Provider: "+ob.getUsername());
                d_payment.setText("Payment: "+ob.getPrice()+"£");
                d_servicename.setText("Service:"+ob.getService_title());

                cancel_service.show();

                yes_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        upaterequeststatus(requestservice_id);

                        SharedPreferences current_user = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);

                        String current_username = current_user.getString("name", "user-undefined");


                        int customer_id=Integer.parseInt(ob.getUser_customer_id());
                        String body=current_username+" has cancelled your service, unfortunately the service "+ob.getService_title()+" will not be provided.";
                        String title="'"+ob.getService_title()+"' service is cancelled";
                        insertNotification(title,body,Integer.parseInt(ob.getService_provider_id()),customer_id,1,get_Current_Date());


                        Call<NotificationsManager> callNotification = AppointmentDetailsFragment.sendNotificationApiInterface
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


                        Intent intent = new Intent(getContext(), MyAppointments.class);
                        startActivity(intent);
                        getActivity().finish();

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


                payment_received_dialogue = new Dialog(getContext());
                payment_received_dialogue.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
                payment_received_dialogue.setContentView(R.layout.payment_received_dialogue);
                Button yes_paid = (Button)payment_received_dialogue.findViewById(R.id.yes_paid);

                Button  cancel = (Button)payment_received_dialogue.findViewById(R.id.cancelbutton);
                d_sp_name = payment_received_dialogue.findViewById(R.id.sp_name);
                d_payment = payment_received_dialogue.findViewById(R.id.to_pay);
                d_servicename = payment_received_dialogue.findViewById(R.id.service_name);

                d_sp_name.setText("Service Provider: "+ob.getUsername());
                d_payment.setText("Payment: "+ob.getPrice()+"£");
                d_servicename.setText("Service:"+ob.getService_title());

                payment_received_dialogue.show();

                yes_paid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

//                        upaterequeststatus(requestservice_id);

                        insertEarnings(Integer.parseInt(ob.getService_provider_id()),Integer.parseInt(ob.getUser_customer_id()),ob.getService_id(), Integer.parseInt(ob.getRequestservice_id()),Double.parseDouble(ob.getPrice()),admin_tax,discount,0.0,ob.getName());
//                        insertEarnings();

                        payment_received_dialogue.cancel();

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



        return view;
    }

    @TargetApi(Build.VERSION_CODES.N)
    public void insertEarnings(final int sp_id, final int user_id, int serviceid , final int requestservice_id, double total_ammout, double admin_tax, double discount, double user_bones, final String sp_name){

        final double admin_earning=(total_ammout *admin_tax)/100;
        double sp_earning= total_ammout-admin_earning;
        String date=getDateTime();

//        System.out.println("Current time => " + c);,

        Call<EarningsBean> call= AppointmentDetailsFragment.apiInterface.Insert_Earnings(sp_id,user_id,serviceid,requestservice_id,total_ammout,admin_tax,discount,user_bones,sp_earning,admin_earning,date);
        call.enqueue(new Callback<EarningsBean>() {
            @Override
            public void onResponse(Call <EarningsBean> call, Response<EarningsBean> response) {

                if(response.body().getResponse().equals("ok")){
                    updateDues(sp_id,admin_earning);

                    payment_received_function(requestservice_id,user_id,sp_name);
                    MDToast.makeText(getContext(),"Earnings Added..",MDToast.LENGTH_SHORT,MDToast.TYPE_SUCCESS).show();

                }else if(response.body().getResponse().equals("exist")){


                    MDToast.makeText(getContext(),"Same Data exists....",MDToast.LENGTH_SHORT,MDToast.TYPE_WARNING).show();

                }
                else if(response.body().getResponse().equals("error")){


                    MDToast.makeText(getContext(),"Something went wrong....",MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();

                }
                else{


                    MDToast.makeText(getContext(),"Something went wrong....",MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();
                }

            }

            @Override
            public void onFailure(Call <EarningsBean> call, Throwable t) {

                MDToast.makeText(getContext(),"Update Failed",MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();

            }
        });
    }



    public void  upaterequeststatus(int requestservice_id){


        Call<CreateRequest> call= AppointmentDetailsFragment.apiInterface.updaterequeststatus(requestservice_id);
        call.enqueue(new Callback<CreateRequest>() {
            @Override
            public void onResponse(Call <CreateRequest> call, Response<CreateRequest> response) {

                if(response.body().getResponse().equals("ok")){


                    MDToast.makeText(getContext(),"Updated Successfully..",MDToast.LENGTH_SHORT,MDToast.TYPE_SUCCESS).show();

                }else if(response.body().getResponse().equals("exist")){


                    MDToast.makeText(getContext(),"Same Data exists....",MDToast.LENGTH_SHORT,MDToast.TYPE_WARNING).show();

                }
                else if(response.body().getResponse().equals("error")){


                    MDToast.makeText(getContext(),"Something went wrong....",MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();

                }
                else{


                    MDToast.makeText(getContext(),"Something went wrong....",MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();
                }

            }

            @Override
            public void onFailure(Call <CreateRequest> call, Throwable t) {

                MDToast.makeText(getContext(),"Update Failed",MDToast.LENGTH_SHORT).show();

            }
        });
    }

    public void payment_received_function(int id, final int customer_id, final String sp_name){
        Call<ServiceRequets> call= AppointmentDetailsFragment.apiInterface.updateOnClickRequests(3,id);
        call.enqueue(new Callback<ServiceRequets>() {
            @Override
            public void onResponse(Call <ServiceRequets> call, Response<ServiceRequets> response) {

                if(response.body().getResponse().equals("ok")){

                    String title="Wanna rate "+sp_name+"?";
                    String body="Make sure to rate and let us know about service provider's behavior.";

                    MDToast.makeText(getContext(),"Service Completed Successfully..",MDToast.LENGTH_SHORT,MDToast.TYPE_SUCCESS).show();

                    //notification to user for ratings
                    Call<NotificationsManager> callNotification = AppointmentDetailsFragment.sendNotificationApiInterface
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


                    MDToast.makeText(getContext(),"Same Data exists....",MDToast.LENGTH_SHORT,MDToast.TYPE_WARNING).show();

                }
                else if(response.body().getResponse().equals("error")){


                    MDToast.makeText(getContext(),"Something went wrong....",MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();

                }
                else{


                    MDToast.makeText(getContext(),"Something went wrong....",MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();
                }

            }

            @Override
            public void onFailure(Call <ServiceRequets> call, Throwable t) {

                MDToast.makeText(getContext(),"Update Failed",MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();

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
                MDToast.makeText(getContext(), "Connection Problem!", MDToast.LENGTH_SHORT,MDToast.TYPE_WARNING).show();
                // MDToast.makeText(getApplicationContext(),e.getMessage(),MDToast.LENGTH_LONG).show();
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        java.util.Date date = new Date();
        return dateFormat.format(date);
    }

    public String get_Current_Date(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = sdf.getDateTimeInstance().format(new Date());
        return currentDate;
    }



    public  void insertNotification(String title , String message, int sender_id , int receiver_id , int status , String created_at){
//         setUIToWait(true);
        Call<AllNotificationBean> call=AppointmentDetailsFragment.apiInterface.Insert_Notification(title,message,sender_id,receiver_id,status,created_at);
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

    public void updateDues(int sp_id, double amount_due){

//        String date=getDateTime();

//        System.out.println("Current time => " + c);,

        Call<EarningsDuesBean> call= AppointmentDetailsFragment.apiInterface.UpdateDues(sp_id,amount_due);
        call.enqueue(new Callback<EarningsDuesBean>() {
            @Override
            public void onResponse(Call <EarningsDuesBean> call, Response<EarningsDuesBean> response) {
                Toast.makeText(getContext(), ""+response.body().getResponse(), Toast.LENGTH_SHORT).show();
                if(response.body().getResponse().equals("ok")){
//                    payment_received_function(requestservice_id,user_id,sp_name);
                    MDToast.makeText(getContext(),"Dues Added..",MDToast.LENGTH_SHORT,MDToast.TYPE_INFO).show();
                }else if(response.body().getResponse().equals("exist")){
                    MDToast.makeText(getContext(),"Same Data exists....",MDToast.LENGTH_SHORT,MDToast.TYPE_WARNING).show();
                }
                else if(response.body().getResponse().equals("error")){
                    MDToast.makeText(getContext(),"Something went wrong....",MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();
                }
                else{
                    MDToast.makeText(getContext(),"Something went wrong....",MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();
                }

            }

            @Override
            public void onFailure(Call <EarningsDuesBean> call, Throwable t) {

                MDToast.makeText(getContext(),"Update Failed",MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();

            }
        });
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
