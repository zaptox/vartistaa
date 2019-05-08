package com.vartista.www.vartista.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.vartista.www.vartista.R;

/**
 * Created by Dell on 2018-08-04.
 */

public class ForUserFragment extends Fragment{

//    private Button btn;
//    private ListView listOfContacts;
//    private EditText search;
//
//    public static ForUserFragment instance=null;
//
//    public static ForUserFragment newInstance(){
//        instance = new ForUserFragment();
//        return instance;
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        View view= inflater.inflate(R.layout.contacts_fragment,container,false);
//
//        search=view.findViewById(R.id.search4);
////        btn=view.findViewById(R.id.button);
//        listOfContacts=view.findViewById(R.id.listofcontacts);
////
////        btn.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
//
//
//        Cursor cursor=null;
//        try {
//            cursor = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null, null);
//            getActivity().startManagingCursor(cursor);
//
//
//        }
//        catch (Exception e){
//            Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//
//        String[] from1={ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,ContactsContract.CommonDataKinds.Phone.NUMBER,
//                ContactsContract.CommonDataKinds.Phone._ID};
//
//
//        int[] to={android.R.id.text1, android.R.id.text2};
//
//
//        SimpleCursorAdapter simpleCursorAdapter= new SimpleCursorAdapter(getActivity().getApplication(),android.R.layout.simple_list_item_2,cursor,from1,to);
//
//
//        listOfContacts.setAdapter(simpleCursorAdapter);
//        listOfContacts.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//
////
////
////
////            }
////        });
////
//        return view;
//
//
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//    }
//    public class ReadContactAsync extends AsyncTask<Void, Void, Void > {
//
//        private ProgressDialog dialog;
//
//        public ReadContactAsync(Context context){
//            dialog = new ProgressDialog(context);
//        }
//
//
//        @Override
//        protected void onPreExecute() {
//            dialog.setMessage("Getting contacts from android database");
//            dialog.show();
//
//            super.onPreExecute();
//
//
//
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            return null;
//        }
//
//        View view= inflater.inflate(R.layout.contacts_fragment,container,false);
//
//        search=view.findViewById(R.id.search4);
////        btn=view.findViewById(R.id.button);
//        listOfContacts=view.findViewById(R.id.listofcontacts);
////
////        btn.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
//
//
//        Cursor cursor=null;
//                try {
//            cursor = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null, null);
//            getActivity().startManagingCursor(cursor);
//
//
//        }
//                catch (Exception e){
//            Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//
//        String[] from1={ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,ContactsContract.CommonDataKinds.Phone.NUMBER,
//                ContactsContract.CommonDataKinds.Phone._ID};
//
//
//        int[] to={android.R.id.text1, android.R.id.text2};
//
//
//        SimpleCursorAdapter simpleCursorAdapter= new SimpleCursorAdapter(getActivity().getApplication(),android.R.layout.simple_list_item_2,cursor,from1,to);
//
//
//                listOfContacts.setAdapter(simpleCursorAdapter);
//                listOfContacts.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//
//
//    }
//


}
