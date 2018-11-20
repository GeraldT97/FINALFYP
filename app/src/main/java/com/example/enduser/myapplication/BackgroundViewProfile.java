package com.example.enduser.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.EditText;
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

public class BackgroundViewProfile  extends AsyncTask<String,Void,String> {
    String p,cn,d,e;
    Context context;
    String user_name;
    BackgroundViewProfile(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {

        String selectprofile_url = "http://192.168.1.64/selectprofile.php";

        user_name = params[0];

        try {
            URL url = new URL(selectprofile_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&";

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
    protected void onPostExecute (String Result){
        try {

            JSONObject c = new JSONObject(Result);


            for (int i = 0; i < c.length(); i++) {
                p = c.getString("password");
                cn = c.getString("contactno");
                e = c.getString("email");

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(context, ViewProfile.class);
        intent.putExtra("password", p);
        intent.putExtra("username",user_name);
        intent.putExtra("contactno", cn);
        intent.putExtra("email", e);
        context.startActivity(intent);
       // Toast.makeText(context, p+user_name, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onProgressUpdate (Void...values){
        super.onProgressUpdate(values);
    }
}



