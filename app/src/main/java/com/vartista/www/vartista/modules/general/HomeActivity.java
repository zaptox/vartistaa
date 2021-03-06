package com.vartista.www.vartista.modules.general;

// menu me rating ka rakha hai yaha par user rating ke click krne se rating khulegi or fragment khulega also xml of fragment is new ,



import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;
import com.valdesekamdem.library.mdtoast.MDToast;
import com.vartista.www.vartista.Offline_user_status_service;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.beans.DeviceToken;
import com.vartista.www.vartista.beans.User;
import com.vartista.www.vartista.fragments.ConfigSettingsFragment;
import com.vartista.www.vartista.fragments.NotificationsFragment;
import com.vartista.www.vartista.fragments.ServiceProviderFragment;
import com.vartista.www.vartista.fragments.UsersFragment;
import com.vartista.www.vartista.modules.payment.PaymentActivity;
import com.vartista.www.vartista.modules.provider.ProviderFragments.AddressSetFragment;
import com.vartista.www.vartista.modules.provider.ProviderFragments.CreateServiceFragment;
import com.vartista.www.vartista.modules.provider.ProviderFragments.DocumentUploadFragment;
import com.vartista.www.vartista.modules.provider.ProviderFragments.EarningFragment;
import com.vartista.www.vartista.modules.provider.ProviderFragments.MyAppointmentsFragment;
import com.vartista.www.vartista.modules.provider.ProviderFragments.MyServiceRequestsFragment;
import com.vartista.www.vartista.modules.provider.ProviderFragments.MyServicesListFragment;
import com.vartista.www.vartista.modules.provider.ProviderFragments.My_Rating_Reviews_Fragment;
import com.vartista.www.vartista.modules.provider.ProviderFragments.UploadDocListFragment;
import com.vartista.www.vartista.modules.provider.ServicestartProvider;
import com.vartista.www.vartista.modules.user.GetDocumentActivity;
import com.vartista.www.vartista.modules.user.user_fragments.BookNowFragment;
import com.vartista.www.vartista.modules.user.user_fragments.FindServicesInListFragment;
import com.vartista.www.vartista.modules.user.user_fragments.MyCompletedServicesFragment;
import com.vartista.www.vartista.modules.user.user_fragments.MyServiceMeetingsFragment;
import com.vartista.www.vartista.modules.user.user_fragments.ServiceProviderDetailFragment;
import com.vartista.www.vartista.modules.user.user_fragments.UserDetailFragment;
import com.vartista.www.vartista.modules.user.user_fragments.User_Rating_Review_Fragment;
import com.vartista.www.vartista.restcalls.ApiClient;
import com.vartista.www.vartista.restcalls.TokenApiInterface;
import com.vartista.www.vartista.services.UserStatusService;
import com.vartista.www.vartista.utilities.CONST;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.vartista.www.vartista.utilities.CONST.BOOK_NOW__FRAGMENT;
import static com.vartista.www.vartista.utilities.CONST.FIND_SERVICE_IN_LIST_FRAGMENT;
import static com.vartista.www.vartista.utilities.CONST.SERVICE_PROVIDER_DETAIL_FRAGMENT;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener {
    BottomNavigationView bottomNav;
    private TextView email, name , spRefNumber;
    public static int user_id;
    public static User user;
    ImageView imageViewProfileDrawer;
    public static TokenApiInterface tokenApiInterface;
    public static NavigationView navigationView;

    Boolean check = true;
    Boolean all_closed= false;
    private UserStatusService mService;
    private boolean mBound,book_wala;

    @Override
    protected void onStart() {
        super.onStart();
        // Bind to LocalService
        Intent intent = new Intent(this, UserStatusService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        if(mBound){
            mService.updateUserStatus(user_id);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.loggoo);
        tokenApiInterface = ApiClient.getApiClient().create(TokenApiInterface.class);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        SharedPreferences ob = getSharedPreferences("Login", Context.MODE_PRIVATE);
        int id = ob.getInt("user_id",0);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        NavigationDrawerUser(false);
        if (check == true) {
            NavigationDrawer_ServiceProvider(true);
            check = false;
        }


        bottomNav = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        bottomNav.setVisibility(View.VISIBLE);



        View headerView = navigationView.getHeaderView(0);
        name = (TextView) headerView.findViewById(R.id.name);
        email = (TextView) headerView.findViewById(R.id.email);
        imageViewProfileDrawer = (ImageView) headerView.findViewById(R.id.imageViewUserProfileDrawer);
        imageViewProfileDrawer.setImageResource(R.drawable.profile);

        Intent intent = getIntent();
        user = getUserFromSharePrefs();
        user_id = user.getId();



        if(intent.getIntExtra("fragment_Flag",0)!=0){
            bottomNav.setVisibility(View.GONE);
            FrameLayout frameLayout=(FrameLayout)findViewById(R.id.fragment_frame_layout);
            swipeFragment(intent.getIntExtra("fragment_Flag",0),intent);
        }
        else{
            // by default user fragment open
            bottomNav.setVisibility(View.VISIBLE);

            replaceFragment(new UsersFragment(user_id));

        }

        if(intent.getStringExtra("fragment")!=null){
            switchNotifFragment(intent.getStringExtra("fragment"));
        }


        name.setText(user.getName());
        email.setText(user.getEmail());


        // save or update device token
        storeDeviceToken();

        if(!(user.getImage().equalsIgnoreCase(""))){


            Picasso.get().load(user.getImage()).fit().centerCrop()
                    .placeholder(R.drawable.profile)
                    .error(R.drawable.profile)
                    .into(imageViewProfileDrawer);

        }
        else{
            imageViewProfileDrawer.setImageResource(R.drawable.profile);
        }




        //making user online


        user_id = ob.getInt("user_id", 0);
        new Connection(user_id, 1).execute();
        new ConnectionForSpCode(user_id).execute();


        startOfflineService();

    }

    private void swipeFragment(int fragment_flag,Intent intent) {

        switch (fragment_flag)
        {
            case FIND_SERVICE_IN_LIST_FRAGMENT:
                book_wala=true;

                int catId=intent.getIntExtra("cat_id",0);
                FindServicesInListFragment findServicesInList=new FindServicesInListFragment(catId);
                replaceFragment(findServicesInList);
                break;
            case  SERVICE_PROVIDER_DETAIL_FRAGMENT:
                book_wala=true;

                int providerId=intent.getIntExtra("s_provider_id",0);
                int categoryId=intent.getIntExtra("cat_id",0);
               int  userId=intent.getIntExtra("user_id",0);
                String spName=intent.getStringExtra("spname");
                String spProfileImage=intent.getStringExtra("profile_photo");
                String serviceTitle=intent.getStringExtra("service_title");

                ServiceProviderDetailFragment serviceProviderDetailFragment=
                        new ServiceProviderDetailFragment(providerId,categoryId,userId,spName,
                        serviceTitle,spProfileImage);
                replaceFragment(serviceProviderDetailFragment);
                break;
            case BOOK_NOW__FRAGMENT:
                book_wala=true;
                int   userCustomerId=intent.getIntExtra("user_id",0);
                    int serviceProviderId=intent.getIntExtra("provider_id",0);
                    int  serviceId=intent.getIntExtra("service_id",0);
                    int serviceCatId=intent.getIntExtra("cat_id",0);
                BookNowFragment bookNowFragment=new  BookNowFragment(serviceProviderId,serviceCatId,userCustomerId
                    ,serviceId);
                replaceFragment(bookNowFragment);
                 break;
                 case CONST.MY_SERVICE_REQUEST_FRAGMENT:
                     replaceFragment(new MyServiceRequestsFragment(user_id));
                     getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.serviceProviderActionBar));

                     break;
            case CONST.EARNING_FRAGMENT:
                replaceFragment(new EarningFragment(user_id));
                getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.serviceProviderActionBar));

                break;
            case CONST.MY_SERVICES_LIST_FRAGMENT:
                replaceFragment(new MyServicesListFragment(user_id));
                getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.serviceProviderActionBar));
                break;
                case CONST.CREATE_SERVICE_FRAGMENT:

                    if(intent.getIntExtra("edit_service_id",0)!=0){
                        int editServiceId=intent.getIntExtra("edit_service_id",0);
                        replaceFragment(new CreateServiceFragment(user_id,editServiceId));
                    }else{
                    replaceFragment(new CreateServiceFragment(user_id));
                        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.serviceProviderActionBar));
                    }
                    break;
            case CONST.UPLOAD_DOC_LIST_FRAGMENT:

                replaceFragment(new UploadDocListFragment());
                getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.serviceProviderActionBar));

                break;
                case CONST.ADDRESS_SET_FRAGMENT:
                    replaceFragment(new AddressSetFragment());
                    getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.serviceProviderActionBar));

                    break;
            case CONST.USER_PROFILE_FRAGMENT:
                replaceFragment(new UserDetailFragment(user));
                break;
                case  CONST.NOTIFIATION_FRAGMENT:
                    replaceFragment( new NotificationsFragment());
                    break;

                    case CONST.MY_COMPLETED_SERVIE_FRAGMENT:
                        replaceFragment(new MyCompletedServicesFragment());
                         break;
                         case CONST.MY_APPOINTMENT_FRAGMENT:
                             replaceFragment(new MyAppointmentsFragment());
                             getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.serviceProviderActionBar));

                             break;
                             case CONST.MY_RATINGS_REVIEW_FRAGMENT:
                                 replaceFragment(new My_Rating_Reviews_Fragment());
                                 getSupportActionBar().setBackgroundDrawable(getResources().
                                         getDrawable(R.color.serviceProviderActionBar));

                                 break;
                                case CONST.MY_SERVICE_MEETNG_FRAGMENT:
                                     replaceFragment(new MyServiceMeetingsFragment());

                                     break;
                                     case CONST.DOC_UPLOAD_FRAGMENT:
                                         replaceFragment(new DocumentUploadFragment());
                                         getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.serviceProviderActionBar));

                                         break;
                                         //newcode
                                         case CONST.USER_RATINGS_REVIEW_FRAGMENT:
                                             replaceFragment(new User_Rating_Review_Fragment());
                                             getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.serviceProviderActionBar));
                                             break;
            //newcode
                                             default:
                                             replaceFragment(new UsersFragment(user_id));

        }}





    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.account) {


            Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
            intent.putExtra("fragment_Flag", CONST.USER_PROFILE_FRAGMENT);
            startActivity(intent);


        } else if (id == R.id.request) {


            Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
            intent.putExtra("fragment_Flag", CONST.MY_SERVICE_REQUEST_FRAGMENT);
            startActivity(intent);

        } else if (id == R.id.notification) {
            Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
            intent.putExtra("fragment_Flag", CONST.NOTIFIATION_FRAGMENT);
            startActivity(intent);




        } else if (id == R.id.appointments) {

            Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
            intent.putExtra("fragment_Flag", CONST.MY_APPOINTMENT_FRAGMENT);
            startActivity(intent);




        } else if (id == R.id.ratings) {

            Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
            intent.putExtra("fragment_Flag", CONST.MY_RATINGS_REVIEW_FRAGMENT);
            startActivity(intent);


        } else if (id == R.id.logout) {
            MDToast.makeText(this, "logout", MDToast.LENGTH_SHORT,MDToast.TYPE_INFO).show();
            SharedPreferences ob = getSharedPreferences("Login", Context.MODE_PRIVATE);
            ob.edit().clear().commit();
            startActivity(new Intent(HomeActivity.this, SiginInActivity.class));

        } else if (id == R.id.payment) {

            Intent intent = new Intent(HomeActivity.this, PaymentActivity.class);
            startActivity(intent);

        } else if (id == R.id.Userappointments) {

            Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
            intent.putExtra("fragment_Flag", CONST.MY_SERVICE_MEETNG_FRAGMENT);
            startActivity(intent);




        }
//        newcode
        else if (id == R.id.Userratings) {
            Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
            intent.putExtra("fragment_Flag", CONST.USER_RATINGS_REVIEW_FRAGMENT);
            startActivity(intent);
        }
//newcode
        else if (id == R.id.provider_doc_upload) {

            Intent intent = new Intent(HomeActivity.this, GetDocumentActivity.class);
            startActivity(intent);




        } else if (id == R.id.Userappointments) {
            replaceFragment(new MyServiceMeetingsFragment());



        }
        else if(id==R.id.user_completed_services){


            Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
            intent.putExtra("fragment_Flag", CONST.MY_COMPLETED_SERVIE_FRAGMENT);
            startActivity(intent);


        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);



        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else{
            Intent intent = getIntent();
           if(intent.getIntExtra("fragment_Flag",0)==FIND_SERVICE_IN_LIST_FRAGMENT){
                      finish();
           }else  if(intent.getIntExtra("fragment_Flag",0)==SERVICE_PROVIDER_DETAIL_FRAGMENT){
               finish();
           }
           else  if(intent.getIntExtra("fragment_Flag",0)==BOOK_NOW__FRAGMENT){
               finish();
           }

           else if(intent.getIntExtra("fragment_Flag",0)!=0) {
                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
            else{
                super.onBackPressed();
            }
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(HomeActivity.this, AppSettings.class));
            return true;
        }

          else if (id == R.id.logout){
            MDToast.makeText(this, "logout", MDToast.LENGTH_SHORT,MDToast.TYPE_INFO).show();
            SharedPreferences ob = getSharedPreferences("Login", Context.MODE_PRIVATE);
            ob.edit().clear().commit();
            startActivity(new Intent(HomeActivity.this, SiginInActivity.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void storeDeviceToken() {


        Call<DeviceToken> call = HomeActivity.tokenApiInterface.saveDeviceToken
                (user_id, FirebaseInstanceId.getInstance().getToken());
        call.enqueue(new Callback<DeviceToken>() {
            @Override
            public void onResponse(Call<DeviceToken> call, Response<DeviceToken> response) {

                if (response.isSuccessful()) {
                    //for debugging

                }
            }

            @Override
            public void onFailure(Call<DeviceToken> call, Throwable t) {
                Log.d("errorinstoredevicetoken", t.getMessage());
            }

        });
    }

    private void startOfflineService() {
        Log.d("HomeActivity", "onPauseCalled");
        Intent intent = new Intent(HomeActivity.this, Offline_user_status_service.class);
        intent.putExtra("user_id",user_id);
        startService(intent);
        Log.d("HomeActivity", "serviceStarted");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }

    class Connection extends AsyncTask<String, String, String> {
        private int user_id;
        private int user_status;



        public Connection(int user_id, int user_status) {
            this.user_id = user_id;
            this.user_status = user_status;
        }

        private ProgressDialog dialog;




        @Override
        protected void onPreExecute() {
//            dialog.setMessage("Retriving data Please Wait..");
//            dialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {


            String result = "";
            String BASE_URL = "";

            BASE_URL = "http://www.vartista.com/vartista_app/update_user_online_status.php?user_id="+user_id+"&user_status="+user_status;

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
                } else {
                    MDToast.makeText(HomeActivity.this, "Check Your Internet", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();

            }


        }

    }
    User userLoggedIn = null;

    public void getbusystatus(int user_id) {

        Call<User> call = SiginInActivity.apiInterface.getUserById(user_id);




        call.enqueue(new Callback<User>() {
            @Override

            public void onResponse(Call<User> call, Response<User> response) {

                if (response.body().getResponse().equals("ok")) {
                    int id = response.body().getId();

                    String name = response.body().getName();

                    String email = response.body().getEmail();

                    String password = response.body().getPassword();

                    String image = response.body().getImage();

                    String status = response.body().getStatus();

                    String contact = response.body().getContact();

                    String created_at = response.body().getCreatedAt();

                    String updated_at = response.body().getUpdatedAt();

                    String gender= response.body().getGender();

                    String sp_status= response.body().getSp_status();

                    int busy_status = response.body().getBusystatus();

                    userLoggedIn = new User(id,busy_status,name, email, password, image, status, contact, created_at, updated_at,gender,sp_status);

                    if(busy_status==1){
                        startActivity(new Intent(HomeActivity.this,ServicestartProvider.class));
                    }



//
//
                } else if (response.body().getResponse().equals("failed")) {

                }
//
                else {


                }



            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                //  Toast.makeText(SiginInActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();

            }
        });

    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectFragment = null;
                    switch (menuItem.getItemId()) {
                        case R.id.nav_as_a_user:
                            NavigationDrawerUser(false  );
                            if (check == true) {
                                NavigationDrawer_ServiceProvider(true);
                                check = false;
                            }
                            selectFragment = new UsersFragment(user_id);
                           replaceFragment(selectFragment);
                            getSupportActionBar().setBackgroundDrawable(getResources().getDrawable
                                    (R.color.colorPrimary));

                            break;
                        case R.id.nav_as_a_provider:
                            if(user.getSp_status().equals("0")|| user.getSp_status().equals("-1")){
                                selectFragment = new ConfigSettingsFragment();
                                NavigationDrawerUser(true);
                                if (check==false){
                                    NavigationDrawer_ServiceProvider(false);
                                    check=true;
                                }

                            }else{
                                selectFragment=new ServiceProviderFragment(user_id);
                                NavigationDrawerUser(true);
                                if (check==false){
                                    NavigationDrawer_ServiceProvider(false);
                                    check=true;
                                }
                            }
                            replaceFragment(selectFragment);
                            getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.serviceProviderActionBar));

                            break;


                    }


                    return true;
                }
            };





    private void NavigationDrawerUser(Boolean boo){
        navigationView.getMenu().getItem(3).setVisible(boo);
        navigationView.getMenu().getItem(4).setVisible(boo);
        navigationView.getMenu().getItem(5).setVisible(boo);

    }
    private void NavigationDrawer_ServiceProvider(Boolean boo){
        navigationView.getMenu().getItem(2).setVisible(boo);
    }



    public void switchNotifFragment(String fragment){


        if(fragment.equals("MyServiceMeetingsFragment")){

            replaceFragment(new MyServiceMeetingsFragment());

        }
        else if(fragment.equals("MyServiceRequestsFragment")){

            new MyServiceRequestsFragment(user_id);

        }
        else if(fragment.equals("ServiceProviderFragment")){

            MDToast.makeText(this, "Login Again to Start your journey at Vartista", MDToast.LENGTH_LONG,MDToast.TYPE_INFO).show();

            SharedPreferences ob = getSharedPreferences("Login", Context.MODE_PRIVATE);
            ob.edit().clear().commit();

            startActivity(new Intent(HomeActivity.this, SiginInActivity.class));
            finish();


        }

        else if(fragment.equals("Removed")){

            MDToast.makeText(this, "You are removed as Service Provider but you can still avail the Services.", MDToast.LENGTH_LONG,MDToast.TYPE_INFO).show();

            SharedPreferences ob = getSharedPreferences("Login", Context.MODE_PRIVATE);
            ob.edit().clear().commit();

            startActivity(new Intent(HomeActivity.this, SiginInActivity.class));
            finish();

        }


        else if(fragment.equals("NotificationsFragment")){

            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().add(R.id.fragment_frame_layout, new NotificationsFragment()).addToBackStack("TAG").commit();

        }
        else if(fragment.equals("UploadDocListFragment")){



        }
        else if(fragment.equals("AppointmentDetailsFragment")){

        }
        else if(fragment.equals("MyCompletedServicesFragment")){

            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().add(R.id.fragment_frame_layout, new MyCompletedServicesFragment()).addToBackStack("TAG").commit();

        }


    }


    class ConnectionForSpCode extends AsyncTask<String, String, String> {
        private int user_id;



        public ConnectionForSpCode(int user_id) {
            this.user_id = user_id;
        }




        @Override
        protected void onPreExecute() {

            View headerView = navigationView.getHeaderView(0);
            spRefNumber = headerView.findViewById(R.id.sp_ref_number);
        }

        @Override
        protected String doInBackground(String... strings) {


            String result = "";
            String BASE_URL = "";

            BASE_URL = "http://vartista.com/vartista_app/get_serv_prv_sp_code.php?sp_id="+user_id;

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
                    String refCode = jsonResult.getString("ref_code");
                    if(refCode!=null){
                        spRefNumber.setText("Ref_Code: "+refCode);
                    }

                } else {
                    spRefNumber.setText("---");
                }
            } catch (JSONException e) {
                MDToast.makeText(HomeActivity.this, "No Internet Connectivity!!!", Toast.LENGTH_SHORT).show();
                e.printStackTrace();

            }


        }

    }




    private void replaceFragment(Fragment newFragment) {
        if (newFragment != null && book_wala==true) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame_layout,newFragment).addToBackStack("booknow").commit();
            book_wala=false;
        }
        else if (newFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame_layout,newFragment).commit();
        }
    }



    public  User getUserFromSharePrefs(){
        User user=new User();
        SharedPreferences ob = getSharedPreferences("Login", Context.MODE_PRIVATE);

        user.setId(ob.getInt("user_id",0));
        user.setBusystatus(ob.getInt("busy_status",0));
        user.setContact(ob.getString("contact",""));
        user.setEmail(ob.getString("Email",""));
        user.setPassword(ob.getString("Password",""));
        user.setName(ob.getString("name",""));
        user.setGender(ob.getString("gender",""));
        user.setSp_status(ob.getString("sp_status",""));
        user.setImage(ob.getString("image",""));
        return  user;
    }

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            UserStatusService.MyLocalBinder binder = (UserStatusService.MyLocalBinder) service;

            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };



}
