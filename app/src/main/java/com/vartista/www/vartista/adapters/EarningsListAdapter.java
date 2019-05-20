package com.vartista.www.vartista.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.EarningBean;
import com.vartista.www.vartista.beans.servicepaapointmentsitems;

import java.util.List;

/**
 * Created by Dell on 2019-02-20.
 */

public class EarningsListAdapter  extends RecyclerView.Adapter<EarningsListAdapter.ViewHolder> {



    Context context;
    List<EarningBean> list;

    public EarningsListAdapter(Context context, List<EarningBean> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.earnings_in_row,parent,false);
        return new EarningsListAdapter.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.service_name.setText("Service: "+list.get(position).getService());
        holder.service_availer.setText("Service Availer: "+list.get(position).getService_availer());
        holder.date.setText(list.get(position).getDate());
        holder.time.setText(list.get(position).getService_time());
        holder.earning.setText("Earning: "+list.get(position).getSp_earning()+"£");
        holder.service_location.setText("Location: "+list.get(position).getLocation());
        holder.admin_tax.setText("Vartista Tax: "+list.get(position).getAdmin_tax()+"%");


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public TextView service_name,service_availer,date,time,earning,service_location,admin_tax;

        public ViewHolder(View itemView) {
            super(itemView);
            mView=itemView;

            service_name=(TextView)mView.findViewById(R.id.textViewname_user);
            service_availer=(TextView)mView.findViewById(R.id.servicedetail_user);
            date=(TextView)mView.findViewById(R .id.textViewdate_user);
            earning=(TextView)mView.findViewById(R .id.textViewtime_user);
            time=(TextView)mView.findViewById(R.id.time_service);
            service_location=(TextView)mView.findViewById(R.id.service_location);
            admin_tax= mView.findViewById(R.id.admin_tax);
        }
    }

}
