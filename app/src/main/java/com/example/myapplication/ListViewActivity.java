package com.example.myapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    SearchView searchCourse;
    ListView listOfCourses;
    FloatingActionButton fab;
    ArrayList<String> courseDetailed = new ArrayList<String>();
    ArrayList<String> courseCode = new ArrayList<String>();
    ArrayList<String> courseName = new ArrayList<String>();
    ArrayList<String> courseDescription = new ArrayList<String>();
    ArrayList<Integer> semester = new ArrayList<Integer>();
    ArrayList<String> coursePrerequisites = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    Cursor fetched;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.mytext);
        mTitleTextView.setText("List Of Courses");
        getSupportActionBar().setCustomView(mCustomView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fab=findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddNewCourse.class);
                startActivity(intent);
                finish();
            }
        });
        searchCourse=findViewById(R.id.search_course);
        listOfCourses=findViewById(R.id.list_of_courses);
        listOfCourses.setOnItemClickListener(this);
        dbHelper = new DBHelper(this);
        fetched = dbHelper.getCourseDetails();
        fetched.moveToFirst();
        for (int i =0; i<fetched.getCount();i++)
        {
            courseCode.add(fetched.getString(0));
            courseName.add(fetched.getString(1));
            courseDescription.add(fetched.getString(2));
            semester.add(fetched.getInt(3));
            coursePrerequisites.add(fetched.getString(4));
            courseDetailed.add(fetched.getString(0)+" ("+fetched.getString(1)+")");
            fetched.moveToNext();
        }
        fetched.close();

        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,courseDetailed);
        listOfCourses.setAdapter(adapter);

        searchCourse.setActivated(true);
        searchCourse.setQueryHint("Type your keyword here");
        searchCourse.onActionViewExpanded();
        searchCourse.setIconified(false);
        searchCourse.clearFocus();

        searchCourse.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);

                return false;
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.list_of_courses:
                switch (position){
                    default:
                        final String Code = courseCode.get(position);
                        String Name = courseName.get(position);
                        String Description = courseDescription.get(position);
                        int Semester = semester.get(position);
                        String Prerequisite = coursePrerequisites.get(position);
                        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                        dialog.setTitle(Name);
                        dialog.setMessage("Course Code: "+Code+"\n"+Description+"\nOffered in Semester "+Semester+"\nPrerequisites: "+Prerequisite);
                        dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                boolean result = dbHelper.removeCourse(Code);
                                onResume();
                            }
                        });

                        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                onResume();
                            }
                        });
                        dialog.show();
                        break;
                }
        }
    }
}
