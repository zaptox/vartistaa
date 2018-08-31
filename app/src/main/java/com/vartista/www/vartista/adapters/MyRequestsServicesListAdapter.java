package com.vartista.www.vartista.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vartista.www.vartista.MyServiceRequests;
import com.vartista.www.vartista.R;

public class MyRequestsServicesListAdapter extends RecyclerView.Adapter<MyRequestsServicesListAdapter.ViewHolder>{


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.services_list_item,parent,false);

        return new MyRequestsServicesListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public TextView tvTitle,tvCategory,tvPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            mView=itemView;

//            tvTitle=(TextView)mView.findViewById(R.id.textViewServiceTitle);
//            tvCategory=(TextView)mView.findViewById(R.id.textViewCategory);
//            tvPrice=(TextView)mView.findViewById(R .id.tvServicePrice);

        }
    }
}
