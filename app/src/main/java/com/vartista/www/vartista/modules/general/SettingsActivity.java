package com.vartista.www.vartista.modules.general;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.vartista.www.vartista.R;

public class SettingsActivity extends AppCompatActivity {

    EditText Name,dateofbirth,cnic,contactno,email;
    Button changepassword,update,uploadimage;
    private static final int PICK_IMAGE=100;
    Uri imageUri;
    private ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Name = (EditText)findViewById(R.id.user_name);
        dateofbirth = (EditText)findViewById(R.id.dateofbirth);
        cnic = (EditText)findViewById(R.id.CNIC);
        contactno = (EditText)findViewById(R.id.contactno);
        email = (EditText)findViewById(R.id.emailupdate);
        changepassword = (Button)findViewById(R.id.changepassword);
        update = (Button)findViewById(R.id.updatedata);
        uploadimage = (Button)findViewById(R.id.upload);
        image= findViewById(R.id.profile_image);

        uploadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();

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




}
