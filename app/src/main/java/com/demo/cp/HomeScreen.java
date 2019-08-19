package com.demo.cp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.demo.cp.serviceDemo.ui.ServiceDemoActivity;
import com.demo.cp.type_one.DemoSystemContentProvider;
import com.demo.cp.type_two.ui.UsersList;

public class HomeScreen extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        findViewById(R.id.btn_topic_1).setOnClickListener(this);
        findViewById(R.id.btn_topic_2).setOnClickListener(this);
        findViewById(R.id.btn_topic_3).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_topic_1:
                startActivity(new Intent(this, DemoSystemContentProvider.class));
                break;

            case R.id.btn_topic_2:
                startActivity(new Intent(this, UsersList.class));
                break;

            case R.id.btn_topic_3:
                startActivity(new Intent(this, ServiceDemoActivity.class));
                break;

            default:
                Toast.makeText(this, "Invalid Option", Toast.LENGTH_SHORT);
                break;
        }
    }
}