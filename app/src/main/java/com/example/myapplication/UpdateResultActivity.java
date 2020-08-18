package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class UpdateResultActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText classTest,midSem,assignment,labPrac,finalExam;
    Spinner rollNum,courseCode;
    Button submit,fetch;
    DBHelper dbHelper;
    List<String> listOfCourseCodes = new ArrayList<String>();
    List<String> listOfCourses = new ArrayList<String>();
    List<String> listOfRollNos = new ArrayList<String>();
    ScrollView s1;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String Email = "emailKey";
    public static final String Theme = "themeKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_result);
        s1 = findViewById(R.id.scroller);
        classTest=findViewById(R.id.edt_class_test);
        midSem=findViewById(R.id.edt_mid_sem);
        assignment=findViewById(R.id.edt_assignment);
        labPrac=findViewById(R.id.edt_lab_prac);
        finalExam=findViewById(R.id.edt_final_exam);
        rollNum=findViewById(R.id.list_roll_nos);
        courseCode=findViewById(R.id.list_courses);
        submit=findViewById(R.id.submit_course);
        fetch=findViewById(R.id.fetch_course);
        dbHelper=new DBHelper(this);
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
                int classtest = Integer.parseInt(classTest.getText().toString());
                int midsem = Integer.parseInt(midSem.getText().toString());
                int assign = Integer.parseInt(assignment.getText().toString());
                int labprac = Integer.parseInt(labPrac.getText().toString());
                int finalexam = Integer.parseInt(finalExam.getText().toString());
                String rollno = rollNum.getSelectedItem().toString();
                String coursecode = courseCode.getSelectedItem().toString().substring(0,5);
                int result = dbHelper.updateMarks(rollno,coursecode,classtest,midsem,assign,labprac,finalexam);
                classTest.setText("");
                midSem.setText("");
                assignment.setText("");
                labPrac.setText("");
                finalExam.setText("");
                Toast.makeText(UpdateResultActivity.this, "Marks Updated Successfully!", Toast.LENGTH_SHORT).show();
            }
        });
        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rollno = rollNum.getSelectedItem().toString();
                String coursecode = courseCode.getSelectedItem().toString().substring(0,5);
                Cursor cursor = dbHelper.getMarksByCourse(rollno,coursecode);
                cursor.moveToFirst();
                classTest.setText(String.valueOf(cursor.getInt(0)));
                midSem.setText(String.valueOf(cursor.getInt(1)));
                assignment.setText(String.valueOf(cursor.getInt(2)));
                labPrac.setText(String.valueOf(cursor.getInt(3)));
                finalExam.setText(String.valueOf(cursor.getInt(4)));
                cursor.close();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
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
