package com.vartista.www.vartista.modules.provider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bcgdv.asia.lib.ticktock.TickTockView;
import com.squareup.picasso.Picasso;
import com.valdesekamdem.library.mdtoast.MDToast;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.adapters.MyRequestsServicesListAdapter;
import com.vartista.www.vartista.beans.NotificationsManager;
import com.vartista.www.vartista.beans.ServiceRequets;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestAlertActivity extends AppCompatActivity {

    TextView  txtUserName, TxtUserNameAddress, txtUserReqServ;
    ImageView reqUserImage;
    TickTockView mCountDown;
    String service_Id = "";
    String serv_provider_id = "";
    String reqUserId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_alert);
        txtUserName = findViewById(R.id.userName);
        TxtUserNameAddress = findViewById(R.id.userlocation);
        txtUserReqServ = findViewById(R.id.user_reqserv);
        reqUserImage = findViewById(R.id.reqUserIdImage);

        Intent I = getIntent();
        reqUserId = I.getStringExtra("reqUserId");
        serv_provider_id = I.getStringExtra("serv_prv_Id");
        service_Id = I.getStringExtra("serv_Id");

        new AsyncTask<String, Void, String>() {

            String result = "" ;

            @Override
            protected String doInBackground(String... strings) {
                try {
                    HttpClient client = new DefaultHttpClient();
                    HttpGet request = new HttpGet();

                    String user_id = strings[0];
                    String servprv_id = strings[1];
                    String serv_id = strings[2];

                    final String BASE_URL = "http://vartista.com/vartista_app/get_user_and_reqinfo.php?id="+user_id+"&serv_prv_Id="+servprv_id+"&serv_Id="+serv_id;
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
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {

                try {
                    JSONObject jsonResult = new JSONObject(result);
                    String success = jsonResult.getString("response");

                    if (success.equals("ok")) {
                        String username = jsonResult.getString("name");
                        if(username != null){
                            txtUserName.setText(username);
                        }

                        String I = jsonResult.getString("image");
                        if(I != null || I.length()>0){
                            Picasso.get().load(I).fit().centerCrop()
                                    .placeholder(R.drawable.person)
                                    .error(R.drawable.person)
                                    .into(reqUserImage);
                        }
                        String location = jsonResult.getString("location");
                        String city = jsonResult.getString("city");
                        String requested_serv_title = jsonResult.getString("service_title");

                        TxtUserNameAddress.setText(location);
                        txtUserReqServ.setText(city);
                        txtUserReqServ.setText(requested_serv_title);
                    }

                }

                catch (JSONException e){

                }


            }
        }.execute(reqUserId, serv_provider_id, service_Id);

        mCountDown = (TickTockView) findViewById(R.id.view_ticktock_countdown);
        if (mCountDown != null) {
            mCountDown.setOnTickListener(new TickTockView.OnTickListener() {
                @Override
                public String getText(long timeRemaining) {
                    int seconds = (int) (timeRemaining / 1000) % 60;
                    int minutes = (int) ((timeRemaining / (1000 * 60)) % 60);
                    int hours = (int) ((timeRemaining / (1000 * 60 * 60)) % 24);
                    int days = (int) (timeRemaining / (1000 * 60 * 60 * 24));
                    boolean hasDays = days > 0;
                    return String.format("%2$02d%5$s %3$02d%6$s",
                            hasDays ? days : hours,
                            hasDays ? hours : minutes,
                            hasDays ? minutes : seconds,
                            hasDays ? "d" : "h",
                            hasDays ? "h" : "m",
                            hasDays ? "m" : "s");
                }
            });

        }
    }
    //    public void remove(int position) {
//        myReqServicesList.remove(position);
//        notifyItemRemoved(position);
//    }
    public void OnClick(final View V){
        switch (V.getId()) {
            case  R.id.button_paynow :
                int status = 1;
                int requestservice_id = Integer.parseInt(service_Id);

                Call<ServiceRequets> call = MyRequestsServicesListAdapter.apiInterface.updateOnClickRequests(status,requestservice_id);
                call.enqueue(new Callback<ServiceRequets>() {
                    @Override
                    public void onResponse(Call<ServiceRequets> call, Response<ServiceRequets> response) {

                        SharedPreferences ob = getSharedPreferences("Login", Context.MODE_PRIVATE);

                        final String name_user = ob.getString("name","");


//                            Toast.makeText(context,myReqServicesList.get(position).getService_provider_id(),Toast.LENGTH_SHORT).show();
                        if(response.body().getResponse().equals("ok")){

                            //notifyDataSetChanged();
                            Call<NotificationsManager> callNotification = MyRequestsServicesListAdapter.sendNotificationApiInterface
                                    .sendPushNotification(Integer.parseInt(reqUserId),
                                            name_user+ " Accepted  your request","Vartista-Accept");
                            callNotification.enqueue(new Callback<NotificationsManager>() {
                                @Override
                                public void onResponse(Call<NotificationsManager> call, Response<NotificationsManager> response) {
                                    if(response.isSuccessful())
                                        MDToast.makeText(V.getContext(),"Request Accepted", Toast.LENGTH_SHORT).show();
                                }


                                @Override
                                public void onFailure(Call<NotificationsManager> call, Throwable t) {

                                }
                            });

                        }

                        else if(response.body().getResponse().equals("error")){

                            Toast.makeText(V.getContext(),"Something went wrong....",Toast.LENGTH_SHORT).show();

                        }
                        else{
                            Toast.makeText(V.getContext(),"Something went wrong....",Toast.LENGTH_SHORT).show();

                        }
//                        remove(position);



                    }

                    @Override
                    public void onFailure(Call<ServiceRequets> call, Throwable t) {
                        Toast.makeText(V.getContext(),"Update Failed",Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Calendar end = Calendar.getInstance();
        end.add(Calendar.MINUTE, 1);
        end.add(Calendar.SECOND, 1);

        Calendar start = Calendar.getInstance();
        start.add(Calendar.MINUTE, -1);
        if (mCountDown != null) {
            mCountDown.start(start, end);
        }

        Calendar c2= Calendar.getInstance();
        c2.add(Calendar.HOUR, 2);
        c2.set(Calendar.MINUTE, 0);
        c2.set(Calendar.SECOND, 0);
        c2.set(Calendar.MILLISECOND, 0);

    }

    @Override
    protected void onStop() {
        super.onStop();
        mCountDown.stop();

    }
}
