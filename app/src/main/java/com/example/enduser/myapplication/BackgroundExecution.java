package com.example.enduser.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

public class BackgroundExecution extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alertDialog;
    private EditText Username, Password, Confirmpass;
    static String user_name;
    String password;

    BackgroundExecution(Context ctx) {
        context = ctx;
    }
    protected String doInBackground(String... params) {
        String type = params[0];
        user_name=params[1];
        String login_url = "http://192.168.1.64/login.php";
        String register_url = "http://192.168.1.64/register.php";
        if (type.equals("login")) {
            try {
               String user_name = params[1];
                password = params[2];
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
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
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (type.equals("register")) {
            try {
                String username = params[1];
                String password = params[2];
                String email = params[3];
                String contactno = params[4];
                String confirmpass = params[5];
                URL url = new URL(register_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") + "&"
                        +URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&"
                        + URLEncoder.encode("confirmpass", "UTF-8") + "=" + URLEncoder.encode(confirmpass, "UTF-8") + "&"
                        + URLEncoder.encode("contactno", "UTF-8") + "=" + URLEncoder.encode(contactno, "UTF-8") + "&";
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
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");
    }

    @Override
    protected void onPostExecute(String result) {

        if (result.toString().equals("Your data has been inserted successfully")) {
            Intent intent = new Intent(context, Login.class);
            context.startActivity(intent);
             DisplayToastSuccess();
        } else if (result.toString().equals("login success !!!!! Welcome user")) {
            //alertDialog.setMessage(result);
            //alertDialog.show();
            DisplayToastwelcome();
            Intent startIntent = new Intent(context, NavigationDrawer.class);
            startIntent.putExtra("username", user_name);
            context.startActivity(startIntent);
        } else {
           // alertDialog.setMessage(result);
            //alertDialog.show();
             DisplayToastFail();
        }
    }

    private void DisplayToastSuccess() {
        Toast.makeText(context, R.string.success, Toast.LENGTH_SHORT).show();

    }

    private void DisplayToastFail() {
        Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show();

    }

    private void DisplayToastwelcome() {
        Toast.makeText(context, R.string.welcome, Toast.LENGTH_SHORT).show();

    }
}


