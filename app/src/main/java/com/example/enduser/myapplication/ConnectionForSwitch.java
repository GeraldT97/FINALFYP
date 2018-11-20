package com.example.enduser.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;


public class ConnectionForSwitch extends Activity {
    // Button btnOn, btnOff;
    TextView txtArduino, txtString, txtStringLength, temperature, oilevel, pressure, btnOn, btnOff;
    Handler bluetoothIn,bluetoothIn2;
    Context context;

    final int handlerState = 0;                        //used to identify handler message
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder recDataString = new StringBuilder();

    private ConnectedThread mConnectedThread2;

    // SPP UUID service - this should work for most devices
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // String for MAC address
    private static String address;


    @SuppressLint("HandlerLeak")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_switch);

        //Link the buttons and textViews to respective views
         btnOn = (Button) findViewById(R.id.buttonOn);
         btnOff = (Button) findViewById(R.id.buttonOff);
       /**txtString = (TextView) findViewById(R.id.txtString);
        txtStringLength = (TextView) findViewById(R.id.testView1);
        temperature = (TextView) findViewById(R.id.sensorView0);
        oilevel = (TextView) findViewById(R.id.sensorView1);
        pressure = (TextView) findViewById(R.id.sensorView2);**/


       /* bluetoothIn2 = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == handlerState) {                                                 //if message is what we want
                    String readMessage = (String) msg.obj;                                                                // msg.arg1 = bytes from connect thread
                    recDataString.append(readMessage);                                              //keep appending to string until ~
                    int endOfLineIndex = recDataString.indexOf("~");                            // determine the end-of-line
                    if (endOfLineIndex > 0) {                                                   // make sure there data before ~
                        String dataInPrint = recDataString.substring(0, endOfLineIndex);         // extract string
                        // txtString.setText("Data Received = " + dataInPrint);
                        //int dataLength = dataInPrint.length();                                //get length of data received
                        // txtStringLength.setText("String Length = " + String.valueOf(dataLength));

                        if (recDataString.charAt(0) == '#')                                  //if it starts with # we know it is what we are looking for
                        {
                            String sensor0 = recDataString.substring(1,5 );                  //get sensor value from string between indices 1-5
                            String sensor1 = recDataString.substring(7,11);                 //same again...
                            String sensor2 = recDataString.substring(12, 18);
                            // String sensor3 = recDataString.substring(16, 20);

                            temperature.setText( "Temperature : " + sensor0 + "*C" );           //update the textviews with sensor values
                            oilevel.setText( "Oil Level : " +sensor1  );
                            pressure.setText("Tyre pressure :"  +sensor2 );

                            Intent intent = new Intent(context,MyService.class);
                            intent.putExtra("sensor1",sensor1);
                            context.startActivity(intent);





                        }
                        recDataString.delete(0, recDataString.length());                    //clear all string data
                        // strIncom =" ";
                        dataInPrint = " ";
                    }


                }
            }
        };*/



        btAdapter = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter
        checkBTState();

        // Set up onClick listeners for buttons to send 1 or 0 to turn on/off LED
       btnOff.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mConnectedThread2.write("t");    // Send "0" via Bluetooth
                Toast.makeText(getBaseContext(), "Headlights ON", Toast.LENGTH_SHORT).show();
            }
        });

        btnOn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mConnectedThread2.write("h");    // Send "1" via Bluetooth
                Toast.makeText(getBaseContext(), "Headlights OFF", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {

        return  device.createRfcommSocketToServiceRecord(BTMODULEUUID);
        //creates secure outgoing connecetion with BT device using UUID
    }

    @Override
    public void onResume() {
        super.onResume();

        Intent intent = getIntent();                                                                      //Get MAC address from DeviceListActivity via intent
        address = intent.getStringExtra(BluetoothConnection.EXTRA_DEVICE_ADDRESS);                        //Get the MAC address from the DeviceListActivty via EXTRA
        BluetoothDevice device = btAdapter.getRemoteDevice(address);                                        //create device and set the MAC address

        try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_LONG).show();
        }
        // Establish the Bluetooth socket connection.
        try
        {
            btSocket.connect();
        } catch (IOException e) {
            try
            {
                btSocket.close();
            } catch (IOException e2)
            {
                //insert code to deal with this
            }
        }
        mConnectedThread2 = new ConnectedThread(btSocket);
        mConnectedThread2.start();

        //I send a character when resuming.beginning transmission to check device is connected
        //If it is not an exception will be thrown in the write method and finish() will be called
        mConnectedThread2.write("x");
    }

    @Override
    public void onPause()
    {
        super.onPause();
        try
        {
            //Don't leave Bluetooth sockets open when leaving activity
            btSocket.close();
        } catch (IOException e2) {
            //insert code to deal with this
        }
    }

    //Checks that the Android device Bluetooth is available and prompts to be turned on if off
    private void checkBTState() {

        if(btAdapter==null) {
            Toast.makeText(getBaseContext(), "Device does not support bluetooth", Toast.LENGTH_LONG).show();
        } else {
            if (btAdapter.isEnabled()) {
            } else {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }


    //create new class for connect thread
    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        //creation of the connect thread
        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                //Create I/O streams for connection
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }



        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;

            // Keep looping to listen for received messages
            while (true) {
                try {
                    bytes = mmInStream.read(buffer);            //read bytes from input buffer
                    String readMessage = new String(buffer, 0, bytes);
                    // Send the obtained bytes to the UI Activity via handler
                    // bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                    bluetoothIn2.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }

        //write method
        public void write(String input) {
            byte[] msgBuffer = input.getBytes();           //converts entered String into bytes
            try {
                mmOutStream.write(msgBuffer);                //write bytes over BT connection via outstream
            } catch (IOException e) {
                //if you cannot write, close the application
                Toast.makeText(getBaseContext(), "Connection Failure", Toast.LENGTH_LONG).show();
                finish();

            }
        }
    }


}