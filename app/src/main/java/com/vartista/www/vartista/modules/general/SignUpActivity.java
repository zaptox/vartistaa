package com.vartista.www.vartista.modules.general;

import android.Manifest;
import android.app.Activity;
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
import android.widget.RadioButton;
import android.widget.Toast;

import com.valdesekamdem.library.mdtoast.MDToast;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.Doument_Upload_Nil;
import com.vartista.www.vartista.modules.provider.DocumentUploadActivity;
import com.vartista.www.vartista.restcalls.ApiClient;
import com.vartista.www.vartista.restcalls.ApiInterface;

import com.vartista.www.vartista.beans.User;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

import id.zelory.compressor.Compressor;
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
    private RadioButton male_radio,female_radio;
    private ProgressDialog progressDialog;
    private static final String UPLOAD_URL = "http://vartista.com/vartista_app/upload_profile.php";
    private boolean select_profile=false;
    private static final int STORAGE_PERMISSION_CODE = 12443;
    private int column_index;
    private String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        apiInterface= ApiClient.getApiClient().create(ApiInterface.class);
//        sp_list= new ArrayList<>();
        requestStoragePermission();

        create= findViewById(R.id.create);
        user_name= findViewById(R.id.user_name);
        user_email= findViewById(R.id.user_email);
        user_contact= findViewById(R.id.user_number);
        user_password= findViewById(R.id.user_password);
        upload= findViewById(R.id.upload);
        image= findViewById(R.id.profile_image);
        male_radio=findViewById(R.id.male);
        female_radio=findViewById(R.id.female);

        Intent  i = getIntent();

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
                String gender="";
                if(male_radio.isChecked()){

                    gender="male";
                }
                else if(female_radio.isChecked()){

                    gender="female";

                }
                setUIToWait(true);


                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                Call<User> call=SignUpActivity.apiInterface.performRegistration(user_name1,user_email1,user_password1,"","1",user_contact1,null,null,gender);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call <User> call, Response<User> response) {

                        if(response.body().getResponse().equals("ok")){
                            setUIToWait(false);
                            uploadMultipart(filePath,user_email.getText().toString(),user_password.getText().toString());

                            insertdocumentnil(user_email.getText().toString(),user_password.getText().toString(),user_contact.getText().toString());
                           startActivity(new Intent(getApplicationContext(),SiginInActivity.class));
//                            MDToast.makeText(SignUpActivity.this,"Account created sucessfully..",MDToast.LENGTH_SHORT,MDToast.TYPE_SUCCESS).show();
                            finish();
                        }
                        else if(response.body().getResponse().equals("exist")){
                            setUIToWait(false);
                             showCompletedDialog("Error","User Already Exist!");
                        }
                        else if(response.body().getResponse().equals("error")){
                            setUIToWait(false);

                           MDToast.makeText(SignUpActivity.this,"Something went wrong....",MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();

                        }

                        else{
                            setUIToWait(false);

                            MDToast.makeText(SignUpActivity.this,"Something went wrong....",MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();

                        }

                    }

                    @Override
                    public void onFailure(Call <User> call, Throwable t) {
                        setUIToWait(false);
                        MDToast.makeText(SignUpActivity.this,"Signup Failed",MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();

                    create.setText(t.getMessage());
                    }
                });

                }


        });


    }

    public void openGallery(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, PICK_IMAGE
        );
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_IMAGE);

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
//        if (requestCode == 1)
//            if (resultCode == Activity.RESULT_OK) {
//                Uri selectedImage = data.getData();
//
//                String filePath = getPath(selectedImage);
//                String file_extn = filePath.substring(filePath.lastIndexOf(".") + 1);
//
//                try {
//                    select_profile=true;
//                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
//                    image.setImageBitmap(bitmap);
//
//                    if (file_extn.equals("img") || file_extn.equals("jpg") || file_extn.equals("jpeg") || file_extn.equals("gif") || file_extn.equals("png")) {
//                        //FINE
//                    } else {
//                        //NOT IN REQUIRED FORMAT
//                    }
//                } catch (FileNotFoundException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }

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


//        String path = "http://vartista.com/vartista_app/images/placeholder/placeholder-man.png";
        String path = "";

//        //for compression
//        File image_file= new File (filePath.getPath());
//        File compressedImageFile = null;
//        try {
//            compressedImageFile = new Compressor(this).compressToFile(image_file);
//        } catch (IOException e) {
////            e.printStackTrace();
//         MDToast.makeText(SignUpActivity.this,e.getMessage(),MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();
//
//
//        }
//        filePath= Uri.fromFile(compressedImageFile);
//
//


        try{
            path = getPath(filePath);
        }
        catch (Exception e){
            Toast.makeText(this, "You can upload your image later!", Toast.LENGTH_SHORT).show();
        }


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
            MDToast.makeText(this, exc.getMessage(), MDToast.LENGTH_SHORT).show();
                                }
    }

    public String getPath(Uri uri) {
//        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
//        cursor.moveToFirst();
//        String document_id = cursor.getString(0);
//        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
//        cursor.close();
//        cursor = getContentResolver().query(
//                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
//        cursor.moveToFirst();
//        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
//        cursor.close();
//
//        return path;
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        column_index = cursor
                .getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        imagePath = cursor.getString(column_index);

        return cursor.getString(column_index);
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
                //Displaying a MDToast
                MDToast.makeText(this, "Permission granted now you can read the storage", MDToast.LENGTH_LONG).show();
            } else {
            }
        }
    }
    public void insertdocumentnil(String UserName ,String Password,String ContactNo){

        Call<Doument_Upload_Nil> call = SignUpActivity.apiInterface.document_upload_nil(UserName,Password,ContactNo);

        call.enqueue(new Callback<Doument_Upload_Nil>() {
            @Override
            public void onResponse(Call<Doument_Upload_Nil> call, Response<Doument_Upload_Nil> response) {
                if (response.isSuccessful()) {

                }
            }

            @Override
            public void onFailure(Call<Doument_Upload_Nil> call, Throwable t) {

            }

        });


    }

}
