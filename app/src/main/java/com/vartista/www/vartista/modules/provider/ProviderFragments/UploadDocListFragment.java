package com.vartista.www.vartista.modules.provider.ProviderFragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vartista.www.vartista.R;
import com.vartista.www.vartista.adapters.DocUploadAdapter;
import com.vartista.www.vartista.beans.DocUpload;
import com.vartista.www.vartista.modules.provider.UploadDocListActivity;

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
 * Activities that contain this fragment must implement the
 * {@link UploadDocListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UploadDocListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UploadDocListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Activity code

    RecyclerView recyclerViewDocsUpload;
    DocUploadAdapter docUploadAdapter;
    List<DocUpload> docUploadList;
    int userId;


    private OnFragmentInteractionListener mListener;

    public UploadDocListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UploadDocListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UploadDocListFragment newInstance(String param1, String param2) {
        UploadDocListFragment fragment = new UploadDocListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload_doc_list, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        recyclerViewDocsUpload=(RecyclerView)view.findViewById(R.id.recyclerViewDocUpload);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Upload Requrired Doc");
        SharedPreferences ob = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);

        userId = ob.getInt("user_id", 0);
        recyclerViewDocsUpload.setHasFixedSize(true);
        recyclerViewDocsUpload.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewDocsUpload.setLayoutManager(mLayoutManager);
        recyclerViewDocsUpload.addItemDecoration(new
                DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        recyclerViewDocsUpload.setItemAnimator(new DefaultItemAnimator());
        docUploadList=new ArrayList<>();



        new UploadDocListFragment.Conncetion(getContext()).execute();


        docUploadAdapter=new DocUploadAdapter(getContext(),docUploadList);




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


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
