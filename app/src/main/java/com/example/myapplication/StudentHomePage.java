package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class StudentHomePage extends AppCompatActivity {
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    Button viewMarks, viewAttendance, viewNotices;
    private ProgressDialog progress;
    ScrollView s1;
    LinearLayout nav_user;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String Email = "emailKey";
    public static final String Theme = "themeKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_page);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.mytext);
        mTitleTextView.setText("Student Handle");
        getSupportActionBar().setCustomView(mCustomView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dl = (DrawerLayout)findViewById(R.id.drawer);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        nv = (NavigationView)findViewById(R.id.navigation_view);
        View hView =  nv.getHeaderView(0);
        nav_user = (LinearLayout) hView.findViewById(R.id.nav_layout);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.go_to_web:
                        Intent intent= new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://www.nirmauni.ac.in/"));
                        Intent chooser= Intent.createChooser(intent,"Open website using...");
                        if(intent.resolveActivity(getPackageManager())!=null);
                        startActivity(chooser);
                        Toast.makeText(StudentHomePage.this, "Visiting Website",Toast.LENGTH_SHORT).show();break;
                    case R.id.visit_contact:
                        Intent intent0 = new Intent(StudentHomePage.this, AfterLoginActivity.class);
                        startActivity(intent0);
                        break;
                    case R.id.logout:
                        Intent intent1 = new Intent(StudentHomePage.this, RelativeLoginActivity.class);
                        startActivity(intent1);
                        Toast.makeText(StudentHomePage.this, "Logged Out",Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });
        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        s1 = findViewById(R.id.scroller);
        if (sharedpreferences.contains(Theme)){
            if (sharedpreferences.getString(Theme,"").matches("Light")){
                s1.setBackgroundResource(R.drawable.navy);
                nav_user.setBackgroundColor(getResources().getColor(R.color.simple_yellow));
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.simple_yellow)));
                getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#FFFFFF\">" + getSupportActionBar().getTitle() + "</font>")));
            }else if (sharedpreferences.getString(Theme,"").matches("Dark")){
                s1.setBackgroundResource(R.drawable.blackcar);
                nav_user.setBackgroundColor(getResources().getColor(R.color.simple_black));
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.simple_black)));
                getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#0000FF\">" + getSupportActionBar().getTitle() + "</font>")));
            }
        }
        viewMarks=findViewById(R.id.student_result);
        viewAttendance=findViewById(R.id.student_attendance);
        viewNotices=findViewById(R.id.view_notices);
        Bundle b = getIntent().getExtras();
        final String Name = b.getString("Name");
        String Email = b.getString("Email");
        final String Department = b.getString("Department");
        final String RollNo = b.getString("RollNo");
        final int YearOfPass = b.getInt("YearOfPass");
        View header = nv.getHeaderView(0);
        TextView text = (TextView) header.findViewById(R.id.username);
        text.setText(Name+" ("+Email+")");
        viewMarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress=new ProgressDialog(StudentHomePage.this);
                progress.setMessage("Opening Activity...");
                progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                //progress.setIndeterminate(true);
                progress.setProgress(0);
                progress.show();

                final int totalProgressTime = 100;
                final Thread t = new Thread() {
                    @Override
                    public void run() {
                        int jumpTime = 0;

                        while(jumpTime < totalProgressTime) {
                            try {
                                sleep(200);
                                jumpTime += 5;
                                if (jumpTime==100){
                                    Intent intent = new Intent(getApplicationContext(),ResultDisplayActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("Name",Name);
                                    bundle.putString("Department",Department);
                                    bundle.putString("RollNo",RollNo);
                                    bundle.putInt("YearOfPass",YearOfPass);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    progress.dismiss();
                                }
                                progress.setProgress(jumpTime);
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                };
                t.start();
            }
        });
        viewAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress=new ProgressDialog(StudentHomePage.this);
                progress.setMessage("Opening Activity...");
                progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                //progress.setIndeterminate(true);
                progress.setProgress(0);
                progress.show();

                final int totalProgressTime = 100;
                final Thread t = new Thread() {
                    @Override
                    public void run() {
                        int jumpTime = 0;

                        while(jumpTime < totalProgressTime) {
                            try {
                                sleep(200);
                                jumpTime += 5;
                                if (jumpTime==100){
                                    Intent intent = new Intent(getApplicationContext(),AttendanceDisplayActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("Name",Name);
                                    bundle.putString("Department",Department);
                                    bundle.putString("RollNo",RollNo);
                                    bundle.putInt("YearOfPass",YearOfPass);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    progress.dismiss();
                                }
                                progress.setProgress(jumpTime);
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                };
                t.start();
            }
        });
        viewNotices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ViewNoticesActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Name",Name);
                bundle.putString("Position","Student");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Logout");
        dialog.setMessage("Do You really want to logout from the application?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent1 = new Intent(StudentHomePage.this, RelativeLoginActivity.class);
                startActivity(intent1);
                Toast.makeText(StudentHomePage.this, "Logged Out",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                onResume();
            }
        });
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_items1,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        switch (item.getItemId()){
            case R.id.dark_theme:
                s1.setBackgroundResource(R.drawable.blackcar);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.simple_black)));
                getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#0000FF\">" + getSupportActionBar().getTitle() + "</font>")));
                editor.putString(Theme, "Dark");
                editor.commit();
                nav_user.setBackgroundColor(getResources().getColor(R.color.simple_black));
                break;
            case R.id.light_theme:
                s1.setBackgroundResource(R.drawable.navy);
                SharedPreferences.Editor editor1 = sharedpreferences.edit();
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.simple_yellow)));
                getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#FFFFFF\">" + getSupportActionBar().getTitle() + "</font>")));
                editor1.putString(Theme, "Light");
                editor1.commit();
                nav_user.setBackgroundColor(getResources().getColor(R.color.simple_yellow));
                break;
            case R.id.exit:
                System.exit(0);
                break;
        }
        return true;
    }
}
