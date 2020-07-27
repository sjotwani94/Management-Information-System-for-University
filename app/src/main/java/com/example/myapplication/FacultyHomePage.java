package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class FacultyHomePage extends AppCompatActivity {
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    Button updateStudentMarks, updateStudentAttendance,viewNotices,addNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_home_page);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.mytext);
        mTitleTextView.setText("Faculty Handle");
        getSupportActionBar().setCustomView(mCustomView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dl = (DrawerLayout)findViewById(R.id.drawer);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        nv = (NavigationView)findViewById(R.id.navigation_view);
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
                        Toast.makeText(FacultyHomePage.this, "Visiting Website",Toast.LENGTH_SHORT).show();break;
                    case R.id.visit_contact:
                        Intent intent0 = new Intent(FacultyHomePage.this, AfterLoginActivity.class);
                        startActivity(intent0);
                        break;
                    case R.id.logout:
                        Intent intent1 = new Intent(FacultyHomePage.this, RelativeLoginActivity.class);
                        startActivity(intent1);
                        Toast.makeText(FacultyHomePage.this, "Logged Out",Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });
        Bundle b = getIntent().getExtras();
        final String Name = b.getString("Name");
        String Email = b.getString("Email");
        final String Department = b.getString("Department");
        View header = nv.getHeaderView(0);
        TextView text = (TextView) header.findViewById(R.id.username);
        text.setText(Name+" ("+Email+")");
        updateStudentMarks = findViewById(R.id.student_marks_list);
        updateStudentAttendance = findViewById(R.id.student_attendance_list);
        viewNotices = findViewById(R.id.view_notices);
        addNotice = findViewById(R.id.add_notice);
        updateStudentMarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UpdateResultActivity.class);
                intent.putExtra("Department",Department);
                startActivity(intent);
            }
        });
        updateStudentAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UpdateAttendanceActivity.class);
                intent.putExtra("Department",Department);
                startActivity(intent);
            }
        });
        viewNotices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ViewNoticesActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Name",Name);
                bundle.putString("Position","Faculty");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        addNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),AddNoticeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Name",Name);
                bundle.putString("Position","Faculty");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}
