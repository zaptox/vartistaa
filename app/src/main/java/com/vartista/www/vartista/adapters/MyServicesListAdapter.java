package com.vartista.www.vartista.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.Service;

import java.util.List;

/**
 * Created by Vksoni on 2/26/2018.
 */

public class MyServicesListAdapter extends RecyclerView.Adapter<MyServicesListAdapter.ViewHolder> {
   public List<Service> myServicesList;
   public Context context;
   public MyServicesListAdapter(Context context, List<Service> myServicesList){
       this.myServicesList = myServicesList;
       this.context=context;

   }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.services_list_item,parent,false);

        return new ViewHolder(view);
    }
@Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    holder.tvTitle.setText(myServicesList.get(position).getService_title());
    holder.tvCategory.setText(""+myServicesList.get(position).getCategory_name());
    holder.tvPrice.setText(""+myServicesList.get(position).getPrice());


    final int service_id= myServicesList.get(position).getService_id();
    holder.mView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        Toast.makeText(context,"requesr ID:"+service_id,Toast.LENGTH_SHORT).show();
        }
    });

    }


    @Override
    public int getItemCount() {
        return myServicesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
     View mView;

     public TextView tvTitle,tvCategory,tvPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            mView=itemView;

            tvTitle=(TextView)mView.findViewById(R.id.textViewServiceTitle);
            tvCategory=(TextView)mView.findViewById(R.id.textViewCategory);
            tvPrice=(TextView)mView.findViewById(R .id.tvServicePrice);

        }
    }
}
