package com.vartista.www.vartista.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.servicepaapointmentsitems;
import com.vartista.www.vartista.modules.user.AssignRatings;
import com.vartista.www.vartista.modules.user.UserAppointmentDetails;

import java.util.List;

public class ServiceUserAppointmentsAdapter extends RecyclerView.Adapter<ServiceUserAppointmentsAdapter.ViewHolder> {

    Context context;
    List<servicepaapointmentsitems> list;
    boolean completed=false;
    boolean forratings=false;
    public ServiceUserAppointmentsAdapter(Context context, List<servicepaapointmentsitems> list) {
        this.context = context;
        this.list = list;
    }
    public ServiceUserAppointmentsAdapter(Context context, List<servicepaapointmentsitems> list,boolean completed) {
        this.context = context;
        this.list = list;
        this.completed=completed;
    }


    @NonNull
    @Override
    public ServiceUserAppointmentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.meeting_items,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ServiceUserAppointmentsAdapter.ViewHolder holder, final int position) {


        holder.rating_text.setVisibility(View.INVISIBLE);

        holder.serviceprovidername.setText(list.get(position).getUsername());
        holder.servicecharges.setText(list.get(position).getService_title()+" "+list.get(position).getPrice()+"£");
        holder.Date.setText(list.get(position).getDate());
        holder.Time.setText(list.get(position).getTime());
        holder.serviceCat.setText(list.get(position).getName());
        holder.serviceDesc.setText(list.get(position).getService_description());
        holder.serviceLoc.setText(list.get(position).getLocation());
        holder.contact.setText("Contact: "+list.get(position).getContact());
        if(completed==false) {

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), UserAppointmentDetails.class);
                    intent.putExtra("object", list.get(position));
                    v.getContext().startActivity(intent);
                }
            });
        }
        else if (completed==true){

            if(list.get(position).getRating_status().equals("0")) {
                holder.rating_text.setVisibility(View.VISIBLE);

                    holder.mView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(v.getContext(), AssignRatings.class);
                            intent.putExtra("object", list.get(position));
                            v.getContext().startActivity(intent);
                        }
                    });

                }


        }


        Picasso.get().load(list.get(position).getImage()).fit().centerCrop()
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile)
                .into(holder.profile_image);
            }

    @Override
    public int getItemCount() { return list.size(); }


    public class ViewHolder extends RecyclerView.ViewHolder{
      public  View mView;


        public TextView serviceprovidername,servicecharges,Date,Time,serviceDesc,serviceCat,serviceLoc,contact,rating_text;
        public ImageView profile_image;
        public ViewHolder(View itemView) {
            super(itemView);
            mView=itemView;

            serviceprovidername=(TextView)mView.findViewById(R.id.textViewname_user);
            servicecharges=(TextView)mView.findViewById(R.id.servicedetail_user);
            Date=(TextView)mView.findViewById(R .id.textViewdate_user);
            Time=(TextView)mView.findViewById(R .id.textViewtime_user);
            serviceCat=(TextView)mView.findViewById(R.id.service_category_user);
            serviceDesc=(TextView)mView.findViewById(R.id.textView_service_description_user);
            serviceLoc= (TextView)mView.findViewById(R.id.textViewloc_user);
            profile_image=mView.findViewById(R.id.profile_image);
            contact=(TextView)mView.findViewById(R.id.textView_sp_contact);
            rating_text=mView.findViewById(R.id.rating_text);

        }
    }
}
