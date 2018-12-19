package com.vartista.www.vartista.firebaseconfig;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.RequestService;
import com.vartista.www.vartista.modules.provider.MyServiceRequests;

import java.util.Random;

public class FirebaseMsgService   extends FirebaseMessagingService {

       private static String TAG="notifciation";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData().get("body"));
            sendNotifcation(remoteMessage.getData().get("title"),remoteMessage.getData().get("body"));

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
            } else {
                // Handle message within 10 seconds
            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {

            Log.d("if not null", "Message Notification Body: " + remoteMessage.getNotification().getBody());

        sendNotifcation("Xoni Developers","This is a msg send from firebase server");        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }



    private void sendNotifcation(String title,String body) {
        NotificationHelper notificationHelper;
        NotificationManagerCompat notificationManager;

        notificationHelper=new NotificationHelper(this);
        notificationManager=NotificationManagerCompat.from(this);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {

            Notification.Builder builder=notificationHelper.getChannelNotiifcation(title,body);
            notificationHelper.getManager().notify(new Random().nextInt(),builder.build());
        }


        // other than android O
        else {
            Intent resultIntent = new Intent(getApplicationContext() , MyServiceRequests.class);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            PendingIntent resultPendingIntent = PendingIntent.getActivity(getApplicationContext(),
                    0 /* Request code */, resultIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            Notification notification = new NotificationCompat.Builder(getApplicationContext(), NotificationHelper.CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentText(title)
                    .setContentTitle(body)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .setContentIntent(resultPendingIntent)
                    .build();

            notificationManager.notify(1, notification);

        }


    }

}
