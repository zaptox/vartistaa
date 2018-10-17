package com.vartista.www.vartista.firebaseconfig;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.modules.general.HomeActivity;

public class FirebaseMessagingService {
//        extends  com.google.firebase.messaging.FirebaseMessagingService {
//
//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//
////        showNotification(remoteMessage.getData().get("message"));
//    }
//
//    private void showNotification(String message) {
//        Intent intent=new Intent(this, HomeActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
//
//        NotificationCompat.Builder builder=new NotificationCompat.Builder(this)
//                .setAutoCancel(true)
//                .setContentTitle("FCM Test")
//                .setContentText(message)
//                .setSmallIcon(R.drawable.logo)
//                .setContentIntent(pendingIntent);
//        NotificationManager notificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
//        notificationManager.notify(0,builder.build());
//
//    }
}
