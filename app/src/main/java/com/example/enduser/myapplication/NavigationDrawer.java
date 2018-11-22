package com.example.enduser.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import static android.widget.Toast.*;

public class NavigationDrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Context context;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle mToggle;
    private static Context mCtx;
    private static NavigationDrawer mInstance;
    String p,cn, d, e, userName;

String user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

       //user_name = getIntent().getStringExtra("username");
        //makeText(context, user_name, LENGTH_LONG).show();



        drawer = findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        drawer.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Fragment fragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.fragment_container, fragment);

        ft.commit();



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){

        Fragment fragment = null;
        p = getIntent().getExtras().getString("password");
        userName = getIntent().getExtras().getString("username");
        cn = getIntent().getExtras().getString("contactno");
        e = getIntent().getExtras().getString("email");
        int id= item.getItemId();

        if(id == R.id.Home){
            fragment = new HomeFragment();
          // user_name = getActivity().getIntent().getExtras().getString("username");
        }else if (id == R.id.Updateprofile) {
           // fragment = new Fragment();
            Intent startIntent = new Intent(getApplicationContext(), EditProfile.class);
            startIntent.putExtra("username", userName);
            startActivity(startIntent);
        }else if (id == R.id.Logout) {
            // fragment = new Fragment();
            Intent startIntent = new Intent(getApplicationContext(), Login.class);
            startActivity(startIntent);
        }


        if(fragment !=null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.fragment_container, fragment);

            ft.commit();

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;


    }

    @Override
    public void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{

        }
        super.onBackPressed();
    }

   @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
