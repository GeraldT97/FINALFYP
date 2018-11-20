package com.example.enduser.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class ViewFingerprint extends AppCompatActivity {

    private EditText Finger1, Finger2, Finger3;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_fingerprint);
        Finger1 = findViewById(R.id.finger1);
        Finger2 = findViewById(R.id.finger2);
        Finger3 = findViewById(R.id.finger3);
    }


    public void OnLogin(View view) {
        String fingeR1 = Finger1.getText().toString();
        String fingeR2 = Finger2.getText().toString();
        String fingeR3 = Finger3.getText().toString();
        String type = "login";
        BackgroundExecution backgroundExecution = new BackgroundExecution(this);
        backgroundExecution.execute(type, fingeR1, fingeR2, fingeR3);

    }
}
