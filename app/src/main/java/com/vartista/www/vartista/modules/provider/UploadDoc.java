package com.vartista.www.vartista.modules.provider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.valdesekamdem.library.mdtoast.MDToast;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.modules.general.HomeActivity;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class UploadDoc extends AppCompatActivity {

    private static final String UPLOAD_URL = "http://vartista.com/vartista_app/upload_doc.php";
    private static final int UPLOAD_DOC_CODE = 95;
    private ImageView imageViewDoc;
    private TextView tvDocTitle,imageViewTitleImage;
    private Button btnUploadDoc;
    private Uri filePath;
    private Bitmap bitmap;
    int userId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_doc);
        tvDocTitle=(TextView)findViewById(R.id.tvDocTitle);
        tvDocTitle=(TextView)findViewById(R.id.tvDocTitle);
        imageViewTitleImage=(TextView)findViewById(R.id.imageViewTitleImage);
        btnUploadDoc=(Button)findViewById(R.id.btnUploadDoc);
        imageViewDoc=(ImageView)findViewById(R.id.imageViewDocImage);
        Intent intent=getIntent();

        SharedPreferences ob = getSharedPreferences("Login", Context.MODE_PRIVATE);

        userId = ob.getInt("user_id", 0);        final String title=intent.getStringExtra("title");
        final int docId=intent.getIntExtra("docId",0);

        tvDocTitle.setText(title);
        getSupportActionBar().setTitle(title);
        imageViewTitleImage.setText("Click to add"+title);
        imageViewDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Complete action using"), UPLOAD_DOC_CODE);

            }
        });
        btnUploadDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();

                SimpleDateFormat mdformat = new SimpleDateFormat("yyyy / MM / dd ");
                String respDate = ""+mdformat.format(calendar.getTime());
                uploadMultipart(filePath,title,userId,docId,respDate );

            }
        });




    }

    public void uploadMultipart(Uri filePath, String title, int  userId, int docId, String responseDate) {

        //getting the actual path of the image
        String path = getPath(filePath);

        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            //Creating a multi part request
            new MultipartUploadRequest(getApplicationContext(), uploadId, UPLOAD_URL)
                    .addFileToUpload(path, "image") //Adding file
                    .addParameter("user_id",""+userId)
                    .addParameter("title", title)
                    .addParameter("doc_id",""+docId)
                    .addParameter("response_date",""+responseDate)

                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(5)

                    .startUpload(); //Starting the upload

        } catch (Exception exc) {

            MDToast.makeText(getApplicationContext(), exc.getMessage(), MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();
        }
    }

    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == UPLOAD_DOC_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageViewDoc.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }}
}
