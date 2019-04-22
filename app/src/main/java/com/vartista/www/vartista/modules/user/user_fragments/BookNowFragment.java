package com.vartista.www.vartista.modules.user.user_fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
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

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
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
    TimePickerDialog.OnTimeSetListener onTime;

    RelativeLayout layoutDate,layoutTime;
    TextView textViewReq_Date,textViewReq_Time;
    int user_customer_id,service_provider_id,service_id,service_cat_id;
    TimePickerDialog timePickerDialog;


    public BookNowFragment() {

    }

    @SuppressLint("ValidFragment")
    public BookNowFragment(int provider_id, int cat_id, int user_id, int service_id) {
        this.user_customer_id=user_id;
        this.service_provider_id=provider_id;
        this.service_id=service_id;
        this.service_cat_id=cat_id;
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

        Calendar calendar1=Calendar.getInstance();
        final int hour=calendar.get(Calendar.HOUR);
        final int minute=calendar.get(Calendar.MINUTE);

        layoutTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             timePickerDialog=new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                 @Override
                 public void onTimeSet(TimePicker timePicker, int hourOfDay, int min) {
                     String amPm="";
                     if(hourOfDay>=12){
                         amPm="PM";
                         hourOfDay=hourOfDay-12;
                     }
                     else{
                         amPm="AM";
                     }
                     textViewReq_Time.setText(String.format("%02d:%02d",hourOfDay,min)+" "+amPm);


                 }
             },hour,minute,false);
      timePickerDialog.show();
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
                final int user_id = ob.getInt("user_id", 0);

                final String name_user = ob.getString("name","");
//             final String title = "Vartista- Request";
                final String title = "Vartista- Request?user-id=?" + user_id + "?servp-id=" + service_provider_id + "?serv-id=" + service_id;

                final String body = name_user+" sent you request";

                Call<CreateRequest> call = BookNowFragment.apiInterface.createRequest(user_customer_id,
                        service_provider_id,
                        service_id,date,time,address,city,0,service_cat_id);

                call.enqueue(new Callback<CreateRequest>() {

                    @Override
                    public void onResponse(Call<CreateRequest> call, Response<CreateRequest> response) {
//                     Toast.makeText(BookNowActivity.this, "in qnque"+response.body().getResponse(), Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getContext(), ""+response.body().getResponse(), Toast.LENGTH_SHORT).show();

                        if (response.body().getResponse().equals("ok")) {
//                         insertNotification(title,body,user_customer_id,service_provider_id,1,date);
//                         Toast.makeText(BookNowActivity.this, ""+response.body().getResponse(), Toast.LENGTH_SHORT).show();
//                         Call<NotificationsManager> callNotification = BookNowActivity.sendNotificationApiInterface
//                                 .sendPushNotification(service_provider_id,body,title);
//                         callNotification.enqueue(new Callback<NotificationsManager>() {
//
//                             @Override
//                             public void onResponse(Call<NotificationsManager> call, Response<NotificationsManager> response) {
//
//                             }
//
//                             @Override
//                             public void onFailure(Call<NotificationsManager> call, Throwable t) {
//
//                             }
//                         });
//
//                         MDToast mdToast = MDToast.makeText(getApplicationContext(), "Request has been Sent succesfully.", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
//                         mdToast.show();
//
//                          Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
//                         intent.putExtra("user", HomeActivity.user);
//
//                         startActivity(intent);
//
//


                            getRequestServId(user_customer_id,service_provider_id,service_id,title,body);



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


    private void showTimePicker() {
        TimePickerFragment date = new TimePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        /**
         * Set Call back to capture selected date
         */

        Bundle args = new Bundle();
        args.putInt("hour", hour);
        args.putInt("minute", minute);
        date.setArguments(args);
        date.setCallBack(onTime);

        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(onTime);
        date.show(myContext.getFragmentManager(), "Time Picker");
    }

//    private void showTmePicker() {
//        TimePickerFragment time = new TimePickerFragment();
//        /**
//         * Set Up Current Date Into dialog
//         */
//        Calendar calender = Calendar.getInstance();
//        Bundle args = new Bundle();
//        args.putInt("year", calender.get(Calendar.YEAR));
//        args.putInt("month", calender.get(Calendar.MONTH));
//        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
//        /**
//         * Set Call back to capture selected date
//         */
//        time.show(myContext.getFragmentManager(), "Date Picker");
//    }


    public void getRequestServId(int user_customer_id, final int service_provider_id, int service_id, final String title, final String body) {

        int req_ser_id = 0;
        new AsyncTask<String, Void, String>() {

            String result = "";

            @Override
            protected String doInBackground(String... strings) {
                try {
                    HttpClient client = new DefaultHttpClient();
                    HttpGet request = new HttpGet();

                    String user_id = strings[0];
                    String servprv_id = strings[1];
                    String serv_id = strings[2];

                    final String BASE_URL = "http://vartista.com/vartista_app/get_request_service_id.php?serv_prv_id="+servprv_id+"&serv_id="+serv_id+"&usr_cust_id="+user_id;
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
                    return new String("There is exception" + e.getMessage());
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {

                int request_serv_id = 0;
                try {
                    JSONObject jsonResult = new JSONObject(result);
                    int success = jsonResult.getInt("success");
                    if (success == 1) {
                        request_serv_id = jsonResult.getInt("req_ser_id");
                    }
                    String titleWithRequsetServId = title + "?req-serv-id=" + request_serv_id;
                    Call<NotificationsManager> callNotification = BookNowFragment.sendNotificationApiInterface
                            .sendPushNotification(service_provider_id, body,titleWithRequsetServId);
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

                    Intent intent = new Intent(getContext(), HomeActivity.class);
                    intent.putExtra("user", HomeActivity.user);

                    startActivity(intent);


                } catch (JSONException e) {

                }


            }
        }.execute(""+user_customer_id, ""+service_provider_id,""+service_id);
    }


}