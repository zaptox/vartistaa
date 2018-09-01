package com.vartista.www.vartista;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vartista.www.vartista.restcalls.ApiClient;
import com.vartista.www.vartista.restcalls.ApiInterface;

import beans.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfile extends AppCompatActivity {

    private TextView header_name;
    private EditText name,email,password;
    private Button update;
    private ProgressDialog progressDialog;
    public static ApiInterface apiInterface;


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



        Intent intent= getIntent();
        final User user= (User) intent.getSerializableExtra("user");

        name.setText(user.getName());
        email.setText(user.getEmail());
        password.setText(user.getPassword());
        header_name.setText(user.getName());

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String namechange=name.getText().toString();
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
                            setUIToWait(false);

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

}
