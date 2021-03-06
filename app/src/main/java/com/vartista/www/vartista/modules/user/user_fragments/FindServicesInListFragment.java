package com.vartista.www.vartista.modules.user.user_fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.valdesekamdem.library.mdtoast.MDToast;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.adapters.ServicesInListMapAdapter;
import com.vartista.www.vartista.beans.GetServiceProviders;
import com.vartista.www.vartista.restcalls.ApiClient;
import com.vartista.www.vartista.restcalls.ApiInterface;
import com.victor.loading.rotate.RotateLoading;

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
import java.util.List;


public class FindServicesInListFragment extends Fragment {

    int user_id;

    // Views Declarations
    Button btnMap, btnFilter, btnApplyFilter, filter_btn_option;
    RecyclerView listViewMyServices;
    EditText filter_on_text;
    ServicesInListMapAdapter myServicesListAdapter;
    View view;
    View view_same;
    List<GetServiceProviders> myservicesList;
    private FragmentActivity myContext;





    public String filterLocation, filterGender;

    public int cat_id2;
    public int filterCost;
    //By default search will be done on user name
    public static String typeOfFilter = "ser.service_title";


    public boolean filterApplied = false;

    public static ApiInterface apiInterface;
    ArrayList<GetServiceProviders> splist;
    TabLayout tabLayout;

    public FindServicesInListFragment() {

    }

    @SuppressLint("ValidFragment")
    public FindServicesInListFragment(int cat_id2) {
        this.cat_id2=cat_id2;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.activity_services_in_list,container,false);

        view_same=view;

        btnFilter = view.findViewById(R.id.filter_button);
        filter_btn_option = view.findViewById(R.id.filter_option_btn);
        filter_on_text = view.findViewById(R.id.filter_txt);

        splist=new ArrayList<GetServiceProviders>();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

//        Intent intent=getActivity().getIntent();
//        final int cat_id=intent.getIntExtra("cat_id",0);
//        cat_id2=cat_id;
//                intent.putExtra("cat_id",cat_id);

        //Getting user id from HomeAcitivty




        SharedPreferences ob = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        user_id = ob.getInt("user_id", 0);

        listViewMyServices=(RecyclerView) view.findViewById(R.id.lvFindServices);
        listViewMyServices.setHasFixedSize(true);
        listViewMyServices.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        listViewMyServices.setLayoutManager(mLayoutManager);
        listViewMyServices.addItemDecoration(new
                DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        listViewMyServices.setItemAnimator(new DefaultItemAnimator());

        myservicesList=new ArrayList<>();


        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog=new Dialog(getContext());
                dialog.setContentView(R.layout.filter_dialog_box);
                dialog.setCanceledOnTouchOutside(true);
                dialog.setTitle("Filter Search");

                final TextView maxCost = dialog.findViewById(R.id.maxCost);
                final EditText locationEditText = dialog.findViewById(R.id.editTxt_location);
                final Spinner genderEditText = dialog.findViewById(R.id.editTxt_gender);
                final SeekBar costSeekBar = dialog.findViewById(R.id.seekBar);
                btnApplyFilter = dialog.findViewById(R.id.applyFilterButton);



                dialog.show();

                costSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        maxCost.setText("Rs. "+(i*100));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });

                btnApplyFilter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        filterLocation = locationEditText.getText().toString();
                        filterGender = genderEditText.getSelectedItem().toString();
                        filterCost = costSeekBar.getProgress()*100;

                        filterApplied = true;
                        dialog.dismiss();

//                        Toast.makeText(FindServicesInList.this, "Filter: "+filterApplied+"\nLocation: "+filterLocation+"\nGender: "+filterGender,Toast.LENGTH_SHORT).show();


                        if(filterApplied==true){


                            new Connection(getContext(),cat_id2,filterLocation,filterGender,filterCost,view_same).execute();


                        }
                        else {

                        }
                    }
                });


            }
        });

        filter_on_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence c, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String filter_text =  filter_on_text.getText().toString();

                if(filterApplied)
                {
                    new Connection2(getContext(),cat_id2,filterLocation,filterGender,filterCost, typeOfFilter,filter_text,view).execute();
                }

                else
                {
                    new Connection2(getContext(),cat_id2, typeOfFilter,filter_text,view).execute();
                    Log.d("TextField1" ,filter_text);
                }



            }
        });

        filter_btn_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog=new Dialog(getContext());
                dialog.setContentView(R.layout.filter_dialog_option);
                dialog.setCanceledOnTouchOutside(true);
                dialog.setTitle("Filter Opiton");

                final RadioGroup rgFilter_option = dialog.findViewById(R.id.radio_group_filter);

                rgFilter_option.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                        switch (checkedId)
                        {
                            case R.id.filter_by_name:
                                typeOfFilter = "u.name";
                                filter_on_text.setHint("Service provider name");
                                break;
                            case R.id.filter_by_service :
                                typeOfFilter = "ser.service_title";
                                filter_on_text.setHint("Service Title");
                                break;

                        }

                    }
                });


                dialog.show();

            }
        });

        new Connection(getContext(), cat_id2,view).execute();

        return view;

    }

    private void sendFilterQuery(CharSequence c) {

    }


    class Connection extends AsyncTask<String,String ,String > {
        private int cat_id;
        private String filter_location;
        private String filter_gender;
        private int filter_cost;
        private ProgressDialog dialog;
        RotateLoading rotateLoading ;
        RelativeLayout relativeLayout;
        View view;

        public Connection(Context activity, int cat_id,View view)  {
            dialog = new ProgressDialog(activity);
            this.cat_id = cat_id;
            this.view=view;
        }

        public Connection(Context activity, int cat_id, String filter_location, String filter_gender, int filter_cost, View view) {
            dialog = new ProgressDialog(activity);
            this.cat_id = cat_id;
            this.filter_location=filter_location;
            this.filter_gender=filter_gender;
            this.filter_cost=filter_cost;
            this.view=view;
        }

        @Override
        protected void onPreExecute() {
            relativeLayout = view.findViewById(R.id.layout_loadinganimation);
            rotateLoading = view.findViewById(R.id.loadinganimation);
            rotateLoading.start();
            relativeLayout.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {


            String result = "";

            if(filterApplied == true)
            {
                splist= new ArrayList<GetServiceProviders>();

                final String BASE_URL = "http://vartista.com/vartista_app/filter_get_service_providers.php?cat_id="+cat_id+"&filterLocation="+filter_location+"&filterGender='"+filterGender+"'&filtercost="+filter_cost+"&user_id="+user_id;

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
                    return new String("There is exception" + e.getMessage());
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;

            }

            else {

                    final String BASE_URL = "http://vartista.com/vartista_app/get_service_providers.php?cat_id=" + cat_id+"&user_id="+user_id;

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
                    return new String("There is exception" + e.getMessage());
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;

            }
        }

        @Override
        protected void onPostExecute(String result) {
            rotateLoading.stop();
            relativeLayout.setVisibility(View.GONE);


            try {
                JSONObject jsonResult = new JSONObject(result);
                int success = jsonResult.getInt("success");

                if (success == 1) {

                    JSONArray services = jsonResult.getJSONArray("services");


                    for (int j = 0; j < services.length(); j++) {

                        JSONObject ser1 = services.getJSONObject(j);
                        int service_id = Integer.parseInt(ser1.getString("service_id"));
                        int user_id2 = Integer.parseInt(ser1.getString("user_id"));
                        int address_id = Integer.parseInt(ser1.getString("id"));
                        int category_id = Integer.parseInt(ser1.getString("category_id"));
                        String service_title = ser1.getString("service_title");
                        String location = ser1.getString("location");

                        String service_description = ser1.getString("service_description");
                        double price = Double.parseDouble(ser1.getString("price"));

                        double longitude = Double.parseDouble(ser1.getString("longitude"));
                        double latitude = Double.parseDouble(ser1.getString("latitude"));
                        String sp_name=ser1.getString("name");

                        double stars = 0;
                        try {
                            stars=Double.parseDouble(ser1.getString("avg_stars"));
                        }
                        catch(Exception e){
                            stars=0;
                        }
                        int user_status = Integer.parseInt(ser1.getString("user_status"));
                        String image = ser1.getString("image");
                        int busy_status=ser1.getInt("busy_status");

                        splist.add(new GetServiceProviders(service_id, address_id, latitude, longitude, user_id2, service_title, service_description, price, category_id,sp_name,stars, user_status, image,busy_status,location));

                    }

                    myServicesListAdapter=new ServicesInListMapAdapter(getContext(),splist,myContext,tabLayout);
                    listViewMyServices.setAdapter(myServicesListAdapter);



                } else {
                    splist= new ArrayList<GetServiceProviders>();
                    myServicesListAdapter=new ServicesInListMapAdapter(getContext(),splist,myContext,tabLayout);
                    listViewMyServices.setAdapter(myServicesListAdapter);


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }


    class Connection2 extends AsyncTask<String,String ,String > {
        private int cat_id;
        private String filter_location;
        private String filter_gender;
        private int filter_cost;
        private String filter_type;
        private String filter_text;
        RotateLoading rotateLoading ;
        RelativeLayout relativeLayout;
        View view;
        private int request_faliure_count = 0;



        public Connection2(Context activity, int cat_id,View view) {

            this.cat_id = cat_id;
            this.view=view;
        }

        public Connection2(Context activity, int cat_id, String filter_location, String filter_gender, int filter_cost,View view) {

            this.cat_id = cat_id;
            this.filter_location=filter_location;
            this.filter_gender=filter_gender;
            this.filter_cost=filter_cost;
            this.view=view;

        }

        public Connection2(Context activity,int cat_id, String filter_location, String filter_gender, int filter_cost, String filter_type, String filter_text,View view) {
            this.cat_id = cat_id;
            this.filter_location = filter_location;
            this.filter_gender = filter_gender;
            this.filter_cost = filter_cost;
            this.filter_type = filter_type;
            this.filter_text = filter_text;
            this.view=view;

        }

        public Connection2(Context activity,int cat_id, String filter_type, String filter_text,View view) {
            this.cat_id = cat_id;
            this.filter_type = filter_type;
            this.filter_text = filter_text;
            this.view=view;

        }

        @Override
        protected void onPreExecute() {
            relativeLayout = view.findViewById(R.id.layout_loadinganimation);
            rotateLoading = view.findViewById(R.id.loadinganimation);
            relativeLayout.setVisibility(View.VISIBLE);
            rotateLoading.start();

        }

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            String BASE_URL = "";

            if(filterApplied)
            {
                BASE_URL = "http://www.vartista.com/vartista_app/filter_get_serv_provider_by_name_or_serv.php?cat_id="+cat_id+"&filterLocation="+filter_location+"&filterGender="+filter_gender+"&filtercost="+filter_cost+"&search_type="+filter_type+"&search_text="+filter_text+"&user_id="+user_id;
            }
            else
            {
                BASE_URL = "http://www.vartista.com/vartista_app/filter_get_serv_provider_by_name_or_serv.php?cat_id="+cat_id+"&search_type="+filter_type+"&search_text="+filter_text+"&user_id="+user_id;
            }

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
                return new String("There is exception" + e.getMessage());
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;

        }

        @Override
        protected void onPostExecute(String result) {

            try {
                JSONObject jsonResult = new JSONObject(result);
                int success = jsonResult.getInt("success");
                if (success == 1) {
                    //hide empty state view if present

                    rotateLoading.stop();
                    relativeLayout.setVisibility(View.GONE);

                    splist.clear();
                    JSONArray services = jsonResult.getJSONArray("services");
                    for (int j = 0; j < services.length(); j++) {

                        JSONObject ser1 = services.getJSONObject(j);
                        int service_id = Integer.parseInt(ser1.getString("service_id"));
                        int user_id2 = Integer.parseInt(ser1.getString("user_id"));
                        int address_id = Integer.parseInt(ser1.getString("id"));
                        int category_id = Integer.parseInt(ser1.getString("category_id"));
                        String service_title = ser1.getString("service_title");
                        String service_description = ser1.getString("service_description");
                        double price = Double.parseDouble(ser1.getString("price"));
                        String location = ser1.getString("location");

                        double longitude = Double.parseDouble(ser1.getString("longitude"));
                        double latitude = Double.parseDouble(ser1.getString("latitude"));
                        String sp_name=ser1.getString("name");
                        double stars =0.0;
                        try{
                            stars= Double.parseDouble(ser1.getString("avg_stars"));
                        }
                        catch (Exception e){
                            stars=0.0;
                        }
                        int user_status = Integer.parseInt(ser1.getString("user_status"));
                        String image = ser1.getString("image");
                        int busy_status=ser1.getInt("busy_status");

                        splist.add(new GetServiceProviders(service_id, address_id, latitude, longitude, user_id2, service_title, service_description, price, category_id,sp_name,stars,user_status,image,busy_status,location));

                    }
//
                    myServicesListAdapter=new ServicesInListMapAdapter(getContext(),splist, myContext,tabLayout);
                    listViewMyServices.setAdapter(myServicesListAdapter);

                } else {
                    MDToast.makeText(myContext,"no data",MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();



                    splist= new ArrayList<GetServiceProviders>();
                    myServicesListAdapter=new ServicesInListMapAdapter(getContext(),splist,myContext,tabLayout);
                    listViewMyServices.setAdapter(myServicesListAdapter);
                }
            } catch (JSONException e) {
                e.printStackTrace();

            }
        }

    }


    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

}