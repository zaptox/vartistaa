package com.vartista.www.vartista.firebaseconfig;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.adapters.MyRequestsServicesListAdapter;
import com.vartista.www.vartista.beans.RequestService;
import com.vartista.www.vartista.beans.ServiceRequets;
import com.vartista.www.vartista.modules.general.Asynctask_MultipleUrl;
import com.vartista.www.vartista.modules.general.HomeActivity;
import com.vartista.www.vartista.modules.provider.MyServiceRequests;
import com.vartista.www.vartista.modules.provider.UploadDoc;
import com.vartista.www.vartista.modules.provider.UploadDocListActivity;
import com.vartista.www.vartista.modules.provider.ServicestartProvider;
import com.vartista.www.vartista.modules.provider.RequestAlertActivity;
import com.vartista.www.vartista.modules.user.AssignRatings;
import com.vartista.www.vartista.modules.user.MyCompletedServices;
import com.vartista.www.vartista.modules.user.MyServiceMeetings;
import com.vartista.www.vartista.modules.user.UserAppointmentDetails;
import com.vartista.www.vartista.modules.user.UserNotificationOnTime;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Random;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

import static com.vartista.www.vartista.adapters.MyRequestsServicesListAdapter.REQUEST_CODE_SP;
import static com.vartista.www.vartista.adapters.MyRequestsServicesListAdapter.sendCompactNotification;

public class FirebaseMsgService   extends FirebaseMessagingService {

       private static String TAG="notifciation";
       public static int REQEUST_CODE_FOR_USER = 101;
    public static int REQEUST_CODE_FOR_USER_BEFORE2H = 201;

    int user_id;
       static int notification_id = -1;
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
        Log.d("if not null", "Message Notification Body: " + remoteMessage.getNotification().getBody());
//        sendNotifcation(remoteMessage.getData().get("title"),remoteMessage.getData().get("body"),"HomeActivity");
            if (remoteMessage.getNotification().getBody().contains("Accepted")){
                String body = remoteMessage.getNotification().getBody();
                String data[] = body.split("_");
                String Msg = data[0];
                String date = data[1];
                String time = data[2];
                String rservice_id= data[3];

                Log.d("if not null", "Message Notification Body: " + remoteMessage.getNotification().getBody());
                sendNotifcation(remoteMessage.getNotification().getTitle(),Msg,"HomeActivity",date,time,rservice_id);
            }
            else{
                Log.d("if not null", "Message Notification Body: " + remoteMessage.getNotification().getBody());
                sendNotifcation(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody(),"HomeActivity","","","");
            }


        }
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    private void sendNotifcation(String title,String body, String activity,String date , String time,String rservice_id) {
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
//           int R_S_ID = Integer.parseInt(rservice_id);
//                if (!date.equals("")){
//                    sendCompactNotification(this,REQEUST_CODE_FOR_USER,date,time,name_user,"minute",-2,0);
//                    sendCompactNotification(this,REQEUST_CODE_FOR_USER_BEFORE2H,date,time,name_user,"minute",-2,R_S_ID );
//
//                }

                resultIntent = new Intent(getApplicationContext(), MyServiceMeetings.class);
                resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            }
            else if(title.contains("Request")){

                resultIntent = new Intent(getApplicationContext(), MyServiceRequests.class);
                resultIntent.putExtra("user", user_id);
                resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                Intent dialogIntent = new Intent(this, RequestAlertActivity.class);
//                dialogIntent.putExtra("user",user_id);
//                dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(dialogIntent);
            }
            else if(title.contains("Decline")){

                resultIntent = new Intent(getApplicationContext(), Asynctask_MultipleUrl.class);
                resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            }
            else if(title.contains("Document")){

                resultIntent = new Intent(getApplicationContext(), UploadDocListActivity.class);
                resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            }


            else if(title.contains("Busy")){

                resultIntent = new Intent(getApplicationContext(), ServicestartProvider.class);
                resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            }
            else if(title.contains("Cash")){
                resultIntent = new Intent(getApplicationContext(), Asynctask_MultipleUrl.class);
                resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            }
            else if(title.contains("rate")){
                resultIntent = new Intent(getApplicationContext(), MyCompletedServices.class);
                resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            }
            else if(title.contains("cancelled")){
                resultIntent = new Intent(getApplicationContext(), Asynctask_MultipleUrl.class);
                resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            }

            else {
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

            SharedPreferences sharedPreferencespre =getSharedPreferences("Login", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferencespre.edit();
            user_id = sharedPreferencespre.getInt("user_id",0);
            new FirebaseMsgService.Conncetion(user_id);
//            insert
            if (notification_id!=-1) {
                editor.putInt("last_notif_id", notification_id);
            }

        }


    }

    public static class  Conncetion extends AsyncTask<String,String ,String > {

        int userId;

        public  Conncetion(int user_id) {

            userId=user_id;

        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... strings) {


            String result="";

            final String BASE_URL="http://vartista.com/vartista_app/fetch_allnotifications_id.php?user_id="+userId;
            try {
                HttpClient client=new DefaultHttpClient();
                HttpGet request=new HttpGet();
                request.setURI(new URI(BASE_URL));
                HttpResponse response=client.execute(request);
                BufferedReader reader=new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuffer stringBuffer=new StringBuffer();
                String line="";
                while((line=reader.readLine())!=null){
                    stringBuffer.append(line);
                    break;
                }
                reader.close();
                result=stringBuffer.toString();


            } catch (URISyntaxException e) {
                e.printStackTrace();
                return new String("There is exception"+e.getMessage());
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {

            try {
                JSONObject jsonResult=new JSONObject(result);
                int success=jsonResult.getInt("success");


                if(success==1){
                    JSONArray services=jsonResult.getJSONArray("services");
                        JSONObject service = services.getJSONObject(0);
                        notification_id = service.getInt("id");
                         }

                else{
                    Log.d("notification", "onPostExecute: Getting notification id failed ");
//                       Toast.makeText(getApplicationContext(),"no data",Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
