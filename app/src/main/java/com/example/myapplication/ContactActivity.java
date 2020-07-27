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

public class ContactActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener{
    Spinner callList,roleList;
    EditText selectedContact;
    Button callNum;
    String[] roles;
    List<String> contactNames = new ArrayList<String>();
    List<Long> contactNumbers = new ArrayList<Long>();
    DBHelper dbHelper;
    Cursor adminCursor, facultyCursor, studentCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        callList=findViewById(R.id.call_list);
        roleList=findViewById(R.id.role_selector);
        selectedContact=findViewById(R.id.selected_number);
        callNum=findViewById(R.id.submit);
        dbHelper = new DBHelper(this);
        roles=getResources().getStringArray(R.array.roles);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, roles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleList.setAdapter(adapter);
        roleList.setOnItemSelectedListener(this);
        callNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPermissionGranted(1)){
                    callPhoneNumber();
                }
            }
        });
    }

    public void callPhoneNumber(){
        long num=Long.parseLong(selectedContact.getText().toString());
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+num));
        startActivity(intent);
    }

    public boolean isPermissionGranted(int no){
        if(Build.VERSION.SDK_INT>22)
        {
            if(no==1)
            {
                if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 101);
                    return false;
                }
                else {
                    return true;
                }
            }
        }
        else {
            return true;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==101)
        {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                callPhoneNumber();
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
        ((TextView) parent.getChildAt(0)).setTextSize(25);
        ((TextView) parent.getChildAt(0)).setTextAppearance(ContactActivity.this, R.style.fontForNotificationLandingPage);
        switch (parent.getId()){
            case R.id.role_selector:
                switch (position){
                    case 0:
                        adminCursor = dbHelper.getAdminContacts();
                        adminCursor.moveToFirst();
                        contactNames.clear();
                        contactNumbers.clear();
                        Log.d("AdminList", "No. of Tuples: "+adminCursor.getCount());
                        for (int i =0; i<adminCursor.getCount();i++)
                        {
                            contactNames.add(adminCursor.getString(0));
                            contactNumbers.add(adminCursor.getLong(1));
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
                        contactNumbers.clear();
                        for (int i =0; i<facultyCursor.getCount();i++)
                        {
                            contactNames.add(facultyCursor.getString(0));
                            contactNumbers.add(facultyCursor.getLong(1));
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
                        contactNumbers.clear();
                        for (int i =0; i<studentCursor.getCount();i++)
                        {
                            contactNames.add(studentCursor.getString(0));
                            contactNumbers.add(studentCursor.getLong(1));
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
                        selectedContact.setText(String.valueOf(contactNumbers.get(position)));
                }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
