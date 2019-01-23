package com.vartista.www.vartista;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vartista.www.vartista.firebaseconfig.NotificationHelper;

import java.util.Random;

public class Main2Activity extends AppCompatActivity {
    EditText editTextTitle,editTextBody;
    NotificationHelper notificationHelper;
    NotificationManagerCompat notificationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toast.makeText(getApplicationContext(),getIntent().getExtras().getString("ok"),Toast.LENGTH_LONG).show();


        editTextTitle=(EditText)findViewById(R.id.editTextTitle);
        editTextBody=(EditText)findViewById(R.id.editTextBody);
        Button btn=(Button)findViewById(R.id.buttonSend);
        notificationHelper=new NotificationHelper(this);
        notificationManager= NotificationManagerCompat.from(this);



        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Toast.makeText(getApplicationContext(),"body: "+bundle.get("body")+"  title:  "+bundle.get("title"),Toast.LENGTH_SHORT).show();
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {

                    Notification.Builder builder=notificationHelper.getChannelNotiifcation(editTextTitle.getText().toString(),editTextBody.getText().toString());
                    notificationHelper.getManager().notify(new Random().nextInt(),builder.build());
                }
                else {
                    Intent resultIntent = new Intent(getApplicationContext() , Main2Activity.class);
                    resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    PendingIntent resultPendingIntent = PendingIntent.getActivity(getApplicationContext(),
                            0 /* Request code */, resultIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT);

                    Notification notification = new NotificationCompat.Builder(getApplicationContext(), NotificationHelper.CHANNEL_ID)
                            .setSmallIcon(R.drawable.ic_launcher_background)
                            .setContentText(editTextBody.getText().toString())
                            .setContentTitle(editTextTitle.getText().toString())
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                            .setContentIntent(resultPendingIntent)
                            .build();

                    notificationManager.notify(1, notification);

                }
            }
        });



    }
}
