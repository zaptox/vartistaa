package com.vartista.www.vartista;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

public class Offline_user_status_service extends Service {
    private int user_status = 0;
    private static int user_id;

    public Offline_user_status_service() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        user_id = (int) intent.getExtras().get("user_id");


        return START_STICKY;


    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        new Connection(user_id,0).execute();
        this.stopSelf();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    class Connection extends AsyncTask<String, String, String> {
        private int user_id;
        private int user_status;



        public Connection(int user_id, int user_status) {
            this.user_id = user_id;
            this.user_status = user_status;
        }

        @Override
        protected void onPreExecute() {


        }

        @Override
        protected String doInBackground(String... strings) {


            String result = "";
            String BASE_URL = "";

            BASE_URL = "http://www.vartista.com/vartista_app/update_user_online_status.php?user_id="+user_id+"&user_status="+user_status;

            try {
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();

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
                result = stringBuffer.toString();


            } catch (URISyntaxException e) {
                e.printStackTrace();
                return new String("There is exception" + e.getMessage());
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
                JSONObject jsonResult = new JSONObject(result);
                int success = jsonResult.getInt("success");

                if (success == 1) {
                } else {
                }

                new Offline_user_status_service().stopSelf();
                Log.d("HomeActivity", "ServiceStopped");
            } catch (JSONException e) {
                e.printStackTrace();

            }


        }

    }
}
