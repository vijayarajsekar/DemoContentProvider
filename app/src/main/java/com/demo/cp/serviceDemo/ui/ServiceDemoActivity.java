package com.demo.cp.serviceDemo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.demo.cp.R;
import com.demo.cp.serviceDemo.services.DemoService;

public class ServiceDemoActivity extends AppCompatActivity {

    private EditText mInput;
    private String mUserData;

    private Intent mServiceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_demo);

        mInput = findViewById(R.id.edit_text_input);

    }

    public void startService(View v) {
        mUserData = mInput.getText().toString();
        mServiceIntent = new Intent(this, DemoService.class);
        mServiceIntent.putExtra("DATA", mUserData);
        ContextCompat.startForegroundService(this, mServiceIntent);
    }

    public void stopService(View v) {
        mServiceIntent = new Intent(this, DemoService.class);
        stopService(mServiceIntent);
    }
}