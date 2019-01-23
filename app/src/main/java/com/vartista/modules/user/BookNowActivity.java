package com.vartista.www.vartista.modules.user;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.valdesekamdem.library.mdtoast.MDToast;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.CreateRequest;
import com.vartista.www.vartista.beans.NotificationsManager;
import com.vartista.www.vartista.fragments.DatePickerFragment;
import com.vartista.www.vartista.fragments.TimePickerFragment;
import com.vartista.www.vartista.modules.general.HomeActivity;
import com.vartista.www.vartista.restcalls.ApiClient;
import com.vartista.www.vartista.restcalls.ApiInterface;
import com.vartista.www.vartista.restcalls.SendNotificationApiInterface;

import java.text.DateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookNowActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener {

    EditText editTextaddress,editTextCity;
    Button buttonBook;
    ImageView imageViewDate,imageViewTime;
    public static ApiInterface apiInterface;
    public static SendNotificationApiInterface sendNotificationApiInterface;

    RelativeLayout layoutDate,layoutTime;
    TextView textViewReq_Date,textViewReq_Time;
    int user_customer_id,service_provider_id,service_id,service_cat_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_now);
        editTextaddress=(EditText)findViewById(R.id.address);
        editTextCity=(EditText)findViewById(R.id.editTxtcity);
        buttonBook=(Button)findViewById(R.id.buttonBook);
        imageViewDate=(ImageView)findViewById(R.id.imageViewDate);
        imageViewTime=(ImageView)findViewById(R.id.imageViewTime);
        layoutDate=(RelativeLayout)findViewById(R.id.layoutDate);
        layoutTime=(RelativeLayout)findViewById(R.id.layouttime);
        textViewReq_Date=(TextView)findViewById(R.id.textViewReq_Date);
        textViewReq_Time=(TextView)findViewById(R.id.textViewReq_time);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        sendNotificationApiInterface = ApiClient.getApiClient().create(SendNotificationApiInterface.class);
        Intent intent=getIntent();
        user_customer_id=intent.getIntExtra("user_id",0);
        service_provider_id=intent.getIntExtra("provider_id",0);
        service_id=intent.getIntExtra("service_id",0);
        service_cat_id=intent.getIntExtra("cat_id",0);
        Calendar calendar=Calendar.getInstance();
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        int month=calendar.get(Calendar.MONTH);
        int year=calendar.get(Calendar.YEAR);
        layoutDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker=new DatePickerFragment();
                datePicker.show(getFragmentManager(),"date picker");
            }
        });
        layoutTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker=new TimePickerFragment();
                timePicker.show(getFragmentManager(),"time picker");
            }
        });

     buttonBook.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             String address = editTextaddress.getText().toString();
             String city = editTextCity.getText().toString();
             String time = textViewReq_Time.getText().toString();
             String date=textViewReq_Date.getText().toString();

             SharedPreferences ob = getSharedPreferences("Login", Context.MODE_PRIVATE);

             final String name_user = ob.getString("name","");

             Call<CreateRequest> call = BookNowActivity.apiInterface.createRequest(user_customer_id,
                     service_provider_id,
                     service_id,date,time,address,city,0,service_cat_id);

             call.enqueue(new Callback<CreateRequest>() {
                 @Override
                 public void onResponse(Call<CreateRequest> call, Response<CreateRequest> response) {
                     if (response.body().getResponse().equals("ok")) {
                         Call<NotificationsManager> callNotification = BookNowActivity.sendNotificationApiInterface
                                 .sendPushNotification(service_provider_id,name_user+" Sent you request","Vartista-Request");
                         callNotification.enqueue(new Callback<NotificationsManager>() {

                             @Override
                             public void onResponse(Call<NotificationsManager> call, Response<NotificationsManager> response) {

                             }

                             @Override
                             public void onFailure(Call<NotificationsManager> call, Throwable t) {

                             }
                         });



                         MDToast mdToast = MDToast.makeText(getApplicationContext(), "Request has been Sent succesfully.", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
                         mdToast.show();

                          Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
                         intent.putExtra("user", HomeActivity.user);

                         startActivity(intent);

                     }


                 }

                 @Override
                 public void onFailure(Call<CreateRequest> call, Throwable t) {
                   //
                 }

             });

             editTextaddress.setText("");
             editTextCity.setText("");
             textViewReq_Date.setText("00/00/0000");
             textViewReq_Time.setText("00:00");
//             Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
//             intent.putExtra("userId",user_customer_id);
//             startActivity(intent);




         }
     });


    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c=Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        String currentDate= DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        textViewReq_Date.setText(currentDate);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        textViewReq_Time.setText(hourOfDay+":"+minute);
    }
}