package com.vartista.www.vartista.modules.user.user_fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.valdesekamdem.library.mdtoast.MDToast;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.adapters.ServiceUserAppointmentsAdapter;
import com.vartista.www.vartista.beans.servicepaapointmentsitems;
import com.vartista.www.vartista.modules.user.MyCompletedServices;

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


public class MyCompletedServicesFragment extends Fragment {
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ServiceUserAppointmentsAdapter listadapter;
    ArrayList<servicepaapointmentsitems> userAppointments;
    int user_id1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_my_completed_services,container,false);

        recyclerView = (RecyclerView)view.findViewById(R.id.Userappointment_recyclerView);
        userAppointments=new ArrayList<servicepaapointmentsitems>();
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        SharedPreferences ob =getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        user_id1 = ob.getInt("user_id",0);
        new MyCompletedServicesFragment.Conncetion(getActivity(),user_id1).execute();
        return view;
    }

    class Conncetion extends AsyncTask<String,String ,String > {
        private int user_id1;
        private ProgressDialog dialog;

        public Conncetion(Context activity, int user_id1) {
            dialog = new ProgressDialog(activity);
            this.user_id1 = user_id1;
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Retriving data Please Wait..");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {


            String result = "";

            final String BASE_URL = "http://vartista.com/vartista_app/service_completed_user.php?user_customer_id="+user_id1;
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
                        String username = ser1.getString("username");
                        String service_description = ser1.getString("service_description");
                        String location = ser1.getString("location");
                        String request_status = ser1.getString("request_status");
                        String date = ser1.getString("date");
                        String service_title = ser1.getString("service_title");
                        String price = ser1.getString("price");
                        String name = ser1.getString("name");
                        String Time = ser1.getString("time");
                        String image=ser1.getString("image");
                        String contact = ser1.getString("contact");
                        String rating_status=ser1.getString("rating_status");
                        String rating_id=ser1.getString("rating_id");
                        userAppointments.add(new servicepaapointmentsitems(requestservice_id,user_customer_id,service_provider_id,username,service_description,location,request_status,date,service_title,price,name,Time,image,contact,rating_status,rating_id));
                    }


                    listadapter = new ServiceUserAppointmentsAdapter(getContext(),userAppointments,true);
                    recyclerView.setAdapter(listadapter);

                }

                else {
                    MDToast.makeText(getContext(),"no data",MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}