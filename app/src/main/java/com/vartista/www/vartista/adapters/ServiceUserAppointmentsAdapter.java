package com.vartista.www.vartista.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.servicepaapointmentsitems;
import com.vartista.www.vartista.modules.general.HomeActivity;
import com.vartista.www.vartista.modules.user.AssignRatings;
import com.vartista.www.vartista.modules.user.MyServiceMeetings;

import java.util.List;

public class ServiceUserAppointmentsAdapter extends RecyclerView.Adapter<ServiceUserAppointmentsAdapter.ViewHolder> {

    Context context;
    List<servicepaapointmentsitems> list;

    public ServiceUserAppointmentsAdapter(Context context, List<servicepaapointmentsitems> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ServiceUserAppointmentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.meeting_items,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ServiceUserAppointmentsAdapter.ViewHolder holder, final int position) {
        holder.serviceprovidername.setText(list.get(position).getUsername());
        holder.servicecharges.setText(list.get(position).getService_title()+" "+list.get(position).getPrice());
        holder.Date.setText(list.get(position).getDate());
        holder.Time.setText(list.get(position).getTime());
        holder.serviceCat.setText(list.get(position).getName());
        holder.serviceDesc.setText(list.get(position).getService_description());
        holder.serviceLoc.setText(list.get(position).getLocation());

         holder.mView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(context, MyServiceMeetings.class);
                 context.startActivity(intent);
             }
         });



    }

    @Override
    public int getItemCount() { return list.size(); }


    public class ViewHolder extends RecyclerView.ViewHolder{
      public  View mView;


        public TextView serviceprovidername,servicecharges,Date,Time,serviceDesc,serviceCat,serviceLoc;

        public ViewHolder(View itemView) {
            super(itemView);
            mView=itemView;

            serviceprovidername=(TextView)mView.findViewById(R.id.textViewname_user);
            servicecharges=(TextView)mView.findViewById(R.id.servicedetail_user);
            Date=(TextView)mView.findViewById(R .id.textViewdate_user);
            Time=(TextView)mView.findViewById(R .id.textViewtime_user);
            serviceCat=(TextView)mView.findViewById(R.id.service_category_user);
            serviceDesc=(TextView)mView.findViewById(R.id.textView_service_description_user);
            serviceLoc= (TextView)mView.findViewById(R.id.textViewloc_user);


        }
    }
}
