package com.vartista.www.vartista.modules.general;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
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

import com.squareup.picasso.Picasso;
import com.vartista.www.vartista.R;
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

public class UserProfile extends AppCompatActivity {

    private TextView header_name;
    private EditText name,email,password;
    private Button update;
    private ImageView profileimage;
    private ProgressDialog progressDialog;
    public static ApiInterface apiInterface;
    private static final String UPLOAD_URL = "http://vartista.com/vartista_app/upload_profile.php";
    private boolean select_profile=false;
    private static final int STORAGE_PERMISSION_CODE = 12443;
    private static final int PICK_IMAGE=100;
    private Uri filePath;
    private Bitmap bitmap;
    private ImageView image;
    private Button upload_image_update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        apiInterface= ApiClient.getApiClient().create(ApiInterface.class);
        name=findViewById(R.id.name1);
        email= findViewById(R.id.email1);
        password= findViewById(R.id.password1);
        update= findViewById(R.id.update);
        header_name=findViewById(R.id.header_name);
        profileimage = (ImageView)findViewById(R.id.profile_image);
        upload_image_update=findViewById(R.id.upload);

        Intent intent= getIntent();
        final User user= (User) intent.getSerializableExtra("user");

        name.setText(user.getName());
        email.setText(user.getEmail());
        password.setText(user.getPassword());
        header_name.setText(user.getName());
        Picasso.get().load(user.getImage()).fit().centerCrop()
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile)
                .into(profileimage);

        upload_image_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
    openGallery();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            final String  namechange=name.getText().toString();
                final String emailchange=email.getText().toString();
                final String passchange= password.getText().toString();
                int id1=user.getId();
                setUIToWait(true);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                Call<User> call=UserProfile.apiInterface.updateUser(namechange,emailchange,passchange,id1);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call <User> call, Response<User> response) {

                        if(response.body().getResponse().equals("ok")){
                            uploadMultipart(filePath,emailchange,passchange);

                            Toast.makeText(UserProfile.this,"Updated Successfully..",Toast.LENGTH_SHORT).show();

                        }else if(response.body().getResponse().equals("exist")){
                            setUIToWait(false);
                            Toast.makeText(UserProfile.this,"Same Data exists....",Toast.LENGTH_SHORT).show();

                        }
                        else if(response.body().getResponse().equals("error")){
                            setUIToWait(false);

                            Toast.makeText(UserProfile.this,"Something went wrong....",Toast.LENGTH_SHORT).show();

                        }

                        else{
                            setUIToWait(false);

                            Toast.makeText(UserProfile.this,"Something went wrong....",Toast.LENGTH_SHORT).show();

                        }

                        user.setName(namechange);
                        user.setEmail(emailchange);
                        user.setPassword(passchange);
                        user.setImage(getPath(filePath));
//                        user_name.setText("response ");
                        Intent intent = new Intent(UserProfile.this, HomeActivity.class);
                        intent.putExtra("user", user);
                        startActivity(intent);

                    }

                    @Override
                    public void onFailure(Call <User> call, Throwable t) {
                        setUIToWait(false);
                        Toast.makeText(UserProfile.this,"Update Failed",Toast.LENGTH_SHORT).show();

//                        create.setText(t.getMessage());
                    }
                });







            }
        });

//        name.setEnabled(false);
//        email.setEnabled(false);
//        password.setEnabled(false);

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
                profileimage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }




    public void uploadMultipart(Uri filePath,String email,String password ) {

        //getting the actual path of the image
        String path = getPath(filePath);
        Toast.makeText(this,   "path "+path, Toast.LENGTH_SHORT).show();
        Toast.makeText(this,   "filepath "+filePath, Toast.LENGTH_SHORT).show();

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
                     setUIToWait(false);

        } catch (Exception exc) {

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
            }
        }
    }

}
