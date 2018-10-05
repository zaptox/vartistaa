package com.vartista.www.vartista.modules.general;

import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.vartista.www.vartista.R;
import com.vartista.www.vartista.adapters.PagerAdapter;

import com.vartista.www.vartista.beans.User;
import com.vartista.www.vartista.modules.provider.MyAppointments;
import com.vartista.www.vartista.modules.provider.MyServiceRequests;
import com.vartista.www.vartista.modules.user.UserNotification_activity;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private TextView email,name;
    User u=null;
    public static int user_id;
    public static User user;
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

        Intent intent= getIntent();
        user= (User) intent.getSerializableExtra("user");
    u=user;
        user_id=u.getId();
      //  Toast.makeText(this, ""+user.id, Toast.LENGTH_SHORT).show();
        name.setText(user.getName());
        email.setText(user.getEmail());


        // view pager
        TabLayout tabLayout=(TabLayout)findViewById(R.id.tabs);
        ViewPager viewpager =(ViewPager)findViewById(R.id.viewpager);

        PagerAdapter adapter=new PagerAdapter(getSupportFragmentManager(),user_id);
        viewpager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewpager);


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
            intent.putExtra("user", user_id);
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

        } else if (id == R.id.ratings) {
            Toast.makeText(this, "raings", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.logout) {
            Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
