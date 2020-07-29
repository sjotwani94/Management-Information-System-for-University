package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ResetPasswordActivity extends AppCompatActivity {
    EditText pass,confirmpass;
    Button submit;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        pass=findViewById(R.id.password);
        confirmpass=findViewById(R.id.confirm_password);
        submit=findViewById(R.id.submit);
        dbHelper = new DBHelper(this);

        Bundle b = getIntent().getExtras();
        final String Email=b.getString("Email");
        final String Position=b.getString("Position");
        Log.d("Email", "Email: "+Email);
        Log.d("Position", "Position: "+Position);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pass.getText().toString().matches("") || confirmpass.getText().toString().matches("")){
                    Toast.makeText(ResetPasswordActivity.this, "Please enter the details...", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (pass.getText().toString().matches(confirmpass.getText().toString())){
                        boolean result = dbHelper.resetPassword(Email,Position,pass.getText().toString());
                        Log.d("Result", "Query Updation: "+result);
                        if (result){
                            Toast.makeText(ResetPasswordActivity.this, "Password Reset Successful!", Toast.LENGTH_LONG).show();
                            Bundle bundle = new Bundle();
                            bundle.putString("Email",Email);
                            bundle.putString("Position",Position);
                            bundle.putString("Name",getIntent().getExtras().getString("Name"));
                            bundle.putString("Department",getIntent().getExtras().getString("Department"));
                            if (Position.matches("Faculty")){
                                Intent intent = new Intent(ResetPasswordActivity.this,FacultyHomePage.class);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finish();
                            }
                            else if (Position.matches("Student")){
                                Intent intent = new Intent(ResetPasswordActivity.this,StudentHomePage.class);
                                bundle.putString("RollNo",getIntent().getExtras().getString("RollNo"));
                                bundle.putInt("YearOfPass",getIntent().getExtras().getInt("YearOfPass"));
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }
                }
            }
        });
    }
}
