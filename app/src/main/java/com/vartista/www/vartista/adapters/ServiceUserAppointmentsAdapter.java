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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.servicepappointmentsitems,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceUserAppointmentsAdapter.ViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() { return list.size(); }


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
