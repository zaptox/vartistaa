package com.vartista.www.vartista.modules.user;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.vartista.www.vartista.R;
import com.vartista.www.vartista.adapters.UserNotificationlistadapter;
import com.vartista.www.vartista.beans.usernotificationitems;

import java.util.ArrayList;
import java.util.List;

public class UserNotification_activity extends AppCompatActivity {

    private RecyclerView.LayoutManager layoutManager;
    private List<usernotificationitems> list;
    private UserNotificationlistadapter listadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_notification_activity);

        RecyclerView view = (RecyclerView)findViewById(R.id.user_notificationlist);
        list = new ArrayList<>();
        listadapter = new UserNotificationlistadapter(this,list);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        view.setAdapter(listadapter);
        view.setHasFixedSize(true);
        view.setLayoutManager(layoutManager);
        Toast.makeText(this, "Adapter set on its position", Toast.LENGTH_SHORT).show();



    }
}
