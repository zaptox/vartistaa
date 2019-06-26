package com.vartista.www.vartista.modules.provider.ProviderFragments;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.valdesekamdem.library.mdtoast.MDToast;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.adapters.ProviderDocumentAdapter;
import com.vartista.www.vartista.adapters.ProviderPhotosAdapter;
import com.vartista.www.vartista.beans.ProviderDocuments;
import com.vartista.www.vartista.beans.ProviderPhotos;
import com.vartista.www.vartista.beans.User;
import com.vartista.www.vartista.modules.general.HomeActivity;
import com.vartista.www.vartista.modules.provider.AddressSetActivity;
import com.vartista.www.vartista.modules.provider.DocumentUploadActivity;
import com.vartista.www.vartista.modules.user.GetDocumentActivity;
import com.vartista.www.vartista.restcalls.ApiClient;
import com.vartista.www.vartista.restcalls.ApiInterface;
import com.vartista.www.vartista.util.CONST;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

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
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DocumentUploadFragment extends Fragment {

    //Activity code
    private static final String UPLOAD_URL = "http://vartista.com/vartista_app/insert_image.php";
    private static final int CNIC_FRONT_IMAGE_REQUEST_CODE = 3;
    private static final int CNIC_BACK_IMAGE_REQUEST_CODE = 7;
    private static final int BANK_DETAILS_IMAGE_REQUEST_CODE = 11;
    private static final int STORAGE_PERMISSION_CODE = 123;
    private ImageView imageViewBankDetails,imageViewCnicFront,imageViewBackCinc;
    private Button btnUploadCnicBack, btnSetAddress,btnUploadBankDetails,btnUploadCNICFront,btnSendRequest;
    private Bitmap bitmapCnicFront,bitmapCnicBack,bitmapBankDetails;
    private TextView imageViewBankDetailsTitle,imageViewCincFrontTitle,imageViewBackCincTitle;
    private Uri filePathCnicFront,filePathCnicBack,filePathBankDetails;
    private  static String cnic_front_document_title="cnic_front";
    private  static String cnic_back_document_title="cnic_back";
    private  static String bank_details_document_title="bank_details";
    private  boolean cnic_front=false,cnic_back=false,bank_details=false;
    private ProgressDialog progressDialog;
    int user_id;
    public static ApiInterface apiInterface;
    private FragmentActivity myContext;
    TabLayout tabLayout;
    List<ProviderPhotos> providerPhotosList;



    public DocumentUploadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.activity_document_upload, container, false);

        SharedPreferences ob = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        requestStoragePermission();
        imageViewBankDetails=(ImageView)view.findViewById(R.id.imageViewBankDetails);
        imageViewCnicFront=(ImageView)view.findViewById(R.id.imageViewCnicFront);
        imageViewBackCinc=(ImageView)view.findViewById(R.id.imageViewBackCinc);
        btnSetAddress= view.findViewById(R.id.set_address_required);
        user_id=ob.getInt("user_id",0);
        apiInterface= ApiClient.getApiClient().create(ApiInterface.class);
        btnUploadCnicBack =(Button)view.findViewById(R.id.btnUploadCNICBack);
        btnUploadBankDetails =(Button)view.findViewById(R.id.btnUploadBankDetails);
        btnUploadCNICFront =(Button)view.findViewById(R.id.btnUploadCNICFront);
        btnSendRequest= (Button)view.findViewById(R.id.send_request);
        imageViewBankDetailsTitle= (TextView) view.findViewById(R.id.imageViewBankDetailsTitle);
        imageViewCincFrontTitle= (TextView) view.findViewById(R.id.imageViewCincFrontTitle);
        imageViewBackCincTitle= (TextView) view.findViewById(R.id.imageViewBackCincTitle);


        //        tabLayout.setVisibility(View.GONE);

        providerPhotosList = new ArrayList<>();


        new getPhotosAsync(getContext(), user_id).execute();


        btnSetAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(myContext, HomeActivity.class);
                intent.putExtra("fragment_Flag", CONST.ADDRESS_SET_FRAGMENT);
                startActivity(intent);

            }
        });

        imageViewBankDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), BANK_DETAILS_IMAGE_REQUEST_CODE);
            }
        });
//
//


        imageViewCnicFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), CNIC_FRONT_IMAGE_REQUEST_CODE);
            }
        });
//
        imageViewBackCinc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), CNIC_BACK_IMAGE_REQUEST_CODE);
            }
        });


        btnUploadCnicBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cnic_back==true) {

                    try{
                        uploadMultipart( filePathCnicBack, cnic_back_document_title);
                        MDToast.makeText(getContext(), "Uploading Image... It may take few minutes.", MDToast.LENGTH_SHORT,MDToast.TYPE_SUCCESS).show();

                    }catch (Exception e){

                    }
                }
                else {
                    showCompletedDialog("error in uploading CNIC Back","Kindly provide required image ");
                }



            }
        });


        btnUploadCNICFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cnic_front==true) {

                    try{
                        uploadMultipart( filePathCnicFront, cnic_front_document_title);
                        MDToast.makeText(getContext(), "Uploading Image... It may take few minutes.", MDToast.LENGTH_SHORT,MDToast.TYPE_SUCCESS).show();
                    }catch (Exception e){
                    }
                }
                else {
                    showCompletedDialog("error in uploading CNIC Front","Kindly provide required image ");
                }



            }
        });
        btnUploadBankDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bank_details==true) {

                    try{
                        uploadMultipart( filePathBankDetails,bank_details_document_title);
                        MDToast.makeText(getContext(), "Uploading Image... It may take few minutes.", MDToast.LENGTH_SHORT,MDToast.TYPE_SUCCESS).show();
                    }catch (Exception e){
                    }
                }
                else {
                    showCompletedDialog("error in uploading Bank Details","Kindly provide required image ");
                }



            }
        });

        btnSendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSPStatus();
            }
        });

        return view;
    }


    private  void updateSPStatus(){
        setUIToWait(true);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);



        Call<User> call= DocumentUploadFragment.apiInterface.UpdateSpStatus(user_id);
        try {
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {

                    if (response.body().getResponse().equals("ok")) {
                        setUIToWait(false);

                        MDToast.makeText(getContext(), "Updated Successfully..", MDToast.LENGTH_SHORT,MDToast.TYPE_SUCCESS).show();

                    } else if (response.body().getResponse().equals("exist")) {
                        setUIToWait(false);

                        MDToast.makeText(getContext(), "Same Data exists....", MDToast.LENGTH_SHORT,MDToast.TYPE_WARNING).show();

                    } else if (response.body().getResponse().equals("error")) {
                        setUIToWait(false);

                        MDToast.makeText(getContext(), "Something went wrong....", MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();

                    } else {
                        setUIToWait(false);

                        MDToast.makeText(getContext(), "Something went wrong....", MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();

                    }


//                        user_name.setText("response ");
                    MDToast.makeText(getContext(), "Request sent to admin", MDToast.LENGTH_SHORT,MDToast.TYPE_SUCCESS).show();

//                            finish();
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    setUIToWait(false);
                    MDToast.makeText(getContext(), "Update Failed", MDToast.LENGTH_SHORT,MDToast.TYPE_WARNING).show();

//                        create.setText(t.getMessage());
                }
            });
        }
        catch(Exception e){
            MDToast mdMDToast = MDToast.makeText(getContext(), ""+e.getMessage(), MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS);
            mdMDToast.show();

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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CNIC_FRONT_IMAGE_REQUEST_CODE && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            filePathCnicFront = data.getData();
            try {
                bitmapCnicFront = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePathCnicFront);
                imageViewCnicFront.setImageBitmap(bitmapCnicFront);
                cnic_front=true;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if (requestCode == CNIC_BACK_IMAGE_REQUEST_CODE && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            filePathCnicBack = data.getData();
            try {
                bitmapCnicBack = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePathCnicBack);
                imageViewBackCinc.setImageBitmap(bitmapCnicBack);
                cnic_back=true;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if (requestCode == BANK_DETAILS_IMAGE_REQUEST_CODE && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            filePathBankDetails = data.getData();
            try {
                bitmapBankDetails = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePathBankDetails);
                imageViewBankDetails.setImageBitmap(bitmapBankDetails);
                bank_details=true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void uploadMultipart(Uri filePath,String title ) {

        //getting the actual path of the image
        String path = getPath(filePath);

        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            //Creating a multi part request
            new MultipartUploadRequest(getContext(), uploadId, UPLOAD_URL)
                    .addFileToUpload(path, "image") //Adding file
                    .addParameter("user_id",""+user_id)
                    .addParameter("title", title)
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(3)

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
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to getContext() block
            //Here you can explain why you need getContext() permission
            //Explain here why you need getContext() permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    //getContext() method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a MDToast
            } else {
                //Displaying another MDToast if permission is not granted
            }
        }
    }

    protected void showCompletedDialog(String title,String msg) {
        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(
                getContext());

        // set title
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder
                .setMessage(msg)
                .setCancelable(true)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();


    }
    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }





    class getPhotosAsync extends AsyncTask<String, String, String> {
        private ProgressDialog dialog;
        int userId;

        public getPhotosAsync(Context activity, int prov_id) {
            dialog = new ProgressDialog(activity);
            userId = prov_id;
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Please Wait..");
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


                        if(cnic_front_url!=null){

                            Picasso.get().load(cnic_front_url).fit().centerCrop()
                                    .placeholder(R.drawable.pictures)
                                    .error(R.drawable.pictures)
                                    .into(imageViewCnicFront);
                            imageViewCincFrontTitle.setText("Click to update CNIC front Image");

                        }
                        if(cnic_back_url!=null){

                            Picasso.get().load(cnic_back_url).fit().centerCrop()
                                    .placeholder(R.drawable.pictures)
                                    .error(R.drawable.pictures)
                                    .into(imageViewBackCinc);
                            imageViewBackCincTitle.setText("Click to update CNIC back Image");

                        }
                        if(bank_doc_url!=null){

                            Picasso.get().load(bank_doc_url).fit().centerCrop()
                                    .placeholder(R.drawable.pictures)
                                    .error(R.drawable.pictures)
                                    .into(imageViewBankDetails);
                            imageViewBankDetailsTitle.setText("Click to update Bank Details");

                        }



                        providerPhotosList.add(new ProviderPhotos(cnic_front_title,cnic_front_url));
                        providerPhotosList.add(new ProviderPhotos(cnic_back_title,cnic_back_url));
                        providerPhotosList.add(new ProviderPhotos(bank_doc_title,bank_doc_url));


                    }


//                    Toast.makeText(getContext(), ""+providerPhotosList, Toast.LENGTH_SHORT).show();



                } else {

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
