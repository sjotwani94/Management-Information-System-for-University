package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class UpdateAttendanceActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText totalPresent,totalLectures;
    Spinner rollNum,courseCode;
    Button submit,fetch;
    DBHelper dbHelper;
    List<String> listOfCourseCodes = new ArrayList<String>();
    List<String> listOfCourses = new ArrayList<String>();
    List<String> listOfRollNos = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_attendance);
        totalPresent=findViewById(R.id.edt_total_present);
        totalLectures=findViewById(R.id.edt_total_lectures);
        rollNum=findViewById(R.id.list_roll_nos);
        courseCode=findViewById(R.id.list_courses);
        submit=findViewById(R.id.submit_course);
        fetch=findViewById(R.id.fetch_course);
        dbHelper=new DBHelper(this);
        String Department = getIntent().getStringExtra("Department");
        String[] Splitted = Department.split(" ");
        String ShortForm = Splitted[0].substring(0,1)+Splitted[1].substring(0,1);
        Log.d("Dept", "Department: "+ShortForm);
        Cursor cursor1 = dbHelper.getCourseDetailsWithFilter(ShortForm);
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
        courseCode.setAdapter(adapter);
        courseCode.setOnItemSelectedListener(this);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int totalpresent = Integer.parseInt(totalPresent.getText().toString());
                int totallectures = Integer.parseInt(totalLectures.getText().toString());
                String rollno = rollNum.getSelectedItem().toString();
                String coursecode = courseCode.getSelectedItem().toString().substring(0,5);
                int result = dbHelper.updateAttendance(rollno,coursecode,totalpresent,totallectures);
                Toast.makeText(UpdateAttendanceActivity.this, "Marks Updated Successfully!", Toast.LENGTH_SHORT).show();
            }
        });
        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rollno = rollNum.getSelectedItem().toString();
                String coursecode = courseCode.getSelectedItem().toString().substring(0,5);
                Cursor cursor = dbHelper.getAttendanceByCourse(rollno,coursecode);
                cursor.moveToFirst();
                for (int i=0; i<cursor.getCount();i++){
                    totalPresent.setText(cursor.getInt(0));
                    totalLectures.setText(cursor.getInt(1));
                    cursor.moveToNext();
                }
                cursor.close();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.list_courses:
                switch (position){
                    default:
                        listOfRollNos.clear();
                        Cursor cursor = dbHelper.getStudentByCourse(listOfCourseCodes.get(position));
                        cursor.moveToFirst();
                        for (int i =0; i<cursor.getCount();i++)
                        {
                            listOfRollNos.add(cursor.getString(0));
                            cursor.moveToNext();
                        }
                        cursor.close();
                        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,listOfRollNos);
                        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        rollNum.setAdapter(adapter1);
                        break;
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
