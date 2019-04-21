package com.vartista.www.vartista.modules.provider;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bcgdv.asia.lib.ticktock.TickTockView;
import com.squareup.picasso.Picasso;
import com.valdesekamdem.library.mdtoast.MDToast;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.adapters.MyRequestsServicesListAdapter;
import com.vartista.www.vartista.beans.AllNotificationBean;
import com.vartista.www.vartista.beans.CreateRequest;
import com.vartista.www.vartista.beans.NotificationsManager;
import com.vartista.www.vartista.beans.ServiceRequets;
import com.vartista.www.vartista.modules.general.HomeActivity;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.vartista.www.vartista.adapters.MyRequestsServicesListAdapter.REQUEST_CODE_SP;
import static com.vartista.www.vartista.adapters.MyRequestsServicesListAdapter.REQUEST_CODE_SP_BEFORE2H;
import static com.vartista.www.vartista.modules.general.HomeActivity.user_id;

public class RequestAlertActivity extends AppCompatActivity {

    TextView  txtUserName, TxtUserNameAddress, txtUserReqServ;
    ImageView reqUserImage;
    Button btnAccept, btnReject;
    TickTockView mCountDown;
    public static ApiInterface apiInterface;
    public static SendNotificationApiInterface sendNotificationApiInterface;
    String name_user;
    String service_Id = "";
    String serv_provider_id = "";
    String reqUserId = "";
    String req_serv_id = "";
    int IntService_Id ;
    int IntServ_provider_id ;
    int IntReqUserId ;
    int IntReq_serv_id ;


    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_alert);
        txtUserName = findViewById(R.id.userName);
        TxtUserNameAddress = findViewById(R.id.userlocation);
        txtUserReqServ = findViewById(R.id.user_reqserv);
        reqUserImage = findViewById(R.id.reqUserIdImage);
        btnAccept =findViewById(R.id.button_paynow);
        btnReject = findViewById(R.id.button_reject);
        Intent I = getIntent();
        reqUserId = I.getStringExtra("reqUserId");
        serv_provider_id = I.getStringExtra("serv_prv_Id");
        service_Id = I.getStringExtra("serv_Id");
        req_serv_id = I.getStringExtra("req_serv_id");
        apiInterface= ApiClient.getApiClient().create(ApiInterface.class);
        sendNotificationApiInterface = ApiClient.getApiClient().create(SendNotificationApiInterface.class);


        IntReq_serv_id = Integer.parseInt(req_serv_id);
        IntReqUserId = Integer.parseInt(reqUserId);
        IntServ_provider_id = Integer.parseInt(serv_provider_id);
        IntService_Id = Integer.parseInt(service_Id);
        new AsyncTask<String, Void, String>() {

            String result = "" ;

            @Override
            protected String doInBackground(String... strings) {
                try {
                    HttpClient client = new DefaultHttpClient();
                    HttpGet request = new HttpGet();

                    String user_id = strings[0];
                    String servprv_id = strings[1];
                    String serv_id = strings[2];

                    final String BASE_URL = "http://vartista.com/vartista_app/get_user_and_reqinfo.php?id="+user_id+"&serv_prv_Id="+servprv_id+"&serv_Id="+serv_id;
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

                try {
                    JSONObject jsonResult = new JSONObject(result);
                    String success = jsonResult.getString("response");

                    if (success.equals("ok")) {
                        String username = jsonResult.getString("name");
                        if(username != null){
                            txtUserName.setText("Name: "+ username);
                        }

                        String I = jsonResult.getString("image");
                        if(I != null || I.length()>0){
                            Picasso.get().load(I).fit().centerCrop()
                                    .placeholder(R.drawable.person)
                                    .error(R.drawable.person)
                                    .into(reqUserImage);
                        }
                        String location = jsonResult.getString("location");
                        String city = jsonResult.getString("city");
                        String requested_serv_title = jsonResult.getString("service_title");

                        TxtUserNameAddress.setText("Address: "+location);
//                        txtUserReqServ.setText(city);
                        txtUserReqServ.setText("Service :"+requested_serv_title);
                    }

                }

                catch (JSONException e){

                }


            }
        }.execute(reqUserId, serv_provider_id, service_Id);

        mCountDown = (TickTockView) findViewById(R.id.view_ticktock_countdown);
        if (mCountDown != null) {
            mCountDown.setOnTickListener(new TickTockView.OnTickListener() {
                @SuppressLint("DefaultLocale")
                @Override
                public String getText(long timeRemaining) {
                    int seconds = (int) (timeRemaining / 1000) % 60;
                    int minutes = (int) ((timeRemaining / (1000 * 60)) % 60);
                    int hours = (int) ((timeRemaining / (1000 * 60 * 60)) % 24);
                    int days = (int) (timeRemaining / (1000 * 60 * 60 * 24));
                    boolean hasDays = days > 0;
                    return String.format("%2$02d%5$s %3$02d%6$s",
                            hasDays ? days : hours,
                            hasDays ? hours : minutes,
                            hasDays ? minutes : seconds,
                            hasDays ? "d" : "h",
                            hasDays ? "h" : "m",
                            hasDays ? "m" : "s");
                }
            });
        }
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertreviewnil(IntReqUserId,IntServ_provider_id,IntService_Id);
                int status = 1;
                final int requestservice_id = IntReq_serv_id;
                SharedPreferences ob = getSharedPreferences("Login", Context.MODE_PRIVATE);
                final int user_id = ob.getInt("user_id",0);
                name_user= ob.getString("name","");
                Call<ServiceRequets> call = RequestAlertActivity.apiInterface.updateOnClickRequests(status,requestservice_id);
                call.enqueue(new Callback<ServiceRequets>() {
                    @Override
                    public void onResponse(Call<ServiceRequets> call, Response<ServiceRequets> response) {


                        if(response.body().getResponse().equals("ok")){
                            String date = "";
                            String time = "";
                            final String body = name_user+ " has accepted  your request_"+date+"_"+time+"_"+requestservice_id;
                            final String title = "Vartista-Accept";

                            insertNotification(title,body,user_id,IntReqUserId,1,get_Current_Date());
                            Call<NotificationsManager> callNotification = RequestAlertActivity.sendNotificationApiInterface
                                    .sendPushNotification(IntReqUserId,
                                            body,title);
                            callNotification.enqueue(new Callback<NotificationsManager>() {
                                @Override
                                public void onResponse(Call<NotificationsManager> call, Response<NotificationsManager> response) {
                                    if(response.isSuccessful()){}

                                    if(response.body().getResponse().equals("ok")){
                                        MDToast.makeText(RequestAlertActivity.this, "Request Accepted", Toast.LENGTH_SHORT).show();

                                    }

//                                    Intent intent=new Intent(RequestAlertActivity.this,HomeActivity.class);
//                                    intent.putExtra("user", HomeActivity.user);
//                                    startActivity(intent);

                                }


                                @Override
                                public void onFailure(Call<NotificationsManager> call, Throwable t) {

                                }
                            });
                            String timeformat = "hour";
                            int timevalue = -2;
//                            sendCompactNotification(RequestAlertActivity.this,REQUEST_CODE_SP_BEFORE2H,date,time,name_user,"minute",-1,requestservice_id);
//                            sendCompactNotification(RequestAlertActivity.this,REQUEST_CODE_SP,date,time,name_user,"minute",-30,requestservice_id);

                        }

                        else if(response.body().getResponse().equals("error")){

                            MDToast.makeText(RequestAlertActivity.this,"Something went wrong....",Toast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();

                        }
                        else{
                            MDToast.makeText(RequestAlertActivity.this,"Something went wrong....",Toast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<ServiceRequets> call, Throwable t) {
                        MDToast.makeText(RequestAlertActivity.this,"Something went wrong....",Toast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();
                    }
                });
                MDToast.makeText(RequestAlertActivity.this, "Request Accepted", Toast.LENGTH_SHORT).show();
                finish();
            }

        });
        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int status = -1;
                int customer_id = IntReqUserId;
                SharedPreferences ob = getSharedPreferences("Login", Context.MODE_PRIVATE);
                final int user_id = ob.getInt("user_id",0);
                name_user= ob.getString("name","");

                Call<ServiceRequets> call = RequestAlertActivity.apiInterface.updateOnClickRequests(status, IntReq_serv_id);
                call.enqueue(new Callback<ServiceRequets>() {
                    @Override
                    public void onResponse(Call<ServiceRequets> call, Response<ServiceRequets> response) {
                        if (response.body().getResponse().equals("ok")) {

                            final String body = name_user + " has Declined your request";
                            final String title = "Vartista- Decline";
                            insertNotification(title, body, user_id, IntReqUserId, 1, get_Current_Date());
                            Call<NotificationsManager> callNotification = RequestAlertActivity.sendNotificationApiInterface
                                    .sendPushNotification(IntReqUserId,
                                            body, title);
                            callNotification.enqueue(new Callback<NotificationsManager>() {
                                @Override
                                public void onResponse(Call<NotificationsManager> call, Response<NotificationsManager> response) {
                                    if (response.isSuccessful()) {
                                    }
                                    finish();
                                }


                                @Override
                                public void onFailure(Call<NotificationsManager> call, Throwable t) {

                                }
                            });


                        } else if (response.body().getResponse().equals("error")) {

                            MDToast.makeText(RequestAlertActivity.this, "Something went wrong....", Toast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();

                        } else {
                            MDToast.makeText(RequestAlertActivity.this, "Something went wrong....", Toast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<ServiceRequets> call, Throwable t) {
                        MDToast.makeText(RequestAlertActivity.this, "Update Failed", Toast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();

                    }
                });
                MDToast.makeText(RequestAlertActivity.this, "Request Declined", Toast.LENGTH_SHORT, MDToast.TYPE_WARNING).show();

                finish();

            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();
        Calendar end = Calendar.getInstance();
        end.add(Calendar.MINUTE, 1);
        end.add(Calendar.SECOND, 1);

        Calendar start = Calendar.getInstance();
        start.add(Calendar.MINUTE, -1);
        if (mCountDown != null) {
            mCountDown.start(start, end);
        }

        Calendar c2= Calendar.getInstance();
        c2.add(Calendar.HOUR, 2);
        c2.set(Calendar.MINUTE, 0);
        c2.set(Calendar.SECOND, 0);
        c2.set(Calendar.MILLISECOND, 0);

    }

    @Override
    protected void onStop() {
        super.onStop();
        mCountDown.stop();

    }
    public static void sendCompactNotification(Context context , int requestcode , String date , String time,String name,String timeformat,int timevalue,int Reqeustserviceid){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
//              PendingIntent pendingIntent;
        String appointmentdate = date+" "+time;
        SimpleDateFormat showsdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date date1 = null;
        try {
            date1 = showsdf.parse(appointmentdate);
        } catch (ParseException e) {
            Log.d("Date Parsing",""+e.getMessage());
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        if (timeformat.equals("hour")){
            calendar.add(Calendar.HOUR,timevalue);
        }
        else{
            calendar.add(Calendar.MINUTE,timevalue);

        }
        Intent intent = new Intent("alarm");
        intent.putExtra("username",name);
        intent.putExtra("requestcode",requestcode);
        intent.putExtra("service_id",Reqeustserviceid);
//              if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                  pendingIntent = PendingIntent.getForegroundService(context, 0, intent, 0);
//              }else {
//                  pendingIntent = PendingIntent.getService(context, 0, intent, 0);
//              }
        PendingIntent broadcast = PendingIntent.getBroadcast(context,requestcode,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),broadcast);

    }

    public String get_Current_Date(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = sdf.getDateTimeInstance().format(new Date());
        return currentDate;
    }

    public  void insertNotification(String title , String message, int sender_id , int receiver_id , int status , String created_at){
//         setUIToWait(true);
        Call<AllNotificationBean> call=RequestAlertActivity.apiInterface.Insert_Notification(title,message,sender_id,receiver_id,status,created_at);
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
    public void insertreviewnil(int user_id,int service_p_id,int service_id){
        String servicetittle = "";
        String Remarks = "";
        String time = "";
        String date = "";
        Call<CreateRequest> call2 = RequestAlertActivity.apiInterface.InsertRatings(0,0.0,user_id,service_p_id,service_id,Remarks,date,time);

        call2.enqueue(new Callback<CreateRequest>() {
            @Override
            public void onResponse(Call<CreateRequest> call, Response<CreateRequest> response) {
                if (response.body().getResponse().equals("ok")) {

//                    MDToast mdToast = MDToast.makeText(RequestAlertActivity.this, "Your Ratings are inserted", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
//                    mdToast.show();


                }


            }

            @Override
            public void onFailure(Call<CreateRequest> call, Throwable t) {
                //
                // Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });


    }
}
