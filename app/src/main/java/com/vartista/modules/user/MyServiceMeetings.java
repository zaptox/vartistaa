package com.vartista.modules.user;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.vartista.www.vartista.R;
import com.vartista.adapters.ServiceUserAppointmentsAdapter;
import com.vartista.adapters.servicepappointmentsadapter;
import com.vartista.beans.servicepaapointmentsitems;
import com.vartista.modules.provider.MyAppointments;

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

import static com.vartista.modules.general.HomeActivity.user_id;

public class MyServiceMeetings extends AppCompatActivity {

    RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ServiceUserAppointmentsAdapter listadapter;
    ArrayList<servicepaapointmentsitems> userAppointments;
    int user_id1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_service_meetings);

        recyclerView = (RecyclerView)findViewById(R.id.Userappointment_recyclerView);
        userAppointments=new ArrayList<servicepaapointmentsitems>();
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        SharedPreferences ob =getSharedPreferences("Login", Context.MODE_PRIVATE);
          user_id1 = ob.getInt("user_id",0);
        new MyServiceMeetings.Conncetion(MyServiceMeetings.this,user_id1).execute();
    }



    class Conncetion extends AsyncTask<String,String ,String > {
        private int user_id1;
        private ProgressDialog dialog;

        public Conncetion(Context activity, int user_id1) {
            dialog = new ProgressDialog(activity);
            this.user_id1 = user_id1;
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Retriving data Please Wait..");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {


            String result = "";

            final String BASE_URL = "http://vartista.com/vartista_app/service_appointments_for_user.php?user_customer_id="+user_id1;
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


                JSONObject jsonResult = new JSONObject(result);

                int success = jsonResult.getInt("success");
                if (success == 1) {
                    JSONArray services = jsonResult.getJSONArray("services");
                    for (int j = 0; j < services.length(); j++) {
                        JSONObject ser1 = services.getJSONObject(j);
                        String requestservice_id = ser1.getString("requestservice_id");
                        String user_customer_id = ser1.getString("user_customer_id");
                        String service_provider_id = ser1.getString("service_provider_id");
                        String username = ser1.getString("username");
                        String service_description = ser1.getString("service_description");
                        String location = ser1.getString("location");
                        String request_status = ser1.getString("request_status");
                        String date = ser1.getString("date");
                        String service_title = ser1.getString("service_title");
                        String price = ser1.getString("price");
                        String name = ser1.getString("name");
                        String Time = ser1.getString("time");
                        userAppointments.add(new servicepaapointmentsitems(requestservice_id,user_customer_id,service_provider_id,username,service_description,location,request_status,date,service_title,price,name,Time));
                    }


                    listadapter = new ServiceUserAppointmentsAdapter(getApplicationContext(),userAppointments);
                    recyclerView.setAdapter(listadapter);

                }

                else {
                       Toast.makeText(getApplicationContext(),"no data",Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
