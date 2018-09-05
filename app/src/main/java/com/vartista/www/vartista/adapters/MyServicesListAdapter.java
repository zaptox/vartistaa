package com.vartista.www.vartista.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.valdesekamdem.library.mdtoast.MDToast;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.Service;
import com.vartista.www.vartista.modules.provider.CreateServiceActivity;
import com.vartista.www.vartista.modules.provider.MyServicesListActivity;
import com.yarolegovich.lovelydialog.LovelyInfoDialog;
import com.yarolegovich.lovelydialog.LovelyProgressDialog;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vksoni on 2/26/2018.
 */

public class MyServicesListAdapter extends RecyclerView.Adapter<MyServicesListAdapter.ViewHolder> {
   public static List<Service> myServicesList;
   public Context context;
    Gson gson;
     int service_id=0;
    Button edit ,delete;


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
    public void onBindViewHolder(ViewHolder holder, final int position) {


       final int abhiwali= position;

    if (myServicesList.get(position).getService_title().length()>20){
        holder.tvTitle.setText(myServicesList.get(position).getService_title().substring(0,20)+"...");
    }
    else {
        holder.tvTitle.setText(myServicesList.get(position).getService_title());
    }
    holder.tvCategory.setText(""+myServicesList.get(position).getCategory_name());
    holder.tvPrice.setText(""+myServicesList.get(position).getPrice());
    if (myServicesList.get(position).getLocation().length()>50){
        holder.tvLocation.setText(" "+myServicesList.get(position).getLocation().substring(0,50)+"...");
    }
    else {
        holder.tvLocation.setText(" "+myServicesList.get(position).getLocation());
    }

    if (myServicesList.get(position).getService_description().length()>120){
        holder.tvDescription.setText("" + myServicesList.get(position).getService_description().substring(0,120)+"...");
    }
    else {
        holder.tvDescription.setText("" + myServicesList.get(position).getService_description());
    }
    //Log.d("Description length",myServicesList.get(position).getService_description().length()+"");



    // holder.tv.setText(""+myServicesList.get(position).getPrice());


     service_id= myServicesList.get(position).getService_id();
    final int user_id= myServicesList.get(position).getService_id();

    edit= holder.mView.findViewById(R.id.edit);
    delete =holder.mView.findViewById(R.id.delete);
    gson = new Gson();

    delete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new LovelyStandardDialog(v.getContext(), LovelyStandardDialog.ButtonLayout.VERTICAL)
                    .setTopColorRes(R.color.color_danger)
                    .setButtonsColorRes(R.color.colorSuccess)
                    .setIcon(R.drawable.delete_small)
                    .setTitle("Delete")
                    .setMessage("Are you sure to delete your service?")
                    .setPositiveButton("Yes", new View.OnClickListener() {
                        @Override
                        public void onClick(final View v) {
                            Call<Service> call = CreateServiceActivity.apiInterface.deleteService(user_id);
                            call.enqueue(new Callback<Service>() {
                                @Override
                                public void onResponse(Call<Service> call, Response<Service> response) {

                                    if (response.body().getResponse().equals("ok")) {



                                        Intent intent=new Intent(v.getContext(),MyServicesListActivity.class);


                                        intent.putExtra("edit_user_id",user_id);


                                        //intent.putStringArrayListExtra("myservicelist",myServicesList);
                                        v.getContext().startActivity(intent);
                                        MDToast mdToast = MDToast.makeText(v.getContext(), "Your Service Deleted Successfully", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
                                        mdToast.show();


                                    }
                                    if (response.isSuccessful()) {
                                        //for debugging

                                    }
                                }

                                @Override
                                public void onFailure(Call<Service> call, Throwable t) {
                                    Toast.makeText(v.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            });

                        }
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .show();

        }
    });

    edit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(v.getContext(),CreateServiceActivity.class);


            intent.putExtra("edit_user_id",user_id);


            //intent.putStringArrayListExtra("myservicelist",myServicesList);
            v.getContext().startActivity(intent);

//            Toast.makeText(context,"edit ID:"+user_id,Toast.LENGTH_SHORT).show();
        }
    });

    holder.mView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        //Toast.makeText(context,"requesr ID:"+user_id,Toast.LENGTH_SHORT).show();

            new LovelyInfoDialog(view.getContext())
                    .setTopColorRes(R.color.price_color)

                    //This will add Don't show again checkbox to the dialog. You can pass any ID as argument

                    .setTitle("Detail")

                    .setMessage(""+    myServicesList.get(position).getService_description())
                    .show();


        }
    });


    }


    @Override
    public int getItemCount() {
        return myServicesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
     View mView;

     public TextView tvTitle,tvCategory,tvPrice,tvLocation,tvDescription;
     Button edit ,delete;

        public ViewHolder(View itemView) {
            super(itemView);
            mView=itemView;

            tvTitle=(TextView)mView.findViewById(R.id.textViewServiceTitle);
            tvCategory=(TextView)mView.findViewById(R.id.textViewCategory);
            tvPrice=(TextView)mView.findViewById(R .id.tvServicePrice);
            edit = (Button)mView.findViewById(R.id.edit);
            delete = (Button)mView.findViewById(R.id.delete);
            tvLocation= (TextView)mView.findViewById(R.id.tvlocation);
            tvDescription= (TextView)mView.findViewById(R.id.tvDescription);


        }
    }
}
