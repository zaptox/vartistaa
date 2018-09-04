package com.vartista.www.vartista.modules.provider;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.vartista.www.vartista.R;
import com.vartista.www.vartista.restcalls.ApiClient;
import com.vartista.www.vartista.restcalls.ApiInterface;

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
import java.util.ArrayList;

import com.vartista.www.vartista.beans.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class  CreateServiceActivity extends AppCompatActivity {
    Button btnCreateSerivce;
    AutoCompleteTextView edTxtServicePrice,edtTxtSerivceTitle;
    EditText edDescription;
    Button btnHome;
    Spinner spinnerService;
    public static ApiInterface apiInterface;
    ArrayList<String> cat;
    ArrayList<Integer> cat_id;
    int category_id;
    static int user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_service);
        cat=new ArrayList<>();
        cat_id=new ArrayList<>();
        btnCreateSerivce = (Button) findViewById(R.id.btnCreateService);
        edtTxtSerivceTitle = (AutoCompleteTextView) findViewById(R.id.edtTxtSerivceTitle);
        edTxtServicePrice = (AutoCompleteTextView) findViewById(R.id.edTxtServicePrice);
        edDescription = (EditText) findViewById(R.id.editTextDescription);
        btnHome = (Button) findViewById(R.id.btnHome);
        spinnerService = (Spinner) findViewById(R.id.spinnerService);
        //  Toast.makeText(getApplicationContext(),user_id,Toast.LENGTH_SHORT).show();

        user_id=getIntent().getIntExtra("userId",0);
      //  Toast.makeText(getApplicationContext(),""+user_id,Toast.LENGTH_SHORT).show();
//
        spinnerService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category_id=cat_id.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


// Selection of the spinner

// Application of the Array to the Spinner
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        new Conncetion(CreateServiceActivity.this).execute();

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(),MyServicesListActivity.class);
                intent.putExtra("userId",user_id);
                startActivity(intent);            }
        });

        btnCreateSerivce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = edtTxtSerivceTitle.getText().toString();
                String price = edTxtServicePrice.getText().toString();
                String description = edDescription.getText().toString();
             //   Toast.makeText(CreateServiceActivity.this, ""+user_id, Toast.LENGTH_SHORT).show();
//
                Call<Service> call = CreateServiceActivity.apiInterface.createService(title,user_id, description, 1, Double.parseDouble(price + ""), category_id, "2018-04-05", "2018,06,04");

                call.enqueue(new Callback<Service>() {
                    @Override
                    public void onResponse(Call<Service> call, Response<Service> response) {
                        if (response.body().equals("ok")) {
                            Toast.makeText(getApplicationContext(), "Registered"+user_id, Toast.LENGTH_SHORT).show();

                        }
                        if (response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Registered", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<Service> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                });

                edDescription.setText("");
                edtTxtSerivceTitle.setText("");
                edTxtServicePrice.setText("");
                Intent intent=new Intent(getApplicationContext(),MyServicesListActivity.class);
                intent.putExtra("userId",user_id);
                startActivity(intent);
            }

        });

    }
    class Conncetion extends AsyncTask<String,String ,String > {
        private ProgressDialog dialog;
        String categoriesArray[]=null;
        Context context;


        public  Conncetion(CreateServiceActivity activity) {
            dialog = new ProgressDialog(activity);
            context=activity;
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Retriving data Please Wait..");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {


            String result="";

            final String BASE_URL="http://www.zaptox.com/mehdiTask/fetch_categories.php";
            try {
                HttpClient client=new DefaultHttpClient();
                HttpGet request=new HttpGet();

                request.setURI(new URI(BASE_URL));
                HttpResponse response=client.execute(request);
                BufferedReader reader=new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuffer stringBuffer=new StringBuffer();
                String line="";
                while((line=reader.readLine())!=null){
                    stringBuffer.append(line);
                    break;
                }
                reader.close();
                result=stringBuffer.toString();


            } catch (URISyntaxException e) {
                e.printStackTrace();
                return new String("There is exception"+e.getMessage());
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
                JSONObject jsonResult=new JSONObject(result);
                int success=jsonResult.getInt("success");



                if(success==1){
                    //  Toast.makeText(getApplicationContext(),"Ok services are there",Toast.LENGTH_SHORT).show();
                    JSONArray catogires=jsonResult.getJSONArray("category");
                    for(int i=0;i<catogires.length();i++){

                        JSONObject category=catogires.getJSONObject(i);
                        int category_id=category.getInt("id");
                        String category_name=category.getString("name");
                        cat.add(category_name);
                        cat_id.add(category_id);
                    }

                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,cat);
                    spinnerService.setAdapter(adapter);



                }

                else{
//                        Toast.makeText(getApplicationContext(),"no data",Toast.LENGTH_SHORT).show();

                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }




}
