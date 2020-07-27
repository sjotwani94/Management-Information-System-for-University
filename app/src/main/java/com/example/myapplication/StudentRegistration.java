package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class StudentRegistration extends AppCompatActivity {
    ScrollView s1;
    Button ib1;
    TextView tmain;
    EditText name,email,address,age,contact,rollno,batch;
    Spinner department;
    RadioButton ge1,ge2;
    DBHelper dbHelper;
    String[] departments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);

        s1=findViewById(R.id.scroller);
        tmain=findViewById(R.id.main_txt);
        registerForContextMenu(tmain);
        ib1=findViewById(R.id.submit);
        name=findViewById(R.id.edt_name);
        email=findViewById(R.id.edt_email);
        address=findViewById(R.id.edt_address);
        age=findViewById(R.id.edt_age);
        contact=findViewById(R.id.edt_phone);
        department=findViewById(R.id.edt_department);
        rollno=findViewById(R.id.edt_roll_no);
        batch=findViewById(R.id.edt_batch);
        ge1=findViewById(R.id.rb_male);
        ge2=findViewById(R.id.rb_female);
        dbHelper=new DBHelper(this);
        departments=getResources().getStringArray(R.array.department);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, departments);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        department.setAdapter(adapter);

        ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().matches("") || email.getText().toString().matches("") ||
                        address.getText().toString().matches("") || age.getText().toString().matches("") ||
                        contact.getText().toString().matches("") || rollno.getText().toString().matches("") ||
                        department.getSelectedItem().toString().matches("") || batch.getText().toString().matches("")){
                    Toast.makeText(StudentRegistration.this, "Some Text Field is Empty...", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent int1=new Intent(StudentRegistration.this,AdminHomePage.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("Name",getIntent().getExtras().getString("Name"));
                    bundle.putString("Email",getIntent().getExtras().getString("Email"));
                    LayoutInflater li1=getLayoutInflater();
                    View layout=li1.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.custom_toast_layout));
                    TextView txt= (TextView) layout.findViewById(R.id.text_toast);
                    txt.setText("Oh Yeah!, Registration Successful!");

                    String Name = name.getText().toString();
                    String EMail = email.getText().toString();
                    String Address = address.getText().toString();
                    int Age = Integer.parseInt(age.getText().toString());
                    long ContactNo = Long.parseLong(contact.getText().toString());
                    String Gender = "None";
                    if(ge1.isChecked()){
                        Gender = "Male";
                    }
                    else if (ge2.isChecked()){
                        Gender = "Female";
                    }
                    String RollNo = rollno.getText().toString();
                    String Department = department.getSelectedItem().toString();
                    int Batch = Integer.parseInt(batch.getText().toString());

                    long rowCount = dbHelper.saveStudentRegistrationDetails(Name,EMail,Address,Age,ContactNo,Gender,Department,RollNo,Batch,"changeme");

                    if (rowCount>0){
                        Toast toast=new Toast(getApplicationContext());
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(layout);
                        toast.show();
                        int1.putExtras(bundle);
                        startActivity(int1);
                    }else {
                        Toast.makeText(StudentRegistration.this, "No Rows Inserted", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
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
                break;
            case R.id.light_theme:
                s1.setBackgroundResource(R.drawable.navy);
            case R.id.exit:
                System.exit(0);
                break;
        }
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater cmi = getMenuInflater();
        cmi.inflate(R.menu.menu_items1,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.dark_theme:
                s1.setBackgroundResource(R.drawable.blackcar);
                break;
            case R.id.light_theme:
                s1.setBackgroundResource(R.drawable.navy);
                break;
            case R.id.exit:
                System.exit(0);
                break;
        }
        return true;
    }
}
