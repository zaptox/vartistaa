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
//import com.vartista.www.vartista.Main2Activity;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.modules.provider.MyServiceRequests;
import static com.vartista.www.vartista.adapters.MyRequestsServicesListAdapter.REQUEST_CODE_SP;
public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm Triggered", Toast.LENGTH_SHORT).show();
        String name = intent.getStringExtra("username");
        int request_code = intent.getIntExtra("requestcode",0);
        Intent notificationIntent = new Intent(context,MyServiceRequests.class);
        TaskStackBuilder stackBuilder  = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MyServiceRequests.class);
        stackBuilder.addNextIntent(notificationIntent);

        if(request_code==REQUEST_CODE_SP) {
            PendingIntent pendingIntent = stackBuilder.getPendingIntent(REQUEST_CODE_SP, PendingIntent.FLAG_UPDATE_CURRENT);

            createNotification(context, "Vartista", "After 3 hours you have to visit "+name, pendingIntent);
        }
    }

    public void createNotification(Context context,CharSequence title,CharSequence body,PendingIntent pendingIntent ){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
//
        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                .setContentInfo("info")
                .setContentIntent(pendingIntent).build();

//                  Notification n = builder.build();
//        n.vibrate = new long[]{150, 300, 150, 400};
//
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.notify( 0,builder.build());
        try {

            Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone toque = RingtoneManager.getRingtone(context, som);
            toque.play();
        } catch (Exception e) {
                   e.printStackTrace();
        }
    }
}
