package com.vartista.www.vartista.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.usernotificationitems;

import java.util.List;

/**
 * Created by khan on 9/4/2018.
 */

public class UserNotificationlistadapter extends RecyclerView.Adapter<UserNotificationlistadapter.ViewHolder> {

    Context context;
    List<usernotificationitems> list;

    public UserNotificationlistadapter(Context context, List<usernotificationitems> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_items,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(list.get(position).getRequest_status().equals("1")){
            holder.username.setText(list.get(position).getName());
            holder.requestdetail.setTextColor(Color.GREEN);
            holder.requestdetail.setText("Request Accepted");
            holder.timeduration.setText(list.get(position).getTime());
        }
        if(list.get(position).getRequest_status().equals("0")){
            holder.username.setText(list.get(position).getName());
            holder.requestdetail.setTextColor(Color.BLUE);
            holder.requestdetail.setText("Request Pending");
            holder.timeduration.setText(list.get(position).getTime());
        }
        if(list.get(position).getRequest_status().equals("-1")){
            holder.username.setText(list.get(position).getName());
            holder.requestdetail.setTextColor(Color.RED);
            holder.requestdetail.setText("Request Rejected");
            holder.timeduration.setText(list.get(position).getTime());

        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public TextView username,requestdetail,timeduration;

        public ViewHolder(View itemView) {
            super(itemView);
            mView=itemView;

            username=(TextView)mView.findViewById(R.id.textViewname);
            requestdetail=(TextView)mView.findViewById(R.id.textViewrequestdetail);
            timeduration=(TextView)mView.findViewById(R .id.textViewtimeduration);

        }
    }




}
