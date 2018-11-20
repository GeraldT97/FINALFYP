package com.example.enduser.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

public class Register extends AppCompatActivity {
    EditText Username, Password, Email, ContactNO, ConfirmPass;
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        Username = (EditText)findViewById(R.id.username);
        Email = (EditText)findViewById(R.id.email);
        Password = (EditText)findViewById(R.id.password);
        ConfirmPass = (EditText)findViewById(R.id.confirmpass);
        ContactNO = (EditText)findViewById(R.id.contactNo);


        String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[\\S+$]).{6,}";
        awesomeValidation.addValidation(Register.this,R.id.username,"[a-zA-Z\\s]+", R.string.username1);
        awesomeValidation.addValidation(Register.this,R.id.password, regexPassword, R.string.password1);
        awesomeValidation.addValidation(Register.this,R.id.confirmpass, R.id.password, R.string.confrimpass1);
        awesomeValidation.addValidation(Register.this,R.id.email,android.util.Patterns.EMAIL_ADDRESS, R.string.email1);
        awesomeValidation.addValidation(Register.this,R.id.contactNo,RegexTemplate.TELEPHONE, R.string.contactNo1);


       /* Button confirmBtn = (Button) findViewById(R.id.confirmBtn);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), Login.class);
                startActivity(startIntent);

            }
        });*/
    }

   @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public void OnReg(View view){
       if(awesomeValidation.validate()){


        String username = Username.getText().toString();
        String password = Password.getText().toString();
           String email = Email.getText().toString();
        String confrimpass = ConfirmPass.getText().toString();
        String contactNO = ContactNO.getText().toString();
        String type = "register";
        BackgroundExecution backgroundWorker = new BackgroundExecution(this);
        backgroundWorker.execute(type, username, password, email, contactNO, confrimpass);
       }
       else{
           Toast.makeText(Register.this, "Error", Toast.LENGTH_SHORT).show();
       }

    }
}
