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
import android.support.v4.content.ContextCompat;

import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.RequestService;
import com.vartista.www.vartista.modules.general.Asynctask_MultipleUrl;
import com.vartista.www.vartista.modules.general.HomeActivity;
import com.vartista.www.vartista.modules.general.SiginInActivity;
import com.vartista.www.vartista.modules.general.SignUpActivity;
import com.vartista.www.vartista.modules.general.SplashActivity;
import com.vartista.www.vartista.modules.provider.MyServiceRequests;
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

            resultIntent = new Intent(getApplicationContext(), MyServiceMeetings.class);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        }
        else if(title.contains("Request")){

            resultIntent = new Intent(getApplicationContext(), MyServiceRequests.class);
            resultIntent.putExtra("user", user_id);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        }
        else if(title.contains("Decline")){

            resultIntent = new Intent(getApplicationContext(), Asynctask_MultipleUrl.class);
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





        {


         }



   return builder;
    }
}
