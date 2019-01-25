package com.vartista.www.vartista.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.DocUpload;
import com.vartista.www.vartista.modules.provider.UploadDoc;
import com.vartista.www.vartista.modules.user.FindServicesInList;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.util.List;
import java.util.UUID;
import java.util.zip.CheckedOutputStream;

public class DocUploadAdapter extends RecyclerView.Adapter<DocUploadAdapter.ViewHolder> {
    public List<DocUpload> myDocUploadList;
    public Context context;
    public DocUploadAdapter(Context context, List<DocUpload> myDocUploadList){
        this.myDocUploadList = myDocUploadList;
        this.context=context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.upload_doc_item,parent,false);


        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final DocUpload docUpload=myDocUploadList.get(position);
      holder.tvDocDate.setText(docUpload.getDate());
      holder.tvDocTitle.setText(docUpload.getTitle());

        final int docId= myDocUploadList.get(position).getId();
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, UploadDoc.class);
                intent.putExtra("docId", docId);
                intent.putExtra("title", docUpload.getTitle());
                intent.putExtra("user_id", docUpload.getUserId());

                context.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return myDocUploadList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public TextView tvDocDate,tvDocTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            mView=itemView;

            tvDocTitle=(TextView)mView.findViewById(R.id.tvDocTitle);
            tvDocDate=(TextView)mView.findViewById(R.id.tvDocDate);



        }
    }


}
