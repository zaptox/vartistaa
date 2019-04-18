package com.vartista.www.vartista.fragments;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.vartista.www.vartista.modules.general.HomeActivity;
import com.vartista.www.vartista.restcalls.ApiClient;
import com.vartista.www.vartista.restcalls.ApiInterface;
import com.vartista.www.vartista.beans.Category;

import com.vartista.www.vartista.R;
import com.vartista.www.vartista.adapters.CategoriesListAdapter;

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



/**
 * A simple {@link Fragment} subclass.
 */
public class UsersFragment extends Fragment {
    int user_id;
    public static ApiInterface apiInterface;
    RecyclerView listViewMyCategories;
    CategoriesListAdapter categoriesListAdapter;
    List<Category> myCategoriesList;
    ViewPager viewpager;
    TabLayout tabLayout;
    private FragmentActivity myContext;


    @SuppressLint("ValidFragment")
    public UsersFragment(int user_id) {
        // Required
        // public constructor
        this.user_id=user_id;
    }
    public UsersFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_users, container, false);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        viewpager = (ViewPager)getActivity().findViewById(R.id.viewpager);
        tabLayout = (TabLayout) getActivity().findViewById(R.id.tabs);
        tabLayout.setVisibility(View.VISIBLE);



        listViewMyCategories=(RecyclerView) view.findViewById(R.id.lvCategory);
        listViewMyCategories.setHasFixedSize(true);
        listViewMyCategories.setLayoutManager(new LinearLayoutManager(getContext()));
        Context context = inflater.getContext();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        listViewMyCategories.setLayoutManager(mLayoutManager);
        listViewMyCategories.setItemAnimator(new DefaultItemAnimator());

        myCategoriesList=new ArrayList<>();


        new UsersFragment.Conncetion(context).execute();


//        categoriesListAdapter=new CategoriesListAdapter(context,myCategoriesList);
//        categoriesListAdapter=new CategoriesListAdapter(context,myCategoriesList,viewpager);
        categoriesListAdapter=new CategoriesListAdapter(context,myCategoriesList,tabLayout, myContext);




        return  view; }





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

            final String BASE_URL="http://vartista.com/vartista_app/fetch_categories.php";
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
                listViewMyCategories.setAdapter(categoriesListAdapter);


                if(success==1){
                    JSONArray catogires=jsonResult.getJSONArray("category");
                    for(int i=0;i<catogires.length();i++){

                        JSONObject category=catogires.getJSONObject(i);
                        int category_id=category.getInt("id");
                        String category_name=category.getString("name");
                        String image=category.getString("image");
                        myCategoriesList.add(new Category(category_name,category_id,image));



                    }




                }

                else{

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
