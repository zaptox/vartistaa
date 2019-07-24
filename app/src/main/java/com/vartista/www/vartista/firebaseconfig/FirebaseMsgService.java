package com.vartista.www.vartista.firebaseconfig;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.valdesekamdem.library.mdtoast.MDToast;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.User;
import com.vartista.www.vartista.modules.general.HomeActivity;
import com.vartista.www.vartista.modules.general.SiginInActivity;
import com.vartista.www.vartista.modules.provider.ServicestartProvider;
import com.vartista.www.vartista.modules.provider.RequestAlertActivity;

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


        Map<String,String> params = remoteMessage.getData();
        JSONObject object= new JSONObject(params);
        Log.d("Msg Data", "onMessageReceived: "+object.toString());

        if (remoteMessage.getData().size() > 0) {
            Log.d("FBM","I m here in data msg back");
            Log.d("FBM DATA",""+remoteMessage.getData());

            Map<String,String> dataMsg=remoteMessage.getData();
            Log.d("FBM DATA1",""+dataMsg.get("title"));
            Log.d("FBM DATA2",""+dataMsg.get("message"));
            String title=dataMsg.get("title");
            String body=dataMsg.get("message");


            if(body.contains("Accepted")){


            String data[] = body.split("_");
            String Msg = data[0];
            String date = data[1];
            String time = data[2];
            String rservice_id= data[3];

            Log.d("if not null", "Message Notification Body: " + body);
            sendNotifcation(title,Msg,"HomeActivity",date,time,rservice_id);
            }
            else{
                Log.d("if not null", "Message Notification Body: " + body);
                sendNotifcation(title,body,"HomeActivity","","","");

            }
        }


            // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
        Log.d("ifnot null", "Message Notification Body: " + remoteMessage.getNotification().getBody());
//        sendNotifcation(remoteMessage.getData().get("title"),remoteMessage.getData().get("body"),"HomeActivity");
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();
          if (remoteMessage.getNotification().getBody().contains("Accepted")){
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

                Notification.Builder builder = notificationHelper.getChannelNotiifcation(title, body);
                notificationHelper.getManager().notify(new Random().nextInt(), builder.build());

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

                title = for_title[0];

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

                MDToast.makeText(getApplicationContext(), "Login Again to Start your journey at Vartista", MDToast.LENGTH_LONG,MDToast.TYPE_INFO).show();
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
