package com.vartista.www.vartista.firebaseconfig;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.valdesekamdem.library.mdtoast.MDToast;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.RequestService;
import com.vartista.www.vartista.beans.User;
import com.vartista.www.vartista.modules.general.Asynctask_MultipleUrl;
import com.vartista.www.vartista.modules.general.HomeActivity;
import com.vartista.www.vartista.modules.general.SiginInActivity;
import com.vartista.www.vartista.modules.general.SignUpActivity;
import com.vartista.www.vartista.modules.general.SplashActivity;
import com.vartista.www.vartista.modules.provider.MyServiceRequests;
import com.vartista.www.vartista.modules.provider.RequestAlertActivity;
import com.vartista.www.vartista.modules.provider.ServicestartProvider;
import com.vartista.www.vartista.modules.user.MyServiceMeetings;

public class NotificationHelper extends ContextWrapper {
    public static  final String CHANNEL_ID="com.zaptoxlbgfg.xffffonflppppppkp[pdfdixzvsgdsvsddsvdfss";
    private  static  final String CHANNEL_NAME="Zaptox fjsdeeNolglc;dfkkklmgswbdfbdfvkfkb,ftifcfdfdfation";
    private NotificationManager manager;
    
    public NotificationHelper(Context base) {
        super(base);
        createChannel();
    }

    private void createChannel() {
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O) {

            NotificationChannel notificationChannel=new NotificationChannel(CHANNEL_ID,CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.enableLights(true);
        notificationChannel.enableVibration(true);
        notificationChannel.setLightColor(Color.GREEN);
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        notificationChannel.setShowBadge(true);
        getManager().createNotificationChannel(notificationChannel);}

    }

    public NotificationManager getManager() {
        if(manager==null){
            manager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        }
        return manager;
    }


    public Notification.Builder getChannelNotiifcation(String title, String body){
        Notification.Builder builder=null;

        Intent resultIntent = null;

        SharedPreferences obj = getSharedPreferences("Login", Context.MODE_PRIVATE);

        int user_id = obj.getInt("user_id", 0);

        if(title.contains("Accept")){
//           int R_S_ID = Integer.parseInt(rservice_id);
//                if (!date.equals("")){
//                    sendCompactNotification(this,REQEUST_CODE_FOR_USER,date,time,name_user,"minute",-2,0);
//                    sendCompactNotification(this,REQEUST_CODE_FOR_USER_BEFORE2H,date,time,name_user,"minute",-2,R_S_ID );
//
//                }

//                resultIntent = new Intent(getApplicationContext(), MyServiceMeetings.class);

            resultIntent = new Intent(getApplicationContext(), HomeActivity.class);
            User user= HomeActivity.user;
            resultIntent.putExtra("user",user);

            String[] for_body= body.split("_");

            body= for_body[0];


            resultIntent.putExtra("fragment","MyServiceMeetingsFragment");
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        }
//            else if(title.contains("Request")){
//
//                resultIntent = new Intent(getApplicationContext(), MyServiceRequests.class);
//                resultIntent.putExtra("user", user_id);
//                resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                Intent dialogIntent = new Intent(this, RequestAlertActivity.class);
////                dialogIntent.putExtra("user",user_id);
////                dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                startActivity(dialogIntent);
//            }

        else if(title.contains("Request")){



            String requestUserId = title.substring(title.indexOf("user-id=")+new String("user-id=").length()+1,title.indexOf("?s"));
            String service_provider_id = title.substring(title.indexOf("servp-id=")+new String("servp-id=").length(),title.indexOf("?s",title.indexOf("servp-id=")));
            String  service_id = title.substring(title.indexOf("serv-id=")+new String("serv-id=").length(),title.indexOf("?r",title.indexOf("serv-id=")));
            String req_serv_id = title.substring(title.indexOf("req-serv-id=")+new String("req-serv-id=").length());

//                resultIntent = new Intent(getApplicationContext(), MyServiceRequests.class);

            resultIntent = new Intent(getApplicationContext(), HomeActivity.class);
            User user= HomeActivity.user;
            resultIntent.putExtra("user",user);


            String[] for_title= title.split("\\?");
            title= for_title[0];

            Handler handler = new Handler(Looper.getMainLooper());
            final String finalTitle = title;

            handler.post(new Runnable() {
                public void run() {
                    Toast.makeText(getApplicationContext(),"ye hai"+ finalTitle,Toast.LENGTH_SHORT).show();
                }
            });


            resultIntent.putExtra("fragment","MyServiceRequestsFragment");
            resultIntent.putExtra("user", user_id);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Intent dialogIntent = new Intent(this, RequestAlertActivity.class);
            dialogIntent.putExtra("user",user_id);
            dialogIntent.putExtra("reqUserId",requestUserId);
            dialogIntent.putExtra("serv_prv_Id",service_provider_id);
            dialogIntent.putExtra("serv_Id",service_id);
            dialogIntent.putExtra("req_serv_id",req_serv_id);
            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(dialogIntent);
        }


        else if (title.contains("Congratulations")){

//                resultIntent = new Intent(getApplicationContext(), SiginInActivity.class);

            User user= HomeActivity.user;
            user.setSp_status("1");
            SharedPreferences sharedPreferencespre =getSharedPreferences("Login", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferencespre.edit();
            editor.putString("sp_status","1");

            resultIntent = new Intent(getApplicationContext(), HomeActivity.class);

            resultIntent.putExtra("fragment","ServiceProviderFragment");
            resultIntent.putExtra("user", user);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                SharedPreferences ob1 = getSharedPreferences("Login", Context.MODE_PRIVATE);
                ob1.edit().clear().commit();

                MDToast.makeText(getApplicationContext(), "Login Again to Start your journey at Vartista", MDToast.LENGTH_LONG, MDToast.TYPE_INFO).show();
            Intent dialogIntent = new Intent(this, SiginInActivity.class);
            startActivity(dialogIntent);
        }

        else if(title.contains("Alert")){
            User user= HomeActivity.user;
            user.setSp_status("0");
            SharedPreferences sharedPreferencespre =getSharedPreferences("Login", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferencespre.edit();
            editor.putString("sp_status","1");
//                HomeActivity.changed_from_notif="1";

            resultIntent = new Intent(getApplicationContext(), HomeActivity.class);
            user.setSp_status("1");

            resultIntent.putExtra("fragment","Removed");
            resultIntent.putExtra("user", user);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        }

        else if(title.contains("Decline")){

//                resultIntent = new Intent(getApplicationContext(), Asynctask_MultipleUrl.class);

            resultIntent = new Intent(getApplicationContext(), HomeActivity.class);
            User user= HomeActivity.user;
            resultIntent.putExtra("user",user);

            resultIntent.putExtra("fragment","NotificationsFragment");
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        }


        else if(title.contains("Document")){

//                resultIntent = new Intent(getApplicationContext(), UploadDocListActivity.class);

            resultIntent = new Intent(getApplicationContext(), HomeActivity.class);
            User user= HomeActivity.user;
            resultIntent.putExtra("user",user);

            resultIntent.putExtra("fragment","UploadDocListFragment");
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        }


        else if(title.contains("Busy")){

            resultIntent = new Intent(getApplicationContext(), ServicestartProvider.class);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        }
        else if(title.contains("Cash")){

//                resultIntent = new Intent(getApplicationContext(), Asynctask_MultipleUrl.class);

            resultIntent = new Intent(getApplicationContext(), HomeActivity.class);
            User user= HomeActivity.user;
            resultIntent.putExtra("user",user);

            resultIntent.putExtra("fragment","AppointmentDetailsFragment");
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        }
        else if(title.contains("rate")){

//                resultIntent = new Intent(getApplicationContext(), MyCompletedServices.class);

            resultIntent = new Intent(getApplicationContext(), HomeActivity.class);
            User user= HomeActivity.user;
            resultIntent.putExtra("user",user);

            resultIntent.putExtra("fragment","MyCompletedServicesFragment");
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        }
        else if(title.contains("cancelled")){
//                resultIntent = new Intent(getApplicationContext(), Asynctask_MultipleUrl.class);

            resultIntent = new Intent(getApplicationContext(), HomeActivity.class);
            User user= HomeActivity.user;
            resultIntent.putExtra("user",user);

            resultIntent.putExtra("fragment","NotificationsFragment");
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        }

        else {

//                resultIntent = new Intent(getApplicationContext(), Asynctask_MultipleUrl.class);

            resultIntent = new Intent(getApplicationContext(), HomeActivity.class);
            User user= HomeActivity.user;
            resultIntent.putExtra("user",user);

            resultIntent.putExtra("fragment","NotificationsFragment");
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }


        PendingIntent resultPendingIntent = PendingIntent.getActivity(getApplicationContext(),
                0 /* Request code */, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

         if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O) {


             builder= new Notification.Builder(getApplicationContext(), CHANNEL_ID)
                    .setContentText(body)
                    .setContentTitle(title)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(resultPendingIntent)
                    .setShowWhen(true)
                    .setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark))
                    .setColorized(true)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))

                    .setAutoCancel(true);
        }







   return builder;
    }
}
