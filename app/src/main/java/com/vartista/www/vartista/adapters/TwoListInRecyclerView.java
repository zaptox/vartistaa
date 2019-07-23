package com.vartista.www.vartista.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.choota.dev.ctimeago.TimeAgo;
import com.kennyc.bottomsheet.BottomSheet;
import com.kennyc.bottomsheet.BottomSheetListener;
import com.squareup.picasso.Picasso;
import com.vartista.www.vartista.*;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.usernotificationitems;
import com.vartista.www.vartista.modules.general.MyTimeAgo;
import com.vartista.www.vartista.modules.provider.DocumentUploadActivity;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

        if(position - list.size()  < notificationlist2.size()){
            return VIEW_TYPE_ADMINNOTIFICATION;
        }

        return -1;
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        public TextView username,requestdetail,timeduration,bs_name,bs_text,bs_text2;
        public ImageView imageView;
        public NotificationViewHolder(View itemView){
            super(itemView);

            this.itemView = itemView;

            imageView = (ImageView)itemView.findViewById(R.id.profile_image);
            username=(TextView)itemView.findViewById(R.id.textViewname_user_noti);
            requestdetail=(TextView)itemView.findViewById(R.id.textViewrequestdetail_noti);
            timeduration=(TextView)itemView.findViewById(R.id.textViewtimeduration_noti);

        }

        public void populate(usernotificationitems ob){

            final String status = ob.getRequest_status();
            final String name = ob.getName();
            final String time = ob.getTime();
            final String image = ob.getImage();
            final String RA = "Request Accepted";
            final String RR = "Request Rejected";
            final String service =ob.getservice_title();
            final double price = ob.getPrice();
            final String date_accepted = ob.getAccepted_date();
            final String date_rejected= ob.getRejected_date();
            final String date_sendat= ob.getReqeustsend_at();
            final String date_payverify= ob.getPay_verify_date();
            final String date_completed= ob.getCompleted_date();
            final String date_cancelled= ob.getCancelled_date();



            Picasso.get().load(image).fit().centerCrop()
                    .placeholder(R.drawable.profile)
                    .error(R.drawable.profile)
                    .into(imageView);


            if(status.equals("1")){
                username.setText(name);
                requestdetail.setTextColor(Color.GRAY);
                requestdetail.setText(RA);
                timeduration.setText(TimeAgo(date_accepted));
            }
            else if(status.equals("0")){
                requestdetail.setTextColor(Color.GRAY);
                requestdetail.setText(Html.fromHtml(
                        "Your request has been sent to "+name));
                timeduration.setText(TimeAgo(date_sendat));
            }
            else if(status.equals("-1")){
                username.setText(name);
                requestdetail.setTextColor(Color.RED);
                requestdetail.setText(RR);
                timeduration.setText(TimeAgo(date_rejected));
            }
            else if(status.equals("-2")){
                //Cancelled
                username.setText(name);
                requestdetail.setTextColor(Color.RED);
                requestdetail.setText(" Cancelled Service");
                timeduration.setText(TimeAgo(date_cancelled));
            }
            else if(status.equals("3")){
                //Completed
                username.setText(name);
                requestdetail.setTextColor(Color.RED);
                requestdetail.setText("Completed Service");
                timeduration.setText(TimeAgo(date_completed));
            }
            else if(status.equals("6")){
                //verification of cash
                username.setText(name);
                requestdetail.setTextColor(Color.RED);
                requestdetail.setText("Verification Of Cash ");
                timeduration.setText(TimeAgo(date_payverify));
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceType")
                @Override
                public void onClick(final View v) {
                    new BottomSheet.Builder(v.getContext())
                            .setView(R.layout.bottomsheet_header)
                            .setListener(new BottomSheetListener() {
                                @Override
                                public void onSheetShown(@NonNull BottomSheet bottomSheet, @Nullable Object o) {
                                    bs_name = (TextView)bottomSheet.findViewById(R.id.name_bottomsheet);
                                    imageView = (ImageView)bottomSheet.findViewById(R.id.imageView_bottomsheet);
                                    bs_text = (TextView)bottomSheet.findViewById(R.id.text_bottomsheet);
                                    bs_text2 = (TextView)bottomSheet.findViewById(R.id.time_bottomsheet3);

                                    Picasso.get().load(image).fit().centerCrop()
                                            .placeholder(R.drawable.profile)
                                            .error(R.drawable.profile)
                                            .into(imageView);
                                    if(status.equals("1")){
                                        bs_name.setText(name+" Has Accepted Your Request");
                                        bs_text.setText(Html.fromHtml("Your Reqeust To "+ name +" For <b>"+service+ "</b> at "+ time +" has been Accepted."));
                                        bs_text2.setText(TimeAgo(date_accepted));
                                    }
                                    else if(status.equals("0")){
                                        bs_name.setText("Your booking request has been sent");
                                        bs_text2.setText("You will be notify soon.");
                                        bs_text.setText(Html.fromHtml("Your "+ service +" for price  "+price+" booking request has been sent to "+"<b>"+name+"</b>"));
                                        bs_text2.setText(TimeAgo(date_sendat));
                                    }
                                    else if(status.equals("-1")){
                                        bs_name.setText(name+" Has Canceled Your Request");
                                        bs_text.setText(Html.fromHtml("Your Reqeust To "+ name +" For <b>"+service+ "</b> for <b> "+ time +"</b> has been Canceled."));
                                        bs_text2.setText(TimeAgo(date_rejected));

                                    }
                                    else if(status.equals("3")){
                                        bs_name.setText(" Your Service is Completed");
                                        bs_text.setText(Html.fromHtml("Service <b>" +service+"</b> By "+ name +" is Completed <br> Thank you For Using V-artista" ));
                                        bs_text2.setText(TimeAgo(date_completed));

                                    }
                                    else if(status.equals("6")){
                                        bs_name.setText("Verification Of Cash");
                                        bs_text.setText(Html.fromHtml(name +" will pay You by cash <br> Did you received it or amount ?" ));
                                        bs_text2.setText(TimeAgo(date_payverify));

                                    }

                                }

                                @Override
                                public void onSheetItemSelected(@NonNull BottomSheet bottomSheet, MenuItem menuItem, @Nullable Object o) {

                                }

                                @Override
                                public void onSheetDismissed(@NonNull BottomSheet bottomSheet, @Nullable Object o, int i) {

                                }
                            })
                            .show();
                }
            });


        }
    }

    public class AdminNotificationViewHolder extends RecyclerView.ViewHolder {

        public TextView username,adminmsg,timeduration,bs_name,bs_text,bs_text2,bs_text3;
        public ImageView imageView;
        public AdminNotificationViewHolder(View itemView){
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.profile_image);
            username=(TextView)itemView.findViewById(R.id.textViewname_user_noti);
            adminmsg=(TextView)itemView.findViewById(R.id.textViewrequestdetail_noti);
            timeduration=(TextView)itemView.findViewById(R.id.textViewtimeduration_noti);
        }
        public void populate(usernotificationitems ob){
            final String msg = ob.getMsg();
            final String image = ob.getImage();
            final String title = ob.getTitle();
            final String Created_at = ob.getCreated_at();
            final String sp_status = ob.getSp_status();


//            Toast.makeText(itemView.getContext(), ""+ob.getMsg(), Toast.LENGTH_SHORT).show();
            username.setText(Html.fromHtml("Dear "+"<b>"+ob.getName()+"<b>"));
            adminmsg.setText(Html.fromHtml("<b>"+ob.getTitle()+"<b> <br> "+ob.getMsg()));
            timeduration.setText(TimeAgo(Created_at));

            Picasso.get().load(R.drawable.profile).fit().centerCrop()
                    .placeholder(R.drawable.profile)
                    .error(R.drawable.profile)
                    .into(imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (msg.contains("upload")){
                        Intent intent = new Intent(itemView.getContext(),DocumentUploadActivity.class);
                        v.getContext().startActivity(intent);
                    }else{

                        new BottomSheet.Builder(v.getContext())
                                .setView(R.layout.bottomsheet_header)
                                .setListener(new BottomSheetListener() {
                                    @Override
                                    public void onSheetShown(@NonNull BottomSheet bottomSheet, @Nullable Object o) {
                                        bs_name = (TextView)bottomSheet.findViewById(R.id.name_bottomsheet);
                                        imageView = (ImageView)bottomSheet.findViewById(R.id.imageView_bottomsheet);
                                        bs_text = (TextView)bottomSheet.findViewById(R.id.text_bottomsheet);
                                        bs_text2 = (TextView)bottomSheet.findViewById(R.id.time_bottomsheet3);
                                        Picasso.get().load(image).fit().centerCrop()
                                                .placeholder(R.drawable.profile)
                                                .error(R.drawable.profile)
                                                .into(imageView);


                                        bs_name.setText(title );
                                        bs_text.setText(Html.fromHtml(""+msg));
                                        bs_text2.setText(TimeAgo(Created_at));
                                    }

                                    @Override
                                    public void onSheetItemSelected(@NonNull BottomSheet bottomSheet, MenuItem menuItem, @Nullable Object o) {

                                    }

                                    @Override
                                    public void onSheetDismissed(@NonNull BottomSheet bottomSheet, @Nullable Object o, int i) {

                                    }
                                })
                                .show();
                    }
                }

            });
        }
    }

    public String TimeAgo(String PastDate){
        SimpleDateFormat sdf = (new SimpleDateFormat("dd/MM/yyyy hh:mm:ss"));
        String result = "";
        try{
            Date p_date =  sdf.parse(PastDate);
            MyTimeAgo timeAgo = new MyTimeAgo().locale(context);
            result = timeAgo.getTimeAgo(p_date);
        } catch(ParseException e) {
            e.printStackTrace();

        }
        return  result;
    }


}
