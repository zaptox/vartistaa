package com.vartista.www.vartista.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.servicepaapointmentsitems;

import java.util.List;

/**
 * Created by Dell on 2018-10-10.
 */

public class UserAppointmentsAdapter extends RecyclerView.Adapter<UserAppointmentsAdapter.ViewHolder> {
    Context context;
    List<servicepaapointmentsitems> list;

    public UserAppointmentsAdapter(Context context, List<servicepaapointmentsitems> list) {
        this.context = context;
        this.list = list;
    }

    public UserAppointmentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_appointments_list,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAppointmentsAdapter.ViewHolder holder, int position) {
        holder.serviceprovidername.setText(list.get(position).getUsername());
        holder.servicecharges.setText(list.get(position).getService_title()+" "+list.get(position).getPrice());
//        holder.Date.setText(list.get(position).getDate());
        holder.Time.setText(list.get(position).getTime());
        holder.location.setText(list.get(position).getLocation());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public TextView serviceprovidername,servicecharges,Date,Time,location;

        public ViewHolder(View itemView) {
            super(itemView);
            mView=itemView;

            serviceprovidername=(TextView)mView.findViewById(R.id.textViewname);
            servicecharges=(TextView)mView.findViewById(R.id.servicedetail);
            Date=(TextView)mView.findViewById(R .id.textViewdate);
            Time=(TextView)mView.findViewById(R .id.textViewtime);
            location=(TextView) mView.findViewById(R.id.textViewlocation);


        }
    }
}
