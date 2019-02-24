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
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.adapters.SpDetailsAdapter;
import com.vartista.www.vartista.beans.Service;

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

public class ServiceProviderDetail extends AppCompatActivity {
        RecyclerView listViewSpDetials;
        SpDetailsAdapter myServicesListAdapter;
        List<Service> myservicesList;
        int provider_id;
        int cat_id;
        int user_id;
        String spname;
        ImageView imageViewProfileImage;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_service_provider_detail);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//            setSupportActionBar(toolbar);
            listViewSpDetials=(RecyclerView) findViewById(R.id.services_sp);
            listViewSpDetials.setHasFixedSize(true);
            listViewSpDetials.setLayoutManager(new LinearLayoutManager(this));
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            listViewSpDetials.setLayoutManager(mLayoutManager);
            imageViewProfileImage=(ImageView)findViewById(R.id.profile_image);

            listViewSpDetials.setItemAnimator(new DefaultItemAnimator());

            myservicesList=new ArrayList<>();
            Intent intent=getIntent();

            provider_id=intent.getIntExtra("s_provider_id",0);
            cat_id=intent.getIntExtra("cat_id",0);
            user_id=intent.getIntExtra("user_id",0);
            spname=intent.getStringExtra("spname");
            String spProfileImage=intent.getStringExtra("profile_photo");
            String serviceTitle=intent.getStringExtra("service_title");




            String spFullName[]=spname.split(" ");
            getSupportActionBar().setTitle(spFullName[0]);
            getSupportActionBar().setSubtitle(serviceTitle);

            Picasso.get().load(spProfileImage)
                    .placeholder(R.drawable.profile)
                    .error(R.drawable.profile)
                    .into(imageViewProfileImage);


       new Conncetion(ServiceProviderDetail.this,provider_id,cat_id).execute();


            myServicesListAdapter=new SpDetailsAdapter(getApplicationContext(),myservicesList,provider_id,cat_id,user_id);


        }
        class Conncetion extends AsyncTask<String,String ,String > {
            private ProgressDialog dialog;
            int provd_id;
            int category_id;

            public  Conncetion(ServiceProviderDetail activity, int provider_id, int cat_id) {
                dialog = new ProgressDialog(activity);
                this.provd_id=provider_id;
                this.category_id=cat_id;
            }

            @Override
            protected void onPreExecute() {
                dialog.setMessage("Retriving data Please Wait..");
                dialog.show();
            }

            @Override
            protected String doInBackground(String... strings) {


                String result="";

                final String BASE_URL="http://vartista.com/vartista_app/fetch_services_by_user_id.php?user_id="+provd_id+"&cat_id="+category_id;
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
                            int home_avail_status= service.getInt("home_avail_status");
                            myservicesList.add(new Service(service_id,user_id,category_name , service_title, service_description,  status,  price,  category_id,  created_at,  updated_at,home_avail_status));

                        }

                    }
                    else{

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

    }

