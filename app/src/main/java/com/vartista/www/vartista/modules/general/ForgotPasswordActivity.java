package com.vartista.www.vartista.modules.general;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.valdesekamdem.library.mdtoast.MDToast;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.adapters.ServiceUserAppointmentsAdapter;
import com.vartista.www.vartista.beans.Category;
import com.vartista.www.vartista.beans.CreateRequest;
import com.vartista.www.vartista.beans.User;
import com.vartista.www.vartista.beans.forgotpassword;
import com.vartista.www.vartista.beans.servicepaapointmentsitems;
import com.vartista.www.vartista.modules.user.AssignRatings;
import com.vartista.www.vartista.modules.user.MyServiceMeetings;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText emaiverifyedittext,verificationcode,newpassword,confirmnewpassword;
    Button submitemail,submitcode,savepassword;
    TextView countdown;
    LinearLayout layoutforcode,layoutforemail,layoutforpasswords;
    public static ApiInterface apiInterface;
    CountDownTimer cTimer = null;
    String code = "";
    String email = "";
    String usersname = "";
    String usersid = "";
//    User userLoggedIn = null;
//    String userinputemail2;
//    boolean check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        emaiverifyedittext = (EditText)findViewById(R.id.emailverifyedittext);
        verificationcode = (EditText)findViewById(R.id.verification_code);
        newpassword = (EditText)findViewById(R.id.NewPassword);
        confirmnewpassword = (EditText)findViewById(R.id.ConfirmNewPassword);
        submitemail = (Button)findViewById(R.id.submitemail);
        submitcode = (Button)findViewById(R.id.submitcode);
        savepassword = (Button)findViewById(R.id.SavePassword);
        layoutforcode = (LinearLayout)findViewById(R.id.layoutforcode);
        layoutforemail = (LinearLayout)findViewById(R.id.layoutforemail);
        layoutforpasswords = (LinearLayout)findViewById(R.id.layoutfornewpassword);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        layoutforcode.setVisibility(View.GONE);
        layoutforpasswords.setVisibility(View.GONE);
        countdown = (TextView)findViewById(R.id.time);
        startTimer();
        submitemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emaiverifyedittext.getText().toString();
                new ForgotPasswordActivity.Connection(getApplicationContext(),email.trim().toString()).execute();
                layoutforemail.setVisibility(View.GONE);
                layoutforcode.setVisibility(View.VISIBLE);

            }


        });

        submitcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String users_code = verificationcode.getText().toString();

                if(users_code.equals(code)){
                    layoutforcode.setVisibility(View.GONE);
                    layoutforpasswords.setVisibility(View.VISIBLE);
                }

            }
        });

        savepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newpasword = newpassword.getText().toString();
                String confirmnewpasword = confirmnewpassword.getText().toString();
                if(newpasword.equals(confirmnewpasword)){
                    updatedata(Integer.parseInt(usersid),usersname,email,confirmnewpasword);
                }
            }
        });

    }




    //start timer function
    void startTimer() {
        cTimer = new CountDownTimer(120000, 1000) {
            public void onTick(long millisUntilFinished) {
                countdown.setText(""+millisUntilFinished/1000);
                }
            public void onFinish() {
            }
        };
        cTimer.start();
    }


    //cancel timer
    void cancelTimer() {
        if(cTimer!=null)
            cTimer.cancel();
    }






class Connection extends AsyncTask<String,String ,String > {
    private ProgressDialog dialog;
    String categoriesArray[]=null;
    Context context;
    String email=null;


    public  Connection(Context activity,String email) {
        dialog = new ProgressDialog(activity);
        context=activity;
        this.email=email;
    }


    @Override
    protected void onPreExecute() {
//        dialog.setMessage("Retriving data Please Wait..");
//        dialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {


        String result="";

        final String BASE_URL="http://vartista.com/vartista_app/forget_password.php?email="+email;
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
//            dialog.dismiss();
        }

        try {
            JSONObject jsonResult=new JSONObject(result);

            String success=""+jsonResult;



            if(success.contains("ok")){
                JSONArray ob = jsonResult.getJSONArray("user");
                code = jsonResult.getString("code");
                usersname = ob.getString(1);
                usersid = ob.getString(0);


            }

            else{
                        Toast.makeText(getApplicationContext(),"no data",Toast.LENGTH_SHORT).show();

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

    public void updatedata(int id,String name,String email,String password){
//        setUIToWait(true);

//    Call<User> call=SettingsActivity.apiInterface.updateUserSettings(id,name,email,password,user.getImage(),1,contact,0,0);
        Call<User> call=ForgotPasswordActivity.apiInterface.updateUser(name, email, password, id);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call <User> call, Response<User> response) {

                if(response.body().getResponse().equals("ok")){


                    Toast.makeText(ForgotPasswordActivity.this,"Updated Successfully..",Toast.LENGTH_SHORT).show();

                }else if(response.body().getResponse().equals("exist")){


                    Toast.makeText(ForgotPasswordActivity.this,"Same Data exists....",Toast.LENGTH_SHORT).show();

                }
                else if(response.body().getResponse().equals("error")){


                    Toast.makeText(ForgotPasswordActivity.this,"Something went wrong....",Toast.LENGTH_SHORT).show();

                }
                else{


                    Toast.makeText(ForgotPasswordActivity.this,"Something went wrong....",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call <User> call, Throwable t) {

                Toast.makeText(ForgotPasswordActivity.this,"Update Failed",Toast.LENGTH_SHORT).show();

            }
        });
    }

}
