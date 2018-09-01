package com.vartista.www.vartista.modules.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.vartista.www.vartista.R;
import com.vartista.www.vartista.adapters.SpDetailsAdapter;
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
import java.util.List;

import com.vartista.www.vartista.beans.Service;

public class BookNowActivity extends AppCompatActivity {
    int provider_id, cat_id, user_id, service_id;
    DatePicker dp_datepicker;
    TimePicker timePicker1;
    EditText editTextaddress;
    Button buttonBook;
    String sDate, sTime, address;
    public static ApiInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_now);
        dp_datepicker = (DatePicker) findViewById(R.id.dp_datepicker);
        timePicker1 = (TimePicker) findViewById(R.id.timePicker1);
        editTextaddress = (EditText) findViewById(R.id.address);
        buttonBook = (Button) findViewById(R.id.buttonBook);
//        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
//
//
//        Intent intent = getIntent();
//        provider_id = intent.getIntExtra("provider_id", 0);
//        cat_id = intent.getIntExtra("cat_id", 0);
//        user_id = intent.getIntExtra("user_id", 0);
//        service_id = intent.getIntExtra("service_id", 0);
//        address = editTextaddress.getText().toString();
//        String a = "" + provider_id + "" + cat_id + "" + user_id + "" + service_id;
    //    Toast.makeText(getApplicationContext(), a, Toast.LENGTH_LONG).show();

        buttonBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//
//
//                Call<CreateRequest> call = BookNowActivity.apiInterface.createRequest(
//                       user_id,provider_id,service_id,"0000-00-00","00:00",address,1,1);
//
//                call.enqueue(new Callback<CreateRequest>() {
//                    @Override
//                    public void onResponse(Call<CreateRequest> call, Response<CreateRequest> response) {
//                        if (response.body().equals("ok")) {
//        //                    Toast.makeText(getApplicationContext(), "Registered"+user_id, Toast.LENGTH_SHORT).show();
//
//                        }
//                        if (response.isSuccessful()) {
//                            Toast.makeText(getApplicationContext(), "Registered", Toast.LENGTH_SHORT).show();
//
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<CreateRequest> call, Throwable t) {
//                        Toast.makeText(getApplicationContext(), "registered ", Toast.LENGTH_SHORT).show();
//                    }
//
//                });

                // startActivity(new Intent(getApplicationContext(),HomeActivity.class));

            }
        });

    }

    public static class ServiceProviderDetail extends AppCompatActivity {
        RecyclerView listViewSpDetials;
        SpDetailsAdapter myServicesListAdapter;
        List<Service> myservicesList;
        int provider_id;
        int cat_id;
        int user_id;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_service_provider_detail);


            listViewSpDetials=(RecyclerView) findViewById(R.id.services_sp);
            listViewSpDetials.setHasFixedSize(true);
            listViewSpDetials.setLayoutManager(new LinearLayoutManager(this));
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            listViewSpDetials.setLayoutManager(mLayoutManager);
            listViewSpDetials.addItemDecoration(new
                    DividerItemDecoration(getApplicationContext(),
                    DividerItemDecoration.VERTICAL));
            listViewSpDetials.setItemAnimator(new DefaultItemAnimator());

            myservicesList=new ArrayList<>();
            Intent intent=getIntent();

            provider_id=intent.getIntExtra("s_provider_id",0);
            cat_id=intent.getIntExtra("cat_id",0);
            user_id=intent.getIntExtra("user_id",0);


//            new Conncetion(getApplication(),provider_id,cat_id).execute();


            myServicesListAdapter=new SpDetailsAdapter(getApplicationContext(),myservicesList,provider_id,cat_id,user_id);


        }
        class Conncetion extends AsyncTask<String,String ,String > {
            private ProgressDialog dialog;
            int userId;

            public  Conncetion(BookNowActivity activity, int user_id, int cat_id) {
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

                final String BASE_URL="http://www.zaptox.com/mehdiTask/fetch_services_by_user_id.php?user_id="+userId+"&cat_id="+cat_id;
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

                    listViewSpDetials.setAdapter(myServicesListAdapter);


                    if(success==1){
                       // Toast.makeText(getApplicationContext(),"Ok services are there",Toast.LENGTH_SHORT).show();
                        JSONArray services=jsonResult.getJSONArray("services");
                        for(int i=0;i<services.length();i++){

                            JSONObject service=services.getJSONObject(i);
                            int service_id=service.getInt("service_id");
                            String service_title=service.getString("service_title");
                            double price=service.getDouble("price");
                            int status=service.getInt("status");
                            String created_at=service.getString("created_at");
                            String updated_at=service.getString("updated_at");
                            int category_id=service.getInt("category_id");
                            String service_description=service.getString("service_description");
                            String category_name=service.getString("name");
                            int user_id=service.getInt("user_id");
                            myservicesList.add(new Service(service_id,user_id,category_name , service_title, service_description,  status,  price,  category_id,  created_at,  updated_at));

                        }

                    }
                    else{
                      //  Toast.makeText(getApplicationContext(),"no data",Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                  //  Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        }

    }
}