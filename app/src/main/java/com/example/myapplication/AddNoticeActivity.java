package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class AddNoticeActivity extends AppCompatActivity {
    EditText noticeSubject,noticeDescription;
    ImageView noticeImage;
    Button submitNotice;
    DBHelper dbHelper;
    Uri selectedImage;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notice);

        noticeSubject=findViewById(R.id.subject_notice);
        noticeImage=findViewById(R.id.image_notice);
        noticeDescription=findViewById(R.id.composed_message);
        submitNotice=findViewById(R.id.submit);
        dbHelper=new DBHelper(this);
        noticeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);
            }
        });
        submitNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noticeSubject.getText().toString().matches("") || noticeDescription.getText().toString().matches("")
                || noticeImage.getDrawable() == null){
                    Toast.makeText(AddNoticeActivity.this, "Please Fill Out All Details...", Toast.LENGTH_LONG).show();
                }
                else {
                    String position = getIntent().getExtras().getString("Position");
                    if (position.matches("Administrator")){
                        try {
                            String name = getIntent().getExtras().getString("Name") + " (Administrator)";
                            String subject = noticeSubject.getText().toString();
                            String description = noticeDescription.getText().toString();
                            InputStream iStream = null;
                            iStream = getContentResolver().openInputStream(selectedImage);
                            byte[] inputData = Utils1.getBytes(iStream);
                            long rowCount = dbHelper.saveNoticeDetails(name,subject,inputData,description);
                            if (rowCount>0){
                                noticeSubject.setText("");
                                noticeDescription.setText("");
                                noticeImage.setImageDrawable(null);
                                Toast.makeText(AddNoticeActivity.this, "Notice Posted Successfully", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(AddNoticeActivity.this, "No Rows Inserted", Toast.LENGTH_SHORT).show();
                            }
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                    else if (position.matches("Faculty")){
                        try {
                            String name = "Prof. " + getIntent().getExtras().getString("Name");
                            String subject = noticeSubject.getText().toString();
                            String description = noticeDescription.getText().toString();
                            InputStream iStream = null;
                            iStream = getContentResolver().openInputStream(selectedImage);
                            byte[] inputData = Utils1.getBytes(iStream);
                            long rowCount = dbHelper.saveNoticeDetails(name,subject,inputData,description);
                            if (rowCount>0){
                                noticeSubject.setText("");
                                noticeDescription.setText("");
                                noticeImage.setImageDrawable(null);
                                Toast.makeText(AddNoticeActivity.this, "Notice Posted Successfully", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(AddNoticeActivity.this, "No Rows Inserted", Toast.LENGTH_SHORT).show();
                            }
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 2) {
                selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                Log.d("Picture Selected", "Picture Path: "+picturePath+"");
                if (ContextCompat.checkSelfPermission(AddNoticeActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(AddNoticeActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Permission is not granted
                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(AddNoticeActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE) && ActivityCompat.shouldShowRequestPermissionRationale(AddNoticeActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.
                    } else {
                        // No explanation needed; request the permission
                        ActivityCompat.requestPermissions(AddNoticeActivity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        ActivityCompat.requestPermissions(AddNoticeActivity.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                    }
                } else {
                    // Permission has already been granted
                }
                noticeImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                cursor.close();
            }
        }
    }
}
