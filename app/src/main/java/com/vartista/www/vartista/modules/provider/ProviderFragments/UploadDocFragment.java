package com.vartista.www.vartista.modules.provider.ProviderFragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.valdesekamdem.library.mdtoast.MDToast;
import com.vartista.www.vartista.R;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UploadDocFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UploadDocFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UploadDocFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Activity code

    private static final String UPLOAD_URL = "http://vartista.com/vartista_app/upload_doc.php";
    private static final int UPLOAD_DOC_CODE = 95;
    private ImageView imageViewDoc;
    private TextView tvDocTitle,imageViewTitleImage;
    private Button btnUploadDoc;
    private Uri filePath;
    private Bitmap bitmap;
    int userId;





    private OnFragmentInteractionListener mListener;

    public UploadDocFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UploadDocFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UploadDocFragment newInstance(String param1, String param2) {
        UploadDocFragment fragment = new UploadDocFragment();
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
        View view = inflater.inflate(R.layout.fragment_upload_doc, container, false);

        tvDocTitle=(TextView)view.findViewById(R.id.tvDocTitle);
        tvDocTitle=(TextView)view.findViewById(R.id.tvDocTitle);
        imageViewTitleImage=(TextView)view.findViewById(R.id.imageViewTitleImage);
        btnUploadDoc=(Button)view.findViewById(R.id.btnUploadDoc);
        imageViewDoc=(ImageView)view.findViewById(R.id.imageViewDocImage);
        Intent intent=getActivity().getIntent();

        SharedPreferences ob = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);

        userId = ob.getInt("user_id", 0);        final String title=intent.getStringExtra("title");
        final int docId=intent.getIntExtra("docId",0);

        tvDocTitle.setText(title);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(title);
        imageViewTitleImage.setText("Click to add"+title);
        imageViewDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), UPLOAD_DOC_CODE);

            }
        });
        btnUploadDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();

                SimpleDateFormat mdformat = new SimpleDateFormat("yyyy / MM / dd ");
                String respDate = ""+mdformat.format(calendar.getTime());
                uploadMultipart(filePath,title,userId,docId,respDate );

            }
        });





        return view;
    }

    public void uploadMultipart(Uri filePath, String title, int  userId, int docId, String responseDate) {

        //getting the actual path of the image
        String path = getPath(filePath);

        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            //Creating a multi part request
            new MultipartUploadRequest(getContext(), uploadId, UPLOAD_URL)
                    .addFileToUpload(path, "image") //Adding file
                    .addParameter("user_id",""+userId)
                    .addParameter("title", title)
                    .addParameter("doc_id",""+docId)
                    .addParameter("response_date",""+responseDate)

                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(5)

                    .startUpload(); //Starting the upload

        } catch (Exception exc) {

            MDToast.makeText(getContext(), exc.getMessage(), MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();
        }
    }

    public String getPath(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getActivity().getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == UPLOAD_DOC_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                imageViewDoc.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }}

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
