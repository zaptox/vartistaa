package com.vartista.www.vartista.modules.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.valdesekamdem.library.mdtoast.MDToast;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.CreateRequest;
import com.vartista.www.vartista.modules.general.HomeActivity;
import com.vartista.www.vartista.restcalls.ApiClient;
import com.vartista.www.vartista.restcalls.ApiInterface;
import com.willy.ratingbar.BaseRatingBar;
import com.willy.ratingbar.ScaleRatingBar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignRatings extends AppCompatActivity {

   TextView   Username,time,Date,location,service;
   EditText user_remarks;
   Button done;
   double rating;
    Intent intent;
    public static ApiInterface apiInterface;
    int service_id,service_p_id,user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_ratings);
        ScaleRatingBar ratingBar = findViewById(R.id.simpleRatingBar);
        ratingBar.setNumStars(5);
        ratingBar.setMinimumStars(1);
        ratingBar.setRating(0);
        ratingBar.setStarPadding(10);
        ratingBar.setStepSize(0.5f);
        ratingBar.setEmptyDrawableRes(R.drawable.empty);
        ratingBar.setFilledDrawableRes(R.drawable.filled);
        user_remarks = (EditText)findViewById(R.id.user_remarks);
        service = (TextView)findViewById(R.id.Service_tittle);
        time = (TextView)findViewById(R.id.time);
        Date = (TextView)findViewById(R.id.date);
        location = (TextView)findViewById(R.id.location);
        Username= (TextView)findViewById(R.id.header_name);
        done = (Button)findViewById(R.id.done);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
         intent = getIntent();
        service.setText("Service :-  "+intent.getStringExtra("Service_Tittle"));
        time.setText("Time :-  "+intent.getStringExtra("Time"));
        Date.setText("Date :-  "+intent.getStringExtra("Date"));
        location.setText("Location :-  "+intent.getStringExtra("location"));
        Username.setText(intent.getStringExtra("UserName"));
        service_id = Integer.parseInt(intent.getStringExtra("Requestservice_id"));
        service_p_id = Integer.parseInt(intent.getStringExtra("Service_p_id"));
        user_id = Integer.parseInt(intent.getStringExtra("user_id"));
        rating = 0.0;
        ratingBar.setOnRatingChangeListener(new BaseRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(BaseRatingBar baseRatingBar, float v) {

                rating = (double)v;
                Toast.makeText(AssignRatings.this, ""+rating, Toast.LENGTH_SHORT).show();

            }


        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String servicetittle = intent.getStringExtra("Service_Tittle");
                String Remarks = user_remarks.getText().toString();
                String time = intent.getStringExtra("Time");
                String date = intent.getStringExtra("Date");
                Call<CreateRequest> call = AssignRatings.apiInterface.InsertRatings(0,rating,user_id,service_p_id,service_id,Remarks,date,time);

                call.enqueue(new Callback<CreateRequest>() {
                    @Override
                    public void onResponse(Call<CreateRequest> call, Response<CreateRequest> response) {
                        if (response.body().getResponse().equals("ok")) {

                            MDToast mdToast = MDToast.makeText(getApplicationContext(), "Request has been Send succesfully.", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
                            mdToast.show();

                            Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
                            intent.putExtra("user", HomeActivity.user);

                            startActivity(intent);

                        }


                    }

                    @Override
                    public void onFailure(Call<CreateRequest> call, Throwable t) {
                        //
                        // Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                });


            }
        });










    }
}
