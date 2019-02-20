package com.vartista.www.vartista.modules.provider;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;

import com.vartista.www.vartista.adapters.MyRequestsServicesListAdapter;
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
import com.vartista.www.vartista.beans.ServiceRequets;
import com.vartista.www.vartista.restcalls.ApiClient;
import com.vartista.www.vartista.restcalls.SendNotificationApiInterface;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.FlipInLeftYAnimator;
import jp.wasabeef.recyclerview.animators.FlipInRightYAnimator;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import jp.wasabeef.recyclerview.animators.holder.AnimateViewHolder;

public class MyServiceRequests extends AppCompatActivity {


    RecyclerView listViewMyReqeustServices;
    MyRequestsServicesListAdapter myRequestServicesListAdapter;
    List<ServiceRequets> serviceRequestsList;
    public static SendNotificationApiInterface sendNotificationApiInterface;



    int user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_service_requests);

        listViewMyReqeustServices=(RecyclerView) findViewById(R.id.myreqeust_ServiceList);
        listViewMyReqeustServices.setItemAnimator(new SlideInLeftAnimator());
        listViewMyReqeustServices.setHasFixedSize(true);
        listViewMyReqeustServices.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        listViewMyReqeustServices.setLayoutManager(mLayoutManager);
        listViewMyReqeustServices.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
 //     SlideInUpAnimator animator = new SlideInUpAnimator(new OvershootInterpolator(1f));
        SlideInLeftAnimator animator = new SlideInLeftAnimator();
        animator.setInterpolator(new OvershootInterpolator());
        listViewMyReqeustServices.setItemAnimator(animator);
        listViewMyReqeustServices.getItemAnimator().setRemoveDuration(1000);



        serviceRequestsList= new ArrayList<ServiceRequets>();

        user_id=getIntent().getIntExtra("user",0);


        new MyServiceRequests.Conncetion(MyServiceRequests.this,user_id).execute();


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


                if(success==1){
                    JSONArray services=jsonResult.getJSONArray("services");
                    for(int i=0;i<services.length();i++){

                        JSONObject service = services.getJSONObject(i);

                        int requestservice_id = (Integer.parseInt(service.getString("requestservice_id")));
                        String user_name =  service.getString("username");
                        String image=service.getString("image");
                        int status = Integer.parseInt(service.getString("request_status"));
                        String date = service.getString("date");
                        String time = service.getString("time");
                        String location = service.getString("location");
                        int user_customer_id=(Integer.parseInt(service.getString("user_customer_id")));
                        int service_provider_id= (Integer.parseInt(service.getString("service_provider_id")));
                        int service_id= (Integer.parseInt(service.getString("service_id")));
                        int service_cat_id =(Integer.parseInt(service.getString("service_cat_id")));
                        String service_title=service.getString("service_title");
                        double price = service.getDouble("price");
                        String service_description= service.getString("service_description");
                        String category_name=service.getString("catgname");
                        serviceRequestsList.add(new ServiceRequets(requestservice_id,user_name,status,date,time,location,user_customer_id,
                                service_provider_id,service_id,service_cat_id,service_title,price,service_description,category_name,image
                        ));
                    }


//                    myRequestServicesListAdapter= new MyRequestsServicesListAdapter(getApplicationContext(),serviceRequestsList);

//                    listViewMyReqeustServices.setAdapter(myRequestServicesListAdapter);



                    myRequestServicesListAdapter = new MyRequestsServicesListAdapter(MyServiceRequests.this,serviceRequestsList);
                    AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(myRequestServicesListAdapter);
                    alphaAdapter.setDuration(1000);
                    alphaAdapter.setInterpolator(new OvershootInterpolator());
                    listViewMyReqeustServices.setAdapter(new ScaleInAnimationAdapter(alphaAdapter));



                }
                else{

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



    static class MyViewHolder extends RecyclerView.ViewHolder implements AnimateViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void preAnimateRemoveImpl(RecyclerView.ViewHolder holder) {

        }

        @Override
        public void animateRemoveImpl(RecyclerView.ViewHolder holder, ViewPropertyAnimatorListener listener) {
            ViewCompat.animate(itemView)
                    .translationY(-itemView.getHeight() * 0.3f)
                    .alpha(0)
                    .setDuration(300)
                    .setListener(listener)
                    .start();


        }

        @Override
        public void preAnimateAddImpl(RecyclerView.ViewHolder holder) {
            ViewCompat.setTranslationY(itemView, -itemView.getHeight() * 0.3f);
            ViewCompat.setAlpha(itemView, 0);
        }

        @Override
        public void animateAddImpl(RecyclerView.ViewHolder holder, ViewPropertyAnimatorListener listener) {
            ViewCompat.animate(itemView)
                    .translationY(0)
                    .alpha(1)
                    .setDuration(300)
                    .setListener(listener)
                    .start();
        }
    }
}
