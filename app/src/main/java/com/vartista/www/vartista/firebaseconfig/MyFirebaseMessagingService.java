package com.vartista.www.vartista.firebaseconfig;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.RequestService;

import java.util.Random;

/**
 * Created by delaroy on 10/8/17.
 */

public class MyFirebaseMessagingService {}
//        extends FirebaseMessagingService {
//
//    private static final String TAG = "MyFirebaseMsgService";
//    String type = "";
//    private NotificationManager mNotificationManager;
//    private NotificationCompat.Builder mBuilder;
//
//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//
//        Log.d("pushNotification", "onMessageReceived: " + remoteMessage.getData().get("body"));
//
//        if (remoteMessage.getData().size() > 0) {
//            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
//
//            String title = remoteMessage.getData().get("title");
//            String message = remoteMessage.getData().get("body");
//            showNotification(title, message);
//
//        }
//
//        // Check if message contains a notification payload.
//        if (remoteMessage.getNotification() != null) {
//            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
//
//            String title = remoteMessage.getData().get("title");
//            String message = remoteMessage.getData().get("body");
//            showNotification(title, message);
//        }
//
//    }
//
//
//    private void showNotification(String title, String body) {
//        NotificationHelper notificationHelper;
//        NotificationManagerCompat notificationManager;
//
//        notificationHelper = new NotificationHelper(this);
//        notificationManager = NotificationManagerCompat.from(this);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//
//            Notification.Builder builder = notificationHelper.getChannelNotiifcation(title, body);
//            notificationHelper.getManager().notify(new Random().nextInt(), builder.build());
//        } else {
//            Intent resultIntent = new Intent(getApplicationContext(), RequestService.class);
//            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//            PendingIntent resultPendingIntent = PendingIntent.getActivity(getApplicationContext(),
//                    0 /* Request code */, resultIntent,
//                    PendingIntent.FLAG_UPDATE_CURRENT);
//
//            Notification notification = new NotificationCompat.Builder(getApplicationContext(), NotificationHelper.CHANNEL_ID)
//                    .setSmallIcon(R.drawable.ic_launcher_background)
//                    .setContentText(body)
//                    .setContentTitle(title)
//                    .setPriority(NotificationCompat.PRIORITY_HIGH)
//                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
//                    .setContentIntent(resultPendingIntent)
//                    .build();
//
//            notificationManager.notify(1, notification);
//
//        }
//
//
//    }
//}
////
////    protected void showNotification(String title, String message) {
////
////     Log.d("Notify",title+"   "+message);
//
//
//
//
//
////        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), getString(R.string.app_name));
////        Intent intent = new Intent(getApplicationContext(), RequestService.class);
////
////        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////        intent.putExtra("title", title);
////        intent.putExtra("msg", message);
//////
//////        if (page != null)
//////        {
//////            intent.putExtra(Consts.LAUNCH_PAGE, page);
//////        }
//////
////        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
////
////        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
////
////        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
////        bigText.bigText(title);
////        bigText.setBigContentTitle(message);
////
////        mBuilder.setContentIntent(pendingIntent);
////        mBuilder.setSmallIcon(R.drawable.loggoo);
////        mBuilder.setContentTitle(title);
////        mBuilder.setContentText(message);
////        mBuilder.setPriority(Notification.PRIORITY_MAX);
////        mBuilder.setAutoCancel(true);
////        mBuilder.setStyle(bigText);
////
////        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
////        {
////            NotificationChannel channel = new NotificationChannel("81", getString(R.string.app_name), NotificationManager.IMPORTANCE_DEFAULT);
////            mNotificationManager.createNotificationChannel(channel);
////        }
////
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
////
////          try{  String channelId = "81";
////            NotificationChannel channel = new NotificationChannel(channelId,   title, NotificationManager.IMPORTANCE_DEFAULT);
////            channel.setDescription(message);
////            mNotificationManager.createNotificationChannel(channel);
////            mBuilder.setChannelId(channelId);}catch (Exception e){
////              Log.d("notify",e.getMessage());
////          }
////        }
//
////        Notification notification = new Notification(R.drawable.loggoo, message, System.currentTimeMillis());
////        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
////
////        Intent resultIntent = new Intent(this , RequestService.class);
////        resultIntent.putExtra("Notify","notify");
////        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//////        PendingIntent resultPendingIntent = PendingIntent.getActivity(this,
//////                Integer.parseInt(NOTIFICATION_CHANNEL_ID) /* Request code */, resultIntent,
//////                PendingIntent.FLAG_UPDATE_CURRENT);
////
////        int uniqueInt = (int) (System.currentTimeMillis() & 0xfffffff);
////        PendingIntent pendingIntent = PendingIntent.getActivity(this, uniqueInt,
////                resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
////
////
////
////        notification.flags |= Notification.FLAG_AUTO_CANCEL;
////
////        mBuilder = new NotificationCompat.Builder(this);
////        mBuilder.setSmallIcon(R.drawable.loggoo);
////        mBuilder.setContentTitle( getResources().getString(R.string.app_name))
////
////                .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
////                .setSound(soundUri)
////                .setDefaults(Notification.DEFAULT_SOUND)
////                .setContentText(message)
////                .setPriority(NotificationCompat.PRIORITY_HIGH)
////
////                .setAutoCancel(true)
////                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
////                .setContentIntent(pendingIntent);
////
////        mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
////
////
////
////
////        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
////        {
////            int importance = NotificationManager.IMPORTANCE_HIGH;
////            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
////            notificationChannel.enableLights(true);
////            notificationChannel.setLightColor(Color.RED);
////            notificationChannel.enableVibration(true);
////
////            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
////            assert mNotificationManager != null;
////            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
////            mNotificationManager.createNotificationChannel(notificationChannel);
////        }
////
////        mNotificationManager.notify(0, mBuilder.build());
////
////
////
////
////
////
////
////
//
//
//
//
//
////        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
////
////            Intent intent = new Intent(getBaseContext(), Main2Activity.class);
////            intent.putExtra("ok","ok");
////            PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, intent, 0);
////
////        PendingIntent contentIntent = stackBuilder.getPendingIntent(0,
////                PendingIntent.FLAG_UPDATE_CURRENT
////                        | PendingIntent.FLAG_ONE_SHOT);
////        Notification notification = new NotificationCompat.Builder(getBaseContext())
////                    .setContentTitle(title).
////                            setContentText(message)
////                    .setSmallIcon(R.drawable.loggoo)
////                    .setContentIntent(pendingIntent).build();
////
//////
////        manager.notify(8, notification);
////
////        }
////        myTask = new Runnable() {
////            public void run() {s
////                Log.d(TAG, "Thread Started");
////
////                if (Looper.myLooper() == null)
////                {
////                    Looper.prepare();
////                }
////            }
////        };
////        this.handler = new android.os.Handler();
////        handler.post(myTask);
////
////
//
////
////        ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 200);
////        toneGen1.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT, 15000);
////
////        Intent i = new Intent(this, MyServiceRequests.class);
////        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_ONE_SHOT);
////
////        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
////
//////
//////        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//////        stackBuilder.addNextIntentWithParentStack(i);
//////// Get the PendingIntent containing the entire back stack
//////        PendingIntent resultPendingIntent =
//////                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
////        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
////                .setSmallIcon( R.drawable.loggoo)
////                .setContentTitle(title)
////                .setContentText(message)
////                .setAutoCancel(true)
////                .setSound(uri)
////                .setContentIntent(pendingIntent);
////
////
////        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
////
////        manager.notify(0, builder.build());
//
//
//
//    //this method will display the notification
//    //We are passing the JSONObject that is received from
//    //firebase cloud messaging
////    private void sendPushNotification(JSONObject json) {
////        String id="",messeage="",title="";
////        //optionally we can display the json into log
////        Log.e(TAG, "Notification JSON " + json.toString());
////        try {
////            //getting the json data
////            JSONObject data = json.getJSONObject("data");
////
////            //parsing json data
////            String title = data.getString("title");
////            String message = data.getString("message");
////            String imageUrl = data.getString("image");
////
////            //creating MyNotificationManager object
////            MyNotificationManager mNotificationManager = new MyNotificationManager(getApplicationContext());
////
////            //creating an intent for the notification
////            Intent intent = new Intent(getApplicationContext(), FCMActivity.class);
////
////            //if there is no image
////            if(imageUrl.equals("null")){
////                //displaying small notification
////                mNotificationManager.showSmallNotification(title, message, intent);
////            }else{
////                //if there is an image
////                //displaying a big notification
////                mNotificationManager.showBigNotification(title, message, imageUrl, intent);
////            }
////        } catch (JSONException e) {
////            Log.e(TAG, "Json Exception: " + e.getMessage());
////        } catch (Exception e) {
////            Log.e(TAG, "Exception: " + e.getMessage());
////        }
////    }
////private  void sendNotification(String messageBody){
////        String id="",message="",title="";
////        if(type.equals("json")){
////            try{
////                JSONObject jsonObject=new JSONObject(messageBody);
////                id=jsonObject.getString("id");
////                message=jsonObject.getString("message");
////                title=jsonObject.getString("title");
////
////
////            }catch (Exception  e){}
////        }
////        else if(type.equals("message")){
////            message=messageBody;
////        }
////        Intent intent=new Intent(this,MyServiceRequests.class);
////        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
////    NotificationCompat.Builder notificationBuilder=new NotificationCompat.Builder(this);
////    notificationBuilder.setContentTitle(getString(R.string.app_name));
////    notificationBuilder.setContentText(message);
////    Uri soundURI= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
////    notificationBuilder.setSound(soundURI);
////    notificationBuilder.setLights(0xff00ff00, 300, 100);
////    notificationBuilder.setSmallIcon(R.drawable.loggoo);
////    notificationBuilder.setShowWhen(true);
////
////    notificationBuilder.setLargeIcon(BitmapFactory.decodeResource(this.getResources(),R.drawable.mehdi));
////    notificationBuilder.setAutoCancel(true);
////    Vibrator vibrator=(Vibrator)this.getSystemService(Context.VIBRATOR_SERVICE);
////    vibrator.vibrate(1000);
////    notificationBuilder.setContentIntent(pendingIntent);
////    NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
////    notificationManager.notify(0,notificationBuilder.build());
////
////
//}

