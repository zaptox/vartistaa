package com.vartista.www.vartista.modules.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bcgdv.asia.lib.ticktock.TickTockView;
import com.valdesekamdem.library.mdtoast.MDToast;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.NotificationsManager;
import com.vartista.www.vartista.beans.User;
import com.vartista.www.vartista.restcalls.ApiClient;
import com.vartista.www.vartista.restcalls.ApiInterface;
import com.vartista.www.vartista.restcalls.SendNotificationApiInterface;

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
    TextView timer;
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
        startservice = (Button)findViewById(R.id.startbutton);
        endservice = (Button)findViewById(R.id.btPause);
        reset = (Button)findViewById(R.id.btReset);
        sendNotificationApiInterface = ApiClient.getApiClient().create(SendNotificationApiInterface.class);
        handler = new Handler() ;

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