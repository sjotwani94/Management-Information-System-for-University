package com.example.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.MyViewHolder> {
    private List<RecyclerListData> functionsList;
    DBHelper dbHelper;
    Cursor cursor;
    String RollNo;
    Context context;
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView courseDesc, courseMarks,courseGrade;
        LinearLayout resultContainer;
        MyViewHolder(View view) {
            super(view);
            courseDesc = view.findViewById(R.id.course_desc);
            courseMarks = view.findViewById(R.id.course_marks);
            courseGrade = view.findViewById(R.id.course_grade);
            resultContainer = view.findViewById(R.id.result_container);
        }
    }
    public RecyclerListAdapter(List<RecyclerListData> functionsList, Context context, String RollNo) {
        this.functionsList = functionsList;
        this.context = context;
        this.RollNo = RollNo;
    }
    @NonNull
    @Override
    public RecyclerListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_file,parent,false);
        return new MyViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerListAdapter.MyViewHolder holder, int position) {
        final RecyclerListData listData = functionsList.get(position);
        holder.courseDesc.setText(listData.getCourseDesc());
        holder.courseMarks.setText(listData.getCourseMarks());
        holder.courseGrade.setText(listData.getCourseGrade());
        holder.resultContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper = new DBHelper(context);
                cursor = dbHelper.getMarksByCourse(RollNo,listData.getCourseDesc().substring(0,5));
                cursor.moveToFirst();
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle(listData.getCourseDesc());
                dialog.setMessage("Class Test: "+cursor.getInt(0)+"/30\n"+
                "Mid Sem Exam: "+cursor.getInt(1)+"/40\n"+
                "Assignment Marks: "+cursor.getInt(2)+"/30\n"+
                "Laboratory Practicals' Marks: "+cursor.getInt(3)+"/100\n"+
                "Final Exam Marks: "+cursor.getInt(4)+"/100");
                dialog.setPositiveButton("Go Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return functionsList.size();
    }
}
