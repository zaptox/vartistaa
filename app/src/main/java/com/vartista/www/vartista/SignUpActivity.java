package com.vartista.www.vartista;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.vartista.www.vartista.restcalls.ApiClient;
import com.vartista.www.vartista.restcalls.ApiInterface;

import beans.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private Button create,upload;
    private EditText user_name,user_email,user_contact,user_password;
    private ImageView image;
    public static ApiInterface apiInterface;
    private static final int PICK_IMAGE=100;
    Uri imageUri;
    private ProgressDialog progressDialog;

//    ArrayList<GetServiceProviders> sp_list;




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
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


                Call<User> call=SignUpActivity.apiInterface.performRegistration(user_name1,user_email1,user_password1,null,"1",user_contact1,null,null);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call <User> call, Response<User> response) {

                        if(response.body().getResponse().equals("ok")){
                            setUIToWait(false);

                       //     Toast.makeText(SignUpActivity.this,"Registred Successfully..",Toast.LENGTH_SHORT).show();

                        }else if(response.body().getResponse().equals("exist")){
                            setUIToWait(false);

                       //     Toast.makeText(SignUpActivity.this,"User Already exists....",Toast.LENGTH_SHORT).show();

                        }
                        else if(response.body().getResponse().equals("error")){
                            setUIToWait(false);

                         //   Toast.makeText(SignUpActivity.this,"Something went wrong....",Toast.LENGTH_SHORT).show();

                        }

                        else{
                            setUIToWait(false);

                         //   Toast.makeText(SignUpActivity.this,"Something went wrong....",Toast.LENGTH_SHORT).show();

                        }

                        user_name.setText("response ");
                    }

                    @Override
                    public void onFailure(Call <User> call, Throwable t) {
                        setUIToWait(false);
                    //    Toast.makeText(SignUpActivity.this,"Signup Failed",Toast.LENGTH_SHORT).show();

                    create.setText(t.getMessage());
                    }
                });


            }
        });


    }

    public void openGallery(){
        Intent i= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(i,PICK_IMAGE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK && requestCode==PICK_IMAGE){
            imageUri=data.getData();
            image.setImageURI(imageUri);

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


}
