package com.vartista.www.vartista.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.valdesekamdem.library.mdtoast.MDToast;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.ServiceRequets;
import com.vartista.www.vartista.restcalls.ApiClient;
import com.vartista.www.vartista.restcalls.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyRequestsServicesListAdapter extends RecyclerView.Adapter<MyRequestsServicesListAdapter.ViewHolder>{
    public List<ServiceRequets> myReqServicesList;
    public Context context;
    public static ApiInterface apiInterface;

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
        holder.tv_Category.setText(myReqServicesList.get(position).getCatgname());
        holder.tv_date.setText(myReqServicesList.get(position).getDate());
        holder.tv_time.setText(myReqServicesList.get(position).getTime());
       holder.accept.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(final View view) {
               int status = 1;
               int requestservice_id = myReqServicesList.get(position).getReqservice_id();
                              Call<ServiceRequets> call = MyRequestsServicesListAdapter.apiInterface.updateOnClickRequests(status,requestservice_id);
               call.enqueue(new Callback<ServiceRequets>() {
                   @Override
                   public void onResponse(Call<ServiceRequets> call, Response<ServiceRequets> response) {

                                              if(response.body().getResponse().equals("ok")){
                                                  remove(position);
                                                  notifyDataSetChanged();
                                                  MDToast.makeText(view.getContext(),"Request Accepted",Toast.LENGTH_SHORT).show();

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

        public TextView tv_Title,tv_Category,tv_date,tv_time;
        public Button accept , decline;
        public ViewHolder(View itemView) {
            super(itemView);
            mView=itemView;

            tv_Title=(TextView)mView.findViewById(R.id.textView_req_name);
            tv_Category=(TextView)mView.findViewById(R.id.textView_req_service);
            tv_date=(TextView)mView.findViewById(R .id.textViewReq_Date);
            tv_time=(TextView)mView.findViewById(R .id.textViewReq_Time);
            accept = mView.findViewById(R.id.button_paynow);
            decline = mView.findViewById(R.id.buttonReject);
            apiInterface= ApiClient.getApiClient().create(ApiInterface.class);

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

}
