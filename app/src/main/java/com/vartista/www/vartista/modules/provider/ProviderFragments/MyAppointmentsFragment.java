package com.vartista.www.vartista.modules.provider.ProviderFragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.vartista.www.vartista.R;
import com.vartista.www.vartista.adapters.servicepappointmentsadapter;
import com.vartista.www.vartista.beans.servicepaapointmentsitems;
import com.vartista.www.vartista.modules.provider.MyAppointments;

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

public class MyAppointmentsFragment extends Fragment {

    RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private servicepappointmentsadapter listadapter;
    ArrayList<servicepaapointmentsitems> myappointments;
    int service_id;
    private FragmentActivity myContext;
    ImageView imageView;


    public MyAppointmentsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_my_appointments, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.service_provider_appointments);
        myappointments=new ArrayList<servicepaapointmentsitems>();
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);


        SharedPreferences ob =getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);

        service_id=ob.getInt("user_id",0);
        Toast.makeText(getContext(), ""+service_id, Toast.LENGTH_SHORT).show();
        new MyAppointmentsFragment.Conncetion(getContext(),service_id).execute();



        return view;
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

            final String BASE_URL = "http://vartista.com/vartista_app/servicepappointments.php?service_provider_id="+service_id;
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
                        int service_id= ser1.getInt("service_id");
                        String service_provider_id = ser1.getString("service_provider_id");
                        String username = ser1.getString("username");
                        String image = ser1.getString("image");
                        String service_description = ser1.getString("service_description");
                        String location = ser1.getString("location");
                        String request_status = ser1.getString("request_status");
                        String date = ser1.getString("date");
                        String service_title = ser1.getString("service_title");
                        String price = ser1.getString("price");
                        String name = ser1.getString("name");
                        String Time = ser1.getString("time");
                        String contact= ser1.getString("contact");
                        String ratingid = ser1.getString("ratingid");
                        Toast.makeText(getActivity(), requestservice_id+":"+ratingid, Toast.LENGTH_SHORT).show();
//                        int rating_status=ser1.getInt("rating_status");
                        myappointments.add(new servicepaapointmentsitems(requestservice_id,user_customer_id,service_provider_id,username,service_description,location,request_status,date,service_title,price,name,Time,image,contact,service_id,ratingid));
                    }


                    listadapter = new servicepappointmentsadapter(getContext(),myappointments);
                    recyclerView.setAdapter(listadapter);

                }

                else {
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }




}
