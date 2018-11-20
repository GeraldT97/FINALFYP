package com.example.enduser.myapplication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.enduser.myapplication.R.layout.activity_edit_profile;

public class ViewProfile extends AppCompatActivity {
    TextView Username, Password, Email, ContactNO;
    String message;
    String p,cn, d, e;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);


        Intent intent = getIntent();
        String message = intent.getStringExtra("username");
       /*cn = getIntent().getExtras().getString("contactno");
        p = getIntent().getExtras().getString("password");
        e = getIntent().getExtras().getString("email");*/
         // Toast.makeText(getApplicationContext(), p+message, Toast.LENGTH_SHORT).show();

        Password = (TextView) findViewById(R.id.Vpassword);
        Password.setText(p);
        ContactNO = (TextView) findViewById(R.id.Vcontactno);
        ContactNO.setText(cn);
        Email = (TextView) findViewById(R.id.Vemail);
        Email.setText(e);
        Username = (TextView) findViewById(R.id.Vname);
        Username.setText(message);




    }
}