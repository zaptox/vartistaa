package com.vartista.www.vartista.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kennyc.bottomsheet.BottomSheet;
import com.kennyc.bottomsheet.BottomSheetListener;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.usernotificationitems;

import java.util.List;

/**
 * Created by khan on 9/4/2018.
 */

public class  UserNotificationlistadapter extends RecyclerView.Adapter<UserNotificationlistadapter.ViewHolder> {

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
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        if(list.get(position).getRequest_status().equals("1")){
            holder.username.setText(list.get(position).getName());
            holder.requestdetail.setTextColor(Color.GREEN);
            holder.requestdetail.setText("Request Accepted");
            holder.timeduration.setText(list.get(position).getTime());
        }
        if(list.get(position).getRequest_status().equals("0")){
            String name = list.get(position).getName();
            String Service =list.get(position).getservice_title();
            double price = list.get(position).getPrice();
            holder.requestdetail.setText(Html.fromHtml("Your "+ Service +" "+price+" booking request has been sent to "+"<b>"+name+"</b>"));
            holder.timeduration.setText(list.get(position).getTime());
        }
        if(list.get(position).getRequest_status().equals("-1")){
            holder.username.setText(list.get(position).getName());
            holder.requestdetail.setTextColor(Color.RED);
            holder.requestdetail.setText("Request Rejected");
            holder.timeduration.setText(list.get(position).getTime());

        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BottomSheet.Builder(v.getContext())
                        .setTitle("Title")
                        .setMessage("Message")
                        .setIcon(v.getContext().getResources().getDrawable(R.drawable.ic_menu_send))
                        .show();
            }
        });

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

            username=(TextView)mView.findViewById(R.id.textViewname_user);
            requestdetail=(TextView)mView.findViewById(R.id.textViewrequestdetail_noti);
            timeduration=(TextView)mView.findViewById(R .id.textViewtimeduration_noti);

        }
    }




}
