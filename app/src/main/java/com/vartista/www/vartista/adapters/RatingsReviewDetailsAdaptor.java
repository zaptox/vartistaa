package com.vartista.www.vartista.adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.RatingsReviewDetailBean;
import com.willy.ratingbar.ScaleRatingBar;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * Created by khan on 10/16/2018.
 */

public class RatingsReviewDetailsAdaptor  extends RecyclerView.Adapter<RatingsReviewDetailsAdaptor.ViewHolder>{

    public List<RatingsReviewDetailBean> Ratingdetails;
    public Context context;
    public TextView Username, ReviewDetail,ServiceTittle,date,time;
    ImageView imageView;
    ScaleRatingBar ratingBar;
    public RatingsReviewDetailsAdaptor(Context context, List<RatingsReviewDetailBean> Ratingdetails){
        this.Ratingdetails = Ratingdetails;
        this.context=context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ratingitems,parent,false);
        return new RatingsReviewDetailsAdaptor.ViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String oldstring = Ratingdetails.get(position).getDate();
        Username.setText(Ratingdetails.get(position).getUserName());
//        ReviewDetail.setText(Ratingdetails.get(position).getUser_remarks());
        ratingBar.setRating(Ratingdetails.get(position).getStars());
        ratingBar.setIsIndicator(true);
        ratingBar.setFocusable(false);
        ServiceTittle.setText(Ratingdetails.get(position).getService_tittle());
        date.setText("Date: "+Ratingdetails.get(position).getDate());
//        time.setText(Ratingdetails.get(position).getTime());


    }


    @Override
    public int getItemCount() {
        return Ratingdetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            Username = (TextView) mView.findViewById(R.id.UserName);
            ReviewDetail = (TextView) mView.findViewById(R.id.ReviewDetails);
            date = (TextView) mView.findViewById(R.id.Date);
//            time = (TextView) mView.findViewById(R.id.Time);
            ServiceTittle = (TextView) mView.findViewById(R.id.Service_Tittle);
            imageView = (ImageView) mView.findViewById(R.id.imageViewCategoryIcon);
            ratingBar = mView.findViewById(R.id.simpleRatingBar);
            ratingBar.setNumStars(5);
            ratingBar.setMinimumStars(1);
            ratingBar.setStarPadding(10);
            ratingBar.setStepSize(0.5f);
            ratingBar.setEnabled(false);



        }

    }






}
