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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class AdminHomePage extends AppCompatActivity {
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    Button facultyReg,studentReg,viewCourses,addCourse,viewNotices,addNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_page);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.mytext);
        mTitleTextView.setText("Admin Handle");
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
                        Toast.makeText(AdminHomePage.this, "Visiting Website",Toast.LENGTH_SHORT).show();break;
                    case R.id.visit_contact:
                        Intent intent0 = new Intent(AdminHomePage.this, AfterLoginActivity.class);
                        startActivity(intent0);
                        break;
                    case R.id.logout:
                        Intent intent1 = new Intent(AdminHomePage.this, RelativeLoginActivity.class);
                        startActivity(intent1);
                        Toast.makeText(AdminHomePage.this, "Logged Out",Toast.LENGTH_SHORT).show();
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
        final String Email = b.getString("Email");
        View header = nv.getHeaderView(0);
        TextView text = (TextView) header.findViewById(R.id.username);
        text.setText(Name+" ("+Email+")");
        facultyReg=findViewById(R.id.faculty_reg);
        studentReg=findViewById(R.id.student_reg);
        viewCourses=findViewById(R.id.view_courses);
        addCourse=findViewById(R.id.add_course);
        viewNotices=findViewById(R.id.view_notices);
        addNotice=findViewById(R.id.add_notice);
        facultyReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent facultyIntent=new Intent(getApplicationContext(),FacultyRegistration.class);
                Bundle bundle = new Bundle();
                bundle.putString("Name",Name);
                bundle.putString("Email",Email);
                facultyIntent.putExtras(bundle);
                startActivity(facultyIntent);
            }
        });
        studentReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent studentIntent=new Intent(getApplicationContext(),StudentRegistration.class);
                Bundle bundle = new Bundle();
                bundle.putString("Name",Name);
                bundle.putString("Email",Email);
                studentIntent.putExtras(bundle);
                startActivity(studentIntent);
            }
        });
        viewCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent=new Intent(getApplicationContext(),ListViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Name",Name);
                bundle.putString("Email",Email);
                viewIntent.putExtras(bundle);
                startActivity(viewIntent);
            }
        });
        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addIntent=new Intent(getApplicationContext(),SpinnerCourseActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Name",Name);
                bundle.putString("Email",Email);
                addIntent.putExtras(bundle);
                startActivity(addIntent);
            }
        });
        viewNotices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ViewNoticesActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Name",Name);
                bundle.putString("Position","Administrator");
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
                bundle.putString("Position","Administrator");
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
