package com.vartista.www.vartista.modules.provider;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.vartista.www.vartista.R;
import com.vartista.www.vartista.adapters.EarningsListAdapter;
import com.vartista.www.vartista.adapters.RatingsReviewDetailsAdaptor;
import com.vartista.www.vartista.adapters.servicepappointmentsadapter;
import com.vartista.www.vartista.beans.EarningBean;
import com.vartista.www.vartista.beans.RatingsReviewDetailBean;
import com.vartista.www.vartista.beans.servicepaapointmentsitems;

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

public class EarningActivity extends AppCompatActivity {


    public TextView total_earning;
    public int serviceproviderid;
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private EarningsListAdapter listadapter;
    ArrayList<EarningBean> earnings_list;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earning);
        total_earning= findViewById(R.id.total_earning);

        recyclerView = (RecyclerView)findViewById(R.id.earning_list);
        earnings_list=new ArrayList<EarningBean>();
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);



        SharedPreferences object =getSharedPreferences("Login", Context.MODE_PRIVATE);
        serviceproviderid= object.getInt("user_id",0);



        new EarningActivity.Conncetion2(EarningActivity.this,serviceproviderid).execute();


        new EarningActivity.Conncetion(EarningActivity.this,serviceproviderid).execute();




    }






    class Conncetion extends AsyncTask<String,String ,String > {
        private int service_id;
        private ProgressDialog dialog;

        public Conncetion(Context activity, int service_id) {
            dialog = new ProgressDialog(activity);
            this.service_id = service_id;
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Retriving data Please Wait..");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {


            String result = "";

            final String BASE_URL = "http://vartista.com/vartista_app/retreive_sp_earning.php?sp_id="+serviceproviderid;
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
                        int id=ser1.getInt("id");
                        String service_provider = ser1.getString("service_provider");
                        String service_availer = ser1.getString("service_availer");
                        String service = ser1.getString("service");
                        String location = ser1.getString("location");
                        String service_time = ser1.getString("service_time");
                        Double total_amount = ser1.getDouble("total_amount");
                        Double admin_tax = ser1.getDouble("admin_tax");
                        Double discount = ser1.getDouble("discount");
                        Double user_bonus = ser1.getDouble("user_bonus");
                        Double sp_earning = ser1.getDouble("sp_earning");
                        Double admin_earning = ser1.getDouble("admin_earning");
                        String date = ser1.getString("date");
                        Toast.makeText(EarningActivity.this, "Yes it has data "+total_amount, Toast.LENGTH_SHORT).show();
                        earnings_list.add(new EarningBean(id,  service_provider,  service_availer,  service,  location,  service_time,  total_amount, admin_tax, discount,  user_bonus,  sp_earning,admin_earning, date));
                        }


                    listadapter = new EarningsListAdapter(getApplicationContext(),earnings_list);
                    recyclerView.setAdapter(listadapter);

                }

                else {
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }







    class Conncetion2 extends AsyncTask<String,String ,String > {
        private int user_customer_id;
        private ProgressDialog dialog;

        public Conncetion2(Context activity, int user_customer_id) {
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


            final String BASE_URL = "http://vartista.com/vartista_app/sp_total_earned.php?sp_id=" + serviceproviderid;
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

            Double rating;

            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            try {

                JSONObject jsonResult = new JSONObject(result);
                double total_earned = 0.0;
                int success = jsonResult.getInt("success");

                if (success == 1) {
                    JSONArray services = jsonResult.getJSONArray("services");
                    for (int j = 0; j < services.length(); j++) {
                        JSONObject ser1 = services.getJSONObject(j);
                        if(ser1.getString("total_earning")!=null) {
                            total_earned = Double.parseDouble(ser1.getString("total_earning"));
                            }
                            else{
                            total_earned=0.0;
                        }
                    }

                    total_earning.setText("" + total_earned);

                } else {
                    Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

    }
