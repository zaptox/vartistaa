package com.vartista.www.vartista.modules.provider;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.modules.user.StartService;
import com.vartista.www.vartista.restcalls.ApiClient;
import com.vartista.www.vartista.restcalls.ApiInterface;

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

public class ServicestartProvider extends AppCompatActivity {
    public static ApiInterface apiInterface;
    TextView timer,service_tittle;
    ImageView imgview;
    Button startservice, endservice, reset;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L;
    Handler handler;
    int Hours, Seconds, Minutes, MilliSeconds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicestart_provider);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        timer = (TextView)findViewById(R.id.tvTimer);
        startservice = (Button)findViewById(R.id.startbutton);
        endservice = (Button)findViewById(R.id.btPause);
        reset = (Button)findViewById(R.id.btReset);
        startservice.setEnabled(false);
        handler = new Handler() ;
        int requestservice_id = 114;
        service_tittle = (TextView)findViewById(R.id.Service_tittle_SP);
        imgview = (ImageView)findViewById(R.id.imageView7instartserviceSP);
        StartTime = SystemClock.uptimeMillis();
        handler.postDelayed(runnable, 0);
        reset.setEnabled(false);
        new ServicestartProvider.Conncetion(ServicestartProvider.this,requestservice_id).execute();
        startservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                SharedPreferences ob =getSharedPreferences("Login", Context.MODE_PRIVATE);
//               int userid = ob.getInt("user_id",0);
//                Call<User> call = StartService.apiInterface.updatebusystatus(userid);
//                call.enqueue(new Callback<User>() {
//                    @Override
//                    public void onResponse(Call<User> call, Response<User> response) {
//                        if (response.body().getResponse().equals("ok")) {
//
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<User> call, Throwable t) {
//
//                    }
//
//                });
//                StartTime = SystemClock.uptimeMillis();
//                handler.postDelayed(runnable, 0);
//
//                reset.setEnabled(false);

            }
        });

        endservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimeBuff += MillisecondTime;

                handler.removeCallbacks(runnable);

                reset.setEnabled(true);

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MillisecondTime = 0L ;
                StartTime = 0L ;
                TimeBuff = 0L ;
                UpdateTime = 0L ;
                Seconds = 0 ;
                Minutes = 0 ;
                MilliSeconds = 0 ;

                timer.setText("00:00:00");

            }
        });

    }

    public Runnable runnable = new Runnable() {

        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = TimeBuff + MillisecondTime;

            Seconds = (int) (UpdateTime / 1000);

            Minutes = Seconds / 60;

            Hours = Minutes / 60;

            Seconds = Seconds % 60;


            timer.setText(String.format("%02d", Hours)+ ":"
                    + String.format("%02d", Minutes) + ":"
                    + String.format("%02d", Seconds));


            handler.postDelayed(this, 0);
        }

    };






    class Conncetion extends AsyncTask<String,String ,String > {
        private int requestservice_id;
        private ProgressDialog dialog;

        public Conncetion(Context activity, int requestservice_id) {
            dialog = new ProgressDialog(activity);
            this.requestservice_id = requestservice_id;
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Retriving data Please Wait..");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {


            String result = "";

            final String BASE_URL = "http://vartista.com/vartista_app/requestdetails.php?requestservice_id="+requestservice_id;
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
                        String username = ser1.getString("Uname");
                        String spname = ser1.getString("Spname");
                        String service_description = ser1.getString("service_description");
                        String spimage = ser1.getString("SpImage");
                        String customerimage = ser1.getString("CustomerPic");
                        String location = ser1.getString("location");
                        String request_status = ser1.getString("request_status");
                        String date = ser1.getString("date");
                        String service_title = ser1.getString("service_title");
                        String price = ser1.getString("price");
                        String name = ser1.getString("name");
                        String time = ser1.getString("time");
                        Picasso.get().load(customerimage).fit().centerCrop()
                                .placeholder(R.drawable.profile)
                                .error(R.drawable.profile)
                                .into(imgview);
                        service_tittle.setText("Service : "+service_title);


                    }

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
