package com.vartista.www.vartista.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vartista.www.vartista.beans.Category;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.modules.provider.ProviderFragments.MyServiceRequestsFragment;
import com.vartista.www.vartista.modules.user.BookNowActivity;
import com.vartista.www.vartista.modules.user.FindServicesInList;
import com.vartista.www.vartista.modules.user.user_fragments.FindServicesInListFragment;

import java.util.List;

public class CategoriesListAdapter extends RecyclerView.Adapter<CategoriesListAdapter.ViewHolder> {
    public List<Category> myCategoryList;
    public Context context;
    ViewPager viewPager;
    TabLayout tabLayout;
    FragmentActivity myContext;
    public CategoriesListAdapter(Context context, List<Category> myCategoryList){
        this.myCategoryList = myCategoryList;
        this.context=context;
    }
//    public CategoriesListAdapter(Context context, List<Category> myCategoryList, ViewPager viewPager){
//        this.myCategoryList = myCategoryList;
//        this.context=context;
//        this.viewPager=viewPager;
//    }

    public CategoriesListAdapter(Context context, List<Category> myCategoryList, TabLayout tabLayout, FragmentActivity myContext){
        this.myCategoryList = myCategoryList;
        this.context=context;
        this.tabLayout=tabLayout;
        this.myContext=myContext;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_item,parent,false);

        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.tvCategoryName.setText(myCategoryList.get(position).getCategory_name());

//        if(myCategoryList.get(position).getCategory_name().equalsIgnoreCase("Beautician")){
//            holder.imageView.setImageResource(R.drawable.beautican);
//
//        }
//        else if(myCategoryList.get(position).getCategory_name().equalsIgnoreCase("Plumber")){
//            holder.imageView.setImageResource(R.drawable.plumber);
//
//        }
//        else {
//            holder.imageView.setImageResource(R.drawable.ele);
//        }

    if(myCategoryList.get(position).getImage().equals("") || myCategoryList.get(position).getImage()==null ){
        holder.imageView.setImageResource(R.drawable.ele);
    }
        else {
        Picasso.get().load(myCategoryList.get(position).getImage()).fit().centerCrop()
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile)
                .into(holder.imageView);
    }
//        holder.imageView.setImageResource(R.drawable.ele);
        final int cat_id= myCategoryList.get(position).getCat_id();
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                viewPager.setVisibility(View.INVISIBLE);

// ye hai neechy wala kaaam waaala

//   tabLayout.setVisibility(View.GONE);


                FragmentManager manager = myContext.getSupportFragmentManager();
                manager.beginTransaction().remove(manager.findFragmentById(R.id.viewpager)).replace(R.id.fragment_frame_layout, new FindServicesInListFragment(cat_id,tabLayout)).addToBackStack("TAG").commit();

//                Intent intent=new Intent(view.getContext(), FindServicesInList.class);
//                intent.putExtra("cat_id",cat_id);
//                context.startActivity(intent);


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
