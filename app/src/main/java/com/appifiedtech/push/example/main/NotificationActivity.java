package com.appifiedtech.push.example.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


public class NotificationActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        textView = (TextView) findViewById(R.id.textView);
        Intent intent = new Intent();
        String data = intent.getStringExtra("data");
        textView.setText(data);
    }
}
