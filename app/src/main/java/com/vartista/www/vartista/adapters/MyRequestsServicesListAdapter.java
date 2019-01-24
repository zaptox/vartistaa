package com.vartista.www.vartista.adapters;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
import com.vartista.www.vartista.beans.CreateRequest;
import com.vartista.www.vartista.beans.NotificationsManager;
import com.vartista.www.vartista.beans.ServiceRequets;
import com.vartista.www.vartista.modules.general.HomeActivity;
import com.vartista.www.vartista.modules.user.AssignRatings;
import com.vartista.www.vartista.restcalls.ApiClient;
import com.vartista.www.vartista.restcalls.ApiInterface;
import com.vartista.www.vartista.restcalls.SendNotificationApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyRequestsServicesListAdapter extends RecyclerView.Adapter<MyRequestsServicesListAdapter.ViewHolder>{
    public List<ServiceRequets> myReqServicesList;
    public Context context;
    public static ApiInterface apiInterface;
    public static SendNotificationApiInterface sendNotificationApiInterface;
    public static final int REQUEST_CODE_SP=0;
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

        Picasso.get().load(myReqServicesList.get(position).getUser_image()).fit().centerCrop()
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile)
                .into(holder.profile_image);

       holder.accept.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(final View view) {
               int status = 1;
               int requestservice_id = myReqServicesList.get(position).getReqservice_id();
               Call<ServiceRequets> call = MyRequestsServicesListAdapter.apiInterface.updateOnClickRequests(status,requestservice_id);
               call.enqueue(new Callback<ServiceRequets>() {
                   @Override
                   public void onResponse(Call<ServiceRequets> call, Response<ServiceRequets> response) {

                       SharedPreferences ob = context.getSharedPreferences("Login", Context.MODE_PRIVATE);

                       final String name_user = ob.getString("name","");


//                            Toast.makeText(context,myReqServicesList.get(position).getService_provider_id(),Toast.LENGTH_SHORT).show();
                       if(response.body().getResponse().equals("ok")){

                           notifyDataSetChanged();
                           Call<NotificationsManager> callNotification = MyRequestsServicesListAdapter.sendNotificationApiInterface
                                   .sendPushNotification(myReqServicesList.get(position).getUser_customer_id(),
                                           name_user+ " Accepted  your request","Vartista-Accept");
                           callNotification.enqueue(new Callback<NotificationsManager>() {
                               @Override
                               public void onResponse(Call<NotificationsManager> call, Response<NotificationsManager> response) {
                                   if(response.isSuccessful())
                                       Toast.makeText(view.getContext(), "Request Accepted",Toast.LENGTH_SHORT).show();

                               }


                               @Override
                               public void onFailure(Call<NotificationsManager> call, Throwable t) {

                               }
                           });

                                              }

                       else if(response.body().getResponse().equals("error")){

                           Toast.makeText(view.getContext(),"Something went wrong....",Toast.LENGTH_SHORT).show();

                       }
                       else{
                           Toast.makeText(view.getContext(),"Something went wrong....",Toast.LENGTH_SHORT).show();

                       }
                       remove(position);


                   }

                   @Override
                   public void onFailure(Call<ServiceRequets> call, Throwable t) {
                       Toast.makeText(view.getContext(),"Update Failed",Toast.LENGTH_SHORT).show();
                   }
               });


           }
       });



       holder.decline.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(final View view) {
               int status = -1;
               int requestservice_id = myReqServicesList.get(position).getReqservice_id();

               Call<ServiceRequets> call = MyRequestsServicesListAdapter.apiInterface.updateOnClickRequests(status,requestservice_id);
               call.enqueue(new Callback<ServiceRequets>() {
                   @Override
                   public void onResponse(Call<ServiceRequets> call, Response<ServiceRequets> response) {
                       if(response.body().getResponse().equals("ok")){

                           MDToast.makeText(view.getContext(),"Request Declined",Toast.LENGTH_SHORT).show();
                           remove(position);
                           notifyDataSetChanged();


                           SharedPreferences ob = context.getSharedPreferences("Login", Context.MODE_PRIVATE);

                           final String name_user = ob.getString("name","");



                           Call<NotificationsManager> callNotification = MyRequestsServicesListAdapter.sendNotificationApiInterface
                                   .sendPushNotification(myReqServicesList.get(position).getUser_customer_id(),
                                           name_user+ " has Declined your request","Vartista-Decline");
                           callNotification.enqueue(new Callback<NotificationsManager>() {
                               @Override
                               public void onResponse(Call<NotificationsManager> call, Response<NotificationsManager> response) {
                                   if(response.isSuccessful())
                                       MDToast.makeText(view.getContext(),"Request Accepted",Toast.LENGTH_SHORT).show();
                               }


                               @Override
                               public void onFailure(Call<NotificationsManager> call, Throwable t) {

                               }
                           });







                       }

                       else if(response.body().getResponse().equals("error")){

                           Toast.makeText(view.getContext(),"Something went wrong....",Toast.LENGTH_SHORT).show();

                       }
                       else{
                           Toast.makeText(view.getContext(),"Something went wrong....",Toast.LENGTH_SHORT).show();

                       }
                   }

                   @Override
                   public void onFailure(Call<ServiceRequets> call, Throwable t) {
                       Toast.makeText(view.getContext(),"Update Failed",Toast.LENGTH_SHORT).show();
                   }
               });
//               removeListItem(view,position);
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








}
