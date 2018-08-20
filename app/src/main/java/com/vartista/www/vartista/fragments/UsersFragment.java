package com.vartista.www.vartista.fragments;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.vartista.www.vartista.ApiClient;
import com.vartista.www.vartista.ApiInterface;
import com.vartista.www.vartista.GetServiceProviders;
import com.vartista.www.vartista.MapActivity;
import com.vartista.www.vartista.R;

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


/**
 * A simple {@link Fragment} subclass.
 */
public class UsersFragment extends Fragment {
    int user_id;
    Button btnMap;
    public static ApiInterface apiInterface;
    ArrayList<GetServiceProviders> splist;
    @SuppressLint("ValidFragment")
    public UsersFragment(int user_id) {
        // Required
        // public constructor
        this.user_id=user_id;
    }

    public UsersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_users, container, false);
        btnMap=view.findViewById(R.id.buttonBeauty);
        splist=new ArrayList<GetServiceProviders>();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Conncetion(getContext(),1).execute();


            }

        });
   return  view; }


    class Conncetion extends AsyncTask<String,String ,String > {
        private int cat_id;
        private ProgressDialog dialog;

        public  Conncetion(Context activity, int cat_id) {
            dialog = new ProgressDialog(activity);
            this.cat_id =cat_id;
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Retriving data Please Wait..");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {


            String result="";

            final String BASE_URL="http://www.zaptox.com/mehdiTask/get_service_providers.php?cat_id="+ cat_id;
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
            //  Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            try {
                JSONObject jsonResult=new JSONObject(result);
                int success=jsonResult.getInt("success");

                //    listViewMyServices.setAdapter(myServicesListAdapter);


                if(success==1){

                    JSONArray services=jsonResult.getJSONArray("services");


                    for(int j=0; j<services.length(); j++){

                        JSONObject ser1=services.getJSONObject(j);
                        int service_id=Integer.parseInt(ser1.getString("service_id"));
                        int user_id=Integer.parseInt(ser1.getString("user_id"));
                        int address_id=Integer.parseInt(ser1.getString("id"));
                        int category_id=Integer.parseInt(ser1.getString("category_id"));
                        String service_title=ser1.getString("service_title");

                        String service_description=ser1.getString("service_description");
                        double price=Double.parseDouble(ser1.getString("price"));

                        double longitude=Double.parseDouble(ser1.getString("longitude"));
                        double latitude=Double.parseDouble(ser1.getString("latitude"));
                        splist.add(new GetServiceProviders(service_id,address_id,latitude,longitude,user_id,service_title,service_description,price,category_id));

                       // Toast.makeText(getContext(), services.length()+"----"+longitude, Toast.LENGTH_SHORT).show();



                    }


//                    Intent intent= new Intent(getContext(),MapActivity.class);
                    Intent intent= new Intent(getContext(),MapActivity.class);

                    intent.putParcelableArrayListExtra("service_providers",splist);
                    intent.putExtra("user_id",user_id);
                    startActivity(intent);


                }
                else{
                    Toast.makeText(getContext(),"no data",Toast.LENGTH_SHORT).show();

                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }



        }
    }

}
