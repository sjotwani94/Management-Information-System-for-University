package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class AfterLoginActivity extends AppCompatActivity {
    Button callNum, sendSMS, sendEmail, sendWhatsapp, openWebview, openList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);
        callNum=findViewById(R.id.verify_phone);
        sendSMS=findViewById(R.id.sms_verification);
        sendEmail=findViewById(R.id.send_email);
        sendWhatsapp=findViewById(R.id.whatsapp_sms);
        openWebview=findViewById(R.id.open_web_view);
        openList=findViewById(R.id.open_list_view);

        callNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(getApplicationContext(), ContactActivity.class);
                startActivity(callIntent);
            }
        });

        openWebview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webIntent = new Intent(getApplicationContext(), WebViewActivity.class);
                startActivity(webIntent);
            }
        });

        openList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listIntent = new Intent(getApplicationContext(), ListViewActivity.class);
                startActivity(listIntent);
            }
        });
    }
}
