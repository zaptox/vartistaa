package com.vartista.www.vartista.modules.provider;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.valdesekamdem.library.mdtoast.MDToast;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.User;
import com.vartista.www.vartista.restcalls.ApiClient;
import com.vartista.www.vartista.restcalls.ApiInterface;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.IOException;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DocumentUploadActivity extends AppCompatActivity
        {
    private static final String UPLOAD_URL = "http://vartista.com/vartista_app/insert_image.php";
    private static final int CNIC_FRONT_IMAGE_REQUEST_CODE = 3;
    private static final int CNIC_BACK_IMAGE_REQUEST_CODE = 7;
    private static final int BANK_DETAILS_IMAGE_REQUEST_CODE = 11;
    private static final int STORAGE_PERMISSION_CODE = 123;
    private ImageView imageViewBankDetails,imageViewCnicFront,imageViewBackCinc;
    private Button btnUpload, btnSetAddress;
    private Bitmap bitmapCnicFront,bitmapCnicBack,bitmapBankDetails;
    private Uri filePathCnicFront,filePathCnicBack,filePathBankDetails;
   private  static String cnic_front_document_title="cnic_front";
   private  static String cnic_back_document_title="cnic_back";
   private  static String bank_details_document_title="bank_details";
   private  boolean cnic_front=false,cnic_back=false,bank_details=false;
            private ProgressDialog progressDialog;
   int user_id;
            public static ApiInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_upload);
        SharedPreferences ob =getSharedPreferences("Login", Context.MODE_PRIVATE);
        requestStoragePermission();
        imageViewBankDetails=(ImageView)findViewById(R.id.imageViewBankDetails);
        imageViewCnicFront=(ImageView)findViewById(R.id.imageViewCnicFront);
        imageViewBackCinc=(ImageView)findViewById(R.id.imageViewBackCinc);
        btnSetAddress= findViewById(R.id.set_address_required);
        user_id=ob.getInt("user_id",0);
        apiInterface= ApiClient.getApiClient().create(ApiInterface.class);

        btnUpload=(Button)findViewById(R.id.btnUpload);

          btnSetAddress.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  startActivity(new Intent(DocumentUploadActivity.this,AddressSetActivity.class));
              }
          });

        imageViewBankDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), BANK_DETAILS_IMAGE_REQUEST_CODE);
            }
        });
//
//
//
        imageViewCnicFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), CNIC_FRONT_IMAGE_REQUEST_CODE);
            }
        });
//
        imageViewBackCinc.setOnClickListener(new View.OnClickListener() {
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
              if(cnic_front==true && cnic_back==true && bank_details==true) {

                 try{ uploadMultipart( filePathBankDetails,bank_details_document_title);
                  uploadMultipart( filePathCnicFront, cnic_front_document_title);
                  uploadMultipart( filePathCnicBack, cnic_back_document_title);}catch (Exception e){
                     Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                 }
              }
              else if(bank_details==false){
                    showCompletedDialog("error in uploading documents","Kindly provide Bank Details ");
              }
              else if(cnic_front==false){
                  showCompletedDialog("error in uploading documents","Kindly provide CNIC front side image");

              }

              else if(cnic_back==false){
                  showCompletedDialog("error in uploading documents","Kindly provide CNIC back side image");

              }


                setUIToWait(true);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);



                Call<User> call= DocumentUploadActivity.apiInterface.UpdateSpStatus(user_id);
                try {
                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {

                            if (response.body().getResponse().equals("ok")) {
                                setUIToWait(false);

                                Toast.makeText(DocumentUploadActivity.this, "Updated Successfully..", Toast.LENGTH_SHORT).show();

                            } else if (response.body().getResponse().equals("exist")) {
                                setUIToWait(false);

                                Toast.makeText(DocumentUploadActivity.this, "Same Data exists....", Toast.LENGTH_SHORT).show();

                            } else if (response.body().getResponse().equals("error")) {
                                setUIToWait(false);

                                Toast.makeText(DocumentUploadActivity.this, "Something went wrong....", Toast.LENGTH_SHORT).show();

                            } else {
                                setUIToWait(false);

                                Toast.makeText(DocumentUploadActivity.this, "Something went wrong....", Toast.LENGTH_SHORT).show();

                            }


//                        user_name.setText("response ");
                            Toast.makeText(DocumentUploadActivity.this, "Request sent to admin", Toast.LENGTH_SHORT).show();

//                            finish();
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            setUIToWait(false);
                            Toast.makeText(DocumentUploadActivity.this, "Update Failed", Toast.LENGTH_SHORT).show();

//                        create.setText(t.getMessage());
                        }
                    });
                }
                catch(Exception e){
                    MDToast mdToast = MDToast.makeText(getApplicationContext(), ""+e.getMessage(), MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
                    mdToast.show();

                }



            }
        });

  }


            private void setUIToWait(boolean wait) {

                if (wait) {
                    progressDialog = ProgressDialog.show(this, null, null, true, true);
//            progressDialog.setContentView(new ProgressBar(this));
                    progressDialog.setContentView(R.layout.loader);

                } else {
                    progressDialog.dismiss();
                }

            }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CNIC_FRONT_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePathCnicFront = data.getData();
            try {
                bitmapCnicFront = MediaStore.Images.Media.getBitmap(getContentResolver(), filePathCnicFront);
                imageViewCnicFront.setImageBitmap(bitmapCnicFront);
                cnic_front=true;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if (requestCode == CNIC_BACK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePathCnicBack = data.getData();
            try {
                bitmapCnicBack = MediaStore.Images.Media.getBitmap(getContentResolver(), filePathCnicBack);
                imageViewBackCinc.setImageBitmap(bitmapCnicBack);
                cnic_back=true;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if (requestCode == BANK_DETAILS_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePathBankDetails = data.getData();
            try {
                bitmapBankDetails = MediaStore.Images.Media.getBitmap(getContentResolver(), filePathBankDetails);
                imageViewBankDetails.setImageBitmap(bitmapBankDetails);
                bank_details=true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


            public void uploadMultipart(Uri filePath,String title ) {

                //getting the actual path of the image
                String path = getPath(filePath);

                //Uploading code
                try {
                    String uploadId = UUID.randomUUID().toString();

                    //Creating a multi part request
                    new MultipartUploadRequest(this, uploadId, UPLOAD_URL)
                            .addFileToUpload(path, "image") //Adding file
                            .addParameter("user_id",""+user_id)
                            .addParameter("title", title)
                            .setNotificationConfig(new UploadNotificationConfig())
                            .setMaxRetries(3)

                            .startUpload(); //Starting the upload

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

            protected void showCompletedDialog(String title,String msg) {
                android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(
                        DocumentUploadActivity.this);

                // set title
                alertDialogBuilder.setTitle(title);
                alertDialogBuilder
                        .setMessage(msg)
                        .setCancelable(true)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();


            }

}
