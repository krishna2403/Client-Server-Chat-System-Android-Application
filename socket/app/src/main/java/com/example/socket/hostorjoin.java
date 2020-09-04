package com.example.socket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class hostorjoin extends AppCompatActivity {
    WifiManager wm;
    String ip, name;
    EditText et1, et2;
    int port = 9090;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostorjoin);

        name = getIntent().getStringExtra("username");
        TextView tv = findViewById(R.id.username);
        tv.setText("Hola " + name);

        wm = (WifiManager)getApplicationContext().getSystemService(getApplicationContext().WIFI_SERVICE);
        ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        TextView tv1 = findViewById(R.id.ipportview);
        tv1.setText("IP: "+ip+"\n"+"Port: "+port);
    }
    public void hostChat(View v) {
        Intent in = new Intent(v.getContext(), chatboard.class);
        startActivity(in);
    }
    public void joinChat(View v) {
        et1 = findViewById(R.id.enterIP);
        et2 = findViewById(R.id.enterPort);
        String hostIP = et1.getText().toString().trim();
        String hostPort = et2.getText().toString().trim();
        if(hostIP.length() == 0 || hostPort.length() == 0) return;
        else {
            Intent in = new Intent(v.getContext(), chatboard2.class);
            in.putExtra("hostIP", hostIP);
            in.putExtra("hostPort", hostPort);
            startActivity(in);
        }
    }
}