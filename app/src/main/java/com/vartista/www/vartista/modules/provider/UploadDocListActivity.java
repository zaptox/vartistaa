package com.vartista.www.vartista.modules.provider;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.vartista.www.vartista.R;
import com.vartista.www.vartista.adapters.DocUploadAdapter;
import com.vartista.www.vartista.adapters.MyServicesListAdapter;
import com.vartista.www.vartista.appconfig.App;
import com.vartista.www.vartista.beans.Category;
import com.vartista.www.vartista.beans.DocUpload;
import com.vartista.www.vartista.beans.DocUploadList;
import com.vartista.www.vartista.beans.Service;
import com.vartista.www.vartista.fragments.UsersFragment;
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
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadDocListActivity extends AppCompatActivity {
     RecyclerView recyclerViewDocsUpload;
    DocUploadAdapter docUploadAdapter;
    List<DocUpload> docUploadList;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_docs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerViewDocsUpload=(RecyclerView)findViewById(R.id.recyclerViewDocUpload);

         getSupportActionBar().setTitle("Upload Requrired Doc");
        SharedPreferences ob = getSharedPreferences("Login", Context.MODE_PRIVATE);

        userId = ob.getInt("user_id", 0);
        recyclerViewDocsUpload.setHasFixedSize(true);
        recyclerViewDocsUpload.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewDocsUpload.setLayoutManager(mLayoutManager);
        recyclerViewDocsUpload.addItemDecoration(new
                DividerItemDecoration(getApplicationContext(),
                DividerItemDecoration.VERTICAL));
        recyclerViewDocsUpload.setItemAnimator(new DefaultItemAnimator());
          docUploadList=new ArrayList<>();



        new UploadDocListActivity.Conncetion(getApplicationContext()).execute();


        docUploadAdapter=new DocUploadAdapter(getApplicationContext(),docUploadList);




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
//            dialog.setMessage("Retriving data Please Wait..");
//            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {


            String result="";

            final String BASE_URL="http://vartista.com/vartista_app/fetch_doc_req.php?user_id="+userId;
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
                    JSONArray docs=jsonResult.getJSONArray("doc");
                    for(int i=0;i<docs.length();i++){

                        JSONObject doc=docs.getJSONObject(i);
                        DocUpload docUpload=new DocUpload();
                        docUpload.setId(doc.getInt("id"));
                        docUpload.setUserId(""+doc.getInt("user_id"));
                        docUpload.setTitle(doc.getString("title"));
                        docUpload.setUrl(doc.getString("url"));
                        docUpload.setDate(doc.getString("date"));
                        docUpload.setResponseDate(doc.getString("response_date"));
                        docUpload.setApproved(doc.getString("approved"));
                        docUpload.setStatus(doc.getInt("status"));
                        docUploadList.add(docUpload);




                    }




                    recyclerViewDocsUpload.setAdapter(docUploadAdapter);


                }

                else{

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
