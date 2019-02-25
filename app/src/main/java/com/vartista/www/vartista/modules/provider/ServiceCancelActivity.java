package com.vartista.www.vartista.modules.provider;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.CreateRequest;
import com.vartista.www.vartista.beans.servicepaapointmentsitems;
import com.vartista.www.vartista.modules.user.Service_user_cancel;
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
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.vartista.www.vartista.R;

import com.vartista.www.vartista.R;

public class ServiceCancelActivity extends AppCompatActivity {

    TextView Date,Time,Service,Spname,Location;
    ImageView Spimage;
    Button cancelservicep;
    public static ApiInterface apiInterface;
    int requestservice_id = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_cancel);
        Date = (TextView)findViewById(R.id.Spdate);
        Time = (TextView)findViewById(R.id.Sptime);
        Service = (TextView)findViewById(R.id.SpService_tittle);
        Spname = (TextView)findViewById(R.id.SpuserName);
        Location = (TextView)findViewById(R.id.Splocation);
        Spimage = (ImageView)findViewById(R.id.imageView7);
        cancelservicep = (Button)findViewById(R.id.cancelbuttonServiceP);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);


        Intent intent = getIntent();
        requestservice_id = intent.getIntExtra("service_id",0);
        Toast.makeText(this, ""+requestservice_id, Toast.LENGTH_SHORT).show();


        new ServiceCancelActivity.Conncetion(ServiceCancelActivity.this,requestservice_id).execute();

        cancelservicep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upaterequeststatus(requestservice_id);
            }
        });
    }


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
                                .into(Spimage);
                        Date.setText("Date : "+date);
                        Time.setText("Time : "+time);
                        Service.setText("Service : "+service_title);
                        Spname.setText("UserName : "+username);
                        Location.setText("Location : "+location);

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

    public void upaterequeststatus(int id){
        Call<CreateRequest> call= Service_user_cancel.apiInterface.updaterequeststatus(id);
        call.enqueue(new Callback<CreateRequest>() {
            @Override
            public void onResponse(Call <CreateRequest> call, Response<CreateRequest> response) {

                if(response.body().getResponse().equals("ok")){


                    Toast.makeText(ServiceCancelActivity.this,"Updated Successfully..",Toast.LENGTH_SHORT).show();

                }else if(response.body().getResponse().equals("exist")){


                    Toast.makeText(ServiceCancelActivity.this,"Same Data exists....",Toast.LENGTH_SHORT).show();

                }
                else if(response.body().getResponse().equals("error")){


                    Toast.makeText(ServiceCancelActivity.this,"Something went wrong....",Toast.LENGTH_SHORT).show();

                }
                else{


                    Toast.makeText(ServiceCancelActivity.this,"Something went wrong....",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call <CreateRequest> call, Throwable t) {

                Toast.makeText(ServiceCancelActivity.this,"Update Failed",Toast.LENGTH_SHORT).show();

            }
        });
    }







}
