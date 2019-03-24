package com.vartista.www.vartista.modules.general;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.BaseAdapter;

import com.squareup.picasso.Picasso;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.adapters.TwoListInRecyclerView;
import com.vartista.www.vartista.adapters.UserNotificationlistadapter;
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

import static com.vartista.www.vartista.modules.general.HomeActivity.user_id;
import static com.vartista.www.vartista.restcalls.ApiClient.BASE_URL;

public class Asynctask_MultipleUrl extends AppCompatActivity {

    RecyclerView view;
    private RecyclerView.LayoutManager layoutManager;
    private TwoListInRecyclerView listadapter;
    ArrayList<usernotificationitems> requestlist;
    ArrayList<usernotificationitems> notificationlist;
    int user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asynctask__multiple_url);
        view = (RecyclerView)findViewById(R.id.user_notificationlist);
        requestlist=new ArrayList<usernotificationitems>();
        notificationlist=new ArrayList<usernotificationitems>();
        layoutManager = new LinearLayoutManager(getApplicationContext());
        view.setHasFixedSize(true);
        view.setLayoutManager(layoutManager);
        SharedPreferences ob = getSharedPreferences("Login", Context.MODE_PRIVATE);

        user_id = ob.getInt("user_id", 0);
        new Asynctask_MultipleUrl.Conncetion(Asynctask_MultipleUrl.this,user_id).execute();
    }




    class Conncetion extends AsyncTask<String,String ,String[]> {
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
        protected String[] doInBackground(String... strings) {

            final String BASE_URL = "http://vartista.com/vartista_app/usernotificationstatus.php?user_customer_id="+user_id;
            final String BASE_URL2 = "http://vartista.com/vartista_app/fetch_notificationmsg.php?user_id="+user_id;
            String[] ob = new String[2];
            ob[0] = getjsonfromurl(BASE_URL);
            ob[1] = getjsonfromurl(BASE_URL2);

            return ob;
        }

        @Override
        protected void onPostExecute(String[] result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            try {

                JSONObject jsonResult = new JSONObject(result[0]);
                JSONObject jsonResult2 = new JSONObject(result[1]);

                int success = jsonResult.getInt("success");
                int success2 = jsonResult2.getInt("success");

                if (success == 1) {
                    JSONArray services = jsonResult.getJSONArray("services");
                    for (int j = 0; j < services.length(); j++) {
                        JSONObject ser1 = services.getJSONObject(j);
                        String username = ser1.getString("username");
                        String image = ser1.getString("image");
                        String request_detail = ser1.getString("request_status");
                        String Time = ser1.getString("time");
                        String Service_title = ser1.getString("service_title");
                        Double price = ser1.getDouble("price");
                        requestlist.add(new usernotificationitems(username,image,request_detail,Time,Service_title,price));
                    }
                } else {
                }
               if(success2==1){
                    JSONArray services2 = jsonResult2.getJSONArray("services");
                    for ( int i = 0 ; i < services2.length(); i++){
                        JSONObject serv2 = services2.getJSONObject(i);
                        String notificationid =  serv2.getString("id");
                        String username = serv2.getString("name");
                        String title = serv2.getString("title");
                        String msg = serv2.getString("msg");
                        String created_at = serv2.getString("created_at");
                        String sp_status = serv2.getString("sp_status");
                        notificationlist.add(new usernotificationitems(notificationid,username,title,msg,created_at,sp_status));
                    }
                } else {
               }
                listadapter = new TwoListInRecyclerView(getApplicationContext(),requestlist,notificationlist);
                view.setAdapter(listadapter);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }




    public String getjsonfromurl(String BASE_URL){
       String result = "";
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
        return  result;
    }
}
