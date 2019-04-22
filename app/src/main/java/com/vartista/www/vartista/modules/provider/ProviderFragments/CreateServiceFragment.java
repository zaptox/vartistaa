package com.vartista.www.vartista.modules.provider.ProviderFragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.valdesekamdem.library.mdtoast.MDToast;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.CreateRequest;
import com.vartista.www.vartista.beans.Service;
import com.vartista.www.vartista.beans.User;
import com.vartista.www.vartista.modules.general.HomeActivity;
import com.vartista.www.vartista.modules.provider.CreateServiceActivity;
import com.vartista.www.vartista.modules.provider.MyServicesListActivity;
import com.vartista.www.vartista.restcalls.ApiClient;
import com.vartista.www.vartista.restcalls.ApiInterface;
import com.vartista.www.vartista.restcalls.ServiceApiInterface;
import com.vartista.www.vartista.util.CONST;

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
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreateServiceFragment extends Fragment {

    //Activity variables
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

    private ProgressDialog progressDialog;


    NiceSpinner niceSpinner;


    User loggedin;
    Boolean for_edit=false;

//    private OnFragmentInteractionListener mListener;

    public CreateServiceFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public CreateServiceFragment(int user_id) {
        // Required empty public constructor
        this.user_id=user_id;
        for_edit=false;
    }

    @SuppressLint("ValidFragment")
    public CreateServiceFragment(int user_id, int edit_service_id) {
        // Required empty public constructor
        this.user_id=user_id;
        this.edit_user_id=edit_service_id;
        for_edit=true;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_create_service, container, false);

        niceSpinner = niceSpinner = (NiceSpinner) view.findViewById(R.id.nice_spinner);
        // spinnerService=view.findViewById(R.id.spinnerService);
        cat=new ArrayList<>();
        cat_id=new ArrayList<>();
        myServicesList = new ArrayList<Service>();
        apiInterface = ApiClient.getApiClient().create(ServiceApiInterface.class);
        service_category=(TextView)view.findViewById(R.id.service_category);
        apiInterface2= ApiClient.getApiClient().create(ApiInterface.class);

        loggedin= HomeActivity.user;

        service_location = (EditText)view.findViewById(R.id.service_location);
        btnCreateSerivce = (Button) view.findViewById(R.id.btnCreateService);
        edtTxtSerivceTitle = (AutoCompleteTextView) view.findViewById(R.id.edtTxtSerivceTitle);
        edTxtServicePrice = (AutoCompleteTextView) view.findViewById(R.id.edTxtServicePrice);
        edDescription = (EditText) view.findViewById(R.id.editTextDescription);
        btnHome = (Button) view.findViewById(R.id.btnHome);
        home_avail= view.findViewById(R.id.home_avail);



        if (for_edit==false){

        }
        else{
            new GetServiceConncetion(getContext(),edit_user_id).execute();

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



        new Conncetion(getContext()).execute();

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent=new Intent(getContext(),HomeActivity.class);
                intent.putExtra("fragment_Flag", CONST.MY_SERVICES_LIST_FRAGMENT);
                startActivity(intent);



            }
        });

        btnCreateSerivce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //update Work here

                if(category_id==0){
                    category_id=cat_id.get(0);
                }

                if (btnCreateSerivce.getText().equals("Edit Service")) {

                    setUIToWait(true);
                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

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


                    Call<Service> call = CreateServiceFragment.apiInterface.updateService(title,description, location,latitude,longitude,country,category_id, Double.parseDouble(price),update_at,edit_user_id,home_avail_status);
                    call.enqueue(new Callback<Service>() {
                        @Override
                        public void onResponse(Call<Service> call, Response<Service> response) {

                            if (response.body().getResponse().equals("ok")) {
                                // MDMDToast mdMDToast = MDMDToast.makeText(getApplicationContext(), "Your Service Edit Successfully", MDMDToast.LENGTH_LONG, MDMDToast.TYPE_SUCCESS);

                                setUIToWait(false);
                                MDToast mdMDToast = MDToast.makeText(getContext(), "Your Service Edit Successfully", MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS);
                                mdMDToast.show();

                                edDescription.setText("");
                                edtTxtSerivceTitle.setText("");
                                edTxtServicePrice.setText("");
                                service_location.setText("");
                                btnCreateSerivce.setText("CREATE SERVICE");



                            }
                            if (response.isSuccessful()) {
                                //for debugging

//                                setUIToWait(false);
//                                MDToast mdMDToast = MDToast.makeText(getContext(), "Your Service Edit Successfully", MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS);
//                                mdMDToast.show();
//
//                                edDescription.setText("");
//                                edtTxtSerivceTitle.setText("");
//                                edTxtServicePrice.setText("");
//                                service_location.setText("");
//                                btnCreateSerivce.setText("CREATE SERVICE");
//


                            }
                        }

                        @Override
                        public void onFailure(Call<Service> call, Throwable t) {
                            MDToast.makeText(getContext(), t.getMessage(), MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();
                        }

                    });





                } else {


                    setUIToWait(true);
                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

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


                    Call<Service> call = CreateServiceFragment.apiInterface.
                            createService(title, user_id, description, location,latitude,longitude,country,
                                    1, Double.parseDouble(price + ""), category_id, date,
                                    home_avail_status);

                    call.enqueue(new Callback<Service>() {
                        @Override
                        public void onResponse(Call<Service> call, Response<Service> response) {
                            if (response.body().equals("ok")) {

                                setUIToWait(false);
                                MDToast mdMDToast = MDToast.makeText(getContext(), "Your Service Created Successfully", MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS);
                                mdMDToast.show();

                                SharedPreferences ob = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
                                int sp_id = ob.getInt("user_id", 0);

                                //for debugging

                            }
                            if (response.isSuccessful()) {

                                edDescription.setText("");
                                edtTxtSerivceTitle.setText("");
                                edTxtServicePrice.setText("");


                                Intent intent=new Intent(getContext(),HomeActivity.class);
                                intent.putExtra("fragment_Flag", CONST.MY_SERVICES_LIST_FRAGMENT);
                                startActivity(intent);


                                setUIToWait(false);

                            }
                        }


                        @Override
                        public void onFailure(Call<Service> call, Throwable t) {
                        }

                    });


                }
            }

        });


        return view;
    }

    class Conncetion extends AsyncTask<String,String ,String > {
        private ProgressDialog dialog;
        String categoriesArray[]=null;
        Context context;


        public  Conncetion(Context activity) {
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

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



    class GetServiceConncetion extends AsyncTask<String,String ,String > {
        private ProgressDialog dialog;
        int service_id;

        public  GetServiceConncetion(Context activity,int service_id) {
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

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    public String getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(getContext());
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
        Call<CreateRequest> call2 = CreateServiceFragment.apiInterface2.InsertRatings(0,0.0,user_id,service_p_id,service_id,Remarks,date,time);

        call2.enqueue(new Callback<CreateRequest>() {
            @Override
            public void onResponse(Call<CreateRequest> call, Response<CreateRequest> response) {
                if (response.body().getResponse().equals("ok")) {



                }


            }

            @Override
            public void onFailure(Call<CreateRequest> call, Throwable t) {
                //
            }

        });


    }

//
//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }


    private void setUIToWait(boolean wait) {

        if (wait) {
            progressDialog = ProgressDialog.show(getContext(), null, null, true, true);
//            progressDialog.setContentView(new ProgressBar(this));
            progressDialog.setContentView(R.layout.loader);

        } else {
            progressDialog.dismiss();
        }

    }

}
