package com.example.enduser.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

public class Login extends AppCompatActivity {

    private EditText Username, Password;

    //ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.layout1);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Username = findViewById(R.id.username);
        Password = findViewById(R.id.password);





       /* Button confirmBtn = (Button) findViewById(R.id.confirmBtn);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), HomePage3.class);
                startIntent.putExtra("name", Username.getText().toString());
                startActivity(startIntent);
            }
        });*/

        Button registerBtn = (Button) findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), Register.class);
                startActivity(startIntent);

            }
        });


       /* Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), Login.class);
                startActivity(startIntent);


            }*/
    }

    public void OnLogin(View view) {
        String userName = Username.getText().toString();
        String passWord = Password.getText().toString();
        String type = "login";
        BackgroundExecution backgroundExecution = new BackgroundExecution(this);
        backgroundExecution.execute(type, userName, passWord);

   /*         constraintLayout.setVisibility(View.GONE);
    Fragment fragment = new HomeFragment();
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.container, fragment);
            transaction.commit();*/

    }
}









