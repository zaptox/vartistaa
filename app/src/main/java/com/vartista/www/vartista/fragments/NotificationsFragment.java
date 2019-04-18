package com.vartista.www.vartista.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.vartista.www.vartista.R;
import com.vartista.www.vartista.adapters.TwoListInRecyclerView;
import com.vartista.www.vartista.beans.usernotificationitems;
import com.vartista.www.vartista.modules.general.Asynctask_MultipleUrl;
import com.vartista.www.vartista.modules.provider.DocumentUploadActivity;

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
 * Created by Dell on 2019-04-18.
 */

public class NotificationsFragment extends Fragment {


    RecyclerView view;
    private RecyclerView.LayoutManager layoutManager;
    private TwoListInRecyclerView listadapter;
    ArrayList<usernotificationitems> requestlist;
    ArrayList<usernotificationitems> notificationlist;
    TabLayout tabLayout;
    int user_id;



    public NotificationsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view1 =inflater.inflate(R.layout.activity_asynctask__multiple_url, container, false);
//        become_sp=(Button)view.findViewById(R.id.become_sp);
        view = (RecyclerView)view1.findViewById(R.id.user_notificationlist);
        requestlist=new ArrayList<usernotificationitems>();
        notificationlist=new ArrayList<usernotificationitems>();
        layoutManager = new LinearLayoutManager(getContext());
        view.setHasFixedSize(true);
        view.setLayoutManager(layoutManager);
        SharedPreferences ob = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        tabLayout=  getActivity().findViewById(R.id.tabs);
        user_id = ob.getInt("user_id", 0);
        new NotificationsFragment.Conncetion(getContext(),user_id).execute();
        tabLayout.setVisibility(View.GONE);
//        become_sp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent=new Intent(getContext(),DocumentUploadActivity.class);
//                intent.putExtra("userId",user_id);
//                startActivity(intent);
//
//
//            }
//        });
        return view1;
    }


    class Conncetion extends AsyncTask<String,String ,String[]> {
        private int user_customer_id;
        private ProgressDialog dialog;

        public Conncetion(Context activity, int user_customer_id) {
            dialog = new ProgressDialog(activity);
            this.user_customer_id = user_customer_id;
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Retriving data Please Wait..");
            dialog.show();
        }

        @Override
        protected String[] doInBackground(String... strings) {

            final String BASE_URL = "http://vartista.com/vartista_app/usernotificationstatus.php?user_customer_id="+user_id;
            final String BASE_URL2 = "http://vartista.com/vartista_app/fetch_notificationmsg.php?user_id="+user_id;
            String[] ob = new String[2];
            ob[0] = getjsonfromurl(BASE_URL);
            ob[1] = getjsonfromurl(BASE_URL2);

            return ob;
        }

        @Override
        protected void onPostExecute(String[] result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            try {

                JSONObject jsonResult = new JSONObject(result[0]);
                JSONObject jsonResult2 = new JSONObject(result[1]);

                int success = jsonResult.getInt("success");
                int success2 = jsonResult2.getInt("success");



                if (success == 1) {
                    JSONArray services = jsonResult.getJSONArray("services");
                    for (int j = 0; j < services.length(); j++) {
                        JSONObject ser1 = services.getJSONObject(j);
                        String username = ser1.getString("username");
                        String image = ser1.getString("image");
                        String request_detail = ser1.getString("request_status");
                        String Time = ser1.getString("time");
                        String reqest_sendat = ser1.getString("reqeustsend_at") ;
                        String accepted_date = ser1.getString("accepted_date");
                        String rejected_date = ser1.getString("rejected_date");
                        String pay_verify_date = ser1.getString("pay_verify_date");
                        String Cancelled_date = ser1.getString("cancelled_date");
                        String completed_date = ser1.getString("completed_date");
                        String Canceled_by_id = ser1.getString("cancelled_by_id");
                        String Service_title = ser1.getString("service_title");
                        Double price = ser1.getDouble("price");
                        requestlist.add(new usernotificationitems(username,image,request_detail,Time,reqest_sendat,accepted_date,rejected_date,pay_verify_date,Cancelled_date,completed_date,Canceled_by_id,Service_title,price));
                    }
                } else {
                }
                if(success2==1){
                    JSONArray services2 = jsonResult2.getJSONArray("services");
                    for ( int i = 0 ; i < services2.length(); i++){
                        JSONObject serv2 = services2.getJSONObject(i);
                        String notificationid =  serv2.getString("id");
                        String username = serv2.getString("name");
                        String title = serv2.getString("title");
                        String msg = serv2.getString("msg");
                        String created_at = serv2.getString("created_at");
                        String sp_status = serv2.getString("sp_status");
                        notificationlist.add(new usernotificationitems(notificationid,username,title,msg,created_at,sp_status));
                    }
                } else {
                }

                listadapter = new TwoListInRecyclerView(getContext(),requestlist,notificationlist);
                view.setAdapter(listadapter);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }




    public String getjsonfromurl(String BASE_URL){
        String result = "";
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
        return  result;
    }



}
