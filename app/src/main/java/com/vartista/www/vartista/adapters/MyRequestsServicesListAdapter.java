package com.vartista.www.vartista.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.vartista.www.vartista.adapters.MyRequestsServicesListAdapter;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.Service;
import com.vartista.www.vartista.beans.ServiceRequets;

import java.util.List;

public class MyRequestsServicesListAdapter extends RecyclerView.Adapter<MyRequestsServicesListAdapter.ViewHolder>{
    public List<ServiceRequets> myReqServicesList;
    public Context context;

    public MyRequestsServicesListAdapter(Context context, List<ServiceRequets> myReqServicesList){
        this.myReqServicesList = myReqServicesList;
        this.context=context;
        Toast.makeText(context, ""+myReqServicesList.size(), Toast.LENGTH_SHORT).show();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.req_list_item,parent,false);

        return new MyRequestsServicesListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tv_Title.setText(myReqServicesList.get(position).getUsername());
        holder.tv_Category.setText(myReqServicesList.get(position).getCatgname());
        holder.tv_date.setText(myReqServicesList.get(position).getDate());
        holder.tv_time.setText(myReqServicesList.get(position).getTime());

    }

    @Override
    public int getItemCount() {

        return myReqServicesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public TextView tv_Title,tv_Category,tv_date,tv_time;

        public ViewHolder(View itemView) {
            super(itemView);
            mView=itemView;

            tv_Title=(TextView)mView.findViewById(R.id.textView_req_name);
            tv_Category=(TextView)mView.findViewById(R.id.textView_req_service);
            tv_date=(TextView)mView.findViewById(R .id.textViewReq_Date);
            tv_time=(TextView)mView.findViewById(R .id.textViewReq_Time);

        }
    }
}
