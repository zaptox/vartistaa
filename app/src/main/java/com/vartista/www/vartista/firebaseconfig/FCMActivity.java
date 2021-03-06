package com.vartista.www.vartista.firebaseconfig;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import com.android.volley.AuthFailureError;
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;

import com.google.firebase.iid.FirebaseInstanceId;
import com.vartista.www.vartista.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FCMActivity extends AppCompatActivity {
    private Button register,buttonGetToken;
    private EditText editTextEmail;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fcm);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        buttonGetToken = (Button) findViewById(R.id.buttonGetToken);

        register = (Button) findViewById(R.id.register);
        buttonGetToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            editTextEmail.setText(FirebaseInstanceId.getInstance().getToken());
            Log.d("Token",editTextEmail.getText().toString());
            }
        });
    }

//    public void sendToken(View view) {
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Registering Device...");
//        progressDialog.show();
//
//        final String token = SharedPreference.getInstance(this).getDeviceToken();
//        final String email = editTextEmail.getText().toString();
//        if (token == null) {
//            progressDialog.dismiss();
//            Toast.makeText(this, "Token not generated", Toast.LENGTH_LONG).show();
//            return;
//        }
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_REGISTER_DEVICE,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        progressDialog.dismiss();
//                        try {
//                            JSONObject obj = new JSONObject(response);
//                            Toast.makeText(FCMActivity.this, obj.getString("message"), Toast.LENGTH_LONG).show();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        progressDialog.dismiss();
//                        Toast.makeText(FCMActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
//                    }
//                }) {
//
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("email", email);
//                params.put("token", token);
//                return params;
//            }
//        };
//        FcmVolley.getInstance(this).addToRequestQueue(stringRequest);
//}
}
