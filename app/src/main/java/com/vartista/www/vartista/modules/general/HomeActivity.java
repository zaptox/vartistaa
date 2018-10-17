package com.vartista.www.vartista.modules.general;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;
import com.vartista.www.vartista.R;
import com.vartista.www.vartista.adapters.PagerAdapter;

import com.vartista.www.vartista.beans.User;
import com.vartista.www.vartista.firebaseconfig.FirebaseMessagingService;
import com.vartista.www.vartista.modules.payment.PaymentActivity;
import com.vartista.www.vartista.modules.provider.DocumentUploadActivity;
import com.vartista.www.vartista.modules.provider.MyAppointments;
import com.vartista.www.vartista.modules.provider.MyServiceRequests;
import com.vartista.www.vartista.modules.provider.My_Rating_Reviews;
import com.vartista.www.vartista.modules.user.MyServiceMeetings;
import com.vartista.www.vartista.modules.user.UserNotification_activity;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private TextView email,name;
    User u=null;
    public static int user_id;
    public static User user;
    ImageView imageViewProfileDrawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        name = (TextView) headerView.findViewById(R.id.name);
        email = (TextView) headerView.findViewById(R.id.email);
        imageViewProfileDrawer=(ImageView)headerView.findViewById(R.id.imageViewUserProfileDrawer);
        imageViewProfileDrawer.setImageResource(R.drawable.profile);

        Intent intent= getIntent();
        user= (User) intent.getSerializableExtra("user");
        u=user;
        user_id=u.getId();
      //  Toast.makeText(this, ""+user.id, Toast.LENGTH_SHORT).show();
        name.setText(user.getName());
        email.setText(user.getEmail());

        Picasso.get().load(user.getImage()).fit().centerCrop()
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile)
                .into(imageViewProfileDrawer);


        // view pager
        TabLayout tabLayout=(TabLayout)findViewById(R.id.tabs);
        ViewPager viewpager =(ViewPager)findViewById(R.id.viewpager);

        PagerAdapter adapter=new PagerAdapter(getSupportFragmentManager(),user_id);
        viewpager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewpager);

//
//        FirebaseMessaging.getInstance().subscribeToTopic("Test");
//        FirebaseInstanceId.getInstance().getToken();
//

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
            startActivity(new Intent(HomeActivity.this,SettingsActivity.class));
            return true;
        }

        else if (id == R.id.logout) {
            Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show();
            SharedPreferences ob =getSharedPreferences("Login", Context.MODE_PRIVATE);
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
            SharedPreferences ob =getSharedPreferences("Login", Context.MODE_PRIVATE);

            user_id=ob.getInt("user_id",0);

            intent.putExtra("user",u );
            startActivity(intent);


        } else if (id == R.id.request) {
            Intent intent = new Intent(HomeActivity.this, MyServiceRequests.class);
            intent.putExtra("user", user_id);
            startActivity(intent);
            Toast.makeText(this, "request", Toast.LENGTH_SHORT).show();

        }
        else if (id == R.id.notification) {
            Intent intent = new Intent(HomeActivity.this, UserNotification_activity.class);
            startActivity(intent);
            Toast.makeText(this, "Notification", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.appointments) {
            Intent intent = new Intent(HomeActivity.this, MyAppointments.class);
            startActivity(intent);
            Toast.makeText(this, "appointments", Toast.LENGTH_SHORT).show();

        }
        else if (id == R.id.ratings) {
            Toast.makeText(this, "raings", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(HomeActivity.this, My_Rating_Reviews.class);
            startActivity(intent);

        } else if (id == R.id.logout) {
            Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show();
            SharedPreferences ob =getSharedPreferences("Login", Context.MODE_PRIVATE);
            ob.edit().clear().commit();
            startActivity(new Intent(HomeActivity.this, SiginInActivity.class));

        }
        else if (id == R.id.payment) {
            Intent intent = new Intent(HomeActivity.this, PaymentActivity.class);
            startActivity(intent);
            Toast.makeText(this, "appointments", Toast.LENGTH_SHORT).show();

        }
        else if (id == R.id.Userappointments) {
            Intent intent = new Intent(HomeActivity.this, MyServiceMeetings.class);
            startActivity(intent);
            Toast.makeText(this, "User appointments", Toast.LENGTH_SHORT).show();

        }

        else if (id == R.id.provider_doc_upload) {
            Intent intent = new Intent(HomeActivity.this, DocumentUploadActivity.class);
            startActivity(intent);

        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
