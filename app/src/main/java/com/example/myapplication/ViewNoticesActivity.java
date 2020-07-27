package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ViewNoticesActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    DBHelper dbHelper;
    Cursor cursor;
    List<String> noticeSender = new ArrayList<String>();
    List<String> noticeSubject = new ArrayList<String>();
    List<byte[]> noticeImage = new ArrayList<byte[]>();
    List<String> noticeDescription = new ArrayList<String>();
    private List<NoticeListData> functionsList = new ArrayList<NoticeListData>();
    private NoticeListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notices);
        recyclerView=findViewById(R.id.list_notices);
        floatingActionButton=findViewById(R.id.fab);
        dbHelper=new DBHelper(this);
        cursor = dbHelper.getNoticeDetails();
        cursor.moveToFirst();
        for (int i=0;i<cursor.getCount();i++){
            noticeSender.add(cursor.getString(0));
            noticeSubject.add(cursor.getString(1));
            noticeImage.add(cursor.getBlob(2));
            noticeDescription.add(cursor.getString(3));
            cursor.moveToNext();
        }
        cursor.close();
        for (int len=0;len<noticeSender.size();len++){
            functionsList.add(new NoticeListData(noticeSender.get(len),noticeSubject.get(len),noticeImage.get(len),noticeDescription.get(len)));
        }
        adapter = new NoticeListAdapter(this,functionsList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        if (getIntent().getExtras().getString("Position").matches("Student")){
            floatingActionButton.setVisibility(View.GONE);
        }
        else {
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(),AddNoticeActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("Name",getIntent().getExtras().getString("Name"));
                    bundle.putString("Position",getIntent().getExtras().getString("Position"));
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }
}
