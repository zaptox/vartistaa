package com.vartista.www.vartista.modules.general;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;
import com.valdesekamdem.library.mdtoast.MDToast;
//import com.vartista.www.vartista.Offline_user_status_service;

import com.vartista.www.vartista.Offline_user_status_service;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.adapters.PagerAdapter;

import com.vartista.www.vartista.adapters.ServicesInListMapAdapter;
import com.vartista.www.vartista.adapters.ViewPagerAdapter;
import com.vartista.www.vartista.beans.DeviceToken;
import com.vartista.www.vartista.beans.GetServiceProviders;
import com.vartista.www.vartista.beans.Service;
import com.vartista.www.vartista.beans.User;
import com.vartista.www.vartista.fragments.ServiceProviderFragment;
import com.vartista.www.vartista.fragments.UsersFragment;
import com.vartista.www.vartista.modules.payment.PaymentActivity;
import com.vartista.www.vartista.modules.provider.CreateServiceActivity;
import com.vartista.www.vartista.modules.provider.DocumentUploadActivity;
import com.vartista.www.vartista.modules.provider.MyAppointments;
import com.vartista.www.vartista.modules.provider.MyServiceRequests;
import com.vartista.www.vartista.modules.provider.My_Rating_Reviews;
import com.vartista.www.vartista.modules.provider.ServiceCancelActivity;
import com.vartista.www.vartista.modules.provider.ServicestartProvider;
import com.vartista.www.vartista.modules.user.AssignRatings;
import com.vartista.www.vartista.modules.user.MyServiceMeetings;
import com.vartista.www.vartista.modules.user.Service_user_cancel;
import com.vartista.www.vartista.modules.user.StartService;
import com.vartista.www.vartista.restcalls.ApiClient;
import com.vartista.www.vartista.modules.user.StartService;
import com.vartista.www.vartista.restcalls.ServiceApiInterface;
import com.vartista.www.vartista.restcalls.TokenApiInterface;

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
import java.util.Collection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private TextView email, name;
    User u = null;
    public static int user_id;
    public static User user;
    ImageView imageViewProfileDrawer;
    public static TokenApiInterface tokenApiInterface;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    DrawerLayout drawer;
    DrawerLayout serviceProvider_Drawer;
    Toolbar toolbar;
    public static NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    private int[] tabIcons = {
            R.drawable.ic_tab,
            R.drawable.ic_tab,

    };
    Boolean check = true;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tokenApiInterface = ApiClient.getApiClient().create(TokenApiInterface.class);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        SharedPreferences ob = getSharedPreferences("Login", Context.MODE_PRIVATE);
        int id = ob.getInt("user_id",0);
//        getbusystatus(id);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        name = (TextView) headerView.findViewById(R.id.name);
        email = (TextView) headerView.findViewById(R.id.email);
        imageViewProfileDrawer = (ImageView) headerView.findViewById(R.id.imageViewUserProfileDrawer);
        imageViewProfileDrawer.setImageResource(R.drawable.profile);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        u = user;
        user_id = u.getId();
        //  Toast.makeText(this, ""+user.id, Toast.LENGTH_SHORT).show();
        name.setText(user.getName());
        email.setText(user.getEmail());


        // save or update device token
        storeDeviceToken();
        Toast.makeText(this, ""+user.getImage(), Toast.LENGTH_SHORT).show();
        Picasso.get().load(user.getImage()).fit().centerCrop()
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile)
                .into(imageViewProfileDrawer);

        //device token add to server

        // view pager
        tabLayout = (TabLayout) findViewById(R.id.tabs);


        viewPager = (SlideOffViewPager) findViewById(R.id.viewpager);
        //        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), user_id, getApplicationContext());
        ((SlideOffViewPager) viewPager).setPagingEnabled(false);
          setupViewPager(viewPager);

                    drawer.addDrawerListener(toggle);
                    toggle.syncState();



        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        final ViewPager viewpager = (ViewPager) findViewById(R.id.viewpager);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(),user_id,getApplicationContext());
         viewpager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewpager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
                         public void onTabSelected(TabLayout.Tab tab) {

                if(tab.getPosition()==1) {
                              NavigationDrawerUser(true);
                             if (check == true) {
                                 NavigationDrawer_ServiceProvider(false);
                             check = false;
                             }

                }
                else if (tab.getPosition()==0){
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

        //a service to make user offline

        startOfflineService();


    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
            Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show();
            SharedPreferences ob = getSharedPreferences("Login", Context.MODE_PRIVATE);
            ob.edit().clear().commit();
            startActivity(new Intent(HomeActivity.this, SiginInActivity.class));
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
            // Handle the camera action

//            Toast.makeText(this, "Account", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(HomeActivity.this, UserProfile.class);
            SharedPreferences ob = getSharedPreferences("Login", Context.MODE_PRIVATE);

            user_id = ob.getInt("user_id", 0);

            intent.putExtra("user", u);
            startActivity(intent);


        } else if (id == R.id.request) {
            Intent intent = new Intent(HomeActivity.this, MyServiceRequests.class);
            intent.putExtra("user", user_id);
            startActivity(intent);

        } else if (id == R.id.notification) {
            Intent intent = new Intent(HomeActivity.this, Asynctask_MultipleUrl.class);
            startActivity(intent);

        } else if (id == R.id.appointments) {
            Intent intent = new Intent(HomeActivity.this, MyAppointments.class);
            startActivity(intent);

        } else if (id == R.id.ratings) {
            Intent intent = new Intent(HomeActivity.this, My_Rating_Reviews.class);
            startActivity(intent);

        } else if (id == R.id.logout) {
            Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show();
            SharedPreferences ob = getSharedPreferences("Login", Context.MODE_PRIVATE);
            ob.edit().clear().commit();
            startActivity(new Intent(HomeActivity.this, SiginInActivity.class));

        } else if (id == R.id.payment) {
            Intent intent = new Intent(HomeActivity.this, PaymentActivity.class);
            startActivity(intent);


        } else if (id == R.id.Userappointments) {
            Intent intent = new Intent(HomeActivity.this, MyServiceMeetings.class);
            startActivity(intent);

        } else if (id == R.id.provider_doc_upload) {
            Intent intent = new Intent(HomeActivity.this, DocumentUploadActivity.class);
            startActivity(intent);

        } else if (id == R.id.Userappointments) {
            Intent intent = new Intent(HomeActivity.this, MyServiceMeetings.class);
            startActivity(intent);

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
            //  Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();

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

//        Toast.makeText(this, "in perform function", Toast.LENGTH_SHORT).show();
        Call<User> call = SiginInActivity.apiInterface.getUserById(user_id);
//        Call<User> call = SiginInActivity.apiInterface.performUserLogin();




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

                    Toast.makeText(HomeActivity.this, "The  busy_status  of user "+email+""+response.body().getBusystatus(), Toast.LENGTH_SHORT).show();
                    if(busy_status==1){
                        startActivity(new Intent(HomeActivity.this,ServicestartProvider.class));
                    }
// Toast.makeText(SiginInActivity.this, "Response: " + response.body().getResponse() + "--name:" + name, Toast.LENGTH_SHORT).show();

//                    upload_document(userLoggedIn.getName(),userLoggedIn.getPassword(),userLoggedIn.getContact());
//                    Toast.makeText(SiginInActivity.this, "The User Id is :- "+userLoggedIn.getId()
//                            +"\n"+"The Name is "+userLoggedIn.getName()
//                            +"\n"+"The password is "+userLoggedIn.getPassword(), Toast.LENGTH_SHORT).show();

//                    Toast.makeText(SiginInActivity.this, ""+userLoggedIn, Toast.LENGTH_SHORT).show();
                    //



//
//
                } else if (response.body().getResponse().equals("failed")) {
                    //  Toast.makeText(SiginInActivity.this, "Login Failed.. Please try again", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(SiginInActivity.this, "", Toast.LENGTH_SHORT).show();


                }
//
                else {

                    //  Toast.makeText(SiginInActivity.this, "Response: " + response.body().getResponse(), Toast.LENGTH_SHORT).show();

                }

//                Toast.makeText(SiginInActivity.this, "In response's last line", Toast.LENGTH_SHORT).show();


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




}
