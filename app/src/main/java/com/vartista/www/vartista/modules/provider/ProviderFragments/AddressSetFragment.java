package com.vartista.www.vartista.modules.provider.ProviderFragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.os.ConfigurationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.valdesekamdem.library.mdtoast.MDToast;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.UserAddressBean;
import com.vartista.www.vartista.modules.general.HomeActivity;
import com.vartista.www.vartista.modules.provider.AddressSetActivity;
import com.vartista.www.vartista.modules.provider.DocumentUploadActivity;
import com.vartista.www.vartista.restcalls.ApiClient;
import com.vartista.www.vartista.restcalls.ApiInterface;
import com.vartista.www.vartista.util.CONST;

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
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddressSetFragment extends Fragment {

    private EditText country,province, city, zipcode, w_address,p_address;
    private Button set_address;
    private ProgressDialog progressDialog;
    public static ApiInterface apiInterface;

    int user_id;

    public AddressSetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_address_set, container, false);

        String countryname = getActivity().getResources().getConfiguration().locale.getDisplayCountry();
        country= view.findViewById(R.id.country);
        province= view.findViewById(R.id.province);
        city= view.findViewById(R.id.city);
        zipcode= view.findViewById(R.id.postal_code);
        w_address= view.findViewById(R.id.w_address);
        p_address= view.findViewById(R.id.p_address);
        set_address= view.findViewById(R.id.set_address);
        apiInterface= ApiClient.getApiClient().create(ApiInterface.class);
        SharedPreferences ob = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);

        user_id = ob.getInt("user_id", 0);

        new Conncetion(user_id).execute();
  String Countryname = getCountryName();
//        Locale locale = ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration()).get(0);
//        String cname = locale.getDisplayName();
//        if (Countryname.trim().length()>0 && Countryname!=null) {
            country.setText(Countryname);
            country.setEnabled(false);
//        }

        set_address.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View v) {

                setUIToWait(true);

                String country_got=country.getText().toString();
                String province_got= province.getText().toString();
                String city_got= city.getText().toString();
                String zipcode_got= zipcode.getText().toString();
                String w_address_got= w_address.getText().toString();
                String p_address_got= p_address.getText().toString();



                if(set_address.getText().toString().equalsIgnoreCase("Update Address")){
                    Call<UserAddressBean> call = AddressSetFragment.apiInterface.UpdateUserAddress(w_address_got, p_address_got, city_got, province_got, zipcode_got, country_got, user_id);
                    call.enqueue(new Callback<UserAddressBean>() {
                        @Override
                        public void onResponse(Call<UserAddressBean> call, Response<UserAddressBean> response) {

                            if (response.body().getResponse().equals("ok")) {


                                setUIToWait(false);
                                getActivity().finish();
//
//                                Intent intent=new Intent(getContext(), HomeActivity.class);
//                                intent.putExtra("fragment_Flag", CONST.DOC_UPLOAD_FRAGMENT);
//                                startActivity(intent);

//                                startActivity(new Intent(getContext(), DocumentUploadActivity.class));

                            } else if (response.body().getResponse().equals("exist")) {
                                setUIToWait(false);

                            } else if (response.body().getResponse().equals("error")) {
                                setUIToWait(false);

                                MDToast.makeText(getContext(), "Something went wrong....", MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();

                            } else {
                                setUIToWait(false);

                                MDToast.makeText(getContext(), "Something went wrong....", MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();

                            }

                        }

                        @Override
                        public void onFailure(Call<UserAddressBean> call, Throwable t) {
                            setUIToWait(false);


                        }
                    });
                }
                else {

///*
                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    Call<UserAddressBean> call = AddressSetFragment.apiInterface.insertUserAddress(w_address_got, p_address_got, city_got, province_got, zipcode_got, country_got, user_id);
                    call.enqueue(new Callback<UserAddressBean>() {
                        @Override
                        public void onResponse(Call<UserAddressBean> call, Response<UserAddressBean> response) {

                            if (response.body().getResponse().equals("ok")) {


                                setUIToWait(false);

                                startActivity(new Intent(getContext(), DocumentUploadActivity.class));

                            } else if (response.body().getResponse().equals("exist")) {
                                setUIToWait(false);

                            } else if (response.body().getResponse().equals("error")) {
                                setUIToWait(false);

                                MDToast.makeText(getContext(), "Something went wrong....", MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();

                            } else {
                                setUIToWait(false);

                                MDToast.makeText(getContext(), "Something went wrong....", MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();

                            }

                        }

                        @Override
                        public void onFailure(Call<UserAddressBean> call, Throwable t) {
                            setUIToWait(false);
                            MDToast.makeText(getContext(), "Signup Failed", MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();


                        }
                    });

                }
            }
        });

        return view;
    }

    class Conncetion extends AsyncTask<String,String ,String > {
        private ProgressDialog dialog;
        int userId;

        public  Conncetion(int user_id) {
            dialog = new ProgressDialog(getContext());
            userId=user_id;

        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Retriving data Please Wait..");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {


            String result="";

            final String BASE_URL="http://vartista.com/vartista_app/fetch_address_stats.php?user_id="+userId;
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
                    JSONObject service = services.getJSONObject(0);

                    w_address.setText(service.getString("work_address"));
                    p_address.setText(service.getString("permanent_address"));
                    city.setText(service.getString("city"));
                    province.setText(service.getString("province"));
                    zipcode.setText(service.getString("zipcode"));
                    country.setText(service.getString("country"));
                    set_address.setText("Update Address");


                }
                else{
                    MDToast.makeText(getContext(), "Insert Address", MDToast.LENGTH_SHORT,MDToast.TYPE_SUCCESS).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                MDToast.makeText(getContext(),e.getMessage(),MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();
            }
        }
    }



    private void setUIToWait(boolean wait) {

        if (wait) {
            progressDialog = ProgressDialog.show(getContext(), null, null, true, true);
//            progressDialog.setContentView(new ProgressBar(this));
            progressDialog.setContentView(R.layout.loader);

        } else {
            progressDialog.dismiss();
        }

    }

    public String getCountryName(){
        String country_name = null;
        try {
            LocationManager lm = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            Geocoder geocoder = new Geocoder(getContext());
            for (String provider : lm.getAllProviders()) {
                Toast.makeText(getContext(), "" + provider, Toast.LENGTH_SHORT).show();
                @SuppressWarnings("ResourceType") Location location = lm.getLastKnownLocation(provider);
                if (location != null) {
                    try {
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        if (addresses != null && addresses.size() > 0) {
                            country_name = addresses.get(0).getCountryName();
                            break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
            return country_name;

    }
}
