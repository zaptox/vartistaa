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
import com.vartista.www.vartista.modules.user.AssignRatings;

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
    public void onBindViewHolder(@NonNull ServiceUserAppointmentsAdapter.ViewHolder holder, final int position) {
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
                Intent intent = new Intent(v.getContext(),AssignRatings.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("UserName",list.get(position).getUsername());
                intent.putExtra("Requestservice_id",list.get(position).getRequestservice_id());
                intent.putExtra("Date",list.get(position).getDate());
                intent.putExtra("Time",list.get(position).getTime());
                intent.putExtra("location",list.get(position).getLocation());
                intent.putExtra("user_id",list.get(position).getUser_customer_id());
                intent.putExtra("Service_Tittle",list.get(position).getService_title());
                intent.putExtra("Service_p_id",list.get(position).getService_provider_id());
                context.startActivity(intent);
                Toast.makeText(context, "intent working", Toast.LENGTH_SHORT).show();

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
