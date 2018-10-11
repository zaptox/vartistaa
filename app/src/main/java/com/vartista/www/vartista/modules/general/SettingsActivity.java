package com.vartista.www.vartista.modules.general;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.User;

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

public class SettingsActivity extends AppCompatActivity {

    EditText Name, DateOfBirth, CNIC, ContactNo,Email;
    Button changepassword,update,uploadimage;
    private static final int PICK_IMAGE=100;
    Uri imageUri;
    private ImageView image;
    User user;
    int user_id = HomeActivity.user_id;
    Dialog changepassworddialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        int userid = HomeActivity.user_id;
        Name = (EditText)findViewById(R.id.user_name);
        DateOfBirth = (EditText)findViewById(R.id.dateofbirth);
        CNIC = (EditText)findViewById(R.id.CNIC);
        ContactNo = (EditText)findViewById(R.id.contactno);
        Email = (EditText)findViewById(R.id.emailupdate);
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
        new SettingsActivity.Conncetion(SettingsActivity.this,userid).execute();
        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changepassworddialog = new Dialog(SettingsActivity.this);
                changepassworddialog.setContentView(R.layout.changepassword_dialoguebox);
                Button  savepassword = (Button)changepassworddialog.findViewById(R.id.savepassword);
                Button  cancel = (Button)changepassworddialog.findViewById(R.id.cancelbutton);
                final EditText oldpassword = (EditText)changepassworddialog.findViewById(R.id.oldPasswordedittext);
                EditText newpassword = (EditText)changepassworddialog.findViewById(R.id.newpasswordedittext);
                EditText confirmnewpassword = (EditText)changepassworddialog.findViewById(R.id.confirmpasswordedittext);
                changepassworddialog.show();

                savepassword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changepassworddialog.cancel();
                    }
                });



            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = Name.getText().toString();
                String contact = ContactNo.getText().toString();
                String email = Email.getText().toString();

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
                Toast.makeText(SettingsActivity.this, "ok", Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(getApplicationContext(),"no data",Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }

    }















}
