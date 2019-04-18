package com.vartista.www.vartista.modules.user.user_fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.valdesekamdem.library.mdtoast.MDToast;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.CreateRequest;
import com.vartista.www.vartista.beans.servicepaapointmentsitems;
import com.vartista.www.vartista.modules.general.HomeActivity;
import com.vartista.www.vartista.modules.user.AssignRatings;
import com.vartista.www.vartista.restcalls.ApiClient;
import com.vartista.www.vartista.restcalls.ApiInterface;
import com.willy.ratingbar.BaseRatingBar;
import com.willy.ratingbar.ScaleRatingBar;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignRatingFragment extends Fragment {

    TextView Username,time,Date,location,service,ratingtext;
    EditText user_remarks;
    Button done;
    ImageView profile_image;
    private ProgressDialog progressDialog;

    double rating;
    Intent intent;
    public static ApiInterface apiInterface;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_assign_rating,container,false);
        profile_image=view.findViewById(R.id.profile_image);
        ScaleRatingBar ratingBar = view.findViewById(R.id.simpleRatingBar);
        ratingBar.setNumStars(5);
        ratingBar.setMinimumStars(1);
        ratingBar.setRating(0);
        ratingBar.setStarPadding(10);
        ratingBar.setStepSize(0.5f);
        ratingBar.setEmptyDrawableRes(R.drawable.empty);
        ratingBar.setFilledDrawableRes(R.drawable.filled);
        user_remarks = (EditText)view.findViewById(R.id.user_remarks);
        service = (TextView)view.findViewById(R.id.Service_tittle);
        ratingtext = (TextView)view.findViewById(R.id.ratingtext);
        time = (TextView)view.findViewById(R.id.time);
        Date = (TextView)view.findViewById(R.id.date);
        location = (TextView)view.findViewById(R.id.location);
        Username= (TextView)view.findViewById(R.id.header_name);
        done = (Button)view.findViewById(R.id.done);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        intent=getActivity().getIntent();
        final servicepaapointmentsitems ob = (servicepaapointmentsitems) intent.getSerializableExtra("object");



        service.setText("Service :- "+ob.getService_title());
        Date.setText("Date :-  "+ob.getDate());
        location.setText("Location :-  "+ob.getLocation());
        Username.setText(""+ob.getUsername());
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

                            MDToast mdToast = MDToast.makeText(getContext(), "Your Ratings are Assigned", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
                            mdToast.show();
                            setUIToWait(false);
                            Intent intent=new Intent(getContext(),HomeActivity.class);
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

        return view;

    }
    public String get_Current_Date(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = sdf.getDateTimeInstance().format(new Date());
        return currentDate;
    }



    private void setUIToWait(boolean wait) {


        if (wait) {
            progressDialog = ProgressDialog.show(getContext(), null, null, true, true);
//            progressDialog.setContentView(new ProgressBar(this));

            progressDialog.setContentView(R.layout.loader);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        } else {
            progressDialog.dismiss();
        }

    }

}
