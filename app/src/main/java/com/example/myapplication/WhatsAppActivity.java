package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class WhatsAppActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener{
    Spinner callList,roleList;
    EditText selectedContact,composedMessage;
    Button smsNum;
    String[] roles;
    List<String> contactNames = new ArrayList<String>();
    List<Long> contactNumbers = new ArrayList<Long>();
    DBHelper dbHelper;
    Cursor adminCursor, facultyCursor, studentCursor;
    RelativeLayout s1;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String Email = "emailKey";
    public static final String Theme = "themeKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whats_app);
        s1=findViewById(R.id.scroller);
        callList=findViewById(R.id.call_list);
        roleList=findViewById(R.id.role_selector);
        selectedContact=findViewById(R.id.selected_number);
        composedMessage=findViewById(R.id.composed_message);
        smsNum=findViewById(R.id.submit);
        dbHelper = new DBHelper(this);
        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        if (sharedpreferences.contains(Theme)){
            if (sharedpreferences.getString(Theme,"").matches("Light")){
                s1.setBackgroundResource(R.drawable.navy);
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.simple_yellow)));
                getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#FFFFFF\">" + getSupportActionBar().getTitle() + "</font>")));
            }else if (sharedpreferences.getString(Theme,"").matches("Dark")){
                s1.setBackgroundResource(R.drawable.blackcar);
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.simple_black)));
                getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#0000FF\">" + getSupportActionBar().getTitle() + "</font>")));
            }
        }
        roles=getResources().getStringArray(R.array.roles);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, roles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleList.setAdapter(adapter);
        roleList.setOnItemSelectedListener(this);
        smsNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumberWithCountryCode = "+91"+selectedContact.getText().toString();
                String message = composedMessage.getText().toString();

                startActivity(
                        new Intent(Intent.ACTION_VIEW,
                                Uri.parse(
                                        String.format("https://api.whatsapp.com/send?phone=%s&text=%s", phoneNumberWithCountryCode, message)
                                )
                        )
                );
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
        ((TextView) parent.getChildAt(0)).setTextSize(25);
        ((TextView) parent.getChildAt(0)).setTextAppearance(WhatsAppActivity.this, R.style.fontForNotificationLandingPage);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_items1,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.dark_theme:
                s1.setBackgroundResource(R.drawable.blackcar);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.simple_black)));
                getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#0000FF\">" + getSupportActionBar().getTitle() + "</font>")));
                editor.putString(Theme, "Dark");
                editor.commit();
                break;
            case R.id.light_theme:
                s1.setBackgroundResource(R.drawable.navy);
                SharedPreferences.Editor editor1 = sharedpreferences.edit();
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.simple_yellow)));
                getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#FFFFFF\">" + getSupportActionBar().getTitle() + "</font>")));
                editor1.putString(Theme, "Light");
                editor1.commit();
                break;
            case R.id.exit:
                System.exit(0);
                break;
        }
        return true;
    }
}
