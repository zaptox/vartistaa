package com.vartista.www.vartista.notifications.NotificationCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.AllNotificationBean;
import com.vartista.www.vartista.firebaseconfig.FirebaseMsgService;
import com.vartista.www.vartista.firebaseconfig.NotificationHelper;
import com.vartista.www.vartista.modules.general.Asynctask_MultipleUrl;
import com.vartista.www.vartista.modules.provider.MyServiceRequests;
import com.vartista.www.vartista.modules.provider.RequestAlertActivity;
import com.vartista.www.vartista.modules.user.MyServiceMeetings;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


import static android.content.Context.CONNECTIVITY_SERVICE;
import static com.vartista.www.vartista.adapters.MyRequestsServicesListAdapter.sendCompactNotification;
import static com.vartista.www.vartista.firebaseconfig.FirebaseMsgService.REQEUST_CODE_FOR_USER;

public class NetworkStateChangeReceiver extends BroadcastReceiver {
    public static final String NETWORK_AVAILABLE_ACTION = "com.NetworkAvailable";
    public static final String IS_NETWORK_AVAILABLE = "isNetworkAvailable";
    List<AllNotificationBean> offline_notification_list;
    SharedPreferences sharedPreferencespre;
    int notification_id,user_id;

    @Override

    public void onReceive(Context context, Intent intent) {
        Intent networkStateIntent = new Intent(NETWORK_AVAILABLE_ACTION);
        networkStateIntent.putExtra(IS_NETWORK_AVAILABLE,  isConnectedToInternet(context));
        LocalBroadcastManager.getInstance(context).sendBroadcast(networkStateIntent);

        offline_notification_list= new ArrayList<AllNotificationBean>();


        if(isConnectedToInternet(context)==true){
            sharedPreferencespre =  context.getSharedPreferences("Login", Context.MODE_PRIVATE);
            notification_id = sharedPreferencespre.getInt("last_notif_id",0);
            user_id = sharedPreferencespre.getInt("user_id",0);

        if (notification_id!=0){
            new NetworkStateChangeReceiver.Conncetion(context,notification_id,user_id).execute();
        }
        }

        
        




    }

    private boolean isConnectedToInternet(Context context) {
        try {
            if (context != null) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                return networkInfo != null && networkInfo.isConnected();
            }

            return false;
        } catch (Exception e) {
            Log.e(NetworkStateChangeReceiver.class.getName(), e.getMessage());
            return false;
        }
    }

    class Conncetion extends AsyncTask<String,String ,String > {
         Context context;
        int notification_id;
        int user_id;

        public Conncetion(Context context, int notification_id, int user_id) {
             this.context = context;
            this.notification_id=notification_id;
            this.user_id = user_id;

        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... strings) {

            String result="";

            final String BASE_URL="http://vartista.com/vartista_app/fetch_offline_notification.php?notification_id="+notification_id+"&user_id="+user_id;
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
                    for(int i=0;i<services.length();i++){
                        JSONObject service = services.getJSONObject(i);
                        int notification_id = (Integer.parseInt(service.getString("id")));
                        String title =  service.getString("title");
                        String message=service.getString("message");
                        int sender_id = Integer.parseInt(service.getString("sender_id"));
                        int receiver_id = Integer.parseInt(service.getString("receiver_id"));
                        int status = Integer.parseInt(service.getString("status"));
                        String created_at = service.getString("created_at");
                        offline_notification_list.add(new AllNotificationBean(notification_id,title,message,sender_id,receiver_id,status,created_at));
                    }

                    if (offline_notification_list!=null) {
                        Toast.makeText(context, "List is not null"+offline_notification_list.size(), Toast.LENGTH_SHORT).show();
                        for (AllNotificationBean ob : offline_notification_list) {
                            sendNotifcation(context, ob.getTitle(), ob.getMessage(), "HomeActivity");

                        }
                    }

                }

                else{
                    Log.d("nodata", "onPostExecute: no data of offline notifications");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private void sendNotifcation(Context context,String title,String message, String activity) {

        String data[] = message.split("_");
        String body = data[0];
        String date = data[1];
        String time = data[2];


        final String name_user = sharedPreferencespre.getString("name","");
        NotificationHelper notificationHelper;
        NotificationManagerCompat notificationManager;
        notificationHelper=new NotificationHelper(context);
        notificationManager=NotificationManagerCompat.from(context);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {

            Notification.Builder builder=notificationHelper.getChannelNotiifcation(title,body);
            notificationHelper.getManager().notify(new Random().nextInt(),builder.build());
        }

        // other than android O
        else {

            Intent resultIntent = null;

            if(title.contains("Accept")){

                if (!date.equals("")){
                    sendCompactNotification(context,REQEUST_CODE_FOR_USER,date,time,name_user);
                }

                resultIntent = new Intent(context.getApplicationContext(), MyServiceMeetings.class);
                resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            }
            else if(title.contains("Request")){

                resultIntent = new Intent(context.getApplicationContext(), MyServiceRequests.class);
                resultIntent.putExtra("user", user_id);
                resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Intent dialogIntent = new Intent(context.getApplicationContext(), RequestAlertActivity.class);
                dialogIntent.putExtra("user",user_id);
                dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(dialogIntent);
            }
            else if(title.contains("Decline")){

                resultIntent = new Intent(context.getApplicationContext(), Asynctask_MultipleUrl.class);
                resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            }
            else{
                resultIntent = new Intent(context.getApplicationContext(), Asynctask_MultipleUrl.class);
                resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }


            PendingIntent resultPendingIntent = PendingIntent.getActivity(context.getApplicationContext(),
                    0 /* Request code */, resultIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);


            Notification notification = new NotificationCompat.Builder(context.getApplicationContext(), NotificationHelper.CHANNEL_ID)
                    .setSmallIcon(R.drawable.logoforsplash)
                    .setContentText(body)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setContentTitle(title)
                    .setAutoCancel(false)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .setContentIntent(resultPendingIntent)
                    .build();
            notificationManager.notify(new Random().nextInt(), notification);

            SharedPreferences sharedPreferencespre =context.getSharedPreferences("Login", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferencespre.edit();
            user_id = sharedPreferencespre.getInt("user_id",0);
            new FirebaseMsgService.Conncetion(user_id);
//            insert
            if (notification_id!=-1) {
                editor.putInt("last_notif_id", notification_id);
            }

        }


    }


}
