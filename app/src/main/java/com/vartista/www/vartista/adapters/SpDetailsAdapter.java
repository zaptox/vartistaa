package com.vartista.www.vartista.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.vartista.www.vartista.modules.user.BookNowActivity;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.Service;

import java.util.List;

/**
 * Created by Vksoni on 2/26/2018.
 */

public class SpDetailsAdapter extends RecyclerView.Adapter<SpDetailsAdapter.ViewHolder> {
   public List<Service> myServicesList;
   int provider_id,cat_id,user_id;
   public Context context;
   public SpDetailsAdapter(Context context, List<Service> myServicesList, int provider_id, int cat_id, int user_id){
       this.myServicesList = myServicesList;
       this.context=context;
       this.provider_id=provider_id;
       this.cat_id=cat_id;
       this.user_id=user_id;
   }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.sp_details,parent,false);

        return new ViewHolder(view);
    }
@Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

    holder.tvService.setText(myServicesList.get(position).getService_title());
    holder.tvPrice.setText(""+myServicesList.get(position).getPrice());

    holder.btnBookNow.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(v.getContext(), BookNowActivity.class);
            intent.putExtra("provide_id",provider_id);
            intent.putExtra("cat_id",cat_id);
            intent.putExtra("user_id",user_id);
            intent.putExtra("service_id",myServicesList.get(position).getService_id());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);

            context.startActivity(intent);
        }
    });
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
        Button btnBookNow;

     public TextView tvService,tvPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            mView=itemView;

            tvService=(TextView)mView.findViewById(R.id.textViewService);
            tvPrice=(TextView)mView.findViewById(R .id.textViewPrice);
            btnBookNow=(Button)mView.findViewById(R.id.buttonBookNow);
        }
    }
}
