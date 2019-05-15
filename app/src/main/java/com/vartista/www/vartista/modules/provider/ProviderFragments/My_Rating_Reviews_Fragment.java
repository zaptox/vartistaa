package com.vartista.www.vartista.modules.provider.ProviderFragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.valdesekamdem.library.mdtoast.MDToast;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.adapters.RatingsReviewDetailsAdaptor;
import com.vartista.www.vartista.beans.RatingsReviewDetailBean;
import com.vartista.www.vartista.modules.provider.My_Rating_Reviews;
import com.willy.ratingbar.ScaleRatingBar;

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


public class My_Rating_Reviews_Fragment extends Fragment {

    //Activity Code
    RecyclerView recyclerView;
    TextView headername;
    private RecyclerView.LayoutManager layoutManager;
    private RatingsReviewDetailsAdaptor listadapter;
    ArrayList<RatingsReviewDetailBean> list;
    int serviceproviderid;
    ScaleRatingBar ratingBar;
    Float serviceProvierRating=0.0f;

    public My_Rating_Reviews_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_my__rating__reviews, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.RatingsDetail);
        list=new ArrayList<RatingsReviewDetailBean>();
        headername = (TextView)view.findViewById(R.id.header_name);
        SharedPreferences ob =getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        SharedPreferences object =getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        serviceproviderid= object.getInt("user_id",0);
        String username = object.getString("name","");
        headername.setText(username);
        new My_Rating_Reviews_Fragment.Conncetion(getContext(),serviceproviderid).execute();
        ratingBar = view.findViewById(R.id.simpleRatingBar);
        ratingBar.setNumStars(5);
        ratingBar.setMinimumStars(1);
        ratingBar.setStarPadding(10);
        ratingBar.setStepSize(0.5f);
        ratingBar.setEnabled(false);
        return view;
    }

    class Conncetion extends AsyncTask<String,String ,String > {
        private int user_customer_id;
        private ProgressDialog dialog;

        public Conncetion(Context activity, int user_customer_id) {
            dialog = new ProgressDialog(activity);
            this.user_customer_id = user_customer_id;
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Retrieving data Please Wait..");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {


            String result = "";

            final String BASE_URL = "http://vartista.com/vartista_app/My_Ratings_Review.php?service_provider_id="+serviceproviderid;
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

            Double rating;

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
                        int id = Integer.parseInt(ser1.getString("id"));
                        Float stars = Float.parseFloat(ser1.getString("stars"));
                        String UserName = ser1.getString("UserName");
                        String user_id = ser1.getString("user_id");
                        String SpName = ser1.getString("SpName");
                        int service_p_id = Integer.parseInt(ser1.getString("service_p_id"));
                        String service_id = ser1.getString("service_id");
                        String service_tittle = ser1.getString("service_title");
                        String user_remarks = ser1.getString("user_remarks");
                        String Date = ser1.getString("date");
                        String Time = ser1.getString("time");
                        list.add(new RatingsReviewDetailBean(id,stars,UserName,user_id,SpName,service_p_id,service_id,service_tittle,user_remarks,Date,Time));
                        serviceProvierRating+=list.get(j).getStars();
                    }
                    listadapter = new RatingsReviewDetailsAdaptor(getContext(),list);
                    recyclerView.setAdapter(listadapter);
                    headername.setText(list.get(0).getSpName());
                    Float finalrating = (Float)serviceProvierRating/list.size();
                    ratingBar.setRating(finalrating);
                    ratingBar.setIsIndicator(true);
                    ratingBar.setFocusable(false);
                } else {
                    MDToast.makeText(getContext(),"no data",MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }



}
