package com.vartista.www.vartista.notifications.NotificationCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.vartista.www.vartista.R;
import com.vartista.www.vartista.modules.provider.MyServiceRequests;
import com.vartista.www.vartista.modules.provider.ServiceCancelActivity;
import com.vartista.www.vartista.modules.user.Service_user_cancel;

import java.util.Random;

import static com.vartista.www.vartista.adapters.MyRequestsServicesListAdapter.REQUEST_CODE_SP;
import static com.vartista.www.vartista.adapters.MyRequestsServicesListAdapter.REQUEST_CODE_SP_BEFORE2H;
import static com.vartista.www.vartista.firebaseconfig.FirebaseMsgService.REQEUST_CODE_FOR_USER;
import static com.vartista.www.vartista.firebaseconfig.FirebaseMsgService.REQEUST_CODE_FOR_USER_BEFORE2H;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm Triggered", Toast.LENGTH_SHORT).show();
        String name = intent.getStringExtra("username");
        int request_code = intent.getIntExtra("requestcode",0);
        int service_id = intent.getIntExtra("service_id",0);


        if(request_code==REQUEST_CODE_SP) {
            Intent notificationIntent = new Intent(context,MyServiceRequests.class);

            TaskStackBuilder stackBuilder  = TaskStackBuilder.create(context);
            stackBuilder.addParentStack(MyServiceRequests.class);
            stackBuilder.addNextIntent(notificationIntent);
            PendingIntent pendingIntent = stackBuilder.getPendingIntent(request_code, PendingIntent.FLAG_UPDATE_CURRENT);
            createNotification(context, "Vartista", "After 1 hours you have to visit "+name, pendingIntent);
        }else if(request_code==REQEUST_CODE_FOR_USER){
            Intent notificationIntent = new Intent(context,MyServiceRequests.class);

            TaskStackBuilder stackBuilder  = TaskStackBuilder.create(context);
            stackBuilder.addParentStack(MyServiceRequests.class);
            stackBuilder.addNextIntent(notificationIntent);
            PendingIntent pendingIntent = stackBuilder.getPendingIntent(request_code, PendingIntent.FLAG_UPDATE_CURRENT);

            createNotification(context, "Vartista", "After 3 hours you have to visit "+name, pendingIntent);
        }
        else if(request_code==REQUEST_CODE_SP_BEFORE2H){
            Intent notificationIntent = new Intent(context,ServiceCancelActivity.class);
            notificationIntent.putExtra("service_id",service_id);
            TaskStackBuilder stackBuilder  = TaskStackBuilder.create(context);
            stackBuilder.addParentStack(ServiceCancelActivity.class);
            stackBuilder.addNextIntent(notificationIntent);
            PendingIntent pendingIntent = stackBuilder.getPendingIntent(request_code, PendingIntent.FLAG_UPDATE_CURRENT);
            createNotification(context, "Vartista", "2 HOURS left do you want to go or cancel ? "+name, pendingIntent);
        }
        else if(request_code==REQEUST_CODE_FOR_USER_BEFORE2H){
            Intent notificationIntent = new Intent(context,Service_user_cancel.class);
            notificationIntent.putExtra("service_id",service_id);
            TaskStackBuilder stackBuilder  = TaskStackBuilder.create(context);
            stackBuilder.addParentStack(Service_user_cancel.class);
            stackBuilder.addNextIntent(notificationIntent);
            PendingIntent pendingIntent = stackBuilder.getPendingIntent(request_code, PendingIntent.FLAG_UPDATE_CURRENT);
            createNotification(context, "Vartista", "Provider will come after 2 hour wanna cancel?"+name, pendingIntent);
        }
    }

    public void createNotification(Context context,CharSequence title,CharSequence body,PendingIntent pendingIntent ){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
//
        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setSmallIcon(R.drawable.logoforsplash)
                .setContentTitle(title)
                .setContentText(body)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                .setContentInfo("info")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent).build();

//                  Notification n = builder.build();
//        n.vibrate = new long[]{150, 300, 150, 400};
//
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.notify( new Random().nextInt(),builder.build());
        try {

            Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone toque = RingtoneManager.getRingtone(context, som);
            toque.play();
        } catch (Exception e) {
                   e.printStackTrace();
        }
    }
}
