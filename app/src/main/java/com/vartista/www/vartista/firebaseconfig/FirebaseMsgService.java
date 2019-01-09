package com.vartista.www.vartista.firebaseconfig;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.adapters.MyRequestsServicesListAdapter;
import com.vartista.www.vartista.beans.RequestService;
import com.vartista.www.vartista.modules.general.Asynctask_MultipleUrl;
import com.vartista.www.vartista.modules.general.HomeActivity;
import com.vartista.www.vartista.modules.provider.MyServiceRequests;
import com.vartista.www.vartista.modules.user.MyServiceMeetings;
import com.vartista.www.vartista.modules.user.UserNotificationOnTime;

import org.json.JSONObject;

import java.util.Map;
import java.util.Random;

public class FirebaseMsgService   extends FirebaseMessagingService {

       private static String TAG="notifciation";
       public static int REQEUST_CODE_FOR_USER = 101;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ


        Map<String,String> params= remoteMessage.getData();
        JSONObject object= new JSONObject(params);
        Log.d("Msg Data", "onMessageReceived: "+object.toString());

        //Toast.makeText(this, ""+object+"", Toast.LENGTH_SHORT).show();

        // Check if message contains a data payload.
//        if (remoteMessage.getData().size() > 0) {
//            Log.d(TAG, "Message data payload: " + remoteMessage.getData().get("gcm.notification.body").toString());
//            sendNotifcation(remoteMessage.getData().get("title"),remoteMessage.getData().get("body"),"HomeActivity");
//
//
//
//
//
////            sendNotifcation(object.getString("title"),remoteMessage.getData().get("gcm.notification.body").toString(),remoteMessage.getData().get("activity"));
//
//            if (/* Check if data needs to be processed by long running job */ true) {
//                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
//            } else {
//                // Handle message within 10 seconds
//            }
//
//        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
           String body = remoteMessage.getNotification().getBody();
           String data[] = body.split(",");
           String Msg = data[0];
           String date = data[1];
           String time = data[2];

        Log.d("if not null", "Message Notification Body: " + remoteMessage.getNotification().getBody());
        sendNotifcation(remoteMessage.getNotification().getTitle(),Msg,"HomeActivity",date,time);
//        sendNotifcation(remoteMessage.getData().get("title"),remoteMessage.getData().get("body"),"HomeActivity");
        }
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }



    private void sendNotifcation(String title,String body, String activity,String date , String time) {


        SharedPreferences ob = getApplicationContext().getSharedPreferences("Login", Context.MODE_PRIVATE);

        final String name_user = ob.getString("name","");
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

            Intent resultIntent = null;

            SharedPreferences obj = getSharedPreferences("Login", Context.MODE_PRIVATE);

            int user_id = obj.getInt("user_id", 0);

            if(title.contains("Accept")){

                resultIntent = new Intent(getApplicationContext(), MyServiceMeetings.class);
                resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

//                MyRequestsServicesListAdapter.sendCompactNotification(this,REQEUST_CODE_FOR_USER,date,time,name_user);


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


            Notification notification = new NotificationCompat.Builder(getApplicationContext(), NotificationHelper.CHANNEL_ID)
                        .setSmallIcon(R.drawable.logoforsplash)
                        .setContentText(body)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setContentTitle(title)
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .setContentIntent(resultPendingIntent)
                        .build();

                notificationManager.notify(1, notification);




        }


    }

}
