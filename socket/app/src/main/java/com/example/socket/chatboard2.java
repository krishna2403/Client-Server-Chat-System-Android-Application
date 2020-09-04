package com.example.socket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.net.*;
import java.io.*;

public class chatboard2 extends AppCompatActivity {
    String hostIP;
    int hostPort;
    Socket socket;
    PrintWriter output;
    BufferedReader input;
    TextView tv, tv1, textView, textView1;
    LinearLayout scroll;
    ScrollView sc;
    Button snd;
    LinearLayout.LayoutParams lp, lp1;
    Boolean stop = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatboard2);

        tv = findViewById(R.id.chatBoardMsg);
        tv1 = findViewById(R.id.chatbox);
        snd = findViewById(R.id.sendMsg);
        sc = findViewById(R.id.scrollView);
        scroll = findViewById(R.id.scrollLinearView);
        hostIP = getIntent().getStringExtra("hostIP");
        hostPort = Integer.parseInt(getIntent().getStringExtra("hostPort"));

        new Thread(new listen()).start();
    }

    class listen implements Runnable {
        @Override
        public void run() {
            try {
                socket = new Socket(hostIP, hostPort);
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                output = new PrintWriter(socket.getOutputStream());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv.setVisibility(View.GONE);
                        tv1.setVisibility(View.VISIBLE);
                        snd.setVisibility(View.VISIBLE);
                    }
                });
                new Thread(new receive()).start();
            } catch (IOException e) {
            }
        }
    }

    class receive implements Runnable {
        @Override
        public void run() {
            while (!stop) {
                try {
                    final String message = input.readLine();
                    if (message != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView1 = new TextView(getApplicationContext());
                                lp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                lp1.setMargins(30, 30, 30, 30);
                                textView1.setLayoutParams(lp1);
                                textView1.setTextSize(24);
                                textView1.setText(message);
                                scroll.addView(textView1);
                                sc.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        sc.fullScroll(ScrollView.FOCUS_DOWN);
                                    }
                                });
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void msgSend(View v) {
        String message = tv1.getText().toString().trim();
        if (!message.isEmpty()) {
            new Thread(new chatboard2.send(message)).start();
        }
    }

    class send implements Runnable {
        private String message;
        send(String message) {
            this.message = message;
        }
        @Override
        public void run() {
            output.write(message+"\n");
            output.flush();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView = new TextView(getApplicationContext());
                    lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(30, 30, 30, 30);
                    textView.setLayoutParams(lp);
                    textView.setTextSize(24);
                    textView.setGravity(5);
                    textView.setText(message);
                    scroll.addView(textView);
                    sc.post(new Runnable() {
                        @Override
                        public void run() {
                            sc.fullScroll(ScrollView.FOCUS_DOWN);
                        }
                    });
                    tv1.setText(null);
                }
            });
        }
    }

    public void stopServer(View v) {
        try {
            socket.close();
            input.close();
            output.close();
        } catch(Exception e) {
        }
        stop = true;
        finish();
    }
}