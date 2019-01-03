package com.vartista.www.vartista.modules.general;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
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
import com.vartista.www.vartista.beans.DeviceToken;
import com.vartista.www.vartista.beans.GetServiceProviders;
import com.vartista.www.vartista.beans.Service;
import com.vartista.www.vartista.beans.User;
import com.vartista.www.vartista.modules.payment.PaymentActivity;
import com.vartista.www.vartista.modules.provider.CreateServiceActivity;
import com.vartista.www.vartista.modules.provider.DocumentUploadActivity;
import com.vartista.www.vartista.modules.provider.MyAppointments;
import com.vartista.www.vartista.modules.provider.MyServiceRequests;
import com.vartista.www.vartista.modules.provider.My_Rating_Reviews;
import com.vartista.www.vartista.modules.user.AssignRatings;
import com.vartista.www.vartista.modules.user.MyServiceMeetings;
import com.vartista.www.vartista.modules.user.UserNotification_activity;
import com.vartista.www.vartista.restcalls.ApiClient;
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
    public static NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tokenApiInterface = ApiClient.getApiClient().create(TokenApiInterface.class);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        Toast.makeText(getApplicationContext(), FirebaseInstanceId.getInstance().getToken(), Toast.LENGTH_SHORT).show();
//        Log.d("deviceToken", FirebaseInstanceId.getInstance().getToken());
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
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        final ViewPager viewpager = (ViewPager) findViewById(R.id.viewpager);

        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(),user_id,getApplicationContext());
         viewpager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewpager);

        //making user online

        SharedPreferences ob = getSharedPreferences("Login", Context.MODE_PRIVATE);

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
            Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(HomeActivity.this, AppSettings.class));
            return true;
        }
        else if(id==R.id.Assign_ratings){
            startActivity(new Intent(HomeActivity.this, AssignRatings.class));
        }
        else if (id == R.id.logout) {
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
            Toast.makeText(this, "request", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.notification) {
            Intent intent = new Intent(HomeActivity.this, Asynctask_MultipleUrl.class);
            startActivity(intent);
            Toast.makeText(this, "Notification", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.appointments) {
            Intent intent = new Intent(HomeActivity.this, MyAppointments.class);
            startActivity(intent);
            Toast.makeText(this, "appointments", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.ratings) {
            Toast.makeText(this, "raings", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, "appointments", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.Userappointments) {
            Intent intent = new Intent(HomeActivity.this, MyServiceMeetings.class);
            startActivity(intent);
            Toast.makeText(this, "User appointments", Toast.LENGTH_SHORT).show();

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
                    MDToast mdToast = MDToast.makeText(getApplicationContext(), "Token stored", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
                    mdToast.show();
                }
            }

            @Override
            public void onFailure(Call<DeviceToken> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
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
}
