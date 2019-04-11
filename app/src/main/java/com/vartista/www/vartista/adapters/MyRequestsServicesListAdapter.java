package com.vartista.www.vartista.adapters;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.valdesekamdem.library.mdtoast.MDToast;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.AllNotificationBean;
import com.vartista.www.vartista.beans.CreateRequest;
import com.vartista.www.vartista.beans.NotificationsManager;
import com.vartista.www.vartista.beans.ServiceRequets;
import com.vartista.www.vartista.beans.User;
import com.vartista.www.vartista.modules.general.HomeActivity;
import com.vartista.www.vartista.modules.general.SiginInActivity;
import com.vartista.www.vartista.modules.general.SignUpActivity;
import com.vartista.www.vartista.modules.user.AssignRatings;
import com.vartista.www.vartista.restcalls.ApiClient;
import com.vartista.www.vartista.restcalls.ApiInterface;
import com.vartista.www.vartista.restcalls.SendNotificationApiInterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.ALARM_SERVICE;

public class MyRequestsServicesListAdapter extends RecyclerView.Adapter<MyRequestsServicesListAdapter.ViewHolder>{
    public List<ServiceRequets> myReqServicesList;
    public Context context;
    public static ApiInterface apiInterface;
    public static SendNotificationApiInterface sendNotificationApiInterface;
    private ProgressDialog progressDialog;
    public static int REQUEST_CODE_SP = 100;
    public static int REQUEST_CODE_SP_BEFORE2H = 211;
    String date,time,name;
    int customer_id,requestservice_id;

    public MyRequestsServicesListAdapter(Context context, List<ServiceRequets> myReqServicesList){
        this.myReqServicesList = myReqServicesList;
        this.context=context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.req_list_item,parent,false);

        return new MyRequestsServicesListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {


        holder.tv_Title.setText(myReqServicesList.get(position).getUsername());
        holder.tv_Service.setText(myReqServicesList.get(position).getService_title()+" "+myReqServicesList.get(position).getPrice());
        holder.tv_address.setText(myReqServicesList.get(position).getLocation());
        holder.tv_date.setText(myReqServicesList.get(position).getDate());
        holder.tv_time.setText(myReqServicesList.get(position).getTime());
        holder.tv_catogery.setText(myReqServicesList.get(position).getCatgname());
        holder.tv_s_desc.setText(myReqServicesList.get(position).getService_description());

        SharedPreferences ob = context.getSharedPreferences("Login", Context.MODE_PRIVATE);

        final String name_user = ob.getString("name","");
        final int user_id = ob.getInt("user_id",0);

        Picasso.get().load(myReqServicesList.get(position).getUser_image()).fit().centerCrop()
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile)
                .into(holder.profile_image);

       holder.accept.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(final View view) {
               customer_id = myReqServicesList.get(position).getUser_customer_id();
               date = myReqServicesList.get(position).getDate();
               time = myReqServicesList.get(position).getTime();
               name = myReqServicesList.get(position).getUsername();


               insertreviewnil(myReqServicesList.get(position).getUser_customer_id(),myReqServicesList.get(position).getService_provider_id(),myReqServicesList.get(position).getService_id());
               int status = 1;
                requestservice_id = myReqServicesList.get(position).getReqservice_id();
               Call<ServiceRequets> call = MyRequestsServicesListAdapter.apiInterface.updateOnClickRequests(status,requestservice_id);

               call.enqueue(new Callback<ServiceRequets>() {
                   @Override
                   public void onResponse(Call<ServiceRequets> call, Response<ServiceRequets> response) {


                       if(response.body().getResponse().equals("ok")){
                          final String body = name_user+ " has accepted  your request_"+date+"_"+time+"_"+requestservice_id;
                          final String title = "Vartista-Accept";
                           notifyDataSetChanged();
                           insertNotification(title,body,user_id,customer_id,1,get_Current_Date());
                           Call<NotificationsManager> callNotification = MyRequestsServicesListAdapter.sendNotificationApiInterface
                                   .sendPushNotification(customer_id,
                                           body,title);
                           callNotification.enqueue(new Callback<NotificationsManager>() {
                               @Override
                               public void onResponse(Call<NotificationsManager> call, Response<NotificationsManager> response) {
                                   if(response.isSuccessful()){}

                                   MDToast.makeText(view.getContext(),"Request Accepted",Toast.LENGTH_SHORT).show();
//                                   view.startActivity(intent);
                               }


                               @Override
                               public void onFailure(Call<NotificationsManager> call, Throwable t) {

                               }



                           });


String timeformat = "hour";
int timevalue = -2;
//                           sendCompactNotification(context,REQUEST_CODE_SP_BEFORE2H,date,time,name,"minute",-1,requestservice_id);
//                           sendCompactNotification(context,REQUEST_CODE_SP,date,time,name,"minute",-30,requestservice_id);

//                            AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
//
//                            String appointmentdate = date+" "+time;
//                            SimpleDateFormat showsdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
//                            Date date1 = null;
//                            try {
//                                date1 = showsdf.parse(appointmentdate);
//                            } catch (PaFrseException e) {
//                                Log.d("Date Parsing",""+e.getMessage());
//                                e.printStackTrace();
//                            }
//                            Calendar calendar = Calendar.getInstance();
//                            calendar.setTime(date1);
//                            calendar.add(Calendar.SECOND,10);
//                            Intent intent = new Intent("alarm");
//                            intent.putExtra("username",name);
//                            intent.putExtra("requestcode",REQUEST_CODE_SP);
//
//                            PendingIntent broadcast = PendingIntent.getBroadcast(context,REQUEST_CODE_SP,intent,PendingIntent.FLAG_UPDATE_CURRENT);
//                            alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),broadcast);
//

                        }

                       else if(response.body().getResponse().equals("error")){

                           MDToast.makeText(view.getContext(),"Something went wrong....",Toast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();

                       }
                       else{
                           MDToast.makeText(view.getContext(),"Something went wrong....",Toast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();

                       }
                       remove(position);

                   }

                   @Override
                   public void onFailure(Call<ServiceRequets> call, Throwable t) {
                       MDToast.makeText(view.getContext(),"Something went wrong....",Toast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();
                   }
               });


           }
       });



       holder.decline.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(final View view) {
               int status = -1;
               int requestservice_id = myReqServicesList.get(position).getReqservice_id();
               customer_id = myReqServicesList.get(position).getUser_customer_id();

               Call<ServiceRequets> call = MyRequestsServicesListAdapter.apiInterface.updateOnClickRequests(status,requestservice_id);
               call.enqueue(new Callback<ServiceRequets>() {
                   @Override
                   public void onResponse(Call<ServiceRequets> call, Response<ServiceRequets> response) {
                       if(response.body().getResponse().equals("ok")){

                           notifyDataSetChanged();
                           final String body = name_user+ " has Declined your request";
                           final String title = "Vartista- Decline";
                           insertNotification(title,body,user_id,customer_id,1,get_Current_Date());
                           Call<NotificationsManager> callNotification = MyRequestsServicesListAdapter.sendNotificationApiInterface
                                   .sendPushNotification(myReqServicesList.get(position).getUser_customer_id(),
                                           body,title);
                           callNotification.enqueue(new Callback<NotificationsManager>() {
                               @Override
                               public void onResponse(Call<NotificationsManager> call, Response<NotificationsManager> response) {
                                   if(response.isSuccessful()){}
                             }


                               @Override
                               public void onFailure(Call<NotificationsManager> call, Throwable t) {

                               }
                           });


                       }

                       else if(response.body().getResponse().equals("error")){

                           MDToast.makeText(view.getContext(),"Something went wrong....",Toast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();

                       }
                       else{
                           MDToast.makeText(view.getContext(),"Something went wrong....",Toast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();

                       }
                       remove(position);
                   }

                   @Override
                   public void onFailure(Call<ServiceRequets> call, Throwable t) {
                       MDToast.makeText(view.getContext(),"Update Failed",Toast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();

                   }
               });
               MDToast.makeText(view.getContext(),"Request Declined",Toast.LENGTH_SHORT,MDToast.TYPE_WARNING).show();

           }
       });



    }

    @Override
    public int getItemCount() {

        return myReqServicesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public TextView tv_Title,tv_Service,tv_date,tv_time,tv_address,tv_s_desc,tv_catogery;
        public Button accept , decline;
        public ImageView profile_image;

        public ViewHolder(View itemView) {
            super(itemView);
            mView=itemView;

            profile_image=mView.findViewById(R.id.profile_image);
            tv_Title=(TextView)mView.findViewById(R.id.textView_req_name);
            tv_Service=(TextView)mView.findViewById(R.id.textView_req_service);
            tv_address=(TextView)mView.findViewById(R.id.textview_address);
            tv_date=(TextView)mView.findViewById(R .id.textViewReq_Date);
            tv_time=(TextView)mView.findViewById(R .id.textViewReq_Time);
            tv_s_desc=(TextView)mView.findViewById(R .id.textView_service_description);
            tv_catogery=(TextView)mView.findViewById(R .id.service_category);
            accept = mView.findViewById(R.id.button_paynow);
            decline = mView.findViewById(R.id.buttonReject);
            apiInterface= ApiClient.getApiClient().create(ApiInterface.class);
            sendNotificationApiInterface = ApiClient.getApiClient().create(SendNotificationApiInterface.class);

        }
    }

//
//    protected void removeListItem(final View rowView, final int position) {
//        // TODO Auto-generated method stub
//
//        final Animation animation = AnimationUtils.loadAnimation(rowView.getContext(), R.anim.scale_down);
//        rowView.startAnimation(animation);
//        Handler handle = new Handler();
//        handle.postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                // TODO Auto-generated method stub
//                myReqServicesList.remove(position);
//
//                animation.cancel();
//            }
//        }, 1000);
//    }


    public void remove(int position) {
        myReqServicesList.remove(position);
        notifyItemRemoved(position);
    }

    public void insertreviewnil(int user_id,int service_p_id,int service_id){
        String servicetittle = "";
        String Remarks = "";
        String time = "";
        String date = "";
        Call<CreateRequest> call2 = MyRequestsServicesListAdapter.apiInterface.InsertRatings(0,0.0,user_id,service_p_id,service_id,Remarks,date,time);

        call2.enqueue(new Callback<CreateRequest>() {
            @Override
            public void onResponse(Call<CreateRequest> call, Response<CreateRequest> response) {
                if (response.body().getResponse().equals("ok")) {

                    MDToast mdToast = MDToast.makeText(context, "Your Ratings are inserted", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
                    mdToast.show();


                }


            }

            @Override
            public void onFailure(Call<CreateRequest> call, Throwable t) {
                //
                // Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });


    }




          public static void sendCompactNotification(Context context , int requestcode , String date , String time,String name,String timeformat,int timevalue,int Reqeustserviceid){
              AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
//              PendingIntent pendingIntent;
              String appointmentdate = date+" "+time;
              SimpleDateFormat showsdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
              Date date1 = null;
              try {
                  date1 = showsdf.parse(appointmentdate);
              } catch (ParseException e) {
                  Log.d("Date Parsing",""+e.getMessage());
                  e.printStackTrace();
              }
              Calendar calendar = Calendar.getInstance();
              calendar.setTime(date1);
              if (timeformat.equals("hour")){
                  calendar.add(Calendar.HOUR,timevalue);
              }
              else{
                  calendar.add(Calendar.MINUTE,timevalue);

              }
              Intent intent = new Intent("alarm");
              intent.putExtra("username",name);
              intent.putExtra("requestcode",requestcode);
              intent.putExtra("service_id",Reqeustserviceid);
//              if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                  pendingIntent = PendingIntent.getForegroundService(context, 0, intent, 0);
//              }else {
//                  pendingIntent = PendingIntent.getService(context, 0, intent, 0);
//              }
              PendingIntent broadcast = PendingIntent.getBroadcast(context,requestcode,intent,PendingIntent.FLAG_UPDATE_CURRENT);
              alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),broadcast);

          }


     public  void insertNotification(String title , String message, int sender_id , int receiver_id , int status , String created_at){
//         setUIToWait(true);
         Call<AllNotificationBean> call=MyRequestsServicesListAdapter.apiInterface.Insert_Notification(title,message,sender_id,receiver_id,status,created_at);
         call.enqueue(new Callback<AllNotificationBean>() {
             @Override
             public void onResponse(Call <AllNotificationBean> call, Response<AllNotificationBean> response) {

                 if(response.body().getResponse().equals("ok")){
//                     setUIToWait(false);

                 }
                 else if(response.body().getResponse().equals("exist")){
//                     setUIToWait(false);

                 }
                 else if(response.body().getResponse().equals("error")){
//                     setUIToWait(false);


                 }

                 else{
//                     setUIToWait(false);


                 }

             }

             @Override
             public void onFailure(Call <AllNotificationBean> call, Throwable t) {

             }
         });


}


public String get_Current_Date(){
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    String currentDate = sdf.getDateTimeInstance().format(new Date());
    return currentDate;
}




 private void setUIToWait(boolean wait) {

        if (wait) {
        progressDialog = ProgressDialog.show(context, null, null, true, true);
//            progressDialog.setContentView(new ProgressBar(this));
        progressDialog.setContentView(R.layout.loader);

        } else {
        progressDialog.dismiss();
        }
        }
}
