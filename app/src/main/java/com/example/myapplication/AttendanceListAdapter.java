package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AttendanceListAdapter extends RecyclerView.Adapter<AttendanceListAdapter.MyViewHolder> {
    private List<AttendanceListData> functionsList;
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView courseDesc, courseAttendance,coursePenalty;
        MyViewHolder(View view) {
            super(view);
            courseDesc = view.findViewById(R.id.course_desc);
            courseAttendance = view.findViewById(R.id.course_attendance);
            coursePenalty = view.findViewById(R.id.course_attend_category);
        }
    }
    public AttendanceListAdapter(List<AttendanceListData> functionsList) {
        this.functionsList = functionsList;
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
    }

    @Override
    public int getItemCount() {
        return functionsList.size();
    }
}
