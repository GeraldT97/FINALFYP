package com.example.enduser.myapplication;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.sql.BatchUpdateException;
import java.util.Scanner;

public class HomeFragment extends Fragment {

    String p, userName, cn, e;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);







        TextView car = (TextView)v.findViewById(R.id.car);
        car.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getActivity().getApplicationContext(), BluetoothConnection.class);
                startActivity(startIntent);
            }

        });

        ImageView carstatus  = (ImageView) v.findViewById(R.id.carstatus);
        carstatus.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getActivity().getApplicationContext(), BluetoothConnection.class);
                startActivity(startIntent);
            }
        });

        ImageView Light  = (ImageView) v.findViewById(R.id.light);
        Light.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getActivity().getApplicationContext(), ConnectionForSwitchPre.class);
                startActivity(startIntent);
            }
        });



        ImageView Profile = (ImageView) v.findViewById(R.id.profile);
        Profile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String user_name = getActivity().getIntent().getStringExtra("username");
                String Password = getActivity().getIntent().getStringExtra("password");
                String Email = getActivity().getIntent().getStringExtra("email");
                String ContactNO = getActivity().getIntent().getStringExtra("contactno");

                Intent startIntent = new Intent(getActivity().getApplicationContext(), ManageProfile.class);
                startIntent.putExtra("username", user_name);
                startIntent.putExtra("password", Password);
                startIntent.putExtra("email", Email);
                startIntent.putExtra("contactno", ContactNO);

                startActivity(startIntent);

            }

        });

        return v;




    }

}


