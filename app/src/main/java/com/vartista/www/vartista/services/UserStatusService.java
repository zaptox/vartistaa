package com.vartista.www.vartista.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Date;

public class UserStatusService extends Service {

    private final IBinder myBinder = new MyLocalBinder();
    public UserStatusService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
        // TODO: Return the communication channel to the service.
    }

    public void updateUserStatus(final int userid){

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    HttpClient client = new DefaultHttpClient();
                    HttpGet request = new HttpGet();

                    String BASE_URL ="http://vartista.com/vartista_app/update_user_online_status.php?user_id="+userid;
                    request.setURI(new URI(BASE_URL));
                    HttpResponse response = client.execute(request);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                    StringBuffer stringBuffer = new StringBuffer();
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        stringBuffer.append(line);
                        break;
                    }
                    reader.close();
                    String result = stringBuffer.toString();


                } catch (URISyntaxException e) {
                    e.printStackTrace();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }).start();

    }

    private int getCurrentTimeInMillis() {
        return Calendar.getInstance().get(Calendar.MILLISECOND);

    }

    public class MyLocalBinder extends Binder {
        public UserStatusService getService() {
            return UserStatusService.this;
        }
    }
}
