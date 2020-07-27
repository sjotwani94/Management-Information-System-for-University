package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SpinnerCourseActivity extends AppCompatActivity {
    Spinner coursesList, rollNosList;
    Button submit;
    List<String> listOfCourses = new ArrayList<String>();
    List<String> listOfRollNos = new ArrayList<String>();
    List<String> listOfCourseCodes = new ArrayList<String>();
    DBHelper dbHelper;
    String selectedCourse,selectedRollNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner_course);
        coursesList=findViewById(R.id.list_courses);
        rollNosList=findViewById(R.id.list_roll_nos);
        submit=findViewById(R.id.submit_course);
        dbHelper = new DBHelper(this);
        Cursor cursor = dbHelper.getStudentRollNo();
        cursor.moveToFirst();
        for (int i =0; i<cursor.getCount();i++)
        {
            listOfRollNos.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        Cursor cursor1 = dbHelper.getCourseDetails();
        cursor1.moveToFirst();
        for (int i =0; i<cursor1.getCount();i++)
        {
            listOfCourseCodes.add(cursor1.getString(0));
            listOfCourses.add(cursor1.getString(0)+" ("+cursor1.getString(1)+")");
            cursor1.moveToNext();
        }
        cursor1.close();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listOfCourses);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        coursesList.setAdapter(adapter);
        coursesList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (parent.getId()){
                    case R.id.list_courses:
                        switch (position){
                            default:
                                selectedCourse = listOfCourseCodes.get(position);
                                break;
                        }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listOfRollNos);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rollNosList.setAdapter(adapter1);
        rollNosList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (parent.getId()){
                    case R.id.list_roll_nos:
                        switch (position){
                            default:
                                selectedRollNo = listOfRollNos.get(position);
                                break;
                        }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long result = dbHelper.saveStudentCourseDetails(selectedRollNo,selectedCourse);
                if (result>0){
                    Toast.makeText(getApplicationContext(), "Enrollment Successful", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Some Technical Errors", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
