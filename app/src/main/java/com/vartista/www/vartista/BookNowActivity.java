package com.vartista.www.vartista;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.vartista.www.vartista.restcalls.ApiInterface;

public class BookNowActivity extends AppCompatActivity {
    int provider_id, cat_id, user_id, service_id;
    DatePicker dp_datepicker;
    TimePicker timePicker1;
    EditText editTextaddress;
    Button buttonBook;
    String sDate, sTime, address;
    public static ApiInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_now);
        dp_datepicker = (DatePicker) findViewById(R.id.dp_datepicker);
        timePicker1 = (TimePicker) findViewById(R.id.timePicker1);
        editTextaddress = (EditText) findViewById(R.id.address);
        buttonBook = (Button) findViewById(R.id.buttonBook);
//        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
//
//
//        Intent intent = getIntent();
//        provider_id = intent.getIntExtra("provider_id", 0);
//        cat_id = intent.getIntExtra("cat_id", 0);
//        user_id = intent.getIntExtra("user_id", 0);
//        service_id = intent.getIntExtra("service_id", 0);
//        address = editTextaddress.getText().toString();
//        String a = "" + provider_id + "" + cat_id + "" + user_id + "" + service_id;
    //    Toast.makeText(getApplicationContext(), a, Toast.LENGTH_LONG).show();

        buttonBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//
//
//                Call<CreateRequest> call = BookNowActivity.apiInterface.createRequest(
//                       user_id,provider_id,service_id,"0000-00-00","00:00",address,1,1);
//
//                call.enqueue(new Callback<CreateRequest>() {
//                    @Override
//                    public void onResponse(Call<CreateRequest> call, Response<CreateRequest> response) {
//                        if (response.body().equals("ok")) {
//        //                    Toast.makeText(getApplicationContext(), "Registered"+user_id, Toast.LENGTH_SHORT).show();
//
//                        }
//                        if (response.isSuccessful()) {
//                            Toast.makeText(getApplicationContext(), "Registered", Toast.LENGTH_SHORT).show();
//
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<CreateRequest> call, Throwable t) {
//                        Toast.makeText(getApplicationContext(), "registered ", Toast.LENGTH_SHORT).show();
//                    }
//
//                });

                // startActivity(new Intent(getApplicationContext(),HomeActivity.class));

            }
        });

    }
}