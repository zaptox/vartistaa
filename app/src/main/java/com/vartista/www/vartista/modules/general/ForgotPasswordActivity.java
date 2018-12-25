package com.vartista.www.vartista.modules.general;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.valdesekamdem.library.mdtoast.MDToast;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.CreateRequest;
import com.vartista.www.vartista.beans.User;
import com.vartista.www.vartista.beans.forgotpassword;
import com.vartista.www.vartista.modules.user.AssignRatings;
import com.vartista.www.vartista.restcalls.ApiClient;
import com.vartista.www.vartista.restcalls.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText emaiverifyedittext,verificationcode,newpassword,confirmnewpassword;
    Button submitemail,submitcode,savepassword;
    TextView countdown;
    LinearLayout layoutforcode,layoutforemail,layoutforpasswords;
    public static ApiInterface apiInterface;
    CountDownTimer cTimer = null;
//    User userLoggedIn = null;
//    String userinputemail2;
//    boolean check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        emaiverifyedittext = (EditText)findViewById(R.id.emailverifyedittext);
        verificationcode = (EditText)findViewById(R.id.verification_code);
        newpassword = (EditText)findViewById(R.id.NewPassword);
        confirmnewpassword = (EditText)findViewById(R.id.ConfirmNewPassword);
        submitemail = (Button)findViewById(R.id.submitemail);
        submitcode = (Button)findViewById(R.id.submitcode);
        savepassword = (Button)findViewById(R.id.SavePassword);
        layoutforcode = (LinearLayout)findViewById(R.id.layoutforcode);
        layoutforemail = (LinearLayout)findViewById(R.id.layoutforemail);
        layoutforpasswords = (LinearLayout)findViewById(R.id.layoutfornewpassword);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        layoutforcode.setVisibility(View.GONE);
        layoutforpasswords.setVisibility(View.GONE);
        countdown = (TextView)findViewById(R.id.time);
        startTimer();
        submitemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emaiverifyedittext.getText().toString();
                Call<forgotpassword> call = ForgotPasswordActivity.apiInterface.User_Verification_Email(email);
                call.enqueue(new Callback<forgotpassword>() {
                    @Override
                    public void onResponse(Call<forgotpassword> call, Response<forgotpassword> response) {

                            MDToast mdToast = MDToast.makeText(getApplicationContext(), "Request has been Send succesfully.", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
                            mdToast.show();



                    }

                    @Override
                    public void onFailure(Call<forgotpassword> call, Throwable t) {
                        //
                        // Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                });
                layoutforemail.setVisibility(View.GONE);
                layoutforcode.setVisibility(View.VISIBLE);
            }


        });

        submitcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutforcode.setVisibility(View.GONE);
                layoutforpasswords.setVisibility(View.VISIBLE);
            }
        });

    }




    //start timer function
    void startTimer() {
        cTimer = new CountDownTimer(120000, 1000) {
            public void onTick(long millisUntilFinished) {
                countdown.setText(""+millisUntilFinished/1000);
            }
            public void onFinish() {
            }
        };
        cTimer.start();
    }


    //cancel timer
    void cancelTimer() {
        if(cTimer!=null)
            cTimer.cancel();
    }





//public boolean checkemail(String userinputemail){
//
//    userinputemail2 = userinputemail;
//    check = false;
//
//    String password = "";
//
//    Call<User> call = SiginInActivity.apiInterface.performUserLogin(userinputemail, password);
//    call.enqueue(new Callback<User>() {
//
//        public void onResponse(Call<User> call, Response<User> response) {
//
////
//            if (response.isSuccessful()) {
////                   // Toast.makeText(SiginInActivity.this, "In response", Toast.LENGTH_SHORT).show();
//
//            }
////
//
//            if (response.body().getResponse().equals("ok")) {
//                int id = response.body().getId();
//
//                String name = response.body().getName();
//
//                String email = response.body().getEmail();
//
//                String password = response.body().getPassword();
//
//                String image = response.body().getImage();
//
//                String status = response.body().getStatus();
//
//                String contact = response.body().getContact();
//
//                String created_at = response.body().getCreatedAt();
//
//                String updated_at = response.body().getUpdatedAt();
//
//                String gender= response.body().getGender();
//
//                String sp_status= response.body().getSp_status();
//
//
//                userLoggedIn = new User(id, name, email, password, image, status, contact, created_at, updated_at,gender,sp_status);
//                if(userLoggedIn.getEmail().equals(userinputemail2)){
//                    check=true;
//                }
//
//            }
//            else if (response.body().getResponse().equals("failed")) {
//                  Toast.makeText(ForgotPasswordActivity.this, "Login Failed.. Please try again", Toast.LENGTH_SHORT).show();
////                    Toast.makeText(SiginInActivity.this, "", Toast.LENGTH_SHORT).show();
//
//}
////
//            else {
//                //  Toast.makeText(SiginInActivity.this, "Response: " + response.body().getResponse(), Toast.LENGTH_SHORT).show();
//            }
////                Toast.makeText(SiginInActivity.this, "In response's last line", Toast.LENGTH_SHORT).show();
// }
//
//        @Override
//        public void onFailure(Call<User> call, Throwable t) {
//            Toast.makeText(ForgotPasswordActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
//        }
//    });
//    return check;
//}

}
