package com.vartista.www.vartista.firebaseconfig;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.RequestService;
import com.vartista.www.vartista.modules.provider.MyServiceRequests;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by delaroy on 10/8/17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    String type="";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
//        if (remoteMessage.getData().size() > 0) {
//            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
//            try {
//                type="json";
//                JSONObject json = new JSONObject(remoteMessage.getData().toString());
//                   sendNotification(remoteMessage.getNotification().getBody());
//
//             } catch (Exception e) {
//                Log.e(TAG, "Exception: " + e.getMessage());
//            }
//        }

        Log.d(TAG, "onMessageReceived: " + remoteMessage.getData().get("message"));

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
        String title = remoteMessage.getData().get("body");
        String message = remoteMessage.getData().get("message");
        showNotification(title, message);
    }


    protected void showNotification(String title, String message) {

        ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 200);
        toneGen1.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT, 15000);
//        myTask = new Runnable() {
//            public void run() {
//                Log.d(TAG, "Thread Started");
//
//                if (Looper.myLooper() == null)
//                {
//                    Looper.prepare();
//                }
//            }
//        };
//        this.handler = new android.os.Handler();
//        handler.post(myTask);
//
//


//
        Intent i = null;
            i = new Intent(this, MyServiceRequests.class);
             i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
             PendingIntent pendingIntent=PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_ONE_SHOT);

             Uri uri=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(i);
// Get the PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(message)
                .setSound(uri)
                .setWhen(System.currentTimeMillis())
                .setOngoing(true)
                .setSmallIcon(R.drawable.loggoo)
                .setContentIntent(resultPendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        manager.notify(0, builder.build());

        Intent intent=new Intent(getApplicationContext(), RequestService.class);
        startActivity(intent);
    }

    //this method will display the notification
    //We are passing the JSONObject that is received from
    //firebase cloud messaging
//    private void sendPushNotification(JSONObject json) {
//        String id="",messeage="",title="";
//        //optionally we can display the json into log
//        Log.e(TAG, "Notification JSON " + json.toString());
//        try {
//            //getting the json data
//            JSONObject data = json.getJSONObject("data");
//
//            //parsing json data
//            String title = data.getString("title");
//            String message = data.getString("message");
//            String imageUrl = data.getString("image");
//
//            //creating MyNotificationManager object
//            MyNotificationManager mNotificationManager = new MyNotificationManager(getApplicationContext());
//
//            //creating an intent for the notification
//            Intent intent = new Intent(getApplicationContext(), FCMActivity.class);
//
//            //if there is no image
//            if(imageUrl.equals("null")){
//                //displaying small notification
//                mNotificationManager.showSmallNotification(title, message, intent);
//            }else{
//                //if there is an image
//                //displaying a big notification
//                mNotificationManager.showBigNotification(title, message, imageUrl, intent);
//            }
//        } catch (JSONException e) {
//            Log.e(TAG, "Json Exception: " + e.getMessage());
//        } catch (Exception e) {
//            Log.e(TAG, "Exception: " + e.getMessage());
//        }
//    }
//private  void sendNotification(String messageBody){
//        String id="",message="",title="";
//        if(type.equals("json")){
//            try{
//                JSONObject jsonObject=new JSONObject(messageBody);
//                id=jsonObject.getString("id");
//                message=jsonObject.getString("message");
//                title=jsonObject.getString("title");
//
//
//            }catch (Exception  e){}
//        }
//        else if(type.equals("message")){
//            message=messageBody;
//        }
//        Intent intent=new Intent(this,MyServiceRequests.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
//    NotificationCompat.Builder notificationBuilder=new NotificationCompat.Builder(this);
//    notificationBuilder.setContentTitle(getString(R.string.app_name));
//    notificationBuilder.setContentText(message);
//    Uri soundURI= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//    notificationBuilder.setSound(soundURI);
//    notificationBuilder.setLights(0xff00ff00, 300, 100);
//    notificationBuilder.setSmallIcon(R.drawable.loggoo);
//    notificationBuilder.setShowWhen(true);
//
//    notificationBuilder.setLargeIcon(BitmapFactory.decodeResource(this.getResources(),R.drawable.mehdi));
//    notificationBuilder.setAutoCancel(true);
//    Vibrator vibrator=(Vibrator)this.getSystemService(Context.VIBRATOR_SERVICE);
//    vibrator.vibrate(1000);
//    notificationBuilder.setContentIntent(pendingIntent);
//    NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//    notificationManager.notify(0,notificationBuilder.build());
//
//
//}
}
