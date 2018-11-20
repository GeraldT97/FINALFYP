package com.example.enduser.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class ManageProfile extends AppCompatActivity {

    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_profile);

        userName = getIntent().getStringExtra("username");
        String Password = getIntent().getStringExtra("password");
        String ContactNO = getIntent().getStringExtra("contactno");
        String Email = getIntent().getStringExtra("email");
        Toast.makeText(getApplicationContext(), "Hi " + userName, Toast.LENGTH_SHORT).show();




       /* ImageView Update = (ImageView) findViewById(R.id.update);
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), EditProfile.class);
                startActivity(startIntent);
            }
        });*/

       /* ImageView View = (ImageView) findViewById(R.id.view);
        View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), ViewProfile.class);
                startActivity(startIntent);
            }
        });*/


    }
    public void JumpToViewProfile(View view) {

        BackgroundViewProfile backgroundworker = new BackgroundViewProfile(this);
        backgroundworker.execute(userName);
        Toast.makeText(getApplicationContext(), userName, Toast.LENGTH_SHORT).show();

    }

   public void JumpToUpdateProfile(View view) {
        BackgroundSelectProfile backgroundWorker = new BackgroundSelectProfile(this);
        backgroundWorker.execute( userName);

    }

}
