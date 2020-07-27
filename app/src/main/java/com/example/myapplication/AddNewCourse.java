package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNewCourse extends AppCompatActivity {
    EditText courseName,courseCode,courseDescription,semester,coursePrerequisites;
    Button submit;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_course);
        courseCode=findViewById(R.id.edt_course_code);
        courseName=findViewById(R.id.edt_course_name);
        courseDescription=findViewById(R.id.edt_course_description);
        semester=findViewById(R.id.edt_semester);
        coursePrerequisites=findViewById(R.id.edt_prerequisites);
        submit=findViewById(R.id.submit);
        dbHelper=new DBHelper(this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (courseCode.getText().toString().matches("") || courseName.getText().toString().matches("") ||
                courseDescription.getText().toString().matches("") || semester.getText().toString().matches("") ||
                coursePrerequisites.getText().toString().matches("")){
                    Toast.makeText(AddNewCourse.this, "Please Enter All Details", Toast.LENGTH_SHORT).show();
                }
                else {
                    long result = dbHelper.saveCourseDetails(courseCode.getText().toString(),courseName.getText().toString(),courseDescription.getText().toString(),Integer.parseInt(semester.getText().toString()),coursePrerequisites.getText().toString());
                    if (result>0){
                        Toast.makeText(AddNewCourse.this, "Course Added Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),ListViewActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(AddNewCourse.this, "No Entry Inserted", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
