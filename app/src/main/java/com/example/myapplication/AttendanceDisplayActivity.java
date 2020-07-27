package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AttendanceDisplayActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView department, rollNo, studentName, semesterNo;
    private List<AttendanceListData> functionsList = new ArrayList<>();
    private AttendanceListAdapter adapter;
    private Calendar mCalendar;
    private int mYear, mMonth, mDay, studyYear;
    DBHelper dbHelper;
    Cursor cursor;
    List<String> courseDesc = new ArrayList<String>();
    List<String> courseAttendance = new ArrayList<String>();
    List<String> coursePenalty = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_display);
        department=findViewById(R.id.degree_name);
        rollNo=findViewById(R.id.edt_roll_num);
        studentName=findViewById(R.id.edt_student_name);
        semesterNo=findViewById(R.id.edt_semester_no);
        dbHelper=new DBHelper(this);
        String Name = getIntent().getExtras().getString("Name");
        String Department = getIntent().getExtras().getString("Department");
        String RollNo = getIntent().getExtras().getString("RollNo");
        int YearOfPass = getIntent().getExtras().getInt("YearOfPass");
        department.setText(Department);
        studentName.setText(Name);
        rollNo.setText(RollNo);
        mCalendar = Calendar.getInstance();
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH) + 1;
        mDay = mCalendar.get(Calendar.DATE);
        studyYear = mYear - YearOfPass;
        if (mMonth>6){
            semesterNo.setText(String.valueOf((studyYear*2)+1));
        }else {
            semesterNo.setText(String.valueOf(2*studyYear));
        }

        cursor = dbHelper.getAttendance(RollNo);
        cursor.moveToFirst();
        for (int i=0;i<cursor.getCount();i++){
            courseDesc.add(cursor.getString(0)+" ("+dbHelper.getCourseName(cursor.getString(0))+")");
            float total;
            Log.d("Total Lectures", "Total Lectures: "+cursor.getInt(2));
            if (cursor.getInt(2)!=0){
                total = (Float.valueOf(cursor.getInt(1))/Float.valueOf(cursor.getInt(2)))*100;
                Log.d("Total", "Attendance Percent: "+total);
            }
            else {
                total = 0;
            }
            courseAttendance.add(String.valueOf(total));
            if (total>=85){
                coursePenalty.add("None");
            }else if (total>75 && total<85){
                coursePenalty.add("A");
            }else if (total>65 && total<=75){
                coursePenalty.add("B");
            }else {
                coursePenalty.add("C");
            }
            cursor.moveToNext();
        }
        cursor.close();

        recyclerView = findViewById(R.id.recycler_functions);
        for (int len=0;len<courseDesc.size();len++){
            functionsList.add(new AttendanceListData(courseDesc.get(len),courseAttendance.get(len),coursePenalty.get(len)));
        }
        adapter = new AttendanceListAdapter(functionsList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}
