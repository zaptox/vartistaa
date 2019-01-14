package com.vartista.www.vartista.modules.provider;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.valdesekamdem.library.mdtoast.MDToast;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.adapters.MyRequestsServicesListAdapter;
import com.vartista.www.vartista.adapters.MyServicesListAdapter;
import com.vartista.www.vartista.beans.CreateRequest;
import com.vartista.www.vartista.beans.User;
import com.vartista.www.vartista.modules.general.HomeActivity;
import com.vartista.www.vartista.modules.general.SiginInActivity;
import com.vartista.www.vartista.restcalls.ApiClient;
import com.vartista.www.vartista.restcalls.ApiInterface;

import org.angmarch.views.NiceSpinner;
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
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.vartista.www.vartista.beans.Service;
import com.vartista.www.vartista.restcalls.ServiceApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class  CreateServiceActivity extends AppCompatActivity {
    public List<Service> myServicesList;
    Button btnCreateSerivce;
    AutoCompleteTextView edTxtServicePrice,edtTxtSerivceTitle;
    EditText edDescription;
    EditText service_location;
    TextView service_category;
    Button btnHome;
    CheckBox home_avail;
    public static ApiInterface apiInterface2;

    //Spinner spinnerService;
    public static ServiceApiInterface apiInterface;
    ArrayList<String> cat;
    ArrayList<Integer> cat_id;
    int category_id;
    //user_id comes from shared preferences
    static int user_id;
    static int  edit_user_id;
    static double latitude;
    static double longitude;
    static String country;


    NiceSpinner niceSpinner;


    User loggedin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_service);
        niceSpinner = niceSpinner = (NiceSpinner) findViewById(R.id.nice_spinner);
       // spinnerService=findViewById(R.id.spinnerService);
        cat=new ArrayList<>();
        cat_id=new ArrayList<>();
        myServicesList = new ArrayList<Service>();
        apiInterface = ApiClient.getApiClient().create(ServiceApiInterface.class);
        service_category=(TextView)findViewById(R.id.service_category);
        apiInterface2= ApiClient.getApiClient().create(ApiInterface.class);

        loggedin= HomeActivity.user;

        service_location = (EditText)findViewById(R.id.service_location);
        btnCreateSerivce = (Button) findViewById(R.id.btnCreateService);
        edtTxtSerivceTitle = (AutoCompleteTextView) findViewById(R.id.edtTxtSerivceTitle);
        edTxtServicePrice = (AutoCompleteTextView) findViewById(R.id.edTxtServicePrice);
        edDescription = (EditText) findViewById(R.id.editTextDescription);
        btnHome = (Button) findViewById(R.id.btnHome);
        home_avail= findViewById(R.id.home_avail);

 //       spinnerService = (Spinner) findViewById(R.id.spinnerService);
          edit_user_id=getIntent().getIntExtra("edit_user_id",0);
/*        MDToast mdToast = MDToast.makeText(getApplicationContext(), getLocationFromAddress("Hyderabad Sindh"), MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
        mdToast.show();*/
        if (edit_user_id==0){
          //  Toast.makeText(getApplicationContext(),"NO ID",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"Create Service Edit"+edit_user_id,Toast.LENGTH_SHORT).show();
            new GetServiceConncetion(CreateServiceActivity.this,edit_user_id).execute();



        }


//
        niceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
       // apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        new Conncetion(CreateServiceActivity.this).execute();

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              user_id=loggedin.getId();
                Intent intent=new Intent(getApplicationContext(),MyServicesListActivity.class);
                intent.putExtra("userId",user_id);
                startActivity(intent);            }
        });

        btnCreateSerivce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //update Work here

                if(category_id==0){
                    category_id=1;
                }

                if (btnCreateSerivce.getText().equals("Edit Service")) {
                   // MDToast mdToast = MDToast.makeText(getApplicationContext(), "Your Service Edit Successfully"+edit_user_id, MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
                    //mdToast.show();
                    String title = edtTxtSerivceTitle.getText().toString();
                    String price = edTxtServicePrice.getText().toString();
                    String description = edDescription.getText().toString();
                    String location = service_location.getText().toString();
                    int home_avail_status=1;
                    if(home_avail.isChecked()){
                        home_avail_status=1;
                    }
                    else{
                        home_avail_status=0;
                    }

                    Date c = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    String formattedDate = df.format(c);
                    String update_at=formattedDate;
                   try {
                       getLocationFromAddress(location);
                   }
                   catch (Exception e){

                   }

                    Call<Service> call = CreateServiceActivity.apiInterface.updateService(title,description, location,latitude,longitude,country,category_id, Double.parseDouble(price),update_at,edit_user_id,home_avail_status);
                    call.enqueue(new Callback<Service>() {
                        @Override
                        public void onResponse(Call<Service> call, Response<Service> response) {

                            if (response.body().getResponse().equals("ok")) {
                               // MDToast mdToast = MDToast.makeText(getApplicationContext(), "Your Service Edit Successfully", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
                                MDToast mdToast = MDToast.makeText(getApplicationContext(), "Your Service Edit Successfully", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
                                mdToast.show();

                                edDescription.setText("");
                                edtTxtSerivceTitle.setText("");
                                edTxtServicePrice.setText("");
                                service_location.setText("");
                                btnCreateSerivce.setText("CREATE SERVICE");



                            }
                            if (response.isSuccessful()) {
                                //for debugging

                            }
                        }

                        @Override
                        public void onFailure(Call<Service> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    });





                } else {

                    String title = edtTxtSerivceTitle.getText().toString();
                    String price = edTxtServicePrice.getText().toString();
                    String description = edDescription.getText().toString();
                    String location = service_location.getText().toString();
                    String add="";
                    int home_avail_status=1;
                    if(home_avail.isChecked()){
                        home_avail_status=1;
                    }
                    else{
                        home_avail_status=0;
                    }

                    try {
                        add = getLocationFromAddress(location);
                    }
                    catch(Exception e){

                    }

                    user_id=loggedin.getId();

                    String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

                    Call<Service> call = CreateServiceActivity.apiInterface.
                            createService(title, user_id, description, location,latitude,longitude,country,
                                    1, Double.parseDouble(price + ""), category_id, date,
                                    home_avail_status);

                    call.enqueue(new Callback<Service>() {
                        @Override
                        public void onResponse(Call<Service> call, Response<Service> response) {
                            if (response.body().equals("ok")) {

                                //for debugging

                            }
                            if (response.isSuccessful()) {
                                MDToast mdToast = MDToast.makeText(getApplicationContext(), "Your Service Created Successfully", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
                                mdToast.show();

                                SharedPreferences ob = getSharedPreferences("Login", Context.MODE_PRIVATE);
                                int sp_id = ob.getInt("user_id", 0);
                                insertreviewnil(-1,sp_id,-1);

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
                    Intent intent = new Intent(getApplicationContext(), MyServicesListActivity.class);
                    intent.putExtra("userId", user_id);
                    startActivity(intent);
                }
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

            final String BASE_URL="http://www.vartista.com/vartista_app/fetch_categories.php";
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

                    JSONArray catogires=jsonResult.getJSONArray("category");
                    for(int i=0;i<catogires.length();i++){

                        JSONObject category=catogires.getJSONObject(i);
                        int category_id=category.getInt("id");
                        String category_name=category.getString("name");
                        cat.add(category_name);
                        cat_id.add(category_id);
                    }

                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,cat);
                //
                    //    spinnerService.setAdapter(adapter);

                    niceSpinner.attachDataSource(cat);



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



    class GetServiceConncetion extends AsyncTask<String,String ,String > {
        private ProgressDialog dialog;
        int service_id;

        public  GetServiceConncetion(CreateServiceActivity activity,int service_id) {
            dialog = new ProgressDialog(activity);
           this.service_id=service_id;
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Retriving data Please Wait..");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {


            String result="";

            final String BASE_URL="http://www.vartista.com/vartista_app/CREATE_SERVICES/get.service.by.id.php?id="+service_id;
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
                    //        Toast.makeText(getApplicationContext(),"Ok services are there",Toast.LENGTH_SHORT).show();
                    JSONArray services=jsonResult.getJSONArray("services");
                    for(int i=0;i<services.length();i++) {

                        JSONObject service = services.getJSONObject(i);
                        int service_id = service.getInt("service_id");
                        String service_title = service.getString("service_title");
                        double price = service.getDouble("price");
                        String location= service.getString("location");
                        int status = service.getInt("status");
                        String created_at = service.getString("created_at");
                        String updated_at = service.getString("updated_at");
                         category_id = service.getInt("category_id");
                        String service_description = service.getString("service_description");
                        String category_name = service.getString("name");
                        int home_avail_status= service.getInt("home_avail_status");
                        int user_id = service.getInt("user_id");


                        if(home_avail_status==1){

                            home_avail.setChecked(true);
                        }
                        else {
                            home_avail.setChecked(false);
                        }
                        edtTxtSerivceTitle.setText(service_title);
                        edTxtServicePrice.setText(""+price);
                        edDescription.setText(service_description);
                        service_location.setText(location);
//                        service_category.setText("Service Category: "+category_name);
                    }

                    btnCreateSerivce.setText("Edit Service");


                }
                else{
                    //   Toast.makeText(getApplicationContext(),"no data",Toast.LENGTH_SHORT).show();

                }
            } catch (JSONException e) {
                e.printStackTrace();
                //   Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }


    public String getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(this);
        List<Address> address;

        String add="";
        try {
            address = coder.getFromLocationName(strAddress, 2);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();
            String country_name= address.get(0).getCountryName();
            String country_code= address.get(0).getCountryCode();
            latitude=(double) (location.getLatitude() );
            longitude=(double) (location.getLongitude() );
            country=country_name+" "+country_code;
             add = "latitude: " + (double) (location.getLatitude() ) + "----" + "longitude: " + (double) (location.getLongitude())+"--"+country+"--"+country_code;


        } catch (IOException e) {
            e.printStackTrace();
        }
        return add;
    }


    public void insertreviewnil(int user_id,int service_p_id,int service_id){
        String servicetittle = "";
        String Remarks = "";
        String time = "";
        String date = "";
        Call<CreateRequest> call2 = CreateServiceActivity.apiInterface2.InsertRatings(0,0.0,user_id,service_p_id,service_id,Remarks,date,time);

        call2.enqueue(new Callback<CreateRequest>() {
            @Override
            public void onResponse(Call<CreateRequest> call, Response<CreateRequest> response) {
                if (response.body().getResponse().equals("ok")) {

//                    MDToast mdToast = MDToast.makeText(context, "Your Ratings are inserted", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
//                    mdToast.show();


                }


            }

            @Override
            public void onFailure(Call<CreateRequest> call, Throwable t) {
                //
                // Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });


    }

    }





