package com.vartista.www.vartista.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.vartista.www.vartista.modules.general.HomeActivity;
import com.vartista.www.vartista.modules.provider.ProviderFragments.EarningFragment;
import com.vartista.www.vartista.modules.user.BookNowActivity;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.Service;
import com.vartista.www.vartista.modules.user.user_fragments.BookNowFragment;
import com.vartista.www.vartista.util.CONST;

import java.util.List;

/**
 * Created by Vksoni on 2/26/2018.
 */

public class SpDetailsAdapter extends RecyclerView.Adapter<SpDetailsAdapter.ViewHolder> {
   public List<Service> myServicesList;
   int provider_id,cat_id,user_id;
   public Context context;
   FragmentActivity myContext;
   TabLayout tabLayout;
   public SpDetailsAdapter(Context context, List<Service> myServicesList, int provider_id, int cat_id, int user_id){
       this.myServicesList = myServicesList;
       this.context=context;
       this.provider_id=provider_id;
       this.cat_id=cat_id;
       this.user_id=user_id;
   }

    public SpDetailsAdapter(Context context, List<Service> myServicesList, int provider_id, int cat_id, int us0er_id, FragmentActivity myContext){
        this.myServicesList = myServicesList;
        this.context=context;
        this.provider_id=provider_id;
        this.cat_id=cat_id;
        this.user_id=user_id;
        this.myContext=myContext;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.sp_details,parent,false);

        return new ViewHolder(view);
    }
@Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

//    holder.home_avail_status.setVisibility(View.INVISIBLE);

    holder.tvService.setText(myServicesList.get(position).getService_title());
    holder.tvPrice.setText("Cost: "+myServicesList.get(position).getPrice()+"£");

    if(myServicesList.get(position).getHome_avail_status()==1){

//        holder.home_avail_status.setVisibility(View.VISIBLE);

    }
    else{
        holder.home_avail_status.setText("(Not Available For Home)");
        holder.home_avail_status.setTextColor(context.getResources().getColor(R.color.black));
    }


    holder.btnBookNow.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(v.getContext(), HomeActivity.class);
            intent.putExtra("fragment_Flag", CONST.BOOK_NOW__FRAGMENT);
            intent.putExtra("provider_id",provider_id);
            intent.putExtra("cat_id",cat_id);
            intent.putExtra("user_id",user_id);
            intent.putExtra("service_id",myServicesList.get(position).getService_id());
            context.startActivity(intent);




        }
    });
    final int service_id= myServicesList.get(position).getService_id();
    holder.mView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
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

     public TextView tvService,tvPrice,home_avail_status;

        public ViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
            home_avail_status=mView.findViewById(R.id.home_avail_status);
            tvService=(TextView)mView.findViewById(R.id.textView_req_service);
            tvPrice=(TextView)mView.findViewById(R .id.textViewPrice);
            btnBookNow=(Button)mView.findViewById(R.id.buttonBookNow);
        }
    }
}
