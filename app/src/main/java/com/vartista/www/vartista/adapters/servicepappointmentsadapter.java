package com.vartista.www.vartista.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.servicepaapointmentsitems;
import com.vartista.www.vartista.modules.general.HomeActivity;
import com.vartista.www.vartista.modules.provider.AppointmentDetails;
import com.vartista.www.vartista.modules.provider.MyAppointments;
import com.vartista.www.vartista.modules.provider.My_Rating_Reviews;

import java.util.List;

/**
 * Created by khan on 9/6/2018.
 */

public class servicepappointmentsadapter extends RecyclerView.Adapter<servicepappointmentsadapter.ViewHolder> {

    Context context;
    List<servicepaapointmentsitems> list;

    public servicepappointmentsadapter(Context context, List<servicepaapointmentsitems> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public servicepappointmentsadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.servicepappointmentsitems,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull servicepappointmentsadapter.ViewHolder holder, final int position) {

        holder.serviceprovidername.setText(list.get(position).getUsername());
        holder.servicecharges.setText(list.get(position).getService_title()+" "+list.get(position).getPrice());
        holder.Date.setText(list.get(position).getDate());
        holder.Time.setText(list.get(position).getTime());
        holder.serviceCat.setText(list.get(position).getName());
        holder.serviceDesc.setText(list.get(position).getService_description());
        holder.serviceLoc.setText(list.get(position).getLocation());
        Picasso.get().load(list.get(position).getImage()).fit().centerCrop()
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile)
                .into(holder.imageView);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), AppointmentDetails.class);
                intent.putExtra("object",list.get(position));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public TextView serviceprovidername,servicecharges,Date,Time,serviceDesc,serviceCat,serviceLoc;
        public ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            mView=itemView;

            serviceprovidername=(TextView)mView.findViewById(R.id.textViewname_user);
            servicecharges=(TextView)mView.findViewById(R.id.servicedetail_user);
            Date=(TextView)mView.findViewById(R .id.textViewdate_user);
            Time=(TextView)mView.findViewById(R .id.textViewtime_user);
            serviceCat=(TextView)mView.findViewById(R.id.service_category);
            serviceDesc=(TextView)mView.findViewById(R.id.textView_service_description);
            serviceLoc= (TextView)mView.findViewById(R.id.textViewloc_user);
            imageView = (ImageView) mView.findViewById(R.id.profile_image);


        }
    }





}
