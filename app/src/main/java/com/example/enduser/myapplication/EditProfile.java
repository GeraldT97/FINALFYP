package com.example.enduser.myapplication;

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

public class EditProfile extends AppCompatActivity {
    EditText Username, Password, Email, ContactNO;
    String userName;
    String p,cn, d, e;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_edit_profile);

        p = getIntent().getExtras().getString("password");
        userName = getIntent().getExtras().getString("username");
        cn = getIntent().getExtras().getString("contactno");
        e = getIntent().getExtras().getString("email");
      //  Toast.makeText(getApplicationContext(), p+userName, Toast.LENGTH_SHORT).show();

        Password = (EditText) findViewById(R.id.Upassword);
        Password.setText(p);
        ContactNO = (EditText) findViewById(R.id.Ucontactno);
        ContactNO.setText(cn);
        Email = (EditText) findViewById(R.id.Uemail);
        Email.setText(e);

        if(!validatePassword(Password.getText().toString())||Password.getText().toString().length()<6){
            Password.setError("Password must be at least 8 characters,one number,one special character and upper case letter ");
            Password.requestFocus();
        }
        if(!validateEmail(Email.getText().toString())) {
            Email.setError("Email must have valid email format.Please try again!");
            Email.requestFocus();
        }
    }

    public void OnUpdate(View view) {

        if(!validatePassword(Password.getText().toString())||Password.getText().toString().length()<8){
            Password.setError("Password must be at least 8 characters,one number,one special character and upper case letter ");
            Password.requestFocus();
        }
        else if(!validateEmail(Email.getText().toString())) {
            Email.setError("Email must have valid email format.Please try again!");
            Email.requestFocus();
        }
       /* else if(!validateName(Username.getText().toString())) {
            Username.setError("PLease enter your name");
            Username.requestFocus();
        }*/
        else if(!validateContact(ContactNO.getText().toString())) {
            ContactNO.setError("PLease enter your contact number");
            ContactNO.requestFocus();
        }

        else {

          //  String username = Username.getText().toString();
            String password = Password.getText().toString();
            String email = Email.getText().toString();
            String contactNO = ContactNO.getText().toString();
            BackgroundUpdate backgroundWorker = new BackgroundUpdate(this);
            backgroundWorker.execute(userName, password, email, contactNO);
        }
    }
    protected boolean validatePassword(String Password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN =  "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{5,10})";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(Password);

        return matcher.matches();


    }


    protected boolean validateEmail(String Email) {
        String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(Email);

        return matcher.matches();
    }

    protected boolean validateName(String Name) {

        if(Name.length()==0){
            return false;

        }else {
            return true;
        }
    }
    protected boolean validateContact(String ContactNo) {

        if( ContactNo.length()!=10){
            return false;

        }else {
            return true;
        }

    }
    protected boolean validateDate(String DateOfBirth) {

        if (DateOfBirth.length() != 10) {
            return false;

        } else {
            return true;
        }

    }


    private class BackgroundUpdate extends AsyncTask<String, Void, String> {
        AlertDialog alertDialog;
        Context context;

        BackgroundUpdate(Context ctx) {
            context = ctx;
        }

        @Override
        protected String doInBackground(String... params) {

            String updateprofile_url = "http://192.168.1.72/updateprofile.php";
                try {
                    userName = params[0];
                String MPassword = params[1];
                String MEmail = params[2];
                String MContactno = params[3];
                URL url = new URL(updateprofile_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(userName, "UTF-8") + "&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(MPassword, "UTF-8") + "&"
                        +URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(MEmail, "UTF-8") + "&"
                        + URLEncoder.encode("contactno", "UTF-8") + "=" + URLEncoder.encode(MContactno, "UTF-8") + "&";

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                ;
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPreExecute() {
            alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Update Profile");
        }

        @Override
        protected void onPostExecute(String Result) {
            if (Result.toString().equals("Your Profile has been successfully updated~")) {



                Intent intent = new Intent (EditProfile.this, NavigationDrawer.class);
               intent.putExtra("username", userName);
                intent.putExtra("password", p);
                intent.putExtra("contactno", cn);
                intent.putExtra("email", e);
                Toast.makeText(getApplicationContext(), "Update Successful", Toast.LENGTH_SHORT).show();
               context.startActivity(intent);
            } else
            {
                alertDialog.setMessage(Result);
                alertDialog.show();
                //      alertDialog.setMessage("PLease enter valid input!!");
            }
        }
    }
}