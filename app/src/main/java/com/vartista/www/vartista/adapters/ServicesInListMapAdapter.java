package com.vartista.www.vartista.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.GetServiceProviders;
import com.vartista.www.vartista.beans.ServiceRequets;
import com.vartista.www.vartista.modules.general.HomeActivity;
import com.vartista.www.vartista.modules.user.BookNowActivity;
import com.vartista.www.vartista.modules.user.FindServicesInList;
import com.vartista.www.vartista.modules.user.ServiceProviderDetail;
import com.willy.ratingbar.ScaleRatingBar;

import java.util.List;

/**
 * Created by Dell on 2018-09-03.
 */




public class ServicesInListMapAdapter extends RecyclerView.Adapter<ServicesInListMapAdapter.ViewHolder> {
    public List<GetServiceProviders> myServicesList;
    public Context context;

    public ServicesInListMapAdapter(Context context, List<GetServiceProviders> myServicesList) {
        this.myServicesList = myServicesList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.services_in_list_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.busy_status.setVisibility(View.INVISIBLE);
        holder.service_p_name.setText("" + myServicesList.get(position).getSp_name());
        holder.service_name.setText("" + myServicesList.get(position).getService_title());
        holder.location.setText(""+myServicesList.get(position).getLocation());
        if(myServicesList.get(position).getRatings()==0.0){
            holder.ratingBar.setRating(0);

        }
        else{
            holder.ratingBar.setRating((float)myServicesList.get(position).getRatings());

        }
        Toast.makeText(context, "rating is "+myServicesList.get(position).getRatings(), Toast.LENGTH_SHORT).show();
        if(myServicesList.get(position).getUser_status() == 1){
            holder.user_status.setImageResource(R.drawable.green_circle);
        }
        else {
            holder.user_status.setImageResource(R.drawable.yellow_circle);
        }

        if(myServicesList.get(position).getBusy_status()==1){
            holder.busy_status.setVisibility(View.VISIBLE);
        }

        Picasso.get().load(myServicesList.get(position).getImage()).fit().centerCrop()
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile)
                .into(holder.user_profile_img);



        final int abhipoition = position;
        final ViewHolder holder1 = holder;


        final String service_name = myServicesList.get(position).getService_title();
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                int s_provider_id= myServicesList.get(position).getUser_id();
                int cat_id= myServicesList.get(position).getCategory_id();
                int user_id= HomeActivity.user_id;
                String sp_name= myServicesList.get(position).getSp_name();
                Intent intent=new Intent(view.getContext(),ServiceProviderDetail.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("s_provider_id",s_provider_id);
                intent.putExtra("cat_id",cat_id);
                intent.putExtra("user_id",user_id);
                intent.putExtra("spname",sp_name);
                intent.putExtra("service_title",myServicesList.get(position).getService_title());
                intent.putExtra("profile_photo",myServicesList.get(position).getImage());


                context.startActivity(intent);

            }
        });

    }


    @Override
    public int getItemCount() {
        return myServicesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View mView;


        public TextView service_p_name, service_name,busy_status,location;

        public ScaleRatingBar ratingBar;
        public ImageView user_status, user_profile_img;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            service_p_name = (TextView) mView.findViewById(R.id.textViewName);
            busy_status = (TextView) mView.findViewById(R.id.busy_status);
            service_name = (TextView) mView.findViewById(R.id.textViewService);
            ratingBar = mView.findViewById(R.id.simpleRatingBar);
            location=mView.findViewById(R.id.textViewAddress);
            user_status = mView.findViewById(R.id.user_status);
            user_profile_img = mView.findViewById(R.id.user_profile_img);

        }
    }

}

