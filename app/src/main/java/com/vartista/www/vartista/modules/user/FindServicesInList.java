package com.vartista.www.vartista.modules.user;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.Toast;

import com.vartista.www.vartista.R;
import com.vartista.www.vartista.adapters.ServicesInListMapAdapter;
import com.vartista.www.vartista.beans.GetServiceProviders;
import com.vartista.www.vartista.fragments.UsersFragment;
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
import java.util.List;

import static java.security.AccessController.getContext;

public class FindServicesInList extends AppCompatActivity {



    int user_id;
    Button btnMap;
    RecyclerView listViewMyServices;
    ServicesInListMapAdapter myServicesListAdapter;
    List<GetServiceProviders> myservicesList;
    
    public static ApiInterface apiInterface;
    ArrayList<GetServiceProviders> splist;
    @SuppressLint("ValidFragment")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_in_list);
        splist=new ArrayList<GetServiceProviders>();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Intent intent=getIntent();
        int cat_id=intent.getIntExtra("cat_id",0);

//                intent.putExtra("cat_id",cat_id);

        listViewMyServices=(RecyclerView) findViewById(R.id.lvFindServices);
        listViewMyServices.setHasFixedSize(true);
        listViewMyServices.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        listViewMyServices.setLayoutManager(mLayoutManager);
        listViewMyServices.addItemDecoration(new
                DividerItemDecoration(getApplicationContext(),
                DividerItemDecoration.VERTICAL));
        listViewMyServices.setItemAnimator(new DefaultItemAnimator());

        myservicesList=new ArrayList<>();


        new FindServicesInList.Conncetion(FindServicesInList.this,cat_id).execute();


    }






    class Conncetion extends AsyncTask<String,String ,String > {
        private int cat_id;
        private ProgressDialog dialog;

        public Conncetion(Context activity, int cat_id) {
            dialog = new ProgressDialog(activity);
            this.cat_id = cat_id;
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Retriving data Please Wait..");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {


            String result = "";

            final String BASE_URL = "http://vartista.com/vartista_app/get_service_providers.php?cat_id=" + cat_id;
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
                return new String("There is exception" + e.getMessage());
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            //  Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            try {
                JSONObject jsonResult = new JSONObject(result);
                int success = jsonResult.getInt("success");

                //    listViewMyServices.setAdapter(myServicesListAdapter);


                if (success == 1) {

                    JSONArray services = jsonResult.getJSONArray("services");


                    for (int j = 0; j < services.length(); j++) {

                        JSONObject ser1 = services.getJSONObject(j);
                        int service_id = Integer.parseInt(ser1.getString("service_id"));
                        int user_id = Integer.parseInt(ser1.getString("user_id"));
                        int address_id = Integer.parseInt(ser1.getString("id"));
                        int category_id = Integer.parseInt(ser1.getString("category_id"));
                        String service_title = ser1.getString("service_title");

                        String service_description = ser1.getString("service_description");
                        double price = Double.parseDouble(ser1.getString("price"));

                        double longitude = Double.parseDouble(ser1.getString("longitude"));
                        double latitude = Double.parseDouble(ser1.getString("latitude"));
                        String sp_name=ser1.getString("name");

                        splist.add(new GetServiceProviders(service_id, address_id, latitude, longitude, user_id, service_title, service_description, price, category_id,sp_name));






                    }
//                    Toast.makeText(FindServicesInList.this, ""+splist, Toast.LENGTH_SHORT).show();

                    myServicesListAdapter=new ServicesInListMapAdapter(getApplicationContext(),splist);
                    listViewMyServices.setAdapter(myServicesListAdapter);



                } else {
                    Toast.makeText(getApplicationContext(),"no data",Toast.LENGTH_SHORT).show();

                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }


        }

    }
}

