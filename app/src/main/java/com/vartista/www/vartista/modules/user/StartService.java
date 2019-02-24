package com.vartista.www.vartista.modules.user;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bcgdv.asia.lib.ticktock.TickTockView;
import com.squareup.picasso.Picasso;
import com.valdesekamdem.library.mdtoast.MDToast;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.NotificationsManager;
import com.vartista.www.vartista.beans.User;
import com.vartista.www.vartista.modules.provider.ServiceCancelActivity;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StartService extends AppCompatActivity {

    public static ApiInterface apiInterface;
    TextView timer,service_tittle;
    ImageView imgview;
    Button startservice, endservice, reset;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L;
    Handler handler;
    int Hours, Seconds, Minutes, MilliSeconds;
    public static SendNotificationApiInterface sendNotificationApiInterface;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_service);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        timer = (TextView)findViewById(R.id.tvTimer);
        service_tittle = (TextView)findViewById(R.id.Service_tittle);
        imgview = (ImageView)findViewById(R.id.imageView7instartserviceuser);
        startservice = (Button)findViewById(R.id.startbutton);
        endservice = (Button)findViewById(R.id.btPause);
        reset = (Button)findViewById(R.id.btReset);
        int requestservice_id = 114;
        sendNotificationApiInterface = ApiClient.getApiClient().create(SendNotificationApiInterface.class);
        handler = new Handler() ;
            new StartService.Conncetion(StartService.this,requestservice_id).execute();
        startservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences ob =getSharedPreferences("Login", Context.MODE_PRIVATE);
               int userid = ob.getInt("user_id",0);
               final String name="shared";

                final int service_provider_id= 63;
                Call<User> call = StartService.apiInterface.updatebusystatus(userid);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.body().getResponse().equals("ok")) {


                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }

                });
                Call<NotificationsManager> callNotification = StartService.sendNotificationApiInterface
                        .sendPushNotification(service_provider_id,name+" has started the work","Vartista-Busy");
                callNotification.enqueue(new Callback<NotificationsManager>() {

                    @Override
                    public void onResponse(Call<NotificationsManager> call, Response<NotificationsManager> response) {
                        Toast.makeText(StartService.this, "notification send", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<NotificationsManager> call, Throwable t) {

                    }
                });
                StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);

                reset.setEnabled(false);

            }
        });

        endservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimeBuff += MillisecondTime;

                handler.removeCallbacks(runnable);

                reset.setEnabled(true);

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MillisecondTime = 0L ;
                StartTime = 0L ;
                TimeBuff = 0L ;
                UpdateTime = 0L ;
                Seconds = 0 ;
                Minutes = 0 ;
                MilliSeconds = 0 ;

                timer.setText("00:00:00");

            }
        });

    }

    public Runnable runnable = new Runnable() {

        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = TimeBuff + MillisecondTime;

            Seconds = (int) (UpdateTime / 1000);

            Minutes = Seconds / 60;

            Hours = Minutes / 60;

            Seconds = Seconds % 60;


            timer.setText( String.format("%02d", Hours)+ ":"+String.format("%02d", Minutes) + ":" + String.format("%02d", Seconds));


            handler.postDelayed(this, 0);
        }

    };



    class Conncetion extends AsyncTask<String,String ,String > {
        private int requestservice_id;
        private ProgressDialog dialog;

        public Conncetion(Context activity, int requestservice_id) {
            dialog = new ProgressDialog(activity);
            this.requestservice_id = requestservice_id;
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Retriving data Please Wait..");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            int requestservice_id = 114;

            String result = "";

            final String BASE_URL = "http://vartista.com/vartista_app/requestdetails.php?requestservice_id="+requestservice_id;
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
                        String requestservice_id = ser1.getString("requestservice_id");
                        String user_customer_id = ser1.getString("user_customer_id");
                        String service_provider_id = ser1.getString("service_provider_id");
                        String spname = ser1.getString("Spname");
                        String username = ser1.getString("Uname");
                        String service_description = ser1.getString("service_description");
                        String Spimage = ser1.getString("SpImage");
                        String customerimage = ser1.getString("CustomerPic");
                        String location = ser1.getString("location");
                        String request_status = ser1.getString("request_status");
                        String date = ser1.getString("date");
                        String service_title = ser1.getString("service_title");
                        String price = ser1.getString("price");
                        String name = ser1.getString("name");
                        String time = ser1.getString("time");


                        Picasso.get().load(Spimage).fit().centerCrop()
                                .placeholder(R.drawable.profile)
                                .error(R.drawable.profile)
                                .into(imgview);
                        service_tittle.setText("Service : "+service_title);










                    }




                }

                else {
                    Toast.makeText(getApplicationContext(),"no data",Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
















}
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_start_service);
//        mCountUp = (TickTockView) findViewById(R.id.timer);
//        startservice = (Button) findViewById(R.id.Startservice);
//        endservice = (Button) findViewById(R.id.endservice);
//        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
//        if (mCountUp != null) {
//            mCountUp.setOnTickListener(new TickTockView.OnTickListener() {
//                SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
//                Date date = new Date();
//
//                @Override
//                public String getText(long timeRemaining) {
//                    date.setTime(System.currentTimeMillis()/1000);
//                    return format.format(date);
//                }
//            });
//        }
//        startservice.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                final TextView timertext = (TextView) findViewById(R.id.timer);
//
////                Toast.makeText(StartService.this, "The id is "+userid, Toast.LENGTH_SHORT).show();
//
//
//            }
//        });
//
//
//    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//        Calendar end = Calendar.getInstance();
//        end.add(Calendar.MINUTE, 4);
//        end.add(Calendar.SECOND, 5);
//
//        Calendar start = Calendar.getInstance();
//        start.add(Calendar.MINUTE, -1);
//
//
//        Calendar c2= Calendar.getInstance();
//        c2.add(Calendar.HOUR, 2);
//        c2.set(Calendar.MINUTE, 0);
//        c2.set(Calendar.SECOND, 0);
//        c2.set(Calendar.MILLISECOND, 0);
//        if (mCountUp != null) {
//            mCountUp.start(c2);
//        }
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//
//        mCountUp.stop();
//    }
//}