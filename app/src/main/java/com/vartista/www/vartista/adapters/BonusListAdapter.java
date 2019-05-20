package com.vartista.www.vartista.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.ProviderBonusBean;

import java.util.List;

/**
 * Created by Yusha on 2019-05-07.
 */

public class BonusListAdapter extends RecyclerView.Adapter<BonusListAdapter.ViewHolder> {



    Context context;
    List<ProviderBonusBean> list;

    public BonusListAdapter(Context context, List<ProviderBonusBean> list) {
        this.context = context;
        this.list = list;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bonus_in_row,parent,false);
        return new BonusListAdapter.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.bonus_title.setText("Description: "+list.get(position).getBonusType());
        holder.date.setText(list.get(position).getCreatedAt());
        holder.bonus.setText("Bonus: "+list.get(position).getAmount()+"");


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public TextView bonus_title,date, bonus;

        public ViewHolder(View itemView) {
            super(itemView);
            mView=itemView;

            bonus_title =(TextView)mView.findViewById(R.id.txt_bonus_type);
            date=(TextView)mView.findViewById(R.id.txt_created_at);
            bonus =(TextView)mView.findViewById(R.id.txt_bonus_amount);
        }
    }

}
