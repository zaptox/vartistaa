package com.vartista.www.vartista.modules.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.valdesekamdem.library.mdtoast.MDToast;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.CreateRequest;
import com.vartista.www.vartista.beans.servicepaapointmentsitems;
import com.vartista.www.vartista.modules.general.HomeActivity;
import com.vartista.www.vartista.restcalls.ApiClient;
import com.vartista.www.vartista.restcalls.ApiInterface;
import com.willy.ratingbar.BaseRatingBar;
import com.willy.ratingbar.ScaleRatingBar;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignRatings extends AppCompatActivity {

   TextView   Username,time,Date,location,service,ratingtext;
   EditText user_remarks;
   Button done;
   ImageView profile_image;
    private ProgressDialog progressDialog;

    double rating;
    Intent intent;
    public static ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_ratings);
        profile_image=findViewById(R.id.profile_image);
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
        Username= (TextView)findViewById(R.id.header_name);
        done = (Button)findViewById(R.id.done);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        intent=getIntent();
        final servicepaapointmentsitems ob = (servicepaapointmentsitems) intent.getSerializableExtra("object");



        service.setText("Service :- "+ob.getService_title());
        Date.setText("Date :-  "+ob.getDate());
        location.setText("Location :-  "+ob.getLocation());
        Username.setText(""+ob.getUsername());
        time.setText("Time:- "+ob.getTime());
        Picasso.get().load(ob.getImage()).fit().centerCrop()
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile)
                .into(profile_image);

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
                String servicetittle = ob.getService_title();
                String Remarks = user_remarks.getText().toString();
                String time = "";
                String date = get_Current_Date();
                setUIToWait(true);
                int rating_id=Integer.parseInt(ob.getRating_id());
                Call<CreateRequest> call = AssignRatings.apiInterface.updateratings(rating_id,rating,Remarks,Integer.parseInt(ob.getRequestservice_id()));

                call.enqueue(new Callback<CreateRequest>() {
                    @Override
                    public void onResponse(Call<CreateRequest> call, Response<CreateRequest> response) {
                        if (response.body().getResponse().equals("ok")) {

                            MDToast mdToast = MDToast.makeText(getApplicationContext(), "Your Ratings are Assigned", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
                            mdToast.show();
                            setUIToWait(false);
                            Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
                            intent.putExtra("user", HomeActivity.user);

                            startActivity(intent);

                        }


                    }

                    @Override
                    public void onFailure(Call<CreateRequest> call, Throwable t) {
                        //
                        // Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        setUIToWait(false);

                    }

                });


            }
        });










    }

    public String get_Current_Date(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = sdf.getDateTimeInstance().format(new Date());
        return currentDate;
    }



    private void setUIToWait(boolean wait) {


        if (wait) {
            progressDialog = ProgressDialog.show(this, null, null, true, true);
//            progressDialog.setContentView(new ProgressBar(this));

            progressDialog.setContentView(R.layout.loader);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        } else {
            progressDialog.dismiss();
        }

    }
}
