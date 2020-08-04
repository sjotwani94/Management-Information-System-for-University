package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ResetPasswordActivity extends AppCompatActivity {
    EditText pass,confirmpass;
    Button submit;
    DBHelper dbHelper;
    RelativeLayout s1;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String Email = "emailKey";
    public static final String Theme = "themeKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        s1 = findViewById(R.id.scroller);
        pass=findViewById(R.id.password);
        confirmpass=findViewById(R.id.confirm_password);
        submit=findViewById(R.id.submit);
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

        Bundle b = getIntent().getExtras();
        final String Email=b.getString("Email");
        final String Position=b.getString("Position");
        Log.d("Email", "Email: "+Email);
        Log.d("Position", "Position: "+Position);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pass.getText().toString().matches("") || confirmpass.getText().toString().matches("")){
                    Toast.makeText(ResetPasswordActivity.this, "Please enter the details...", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (pass.getText().toString().matches(confirmpass.getText().toString())){
                        boolean result = dbHelper.resetPassword(Email,Position,pass.getText().toString());
                        Log.d("Result", "Query Updation: "+result);
                        if (result){
                            Toast.makeText(ResetPasswordActivity.this, "Password Reset Successful!", Toast.LENGTH_LONG).show();
                            Bundle bundle = new Bundle();
                            bundle.putString("Email",Email);
                            bundle.putString("Position",Position);
                            bundle.putString("Name",getIntent().getExtras().getString("Name"));
                            bundle.putString("Department",getIntent().getExtras().getString("Department"));
                            if (Position.matches("Faculty")){
                                Intent intent = new Intent(ResetPasswordActivity.this,FacultyHomePage.class);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finish();
                            }
                            else if (Position.matches("Student")){
                                Intent intent = new Intent(ResetPasswordActivity.this,StudentHomePage.class);
                                bundle.putString("RollNo",getIntent().getExtras().getString("RollNo"));
                                bundle.putInt("YearOfPass",getIntent().getExtras().getInt("YearOfPass"));
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }
                }
            }
        });
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
