package com.vartista.www.vartista.modules.user;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.vartista.www.vartista.R;
import com.vartista.www.vartista.adapters.UserNotificationlistadapter;
import com.vartista.www.vartista.beans.GetServiceProviders;
import com.vartista.www.vartista.beans.usernotificationitems;

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

public class UserNotification_activity extends AppCompatActivity {
    RecyclerView view;
    private RecyclerView.LayoutManager layoutManager;
    private UserNotificationlistadapter listadapter;
    ArrayList<usernotificationitems> requestlist;
    int customer_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_notification_activity);
        view = (RecyclerView)findViewById(R.id.user_notificationlist);
        requestlist=new ArrayList<usernotificationitems>();
        layoutManager = new LinearLayoutManager(getApplicationContext());
        view.setHasFixedSize(true);
        view.setLayoutManager(layoutManager);
        customer_id = 1;
        new UserNotification_activity.Conncetion(UserNotification_activity.this,customer_id).execute();


    }


    class Conncetion extends AsyncTask<String,String ,String > {
        private int user_customer_id;
        private ProgressDialog dialog;

        public Conncetion(Context activity, int user_customer_id) {
            dialog = new ProgressDialog(activity);
            this.user_customer_id = user_customer_id;
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Retriving data Please Wait..");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {


            String result = "";

            final String BASE_URL = "http://vartista.com/vartista_app/usernotificationstatus.php?user_customer_id="+1;
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
                return new String("Exception is " + e.getMessage());
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            try {
                Toast.makeText(UserNotification_activity.this, "ok", Toast.LENGTH_SHORT).show();

                JSONObject jsonResult = new JSONObject(result);

                int success = jsonResult.getInt("success");

                if (success == 1) {
                    JSONArray services = jsonResult.getJSONArray("services");
                    for (int j = 0; j < services.length(); j++) {
                        JSONObject ser1 = services.getJSONObject(j);
                        String username = ser1.getString("username");
                        String request_detail = ser1.getString("request_status");
                        String Time = ser1.getString("time");
                        requestlist.add(new usernotificationitems(username,request_detail,Time));
                    }
                    listadapter = new UserNotificationlistadapter(getApplicationContext(),requestlist);
                    view.setAdapter(listadapter);
                } else {
                    Toast.makeText(getApplicationContext(),"no data",Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }

    }

}
