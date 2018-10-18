package com.vartista.www.vartista.modules.general;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.vartista.www.vartista.R;
import com.vartista.www.vartista.modules.provider.DocumentUploadActivity;
import com.vartista.www.vartista.restcalls.ApiClient;
import com.vartista.www.vartista.restcalls.ApiInterface;

import com.vartista.www.vartista.beans.User;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.IOException;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private Button create,upload;
    private EditText user_name,user_email,user_contact,user_password;
    private ImageView image;
    public static ApiInterface apiInterface;
    private static final int PICK_IMAGE=100;
    private Uri filePath;
    private Bitmap bitmap;
    private ProgressDialog progressDialog;
    private static final String UPLOAD_URL = "http://vartista.com/vartista_app/upload_profile.php";
    private boolean select_profile=false;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        apiInterface= ApiClient.getApiClient().create(ApiInterface.class);
//        sp_list= new ArrayList<>();

        create= findViewById(R.id.create);
        user_name= findViewById(R.id.user_name);
        user_email= findViewById(R.id.user_email);
        user_contact= findViewById(R.id.user_number);
        user_password= findViewById(R.id.user_password);
        upload= findViewById(R.id.upload);
        image= findViewById(R.id.profile_image);

        Intent  i = getIntent();
//        sp_list=i.getParcelableArrayListExtra("service_providers");
//        Toast.makeText(this, ""+sp_list.get(0).getService_title(), Toast.LENGTH_SHORT).show();

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();

            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user_name1=user_name.getText().toString();
                String user_email1= user_email.getText().toString();
                String user_contact1= user_contact.getText().toString();
                String user_password1= user_password.getText().toString();

                setUIToWait(true);

///*
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                Call<User> call=SignUpActivity.apiInterface.performRegistration(user_name1,user_email1,user_password1,null,"1",user_contact1,null,null);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call <User> call, Response<User> response) {

                        if(response.body().getResponse().equals("ok")){
                            setUIToWait(false);
                          if(select_profile){
                           uploadMultipart(filePath,user_email.getText().toString(),user_password.getText().toString());
                           startActivity(new Intent(getApplicationContext(),SiginInActivity.class));
                              finish();}
                              else{
                              showCompletedDialog("Error","select the profile image!");
                          }

                        }else if(response.body().getResponse().equals("exist")){
                            setUIToWait(false);

                             showCompletedDialog("Error","User Already Exist!");
                        }
                        else if(response.body().getResponse().equals("error")){
                            setUIToWait(false);

                           Toast.makeText(SignUpActivity.this,"Something went wrong....",Toast.LENGTH_SHORT).show();

                        }

                        else{
                            setUIToWait(false);

                            Toast.makeText(SignUpActivity.this,"Something went wrong....",Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onFailure(Call <User> call, Throwable t) {
                        setUIToWait(false);
                        Toast.makeText(SignUpActivity.this,"Signup Failed",Toast.LENGTH_SHORT).show();

                    create.setText(t.getMessage());
                    }
                });


            }
        });


    }

    public void openGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_IMAGE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
                filePath = data.getData();
                try {
                    select_profile=true;
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    image.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

        super.onActivityResult(requestCode, resultCode, data);
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


    public void uploadMultipart(Uri filePath,String email,String password ) {

        //getting the actual path of the image
        String path = getPath(filePath);

        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            //Creating a multi part request
            new MultipartUploadRequest(this, uploadId, UPLOAD_URL)
                    .addFileToUpload(path, "image") //Adding file
                    .addParameter("email", email)
                    .addParameter("password", password)
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

    protected void showCompletedDialog(String title,String msg) {
        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(
                SignUpActivity.this);

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
