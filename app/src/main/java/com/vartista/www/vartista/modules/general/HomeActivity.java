package com.vartista.www.vartista.modules.general;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;
import com.valdesekamdem.library.mdtoast.MDToast;
import com.vartista.www.vartista.Offline_user_status_service;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.adapters.PagerAdapter;

import com.vartista.www.vartista.adapters.ServicesInListMapAdapter;
import com.vartista.www.vartista.adapters.ViewPagerAdapter;
import com.vartista.www.vartista.beans.DeviceToken;
import com.vartista.www.vartista.beans.GetServiceProviders;
import com.vartista.www.vartista.beans.Service;
import com.vartista.www.vartista.beans.User;
import com.vartista.www.vartista.fragments.NotificationsFragment;
import com.vartista.www.vartista.fragments.ServiceProviderFragment;
import com.vartista.www.vartista.fragments.UserProfileFragment;
import com.vartista.www.vartista.fragments.UsersFragment;
import com.vartista.www.vartista.modules.payment.PaymentActivity;
import com.vartista.www.vartista.modules.provider.DocumentUploadActivity;
import com.vartista.www.vartista.modules.provider.ProviderFragments.AddressSetFragment;
import com.vartista.www.vartista.modules.provider.ProviderFragments.CreateServiceFragment;
import com.vartista.www.vartista.modules.provider.ProviderFragments.EarningFragment;
import com.vartista.www.vartista.modules.provider.ProviderFragments.MyAppointmentsFragment;
import com.vartista.www.vartista.modules.provider.ProviderFragments.MyServiceRequestsFragment;
import com.vartista.www.vartista.modules.provider.ProviderFragments.MyServicesListFragment;
import com.vartista.www.vartista.modules.provider.ProviderFragments.My_Rating_Reviews_Fragment;
import com.vartista.www.vartista.modules.provider.ProviderFragments.UploadDocListFragment;
import com.vartista.www.vartista.modules.provider.ServiceCancelActivity;
import com.vartista.www.vartista.modules.provider.ServicestartProvider;
import com.vartista.www.vartista.modules.user.AssignRatings;
import com.vartista.www.vartista.modules.user.FindServicesInList;
import com.vartista.www.vartista.modules.user.Service_user_cancel;
import com.vartista.www.vartista.modules.user.StartService;
import com.vartista.www.vartista.modules.user.user_fragments.BookNowFragment;
import com.vartista.www.vartista.modules.user.user_fragments.FindServicesInListFragment;
import com.vartista.www.vartista.modules.user.user_fragments.MyCompletedServicesFragment;
import com.vartista.www.vartista.modules.user.user_fragments.MyServiceMeetingsFragment;
import com.vartista.www.vartista.modules.user.user_fragments.ServiceProviderDetailFragment;
import com.vartista.www.vartista.restcalls.ApiClient;
import com.vartista.www.vartista.restcalls.TokenApiInterface;
import com.vartista.www.vartista.util.CONST;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener {

    private TextView email, name;
    public static int user_id;
    public static User user;
    ImageView imageViewProfileDrawer;
    public static TokenApiInterface tokenApiInterface;
    private ViewPager viewPager;
    TabLayout tabLayout=null;
    public static NavigationView navigationView;
    private int[] tabIcons = {
            R.drawable.ic_asauser_24dp,
            R.drawable.myservices};
    Boolean check = true;

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
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        name = (TextView) headerView.findViewById(R.id.name);
        email = (TextView) headerView.findViewById(R.id.email);
        imageViewProfileDrawer = (ImageView) headerView.findViewById(R.id.imageViewUserProfileDrawer);
        imageViewProfileDrawer.setImageResource(R.drawable.profile);

        Intent intent = getIntent();
        user = getUserFromSharePrefs();
        user_id = user.getId();

        if(intent.getIntExtra("fragment_Flag",0)!=0){
            swipeFragment(intent.getIntExtra("fragment_Flag",0),intent);
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

        viewPager = (SlideOffViewPager) findViewById(R.id.viewpager);
        ((SlideOffViewPager) viewPager).setPagingEnabled(false);
          setupViewPager(viewPager);

                    drawer.addDrawerListener(toggle);
                    toggle.syncState();



        final ViewPager viewpager = (ViewPager) findViewById(R.id.viewpager);


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(),user_id,getApplicationContext());
         viewpager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewpager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
                         public void onTabSelected(TabLayout.Tab tab) {

                if(tab.getPosition()==1) {
                    tabLayout.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.color.serviceProviderActionBar));

                    getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.serviceProviderActionBar));

                    NavigationDrawerUser(true);
                             if (check == true) {
                                 NavigationDrawer_ServiceProvider(false);
                             check = false;
                             }

                }
                else if (tab.getPosition()==0){
                    getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.colorPrimaryDark));

                    tabLayout.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.color.colorPrimaryDark));

                    NavigationDrawerUser(false);
                     if (check==false){
                         NavigationDrawer_ServiceProvider(true);
                         check=true;
                     }


                }
            }


            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });




        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        //making user online


        user_id = ob.getInt("user_id", 0);
        new Connection(user_id, 1).execute();


        startOfflineService();

    }

    private void swipeFragment(int fragment_flag,Intent intent) {

        switch (fragment_flag)
        {
            case CONST.FIND_SERVICE_IN_LIST_FRAGMENT:


                tabLayout.setVisibility(View.GONE);

                int catId=intent.getIntExtra("cat_id",0);
                FindServicesInListFragment findServicesInList=new FindServicesInListFragment(catId,tabLayout);
                replaceFragment(findServicesInList);
                break;
            case  CONST.SERVICE_PROVIDER_DETAIL_FRAGMENT:

                tabLayout= (TabLayout) findViewById(R.id.tabs);
                tabLayout.setVisibility(View.GONE);
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
            case CONST.BOOK_NOW__FRAGMENT:
                tabLayout.setVisibility(View.GONE);
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
                     break;
            case CONST.EARNING_FRAGMENT:
                replaceFragment(new EarningFragment(user_id));
                break;
            case CONST.MY_SERVICES_LIST_FRAGMENT:
                replaceFragment(new MyServicesListFragment(user_id));
                break;
                case CONST.CREATE_SERVICE_FRAGMENT:

                    if(intent.getIntExtra("edit_service_id",0)!=0){
                        int editServiceId=intent.getIntExtra("edit_service_id",0);
                        replaceFragment(new CreateServiceFragment(user_id,tabLayout,editServiceId));
                    }else{
                    replaceFragment(new CreateServiceFragment(user_id,tabLayout));}
                    break;
            case CONST.UPLOAD_DOC_LIST_FRAGMENT:
                replaceFragment(new UploadDocListFragment());
                break;
                case CONST.ADDRESS_SET_FRAGMENT:
                    replaceFragment(new AddressSetFragment());
                    break;
        }}




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);



        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else{
                            super.onBackPressed();

        }}

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
        else if(id==R.id.Assign_ratings){
            startActivity(new Intent(HomeActivity.this, AssignRatings.class));
        }

        else if(id==R.id.Start_Service){
            startActivity(new Intent(HomeActivity.this, StartService.class));
        }
//        else if(id==R.id.Start_Service_Provider){
//            startActivity(new Intent(HomeActivity.this, ServicestartProvider.class));
//        }

        else if(id==R.id.user_cancelservice){
            startActivity(new Intent(HomeActivity.this, Service_user_cancel.class));
        }

        else if(id==R.id.cancel_service){
            startActivity(new Intent(HomeActivity.this, ServiceCancelActivity.class));
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.account) {
            replaceFragment(new UserProfileFragment(user));



        } else if (id == R.id.request) {

             replaceFragment(new MyServiceRequestsFragment(user_id));

        } else if (id == R.id.notification) {

            replaceFragment( new NotificationsFragment());



        } else if (id == R.id.appointments) {

             replaceFragment(new MyAppointmentsFragment());

        } else if (id == R.id.ratings) {

            replaceFragment(new My_Rating_Reviews_Fragment());


        } else if (id == R.id.logout) {
            MDToast.makeText(this, "logout", MDToast.LENGTH_SHORT,MDToast.TYPE_INFO).show();
            SharedPreferences ob = getSharedPreferences("Login", Context.MODE_PRIVATE);
            ob.edit().clear().commit();
            startActivity(new Intent(HomeActivity.this, SiginInActivity.class));

        } else if (id == R.id.payment) {
            Intent intent = new Intent(HomeActivity.this, PaymentActivity.class);
            startActivity(intent);

        } else if (id == R.id.Userappointments) {


             replaceFragment(new MyServiceMeetingsFragment());



        } else if (id == R.id.provider_doc_upload) {

            Intent intent = new Intent(HomeActivity.this, DocumentUploadActivity.class);
            startActivity(intent);



        } else if (id == R.id.Userappointments) {
            replaceFragment(new MyServiceMeetingsFragment());



        }
        else if(id==R.id.user_completed_services){

             replaceFragment(new MyCompletedServicesFragment());

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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







    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new UsersFragment(), "As a User");
        adapter.addFrag(new ServiceProviderFragment(), "As a Service Provider");
        viewPager.setAdapter(adapter);
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);



    }

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




    private void replaceFragment(Fragment newFragment) {
        if (newFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame_layout,newFragment).commit();
        }
    }



    public User getUserFromSharePrefs(){
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

}
