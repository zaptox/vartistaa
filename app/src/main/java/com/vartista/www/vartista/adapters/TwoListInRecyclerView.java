package com.vartista.www.vartista.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kennyc.bottomsheet.BottomSheet;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.usernotificationitems;

import java.util.List;

public class TwoListInRecyclerView extends RecyclerView.Adapter{

    final int VIEW_TYPE_NOTIFICATION = 0;
    final int VIEW_TYPE_ADMINNOTIFICATION = 1;


    Context context;
    List<usernotificationitems> list;
    List<usernotificationitems> notificationlist2;

    public TwoListInRecyclerView(Context context, List<usernotificationitems> list,List<usernotificationitems> notificationlist2) {
        this.context = context;
        this.list = list;
         this.notificationlist2 = notificationlist2;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        if(viewType == VIEW_TYPE_NOTIFICATION){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_items,parent,false);
            return new NotificationViewHolder(v);
        }

        if(viewType == VIEW_TYPE_ADMINNOTIFICATION){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_items,parent,false);
            return new AdminNotificationViewHolder(v);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position){
        if(viewHolder instanceof NotificationViewHolder){
            ((NotificationViewHolder) viewHolder).populate(list.get(position));
        }

        if(viewHolder instanceof AdminNotificationViewHolder){
            ((AdminNotificationViewHolder) viewHolder).populate(notificationlist2.get(position - list.size()));
        }
    }

    @Override
    public int getItemCount(){
        return list.size() + notificationlist2.size();
    }

    @Override
    public int getItemViewType(int position){
        if(position < list.size()){
            return VIEW_TYPE_NOTIFICATION;
        }

        if(position - list.size() < notificationlist2.size()){
            return VIEW_TYPE_ADMINNOTIFICATION;
        }

        return -1;
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {


        public TextView username,requestdetail,timeduration;
        public NotificationViewHolder(View itemView){
            super(itemView);


            username=(TextView)itemView.findViewById(R.id.textViewname_user_noti);
            requestdetail=(TextView)itemView.findViewById(R.id.textViewrequestdetail_noti);
            timeduration=(TextView)itemView.findViewById(R .id.textViewtimeduration_noti);
        }

        public void populate(usernotificationitems ob){
            if(ob.getRequest_status().equals("1")){
                username.setText(ob.getName());
                requestdetail.setTextColor(Color.GREEN);
                requestdetail.setText("Request Accepted");
                timeduration.setText(ob.getTime());
            }
            if(ob.getRequest_status().equals("0")){
                String name = ob.getName();
                String Service =ob.getservice_title();
                double price = ob.getPrice();
                requestdetail.setText(Html.fromHtml("Your "+ Service +" "+price+" booking request has been sent to "+"<b>"+name+"</b>"));
                timeduration.setText(ob.getTime());
            }
            if(ob.getRequest_status().equals("-1")){
                username.setText(ob.getName());
                requestdetail.setTextColor(Color.RED);
                requestdetail.setText("Request Rejected");
                timeduration.setText(ob.getTime());

            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new BottomSheet.Builder(v.getContext())
                            .setTitle("Title")
                            .setMessage("Message")
                            .setIcon(v.getContext().getDrawable(R.drawable.ic_menu_send))
                            .show();
                }
            });


        }
    }

    public class AdminNotificationViewHolder extends RecyclerView.ViewHolder {

        public TextView username,adminmsg,timeduration;
        public AdminNotificationViewHolder(View itemView){
            super(itemView);
            username=(TextView)itemView.findViewById(R.id.textViewname_user_noti);
            adminmsg=(TextView)itemView.findViewById(R.id.textViewrequestdetail_noti);


        }

        public void populate(usernotificationitems ob){
                  username.setText(Html.fromHtml("Hello Mr : "+"<b>"+ob.getName()+"<b>"));
                  adminmsg.setText(Html.fromHtml("From Admin : "+ob.getMsg()));
    }
    }

}
