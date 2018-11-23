package com.vartista.www.vartista.modules.provider;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vartista.www.vartista.R;
import com.vartista.www.vartista.adapters.MyRequestsServicesListAdapter;
import com.vartista.www.vartista.beans.ServiceRequets;
import com.vartista.www.vartista.beans.User;
import com.vartista.www.vartista.beans.UserAddressBean;
import com.vartista.www.vartista.modules.general.SiginInActivity;
import com.vartista.www.vartista.modules.general.SignUpActivity;
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

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressSetActivity extends AppCompatActivity {



    private EditText country,province, city, zipcode, w_address,p_address;
    private Button set_address;
    private ProgressDialog progressDialog;
    public static ApiInterface apiInterface;

    int user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_set);

        country= findViewById(R.id.country);
        province= findViewById(R.id.province);
        city= findViewById(R.id.city);
        zipcode= findViewById(R.id.postal_code);
        w_address= findViewById(R.id.w_address);
        p_address= findViewById(R.id.p_address);
        set_address= findViewById(R.id.set_address);
        apiInterface= ApiClient.getApiClient().create(ApiInterface.class);
        SharedPreferences ob = getSharedPreferences("Login", Context.MODE_PRIVATE);

        user_id = ob.getInt("user_id", 0);

        new AddressSetActivity.Conncetion(AddressSetActivity.this,user_id).execute();


        set_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setUIToWait(true);

                String country_got=country.getText().toString();
                String province_got= province.getText().toString();
                String city_got= city.getText().toString();
                String zipcode_got= zipcode.getText().toString();
                String w_address_got= w_address.getText().toString();
                String p_address_got= p_address.getText().toString();

                SharedPreferences ob =getSharedPreferences("Login", Context.MODE_PRIVATE);
                int user_id=ob.getInt("user_id",0);



///*
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                Call<UserAddressBean> call= AddressSetActivity.apiInterface.insertUserAddress(w_address_got,p_address_got,city_got,province_got,zipcode_got,country_got,user_id);
                call.enqueue(new Callback<UserAddressBean>() {
                    @Override
                    public void onResponse(Call <UserAddressBean> call, Response<UserAddressBean> response) {

                        if(response.body().getResponse().equals("ok")){


                            setUIToWait(false);

                            startActivity(new Intent(AddressSetActivity.this,DocumentUploadActivity.class));

                        }else if(response.body().getResponse().equals("exist")){
                            setUIToWait(false);

                        }
                        else if(response.body().getResponse().equals("error")){
                            setUIToWait(false);

                            Toast.makeText(AddressSetActivity.this,"Something went wrong....",Toast.LENGTH_SHORT).show();

                        }

                        else{
                            setUIToWait(false);

                            Toast.makeText(AddressSetActivity.this,"Something went wrong....",Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onFailure(Call <UserAddressBean> call, Throwable t) {
                        setUIToWait(false);
                        Toast.makeText(AddressSetActivity.this,"Signup Failed",Toast.LENGTH_SHORT).show();


                    }
                });


            }
        });





}

    class Conncetion extends AsyncTask<String,String ,String > {
        private ProgressDialog dialog;
        int userId;

        public  Conncetion(AddressSetActivity activity,int user_id) {
            dialog = new ProgressDialog(activity);
            userId=user_id;

        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Retriving data Please Wait..");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {


            String result="";

            final String BASE_URL="http://vartista.com/vartista_app/fetch_address_stats.php?user_id="+userId;
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
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            try {
                JSONObject jsonResult=new JSONObject(result);
                int success=jsonResult.getInt("success");

                Toast.makeText(getApplicationContext(),jsonResult.toString(),Toast.LENGTH_SHORT).show();

                if(success==1){
                    JSONArray services=jsonResult.getJSONArray("services");
                    JSONObject service = services.getJSONObject(0);

                    w_address.setText(service.getString("work_address"));
                    p_address.setText(service.getString("permanent_address"));
                    city.setText(service.getString("city"));
                    province.setText(service.getString("province"));
                    zipcode.setText(service.getString("zipcode"));
                    country.setText(service.getString("country"));


                }
                else{
                    Toast.makeText(AddressSetActivity.this, "Insert Address", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }



    private void setUIToWait(boolean wait) {

        if (wait) {
            progressDialog = ProgressDialog.show(this, null, null, true, true);
//            progressDialog.setContentView(new ProgressBar(this));
            progressDialog.setContentView(R.layout.loader);

        } else {
            progressDialog.dismiss();
        }

    }

}
