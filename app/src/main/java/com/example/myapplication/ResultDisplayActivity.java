package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ResultDisplayActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView department, rollNo, studentName, semesterNo;
    private List<RecyclerListData> functionsList = new ArrayList<>();
    private RecyclerListAdapter adapter;
    private Calendar mCalendar;
    private int mYear, mMonth, mDay, studyYear;
    DBHelper dbHelper;
    Cursor cursor;
    List<String> courseDesc = new ArrayList<String>();
    List<String> courseMarks = new ArrayList<String>();
    List<String> courseGrade = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_display);
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

        cursor = dbHelper.getMarks(RollNo);
        cursor.moveToFirst();
        for (int i=0;i<cursor.getCount();i++){
            courseDesc.add(cursor.getString(0)+" ("+dbHelper.getCourseName(cursor.getString(0))+")");
            Double total = 0.4*(cursor.getInt(1)+cursor.getInt(2)+cursor.getInt(3))+0.2*cursor.getInt(4)+0.4*cursor.getInt(5);
            courseMarks.add(String.valueOf(total));
            if (total>90){
                courseGrade.add("A+");
            }else if (total>80 && total<=90){
                courseGrade.add("A");
            }else if (total>70 && total<=80){
                courseGrade.add("B+");
            }else if (total>60 && total<=70){
                courseGrade.add("B");
            }else if (total>50 && total<=60){
                courseGrade.add("C+");
            }else if (total>40 && total<=50){
                courseGrade.add("C");
            }else {
                courseGrade.add("F");
            }
        }

        recyclerView = findViewById(R.id.recycler_functions);
        for (int len=0;len<courseDesc.size();len++){
            functionsList.add(new RecyclerListData(courseDesc.get(len),courseMarks.get(len),courseGrade.get(len)));
        }
        adapter = new RecyclerListAdapter(functionsList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}
