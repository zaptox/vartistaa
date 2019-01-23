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

   TextView   Username,time,Date,location,service,ratingtext;
   EditText user_remarks;
   Button done;
   double rating;
    Intent intent;
    public static ApiInterface apiInterface;

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
        ratingtext = (TextView)findViewById(R.id.ratingtext);
        time = (TextView)findViewById(R.id.time);
        Date = (TextView)findViewById(R.id.date);
        location = (TextView)findViewById(R.id.location);
        done = (Button)findViewById(R.id.done);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        service.setText("Service :-  HairCut");
        time.setText("Time :-  19:45");
        Date.setText("Date :-  07/11/2018");
        location.setText("Location :-  Hyderabad");
        getActionBar().setTitle("Masood");
        rating = 0.0;
        ratingBar.setOnRatingChangeListener(new BaseRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(BaseRatingBar baseRatingBar, float v) {

                rating = (double)v;
                ratingtext.setText("Rating :-  "+rating+" Stars");

            }


        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String servicetittle = "HairCut";
                String Remarks = user_remarks.getText().toString();
                String time = "19:45";
                String date = "07/11/2018";
                Call<CreateRequest> call = AssignRatings.apiInterface.InsertRatings(0,rating,70,68,10,Remarks,date,time);

                call.enqueue(new Callback<CreateRequest>() {
                    @Override
                    public void onResponse(Call<CreateRequest> call, Response<CreateRequest> response) {
                        if (response.body().getResponse().equals("ok")) {

                            MDToast mdToast = MDToast.makeText(getApplicationContext(), "Your Ratings are Assigned", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
                            mdToast.show();

                            Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
                            intent.putExtra("user", HomeActivity.user);

                            startActivity(intent);

                        }


                    }

                    @Override
                    public void onFailure(Call<CreateRequest> call, Throwable t) {
                        //
                    }

                });


            }
        });










    }
}
