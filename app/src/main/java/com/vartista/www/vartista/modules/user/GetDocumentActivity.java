package com.vartista.www.vartista.modules.user;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vartista.www.vartista.R;
import com.vartista.www.vartista.adapters.ProviderDocumentAdapter;
import com.vartista.www.vartista.adapters.ProviderPhotosAdapter;
import com.vartista.www.vartista.beans.ProviderDocuments;
import com.vartista.www.vartista.beans.ProviderPhotos;
import com.vartista.www.vartista.beans.User;
import com.vartista.www.vartista.modules.general.HomeActivity;

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

public class GetDocumentActivity extends AppCompatActivity {

    RecyclerView docRecyclerView;
    ProviderDocumentAdapter providerDocumentAdapter;
    ProviderPhotosAdapter providerPhotosAdapter;
    List<ProviderDocuments> providerDocumentsList;
    List<ProviderPhotos> providerPhotosList;

    int user_id;
    private RecyclerView photoRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_document);

        docRecyclerView = (RecyclerView) findViewById(R.id.file_list);
        docRecyclerView.setHasFixedSize(true);
        docRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        docRecyclerView.setLayoutManager(mLayoutManager);
        docRecyclerView.addItemDecoration(new
                DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        docRecyclerView.setItemAnimator(new DefaultItemAnimator());
        photoRecyclerView = findViewById(R.id.photo_list);
        photoRecyclerView.setHasFixedSize(false);
        photoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.LayoutManager LayoutManager = new LinearLayoutManager(this);
        photoRecyclerView.setLayoutManager(LayoutManager);
        photoRecyclerView.addItemDecoration(new
                DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        photoRecyclerView.setItemAnimator(new DefaultItemAnimator());


        providerDocumentsList = new ArrayList<>();
        providerPhotosList = new ArrayList<>();
        //  user_id=getIntent().getIntExtra("userId",10);
        //ye shared prefrences se aega
        User u = HomeActivity.user;

        SharedPreferences ob = getSharedPreferences("Login", Context.MODE_PRIVATE);

        user_id = ob.getInt("user_id", 0);


        new Connection(this, user_id).execute();
        new getPhotosAsync(this, user_id).execute();
    }

    class Connection extends AsyncTask<String, String, String> {
        private ProgressDialog dialog;
        int userId;

        public Connection(Activity activity, int user_id) {
            dialog = new ProgressDialog(activity);
            userId = user_id;
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Retriving data Please Wait..");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {


            String result = "";

            final String BASE_URL = "http://vartista.com/vartista_app/get_docs_of_serv_prov.php?user_id="+userId;
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
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            try {
                JSONObject jsonResult = new JSONObject(result);
                int success = jsonResult.getInt("success");

                docRecyclerView.setAdapter(providerDocumentAdapter);


                if (success == 1) {
                    JSONArray files = jsonResult.getJSONArray("documents");
                    for (int i = 0; i < files.length(); i++) {

                        JSONObject file = files.getJSONObject(i);
                        String fileTitle = file.getString("title");
                        String filePath = file.getString("url");

                        providerDocumentsList.add(new ProviderDocuments(fileTitle, filePath));
                    }
                    providerDocumentAdapter = new ProviderDocumentAdapter(GetDocumentActivity.this, providerDocumentsList);
                    docRecyclerView.setAdapter(providerDocumentAdapter);

                } else {

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class getPhotosAsync extends AsyncTask<String, String, String> {
        private ProgressDialog dialog;
        int userId;

        public getPhotosAsync(Activity activity, int prov_id) {
            dialog = new ProgressDialog(activity);
            userId = prov_id;
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Retriving data Please Wait..");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {


            String result = "";

            final String BASE_URL = "http://vartista.com/vartista_app/get_photos_of_srv_prov.php?prov_id="+userId;
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
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            try {
                JSONObject jsonResult = new JSONObject(result);
                int success = jsonResult.getInt("success");

                photoRecyclerView.setAdapter(providerPhotosAdapter);


                if (success == 1) {
                    JSONArray files = jsonResult.getJSONArray("photos");
                    for (int i = 0; i < files.length(); i++) {

                        JSONObject file = files.getJSONObject(i);
                        String cnic_front_title = file.getString("cinc_front_title");
                        String cnic_front_url = file.getString("cinc_front_url");
                        String cnic_back_title = file.getString("cinc_back_title");
                        String cnic_back_url = file.getString("cinc_back_url");
                        String bank_doc_title = file.getString("bank_doc_title");
                        String bank_doc_url = file.getString("bank_doc_url");


                        providerPhotosList.add(new ProviderPhotos(cnic_front_title,cnic_front_url));
                        providerPhotosList.add(new ProviderPhotos(cnic_back_title,cnic_back_url));
                        providerPhotosList.add(new ProviderPhotos(bank_doc_title,bank_doc_url));


                    }
                    providerPhotosAdapter = new ProviderPhotosAdapter(GetDocumentActivity.this, providerPhotosList);
                   photoRecyclerView.setAdapter(providerPhotosAdapter);

                } else {

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
