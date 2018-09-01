package com.vartista.www.vartista.modules.provider;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.vartista.www.vartista.adapters.MyServicesListAdapter;

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

import com.vartista.www.vartista.R;

public class MyServiceRequests extends AppCompatActivity {


    RecyclerView listViewMyReqeustServices;
    MyServicesListAdapter myRequestServicesListAdapter;
    List<Service> myRqeuestservicesList;
    int user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_service_requests);

        listViewMyReqeustServices=(RecyclerView) findViewById(R.id.lvServiceList);
        listViewMyReqeustServices.setHasFixedSize(true);
        listViewMyReqeustServices.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        listViewMyReqeustServices.setLayoutManager(mLayoutManager);
        listViewMyReqeustServices.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        listViewMyReqeustServices.setItemAnimator(new DefaultItemAnimator());

        myRqeuestservicesList = new ArrayList<>();

        user_id=getIntent().getIntExtra("userId",0);

        new MyServiceRequests.Conncetion(MyServiceRequests.this,user_id);




    }



    class Conncetion extends AsyncTask<String,String ,String > {
        private ProgressDialog dialog;
        int userId;

        public  Conncetion(MyServiceRequests activity,int user_id) {
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

            final String BASE_URL="http://vartista.com/vartista_app/req_service.php?service_provider_id="+userId;
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

                listViewMyReqeustServices.setAdapter(myRequestServicesListAdapter);


                if(success==1){
                    //        Toast.makeText(getApplicationContext(),"Ok services are there",Toast.LENGTH_SHORT).show();
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
                        myRqeuestservicesList.add(new Service(service_id,user_id,category_name , service_title, service_description,  status,  price,  category_id,  created_at,  updated_at));

                    }

                }
                else{
                    //   Toast.makeText(getApplicationContext(),"no data",Toast.LENGTH_SHORT).show();

                }
            } catch (JSONException e) {
                e.printStackTrace();
                //   Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }
}
