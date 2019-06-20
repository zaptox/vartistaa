package com.vartista.www.vartista.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.valdesekamdem.library.mdtoast.MDToast;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.ProviderDocuments;

import java.util.List;

/**
 * Created by Yousha Arif on 4/18/2019.
 */

public class ProviderDocumentAdapter extends RecyclerView.Adapter<ProviderDocumentAdapter.ViewHolder>{

    public static List<ProviderDocuments> providerDocumentsList;
    public Activity context;

    public ProviderDocumentAdapter(Activity context, List<ProviderDocuments> providerDocuments) {
        this.context = context;
        this.providerDocumentsList = providerDocuments;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_doc_item,parent,false);

        return new ViewHolder(view);
    }
    @Override
    public int getItemCount() {
        return providerDocumentsList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public TextView fileName;

        public ViewHolder(View itemView) {
            super(itemView);
            mView=itemView;

            fileName =(TextView)mView.findViewById(R.id.txt_file_name);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final int currentPosition = position;

        final String title = providerDocumentsList.get(position).getFileName();
        final String URL = providerDocumentsList.get(position).getFilePath();

        if (title.length() > 20) {
            holder.fileName.setText(title.substring(0, 20) + "...");
        } else {
            holder.fileName.setText(title);
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.dialog_view_image);
                    // set the custom dialog components - text, image and button
                    TextView text = (TextView) dialog.findViewById(R.id.img_title);
                    text.setText(title);
                    ImageView image = (ImageView) dialog.findViewById(R.id.img_photo);
                    Picasso.get().load(URL).fit().centerCrop()
                            .placeholder(R.drawable.folder)
                            .error(R.drawable.folder)
                            .into(image);

                    dialog.show();
                }
                catch(Exception e){
                    MDToast.makeText(context, "No Document found", MDToast.LENGTH_SHORT,MDToast.TYPE_INFO).show();

                }
            }
        });

    }
}
