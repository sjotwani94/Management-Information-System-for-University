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

import java.util.List;

public class AttendanceListAdapter extends RecyclerView.Adapter<AttendanceListAdapter.MyViewHolder> {
    private List<AttendanceListData> functionsList;
    DBHelper dbHelper;
    Cursor cursor;
    String RollNo;
    Context context;
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView courseDesc, courseAttendance,coursePenalty;
        LinearLayout attendanceContainer;
        MyViewHolder(View view) {
            super(view);
            courseDesc = view.findViewById(R.id.course_desc);
            courseAttendance = view.findViewById(R.id.course_attendance);
            coursePenalty = view.findViewById(R.id.course_attend_category);
            attendanceContainer = view.findViewById(R.id.attendance_container);
        }
    }
    public AttendanceListAdapter(List<AttendanceListData> functionsList, Context context, String RollNo) {
        this.functionsList = functionsList;
        this.context = context;
        this.RollNo = RollNo;
    }
    @NonNull
    @Override
    public AttendanceListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.attendance_file,parent,false);
        return new MyViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceListAdapter.MyViewHolder holder, int position) {
        final AttendanceListData listData = functionsList.get(position);
        holder.courseDesc.setText(listData.getCourseDesc());
        holder.courseAttendance.setText(listData.getCourseAttendance());
        holder.coursePenalty.setText(listData.getCoursePenaltyCategory());
        holder.attendanceContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper = new DBHelper(context);
                cursor = dbHelper.getAttendanceByCourse(RollNo,listData.getCourseDesc().substring(0,5));
                cursor.moveToFirst();
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle(listData.getCourseDesc());
                dialog.setMessage("Total Presence: "+cursor.getInt(0)+"/"+cursor.getInt(1)+" Lectures\n"+
                        "Total Absence: "+(cursor.getInt(1) - cursor.getInt(0))+"/"+cursor.getInt(1)+" Lectures");
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
