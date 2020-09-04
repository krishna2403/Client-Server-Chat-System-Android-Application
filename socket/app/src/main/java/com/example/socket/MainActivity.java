package com.example.socket;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.EditText;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void reg(View v) {
        EditText e = (EditText)findViewById(R.id.usernameText);
        String name = e.getText().toString();
        if(name.length() == 0)
            Toast.makeText(getApplicationContext(), "Username cannot be empty", Toast.LENGTH_SHORT).show();
        else {
            // Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
            Intent in = new Intent(v.getContext(), hostorjoin.class);
            in.putExtra("username", name);
            startActivity(in);
        }
    }
}