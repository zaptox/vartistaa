package com.vartista.www.vartista.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.RatingsReviewDetailBean;

import java.util.List;

/**
 * Created by khan on 10/16/2018.
 */

public class RatingsReviewDetailsAdaptor  extends RecyclerView.Adapter<RatingsReviewDetailsAdaptor.ViewHolder>{

    public List<RatingsReviewDetailBean> Ratingdetails;
    public Context context;
    public TextView Username, ReviewDetail;
    ImageView imageView;
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

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Username.setText(Ratingdetails.get(position).getUserName());
        ReviewDetail.setText(Ratingdetails.get(position).getUser_remarks());




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
            imageView = (ImageView) mView.findViewById(R.id.imageViewCategoryIcon);

        }

    }






}
