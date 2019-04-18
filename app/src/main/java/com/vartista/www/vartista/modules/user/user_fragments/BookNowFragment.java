package com.vartista.www.vartista.modules.user.user_fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.valdesekamdem.library.mdtoast.MDToast;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.AllNotificationBean;
import com.vartista.www.vartista.beans.CreateRequest;
import com.vartista.www.vartista.beans.NotificationsManager;
import com.vartista.www.vartista.fragments.DatePickerFragment;
import com.vartista.www.vartista.fragments.ServiceProviderFragment;
import com.vartista.www.vartista.fragments.TimePickerFragment;
import com.vartista.www.vartista.modules.general.HomeActivity;
import com.vartista.www.vartista.modules.user.BookNowActivity;
import com.vartista.www.vartista.restcalls.ApiClient;
import com.vartista.www.vartista.restcalls.ApiInterface;
import com.vartista.www.vartista.restcalls.SendNotificationApiInterface;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BookNowFragment extends Fragment  {


    EditText editTextaddress,editTextCity;
    Button buttonBook;
    ImageView imageViewDate,imageViewTime;
    public static ApiInterface apiInterface;
    public static SendNotificationApiInterface sendNotificationApiInterface;
    private FragmentActivity myContext;
    DatePickerDialog.OnDateSetListener ondate;

    RelativeLayout layoutDate,layoutTime;
    TextView textViewReq_Date,textViewReq_Time;
    int user_customer_id,service_provider_id,service_id,service_cat_id;

    TabLayout tabLayout;

    public BookNowFragment() {

    }

    @SuppressLint("ValidFragment")
    public BookNowFragment(int provider_id, int cat_id, int user_id, int service_id, TabLayout tabLayout) {
        this.user_customer_id=user_id;
        this.service_provider_id=provider_id;
        this.service_id=service_id;
        this.service_cat_id=cat_id;
        this.tabLayout= tabLayout;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_book_now,container,false);
        editTextaddress=(EditText)view.findViewById(R.id.address);
        editTextCity=(EditText)view.findViewById(R.id.editTxtcity);
        buttonBook=(Button)view.findViewById(R.id.buttonBook);
        imageViewDate=(ImageView)view.findViewById(R.id.imageViewDate);
        imageViewTime=(ImageView)view.findViewById(R.id.imageViewTime);
        layoutDate=(RelativeLayout)view.findViewById(R.id.layoutDate);
        layoutTime=(RelativeLayout)view.findViewById(R.id.layouttime);
        textViewReq_Date=(TextView)view.findViewById(R.id.textViewReq_Date);
        textViewReq_Time=(TextView)view.findViewById(R.id.textViewReq_time);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        sendNotificationApiInterface = ApiClient.getApiClient().create(SendNotificationApiInterface.class);

        tabLayout.setVisibility(View.GONE);
        Calendar calendar=Calendar.getInstance();
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        int month=calendar.get(Calendar.MONTH);
        int year=calendar.get(Calendar.YEAR);
        layoutDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        layoutTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker=new TimePickerFragment();
                timePicker.show(myContext.getFragmentManager(),"time picker");
            }
        });
        buttonBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = editTextaddress.getText().toString();
                String city = editTextCity.getText().toString();
                String time = textViewReq_Time.getText().toString();
                final String date=textViewReq_Date.getText().toString();

                SharedPreferences ob = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);

                final String name_user = ob.getString("name","");
                final String title = "Vartista- Request";
                final String body = name_user+" sent you request";
                Call<CreateRequest> call = BookNowFragment.apiInterface.createRequest(user_customer_id,
                        service_provider_id,
                        service_id,date,time,address,city,0,service_cat_id);

                call.enqueue(new Callback<CreateRequest>() {

                    @Override
                    public void onResponse(Call<CreateRequest> call, Response<CreateRequest> response) {
                        Toast.makeText(getContext(), ""+response.body().getResponse(), Toast.LENGTH_SHORT).show();

                        if (response.body().getResponse().equals("ok")) {
                            insertNotification(title,body,user_customer_id,service_provider_id,1,date);
                            Toast.makeText(getContext(), ""+response.body().getResponse(), Toast.LENGTH_SHORT).show();
                            Call<NotificationsManager> callNotification = BookNowFragment.sendNotificationApiInterface
                                    .sendPushNotification(service_provider_id,body,title);
                            callNotification.enqueue(new Callback<NotificationsManager>() {

                                @Override
                                public void onResponse(Call<NotificationsManager> call, Response<NotificationsManager> response) {

                                }

                                @Override
                                public void onFailure(Call<NotificationsManager> call, Throwable t) {

                                }
                            });

                            MDToast mdToast = MDToast.makeText(getContext(), "Request has been Sent succesfully.", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
                            mdToast.show();




                            FragmentManager manager = myContext.getSupportFragmentManager();
                            manager.beginTransaction().remove(manager.findFragmentById(R.id.viewpager)).replace(R.id.fragment_frame_layout,
                            new ServiceProviderFragment(user_customer_id)).addToBackStack("TAG").commit();


                        }


                    }

                    @Override
                    public void onFailure(Call<CreateRequest> call, Throwable t) {
                        //
                    }

                });

                editTextaddress.setText("");
                editTextCity.setText("");
                textViewReq_Date.setText("00-00-0000");
                textViewReq_Time.setText("00:00");


            }
        });


         ondate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                        Calendar c=Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,monthOfYear);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = sdf.format(c.getTime());
        textViewReq_Date.setText(currentDate);
            }
        };

        return view;

    }






    public void insertNotification(String title , String message, int sender_id , int receiver_id , int status , String created_at){
//         setUIToWait(true);
        Call<AllNotificationBean> call=BookNowFragment.apiInterface.Insert_Notification(title,message,sender_id,receiver_id,status,created_at);
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

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(myContext.getFragmentManager(), "Date Picker");
    }

//    @Override
//    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//        String amPm="";
//        if(hourOfDay>=12){
//            amPm="PM";
//            hourOfDay=hourOfDay-12;
//        }
//        else{
//            amPm="AM";
//        }
//        textViewReq_Time.setText(String.format("%02d:%02d",hourOfDay,minute)+" "+amPm);
//
//    }
}