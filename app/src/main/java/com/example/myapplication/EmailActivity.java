package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EmailActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener{
    Spinner callList,roleList;
    EditText selectedContact,composedSubject,composedMessage;
    Button emailPerson;
    String[] roles;
    List<String> contactNames = new ArrayList<String>();
    List<String> emailIds = new ArrayList<String>();
    DBHelper dbHelper;
    Cursor adminCursor, facultyCursor, studentCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        callList=findViewById(R.id.call_list);
        roleList=findViewById(R.id.role_selector);
        selectedContact=findViewById(R.id.selected_number);
        composedSubject=findViewById(R.id.composed_subject);
        composedMessage=findViewById(R.id.composed_message);
        emailPerson=findViewById(R.id.submit);
        dbHelper = new DBHelper(this);
        roles=getResources().getStringArray(R.array.roles);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, roles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleList.setAdapter(adapter);
        roleList.setOnItemSelectedListener(this);
        emailPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int2=new Intent(Intent.ACTION_SEND);
                String[] clients = {selectedContact.getText().toString()};
                int2.putExtra(Intent.EXTRA_EMAIL, clients);
                int2.putExtra(Intent.EXTRA_SUBJECT, composedSubject.getText().toString());
                int2.putExtra(Intent.EXTRA_TEXT, composedMessage.getText().toString());
                int2.setType("message/rfc822");
                startActivity(int2);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
        ((TextView) parent.getChildAt(0)).setTextSize(25);
        ((TextView) parent.getChildAt(0)).setTextAppearance(EmailActivity.this, R.style.fontForNotificationLandingPage);
        switch (parent.getId()){
            case R.id.role_selector:
                switch (position){
                    case 0:
                        adminCursor = dbHelper.getAdminContacts();
                        adminCursor.moveToFirst();
                        contactNames.clear();
                        emailIds.clear();
                        Log.d("AdminList", "No. of Tuples: "+adminCursor.getCount());
                        for (int i =0; i<adminCursor.getCount();i++)
                        {
                            contactNames.add(adminCursor.getString(0));
                            emailIds.add(adminCursor.getString(2));
                            adminCursor.moveToNext();
                        }
                        adminCursor.close();
                        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, contactNames);
                        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        callList.setAdapter(adapter1);
                        callList.setOnItemSelectedListener(this);
                        break;
                    case 1:
                        facultyCursor = dbHelper.getFacultyContacts();
                        facultyCursor.moveToFirst();
                        contactNames.clear();
                        emailIds.clear();
                        for (int i =0; i<facultyCursor.getCount();i++)
                        {
                            contactNames.add(facultyCursor.getString(0));
                            emailIds.add(facultyCursor.getString(2));
                            facultyCursor.moveToNext();
                        }
                        facultyCursor.close();
                        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, contactNames);
                        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        callList.setAdapter(adapter2);
                        callList.setOnItemSelectedListener(this);
                        break;
                    case 2:
                        studentCursor = dbHelper.getStudentContacts();
                        studentCursor.moveToFirst();
                        contactNames.clear();
                        emailIds.clear();
                        for (int i =0; i<studentCursor.getCount();i++)
                        {
                            contactNames.add(studentCursor.getString(0));
                            emailIds.add(studentCursor.getString(2));
                            studentCursor.moveToNext();
                        }
                        studentCursor.close();
                        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, contactNames);
                        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        callList.setAdapter(adapter3);
                        callList.setOnItemSelectedListener(this);
                        break;
                }
                break;
            case R.id.call_list:
                switch (position){
                    default:
                        selectedContact.setText(emailIds.get(position));
                }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
