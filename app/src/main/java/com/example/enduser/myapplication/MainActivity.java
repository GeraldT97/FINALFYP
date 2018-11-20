package com.example.enduser.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText Username, Password, Email, ContactNO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Username = (EditText)findViewById(R.id.username);
        Email = (EditText)findViewById(R.id.email);
        Password = (EditText)findViewById(R.id.password);
        ContactNO = (EditText)findViewById(R.id.contactNo);

       /* Button confirmBtn = (Button) findViewById(R.id.confirmBtn);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), Login.class);
                startActivity(startIntent);

            }
        });*/
    }

    public void OnReg(View view){
        String username = Username.getText().toString();
        String email = Email.getText().toString();
        String password = Password.getText().toString();
        String contactNO = ContactNO.getText().toString();
        String type = "register";
        BackgroundExecution backgroundWorker = new BackgroundExecution(this);
        backgroundWorker.execute(type, username, email, password, contactNO);


    }
}
