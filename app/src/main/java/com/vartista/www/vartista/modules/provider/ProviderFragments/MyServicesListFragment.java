package com.vartista.www.vartista.modules.provider.ProviderFragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vartista.www.vartista.R;
import com.vartista.www.vartista.adapters.MyServicesListAdapter;
import com.vartista.www.vartista.beans.Service;
import com.vartista.www.vartista.beans.User;
import com.vartista.www.vartista.modules.general.HomeActivity;
import com.vartista.www.vartista.modules.provider.MyServicesListActivity;

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



public class MyServicesListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Activity Code

    RecyclerView listViewMyServices;
    MyServicesListAdapter myServicesListAdapter;
    List<Service> myservicesList;

    int user_id;

//    private OnFragmentInteractionListener mListener;

    public MyServicesListFragment() {
        // Required empty public constructor
    }


    @SuppressLint("ValidFragment")
    public MyServicesListFragment(int user_id) {
        // Required empty public constructor
        this.user_id=user_id;
    }

//    public static MyServicesListFragment newInstance(String param1, String param2) {
//        MyServicesListFragment fragment = new MyServicesListFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_my_services_list, container, false);

        listViewMyServices=(RecyclerView) view.findViewById(R.id.lvServiceList);
        listViewMyServices.setHasFixedSize(true);
        listViewMyServices.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        listViewMyServices.setLayoutManager(mLayoutManager);
        listViewMyServices.addItemDecoration(new
                DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        listViewMyServices.setItemAnimator(new DefaultItemAnimator());

        myservicesList=new ArrayList<>();
//          user_id=getIntent().getIntExtra("userId",10);
        //ye shared prefrences se aega
        User u= HomeActivity.user;

        SharedPreferences ob = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);

        user_id = ob.getInt("user_id", 0);



        new Conncetion(getContext(),user_id).execute();


        myServicesListAdapter=new MyServicesListAdapter(getContext(),myservicesList);


        return view;
    }

    class Conncetion extends AsyncTask<String,String ,String > {
        private ProgressDialog dialog;
        int userId;

        public  Conncetion(Context activity,int user_id) {
            dialog = new ProgressDialog(activity);
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

            final String BASE_URL="http://www.vartista.com/vartista_app/CREATE_SERVICES/view.services.php?user_id="+userId;
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

                listViewMyServices.setAdapter(myServicesListAdapter);


                if(success==1){
                    JSONArray services=jsonResult.getJSONArray("services");
                    for(int i=0;i<services.length();i++){

                        JSONObject service=services.getJSONObject(i);
                        int service_id=service.getInt("service_id");
                        String service_title=service.getString("service_title");
                        double price=service.getDouble("price");
                        int status=service.getInt("status");
                        String created_at=service.getString("created_at");
                        String updated_at=service.getString("updated_at");
                        int category_id=service.getInt("category_id");
                        String service_description=service.getString("service_description");
                        String location=service.getString("location");
                        String category_name=service.getString("name");
                        int user_id=service.getInt("user_id");
                        int home_avail_status= service.getInt("home_avail_status");

                        myservicesList.add(new Service(service_id,user_id,category_name , service_title, service_description, location, status,  price,  category_id,  created_at,  updated_at,home_avail_status));

                        Toast.makeText(getContext(), ""+myservicesList, Toast.LENGTH_SHORT).show();
                    }

                }
                else{

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    // TODO: Rename method, update argument and hook method into UI event
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
