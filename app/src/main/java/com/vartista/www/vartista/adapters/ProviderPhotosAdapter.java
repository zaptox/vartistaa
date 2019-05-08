package com.vartista.www.vartista.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.ProviderPhotos;
import com.vartista.www.vartista.modules.user.GetDocumentActivity;

import java.util.List;

/**
 * Created by Samina Arif on 4/18/2019.
 */

public class ProviderPhotosAdapter extends RecyclerView.Adapter<ProviderPhotosAdapter.ViewHolder>{

    public static List<ProviderPhotos> providerPhotosList;
    public Activity context;

    public ProviderPhotosAdapter(Activity context, List<ProviderPhotos> providerDocuments) {
        this.context = context;
        this.providerPhotosList = providerDocuments;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_photo_item,parent,false);

        return new ViewHolder(view);
    }
    @Override
    public int getItemCount() {
        return providerPhotosList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public TextView fileName;

        public ViewHolder(View itemView) {
            super(itemView);
            mView=itemView;

            fileName =(TextView)mView.findViewById(R.id.txt_photo_name);


        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final String title = providerPhotosList.get(position).getTitle();
        final String URL = providerPhotosList.get(position).getUrl();

        if (title.length() > 20) {
            holder.fileName.setText(title.substring(0, 20) + "...");
        } else {
            holder.fileName.setText(title);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_view_image);
                // set the custom dialog components - text, image and button
                TextView text = (TextView) dialog.findViewById(R.id.img_title);
                text.setText(title);
                ImageView image = (ImageView) dialog.findViewById(R.id.img_photo);
//                Toast.makeText(context, ""+URL, Toast.LENGTH_SHORT).show();
                Picasso.get().load(URL).fit().centerCrop()
                        .placeholder(R.drawable.pictures)
                        .error(R.drawable.pictures)
                        .into(image);

                dialog.show();

            }
        });


    }
}
