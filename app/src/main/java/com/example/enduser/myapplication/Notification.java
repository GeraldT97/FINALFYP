package com.example.enduser.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


//import static com.example.marcus.fyp.notification.CHANNEL_ID;

public class Notification extends AppCompatActivity {

    private final String CHANNEL_ID = "personal_notifications";
    private final int NOTIFICATION_ID = 001;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);


            displayNotification();


    }



    public void displayNotification(){
        createNotificationChannel();
        Toast.makeText(Notification.this, "Walao eh", Toast.LENGTH_SHORT).show();

        Intent landingIntent = new Intent(this, HomePage3.class);
        landingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent landingPendingIntent = PendingIntent.getActivity(this,0,landingIntent,PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.gas);
        builder.setContentTitle("Teck Kian very handsome");
        builder.setContentText("Teck Kian is really handsome now");
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setContentIntent(landingPendingIntent);
        builder.setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat= NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());

    }

    private  void createNotificationChannel()
    {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            CharSequence name= "Personal Notifications";
            String description = "Include all the personal notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,name,importance);

            notificationChannel.setDescription(description);

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}
