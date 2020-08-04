package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.io.IOException;

public class RelativeLoginActivity extends AppCompatActivity {
    Button b1,b2;
    EditText e1,e2;
    ScrollView mainLayout;
    DBHelper dbHelper;
    Drawable drawable;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String Email = "emailKey";
    public static final String Theme = "themeKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relative_login);

        b1=findViewById(R.id.login);
        b2=findViewById(R.id.register);
        e1=findViewById(R.id.edt_name);
        e2=findViewById(R.id.edt_pwd);
        dbHelper = new DBHelper(this);
        mainLayout=findViewById(R.id.main_layout);
        drawable = mainLayout.getBackground();
        sharedpreferences = getSharedPreferences(mypreference,Context.MODE_PRIVATE);
        if(sharedpreferences.contains(Email))
        {
            e1.setText(sharedpreferences.getString(Email, ""));
        }
        if (sharedpreferences.contains(Theme)){
            if (sharedpreferences.getString(Theme,"").matches("Light")){
                mainLayout.setBackgroundResource(R.drawable.navy);
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.simple_yellow)));
                getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#FFFFFF\">" + getSupportActionBar().getTitle() + "</font>")));
                drawable = mainLayout.getBackground();
            }else if (sharedpreferences.getString(Theme,"").matches("Dark")){
                mainLayout.setBackgroundResource(R.drawable.blackcar);
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.simple_black)));
                getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#0000FF\">" + getSupportActionBar().getTitle() + "</font>")));
                drawable = mainLayout.getBackground();
            }
        }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (e1.getText().toString().matches("") || e2.getText().toString().matches("")){
                    Toast.makeText(RelativeLoginActivity.this, "Please Enter Credentials...", Toast.LENGTH_SHORT).show();
                }
                else {
                    String email = e1.getText().toString();
                    String password = e2.getText().toString();
                    boolean result = dbHelper.getSavedDetails(email,password);
                    boolean result1 = dbHelper.getFacultySavedDetails(email,password);
                    boolean result2 = dbHelper.getStudentSavedDetails(email,password);
                    Log.d("result", "Query Found Admin: "+result);
                    Log.d("result1", "Query Found Faculty: "+result1);
                    Log.d("result2", "Query Found Student: "+result2);
                    if (result){
                        Toast.makeText(RelativeLoginActivity.this, "Logged In...", Toast.LENGTH_SHORT).show();
                        Intent adminIntent = new Intent(RelativeLoginActivity.this, AdminHomePage.class);
                        String name="";
                        Cursor cursor = dbHelper.getRequestedData("SELECT * FROM registration_details WHERE EMail='"+email+"' AND Password='"+password+"'");
                        cursor.moveToFirst();
                        for (int i=0;i<cursor.getCount();i++){
                            name=cursor.getString(1);
                            cursor.moveToNext();
                        }
                        cursor.close();
                        Bundle bundle = new Bundle();
                        bundle.putString("Position","Admin");
                        bundle.putString("Email",email);
                        bundle.putString("Password",password);
                        bundle.putString("Name",name);
                        adminIntent.putExtras(bundle);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(Email, email);
                        editor.commit();
                        startActivity(adminIntent);
                        finish();
                    }
                    else if(result1){
                        Toast.makeText(RelativeLoginActivity.this, "Logged In...", Toast.LENGTH_SHORT).show();
                        if(password.matches("changeme")){
                            Intent facultyIntent = new Intent(RelativeLoginActivity.this, ResetPasswordActivity.class);
                            Bundle bundle = new Bundle();
                            String name="";
                            String department="";
                            Cursor cursor = dbHelper.getRequestedData("SELECT * FROM faculty_registrations WHERE EMail='"+email+"' AND Password='"+password+"'");
                            cursor.moveToFirst();
                            for (int i=0;i<cursor.getCount();i++){
                                name=cursor.getString(1);
                                department=cursor.getString(7);
                                cursor.moveToNext();
                            }
                            cursor.close();
                            bundle.putString("Position","Faculty");
                            bundle.putString("Email",email);
                            bundle.putString("Password",password);
                            bundle.putString("Name",name);
                            bundle.putString("Department",department);
                            facultyIntent.putExtras(bundle);
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(Email, email);
                            editor.commit();
                            startActivity(facultyIntent);
                            finish();
                        }else {
                            Intent facultyIntent = new Intent(RelativeLoginActivity.this, FacultyHomePage.class);
                            Bundle bundle = new Bundle();
                            String name="";
                            String department="";
                            Cursor cursor = dbHelper.getRequestedData("SELECT * FROM faculty_registrations WHERE EMail='"+email+"' AND Password='"+password+"'");
                            cursor.moveToFirst();
                            for (int i=0;i<cursor.getCount();i++){
                                name=cursor.getString(1);
                                department=cursor.getString(7);
                                cursor.moveToNext();
                            }
                            cursor.close();
                            bundle.putString("Position","Faculty");
                            bundle.putString("Email",email);
                            bundle.putString("Password",password);
                            bundle.putString("Name",name);
                            bundle.putString("Department",department);
                            facultyIntent.putExtras(bundle);
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(Email, email);
                            editor.commit();
                            startActivity(facultyIntent);
                            finish();
                        }
                    }
                    else if (result2){
                        Toast.makeText(RelativeLoginActivity.this, "Logged In...", Toast.LENGTH_SHORT).show();
                        if(password.matches("changeme")){
                            Intent studentIntent = new Intent(RelativeLoginActivity.this, ResetPasswordActivity.class);
                            Bundle bundle = new Bundle();
                            String name="";
                            String department="";
                            String rollno="";
                            int yearofpass=2021;
                            Cursor cursor = dbHelper.getRequestedData("SELECT * FROM student_registrations WHERE EMail='"+email+"' AND Password='"+password+"'");
                            cursor.moveToFirst();
                            for (int i=0;i<cursor.getCount();i++){
                                name=cursor.getString(1);
                                department=cursor.getString(7);
                                rollno=cursor.getString(8);
                                yearofpass=cursor.getInt(9);
                                cursor.moveToNext();
                            }
                            cursor.close();
                            bundle.putString("Name",name);
                            bundle.putString("Department",department);
                            bundle.putString("RollNo",rollno);
                            bundle.putInt("YearOfPass",yearofpass);
                            bundle.putString("Position","Student");
                            bundle.putString("Email",email);
                            bundle.putString("Password",password);
                            studentIntent.putExtras(bundle);
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(Email, email);
                            editor.commit();
                            startActivity(studentIntent);
                            finish();
                        }else {
                            Intent studentIntent = new Intent(RelativeLoginActivity.this, StudentHomePage.class);
                            Bundle bundle = new Bundle();
                            String name="";
                            String department="";
                            String rollno="";
                            int yearofpass=2021;
                            Cursor cursor = dbHelper.getRequestedData("SELECT * FROM student_registrations WHERE EMail='"+email+"' AND Password='"+password+"'");
                            cursor.moveToFirst();
                            for (int i=0;i<cursor.getCount();i++){
                                name=cursor.getString(1);
                                department=cursor.getString(7);
                                rollno=cursor.getString(8);
                                yearofpass=cursor.getInt(9);
                                cursor.moveToNext();
                            }
                            cursor.close();
                            bundle.putString("Name",name);
                            bundle.putString("Department",department);
                            bundle.putString("RollNo",rollno);
                            bundle.putInt("YearOfPass",yearofpass);
                            bundle.putString("Position","Student");
                            bundle.putString("Email",email);
                            bundle.putString("Password",password);
                            studentIntent.putExtras(bundle);
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(Email, email);
                            editor.commit();
                            startActivity(studentIntent);
                            finish();
                        }
                    }
                    else {
                        Toast.makeText(RelativeLoginActivity.this, "Invalid Credentials...", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RelativeLoginActivity.this, RegistrationActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Exit From App");
        dialog.setMessage("Do You really want to exit from the application?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                System.exit(0);
            }
        });

        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                onResume();
            }
        });
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_items,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.camera:
                if (isPermissionGranted(1)) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 1);
                }
                break;
            case R.id.gallery:
                Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);
                break;
            case R.id.open_calc:
                Intent intent1 = new Intent(getApplicationContext(),CalculatorActivity.class);
                startActivity(intent1);
                break;
            case R.id.change_theme:
                if (drawable.getConstantState()==getResources().getDrawable(R.drawable.navy).getConstantState()){
                    mainLayout.setBackgroundResource(R.drawable.blackcar);
                    drawable = mainLayout.getBackground();
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.simple_black)));
                    getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#0000FF\">" + getSupportActionBar().getTitle() + "</font>")));
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(Theme, "Dark");
                    editor.commit();
                } else {
                    mainLayout.setBackgroundResource(R.drawable.navy);
                    drawable = mainLayout.getBackground();
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.simple_yellow)));
                    getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#FFFFFF\">" + getSupportActionBar().getTitle() + "</font>")));
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(Theme, "Light");
                    editor.commit();
                }
                break;
            case R.id.exit:
                System.exit(0);
                break;
        }
        return true;
    }

    public boolean isPermissionGranted(int no){
        if(Build.VERSION.SDK_INT>22)
        {
            if(no==1)
            {
                if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 101);
                    return false;
                }
                else {
                    return true;
                }
            }
        }
        else {
            return true;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==101)
        {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                mainLayout.setBackground(new BitmapDrawable(getResources(),photo));
            } else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                Bitmap bmp = null;
                try {
                    bmp = getBitmapFromUri(selectedImage);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                mainLayout.setBackground(new BitmapDrawable(getResources(),bmp));
            }
        }
    }
    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }
}
