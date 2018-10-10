package com.vartista.www.vartista.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.servicepaapointmentsitems;
import com.vartista.www.vartista.beans.usernotificationitems;

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
    public void onBindViewHolder(@NonNull servicepappointmentsadapter.ViewHolder holder, int position) {
        holder.serviceprovidername.setText(list.get(position).getName());
        holder.servicecharges.setText(list.get(position).getService_title()+" "+list.get(position).getPrice());
//        holder.Date.setText(list.get(position).getDate());
        holder.Time.setText(list.get(position).getTime());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public TextView serviceprovidername,servicecharges,Date,Time;

        public ViewHolder(View itemView) {
            super(itemView);
            mView=itemView;

            serviceprovidername=(TextView)mView.findViewById(R.id.textViewname);
            servicecharges=(TextView)mView.findViewById(R.id.servicedetail);
            Date=(TextView)mView.findViewById(R .id.textViewdate);
            Time=(TextView)mView.findViewById(R .id.textViewtime);

        }
    }





}
