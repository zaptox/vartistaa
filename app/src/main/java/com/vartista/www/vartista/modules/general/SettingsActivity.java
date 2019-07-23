package com.vartista.www.vartista.modules.general;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.valdesekamdem.library.mdtoast.MDToast;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.User;
import com.vartista.www.vartista.restcalls.ApiClient;
import com.vartista.www.vartista.restcalls.ApiInterface;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsActivity extends AppCompatActivity {

    EditText Name, DateOfBirth, CNIC, ContactNo,Email,OldPassword,NewPassword,ConfirmNewPassword;
    Button changepassword,update,uploadimage;
    private static final int PICK_IMAGE=100;
//    Uri imageUri;
    private ImageView image;
    private static final String UPLOAD_URL = "http://vartista.com/vartista_app/upload_profile.php";
    User user;
    Uri filePath;
            String imagePath;
            Bitmap bitmap;
    private ProgressDialog progressDialog;
    int user_id = HomeActivity.user_id;
    Dialog changepassworddialog;
    private int column_index;
    public static ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        int userid = HomeActivity.user_id;
        apiInterface= ApiClient.getApiClient().create(ApiInterface.class);
        Name = (EditText)findViewById(R.id.user_name);
        DateOfBirth = (EditText)findViewById(R.id.dateofbirth);
        CNIC = (EditText)findViewById(R.id.CNIC);
        ContactNo = (EditText)findViewById(R.id.contactno);
        Email = (EditText)findViewById(R.id.emailupdate);
//        changepassword = (Button)findViewById(R.id.changepassword);
        update = (Button)findViewById(R.id.updatedata);
        uploadimage = (Button)findViewById(R.id.upload);
        image= findViewById(R.id.profile_image);

        SharedPreferences ob = getSharedPreferences("Login", Context.MODE_PRIVATE);
        String images = ob.getString("image","");


        Picasso.get().load(images).fit().centerCrop()
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile)
                .into(image);

        uploadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();

            }
        });
        new SettingsActivity.Conncetion(SettingsActivity.this,userid).execute();
//        changepassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                changepassworddialog = new Dialog(SettingsActivity.this);
//                changepassworddialog.setContentView(R.layout.changepassword_dialoguebox);
//                Button  savepassword = (Button)changepassworddialog.findViewById(R.id.savepassword);
//                Button  cancel = (Button)changepassworddialog.findViewById(R.id.cancelbutton);
//                OldPassword = (EditText)changepassworddialog.findViewById(R.id.oldPasswordedittext);
//                NewPassword = (EditText)changepassworddialog.findViewById(R.id.newpasswordedittext);
//                ConfirmNewPassword = (EditText)changepassworddialog.findViewById(R.id.confirmpasswordedittext);
//                changepassworddialog.show();
//
//                savepassword.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                      String oldpassword = OldPassword.getText().toString();
//                      String newpassword = NewPassword.getText().toString();
//                      String confirmnewpassword = ConfirmNewPassword.getText().toString();
//                      if(newpassword.equals(confirmnewpassword)){
//                          updatedata(user.getId(),user.getName(),user.getEmail(),user.getContact(),confirmnewpassword);
//                          changepassworddialog.cancel();
//                      }
//
//                    }
//                });
//
//                cancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        changepassworddialog.cancel();
//                    }
//                });
//
//
//
//            }
//        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = Name.getText().toString();
                String contact = ContactNo.getText().toString();
                String email = Email.getText().toString();
                int id = user.getId();
                    updatedata(id,name,email,contact,user.getPassword());
            }
        });



    }
    public void openGallery(){
        Intent i= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);

        startActivityForResult(i,PICK_IMAGE);

    }
    public void uploadMultipart(Uri filePath, String email, int id ) {

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
                    .addParameter("id",id+"")
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK && requestCode==PICK_IMAGE){
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    class Conncetion extends AsyncTask<String,String ,String > {
        private int user_id;
        private ProgressDialog dialog;

        public Conncetion(Context activity, int user_id) {
            dialog = new ProgressDialog(activity);
            this.user_id = user_id;
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Retriving data Please Wait..");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {


            String result = "";

            final String BASE_URL = "http://www.vartista.com/vartista_app/User_Settings_data.php?userid="+user_id;
            try {
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(BASE_URL));
                HttpResponse response = client.execute(request);
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuffer stringBuffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    stringBuffer.append(line);
                    break;
                }
                reader.close();
                result = stringBuffer.toString();
            } catch (URISyntaxException e) {
                e.printStackTrace();
                return new String("Exception is " + e.getMessage());
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            try {

                JSONObject jsonResult = new JSONObject(result);

                int success = jsonResult.getInt("success");

                if (success == 1) {
                    JSONArray services = jsonResult.getJSONArray("services");
                    for (int j = 0; j < services.length(); j++) {
                        JSONObject ser1 = services.getJSONObject(j);
                        int id = ser1.getInt("id");
                        String username = ser1.getString("name");
                        String email = ser1.getString("email");
                        String password = ser1.getString("password");
                        String contact = ser1.getString("contact");
                        String createdat = ser1.getString("created_at");
                        String updatedat = ser1.getString("updated_at");
                        user = new User(id, username, email, password, "", "1", contact,createdat,updatedat);

                    }
                    Name.setText(user.getName());
                    Email.setText(user.getEmail());
                    ContactNo.setText(user.getContact());
                } else {
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }



    private void setUIToWait(boolean wait) {

        if (wait) {
            progressDialog = ProgressDialog.show(this, null, null, true, true);
            progressDialog.setContentView(R.layout.loader);

        } else {
            progressDialog.dismiss();
        }

    }

public void updatedata(final int id, String name, final String email, String contact, final String password){
    setUIToWait(true);
    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//    Call<User> call=SettingsActivity.apiInterface.updateUserSettings(id,name,email,password,user.getImage(),1,contact,0,0);
    Call<User> call=SettingsActivity.apiInterface.updateUser(name, email, password, id);
    call.enqueue(new Callback<User>() {
        @Override
        public void onResponse(Call <User> call, Response<User> response) {

            if(response.body().getResponse().equals("ok")){
                setUIToWait(false);
                uploadMultipart(filePath, email, id);

                MDToast.makeText(SettingsActivity.this,"Updated Successfully..",MDToast.LENGTH_SHORT,MDToast.TYPE_SUCCESS).show();

            }else if(response.body().getResponse().equals("exist")){
                setUIToWait(false);

                MDToast.makeText(SettingsActivity.this,"Same Data exists....",MDToast.LENGTH_SHORT,MDToast.TYPE_WARNING).show();

            }
            else if(response.body().getResponse().equals("error")){
                setUIToWait(false);

                MDToast.makeText(SettingsActivity.this,"Something went wrong....",MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();

            }
            else{
                setUIToWait(false);

                MDToast.makeText(SettingsActivity.this,"Something went wrong....",MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();
            }

        }

        @Override
        public void onFailure(Call <User> call, Throwable t) {
            setUIToWait(false);

        }
    });
}










}
