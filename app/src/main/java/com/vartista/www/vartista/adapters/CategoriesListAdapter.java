package com.vartista.www.vartista.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vartista.www.vartista.beans.Category;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.modules.user.BookNowActivity;
import com.vartista.www.vartista.modules.user.FindServicesInList;

import java.util.List;

public class CategoriesListAdapter extends RecyclerView.Adapter<CategoriesListAdapter.ViewHolder> {
    public List<Category> myCategoryList;
    public Context context;
    public CategoriesListAdapter(Context context, List<Category> myCategoryList){
        this.myCategoryList = myCategoryList;
        this.context=context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_item,parent,false);

        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.tvCategoryName.setText(myCategoryList.get(position).getCategory_name());

        if(myCategoryList.get(position).getCategory_name().equalsIgnoreCase("Beautician")){
            holder.imageView.setImageResource(R.drawable.beautican);

        }
        else if(myCategoryList.get(position).getCategory_name().equalsIgnoreCase("Plumber")){
            holder.imageView.setImageResource(R.drawable.plumber);

        }
        else {
            holder.imageView.setImageResource(R.drawable.ele);
        }
        final int cat_id= myCategoryList.get(position).getCat_id();
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"cat_id :"+cat_id,Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(view.getContext(), FindServicesInList.class);
                intent.putExtra("cat_id",cat_id);

                context.startActivity(intent);


            }
        });

    }


    @Override
    public int getItemCount() {
        return myCategoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public TextView tvCategoryName;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mView=itemView;

            tvCategoryName=(TextView)mView.findViewById(R.id.tvCategoryItem);
            imageView=(ImageView) mView.findViewById(R .id.imageViewCategoryIcon);

        }
    }
}
