package com.vartista.www.vartista.modules.provider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vartista.www.vartista.R;

//import net.gotev.uploadservice.MultipartUploadRequest;
//import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.IOException;
import java.util.UUID;

public class DocumentUploadActivity extends AppCompatActivity
        {
    private static final String UPLOAD_URL = "http://vartista.com/vartista_app/insert_image.php";
    private static final int CNIC_FRONT_IMAGE_REQUEST_CODE = 3;
    private static final int CNIC_BACK_IMAGE_REQUEST_CODE = 7;
    private static final int BANK_DETAILS_IMAGE_REQUEST_CODE = 11;
    private static final int STORAGE_PERMISSION_CODE = 123;
    private ImageView imageViewBankDetails,imageViewCnicFront,imageViewBackCinc;
    private Button btnUpload;
    private Bitmap bitmapCnicFront,bitmapCnicBack,bitmapBankDetails;
    private Uri filePathCnicFront,filePathCnicBack,filePathBankDetails;
   private  static String cnic_front_document_title="CNIC Front";
   private  static String cnic_back_document_title="CNIC Back";
   private  static String bank_details_document_title="Bank Details";

            int user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_upload);

        SharedPreferences ob =getSharedPreferences("Login", Context.MODE_PRIVATE);
        requestStoragePermission();
        imageViewBankDetails=(ImageView)findViewById(R.id.imageViewBankDetails);
        imageViewCnicFront=(ImageView)findViewById(R.id.imageViewCnicFront);
        imageViewBackCinc=(ImageView)findViewById(R.id.imageViewBackCinc);

        user_id=ob.getInt("user_id",0);
         btnUpload=(Button)findViewById(R.id.btnUpload);

        imageViewBankDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), BANK_DETAILS_IMAGE_REQUEST_CODE);
            }
        });



        imageViewCnicFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), CNIC_FRONT_IMAGE_REQUEST_CODE);
            }
        });

        imageViewBankDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), CNIC_BACK_IMAGE_REQUEST_CODE);
            }
        });


        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadMultipart(bank_details_document_title,filePathBankDetails);
//                uploadMultipart(cnic_front_document_title,filePathCnicFront);
//                uploadMultipart(cnic_back_document_title,filePathCnicBack);


            }
        });

  }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CNIC_FRONT_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePathCnicFront = data.getData();
            try {
                bitmapCnicFront = MediaStore.Images.Media.getBitmap(getContentResolver(), filePathCnicFront);
                cnic_front_document_title="Path: ". concat(getPath(filePathCnicFront));
                imageViewCnicFront.setImageBitmap(bitmapCnicFront);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if (requestCode == CNIC_BACK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePathCnicBack = data.getData();
            try {
                bitmapCnicFront = MediaStore.Images.Media.getBitmap(getContentResolver(), filePathCnicBack);
                cnic_back_document_title="Path: ". concat(getPath(filePathCnicBack));
                imageViewBackCinc.setImageBitmap(bitmapCnicBack);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if (requestCode == BANK_DETAILS_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePathBankDetails = data.getData();
            try {
                bitmapCnicFront = MediaStore.Images.Media.getBitmap(getContentResolver(), filePathBankDetails);
                bank_details_document_title="Path: ". concat(getPath(filePathBankDetails));
                imageViewBankDetails.setImageBitmap(bitmapBankDetails);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

//    public void uploadMultipart() {
//        String caption = etCaption.getText().toString().trim();
//
//        //getting the actual path of the image
//        String path = getPath(filePath);
//
//        //Uploading code
//        try {
//            String uploadId = UUID.randomUUID().toString();
//
//            //Creating a multi part request
//            new MultipartUploadRequest(this, uploadId, UPLOAD_URL)
//                    .addFileToUpload(path, "image") //Adding file
//                    .addParameter("document_title", caption) //Adding text parameter to the request
//                    .addParameter("user_id",""+user_id)
//                    .setNotificationConfig(new UploadNotificationConfig())
//                    .setMaxRetries(2)
//                    .startUpload(); //Starting the upload
//        } catch (Exception exc) {
//            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//    }


            public void uploadMultipart(String document_title,Uri filePath ) {

                //getting the actual path of the image
                String path = getPath(filePath);

                //Uploading code
                try {
                    String uploadId = UUID.randomUUID().toString();

                    //Creating a multi part request
//                    new MultipartUploadRequest(this, uploadId, UPLOAD_URL)
//                            .addFileToUpload(path, "image") //Adding file
//                            .addParameter("document_title", document_title) //Adding text parameter to the request
//                            .addParameter("user_id",""+user_id)
//                            .setNotificationConfig(new UploadNotificationConfig())
//                            .setMaxRetries(2)
//                            .startUpload(); //Starting the upload
                } catch (Exception exc) {
                    Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

}
