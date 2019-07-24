package com.vartista.www.vartista.modules.provider;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignRatingsToUser extends AppCompatActivity {

    TextView Username,time,Date,location,service,ratingtext;
    EditText user_remarks;
    Button done;
    ImageView profile_image;
    private ProgressDialog progressDialog;


    String service_title;
    String reqeustservice_id;
    String Irating_id;
    String date ;
    String location1 ;
    String username ;
    String image;
    int service_id;
    String customer_id ;

    double rating;
    Intent intent;
    public static ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_ratings_to_user);
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
            int SharedpreRating = intent.getIntExtra("assignratings",0);
        if (SharedpreRating==0) {
            final servicepaapointmentsitems ob = (servicepaapointmentsitems) intent.getSerializableExtra("object");

            service_title = ob.getService_title();
            reqeustservice_id = ob.getRequestservice_id();
            Irating_id = ob.getRating_id();
            service_id = ob.getService_id();
            date = ob.getDate();
            username = ob.getUsername();
            location1 = ob.getLocation();
            customer_id = ob.getUser_customer_id();
            image = ob.getImage();
            Toast.makeText(this, "Rating Id "+Irating_id, Toast.LENGTH_SHORT).show();

            SharedPreferences sharedPreferencespre = getSharedPreferences("Login", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferencespre.edit();
            editor.putBoolean("RatingsToUser", true);
            editor.apply();
            SharedPreferences sharedPreferencespre1 = getSharedPreferences("AssignRatings", Context.MODE_PRIVATE);
            SharedPreferences.Editor spe = sharedPreferencespre1.edit();
            spe.putInt("service_id", service_id);
            spe.putString("date",date);
            spe.putString("Rating_id", Irating_id);
            spe.putString("Service_title", service_title);
            spe.putString("username", username);
            spe.putString("location",location1);
            spe.putString("customer_id",customer_id);
            spe.putString("image", image);
            spe.putString("reqestservice_id", reqeustservice_id);
            spe.apply();
            spe.commit();

//             service_title = ob.getService_title();
//             date = ob.getDate();
//             location1 = ob.getLocation();
//             username = ob.getUsername();
//             image = ob.getImage();

//            SharedPreferences ob2 = getSharedPreferences("AssignRatings", Context.MODE_PRIVATE);
//       String   reqeustservice_id1 = ob2.getString("reqestservice_id","");
//           String service_title1 = ob2.getString("Service_title","");
//           String image1 = ob2.getString("image","");
//          String  Irating_id1 = ob2.getString("Rating_id","");
//            Toast.makeText(this, ""+reqeustservice_id1+""+image1+""+service_title1+""+Irating_id1, Toast.LENGTH_SHORT).show();


//            service.setText("Service :- " + ob.getService_title());
//            Date.setText("Date :-  " + ob.getDate());
//            location.setText("Location :-  " + ob.getLocation());
//            Username.setText("" + ob.getUsername());
//            Picasso.get().load(ob.getImage()).fit().centerCrop()
//                    .placeholder(R.drawable.profile)
//                    .error(R.drawable.profile)
//                    .into(profile_image);
//
//            rating = 0.0;
//            ratingBar.setOnRatingChangeListener(new BaseRatingBar.OnRatingChangeListener() {
//                @Override
//                public void onRatingChange(BaseRatingBar baseRatingBar, float v) {
//
//                    rating = (double) v;
//                    ratingtext.setText("Rating :-  " + rating + " Stars");
//
//                }
//
//
//            });

        }else{


            SharedPreferences ob = getSharedPreferences("AssignRatings", Context.MODE_PRIVATE);
            reqeustservice_id = ob.getString("reqestservice_id","");
            service_title = ob.getString("Service_title","");
            image = ob.getString("image","");
            Irating_id = ob.getString("Rating_id","");

            date = ob.getString("date","");
            location1 = ob.getString("location","");
            username = ob.getString("username","");
            image = ob.getString("image","");
//            reqeustservice_id = ob.getString("reqestservice_id","");



        }


        service.setText("Service :- " + service_title);
        Date.setText("Date :-  " + date);
        location.setText("Location :-  " + location1);
        Username.setText("" + username);
        Picasso.get().load(image).fit().centerCrop()
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile)
                .into(profile_image);

        rating = 0.0;
        ratingBar.setOnRatingChangeListener(new BaseRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(BaseRatingBar baseRatingBar, float v) {

                rating = (double) v;
                ratingtext.setText("Rating :-  " + rating + " Stars");

            }


        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(AssignRatingsToUser.this, "Button Clicked", Toast.LENGTH_SHORT).show();
                String Remarks = user_remarks.getText().toString();
                String time = "";
                setUIToWait(true);
                int rating_id=Integer.parseInt(Irating_id);
                int rs_id = Integer.parseInt(reqeustservice_id);

                Call<CreateRequest> call = AssignRatingsToUser.apiInterface.updateSpRatings(rating_id,rating,Remarks,rs_id);

                call.enqueue(new Callback<CreateRequest>() {
                    @Override
                    public void onResponse(Call<CreateRequest> call, Response<CreateRequest> response) {
                        if (response.body().getResponse().equals("ok")) {

                            MDToast mdToast = MDToast.makeText(getApplicationContext(), "Your Ratings are Assigned To User ", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
                            mdToast.show();
                            setUIToWait(false);
                            SharedPreferences sharedPreferencespre =getSharedPreferences("Login", Context.MODE_PRIVATE);

                            SharedPreferences.Editor editor=sharedPreferencespre.edit();
                            editor.putBoolean("RatingsToUser",false);
                            editor.apply();

                            SharedPreferences ob = getSharedPreferences("AssignRatings", Context.MODE_PRIVATE);
                            ob.edit().remove("AssignRatings").commit();
                            Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
                            intent.putExtra("user", HomeActivity.user);
                            startActivity(intent);

                        }
                        else{
                            Toast.makeText(AssignRatingsToUser.this, "AssignRating not Updated", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {
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
