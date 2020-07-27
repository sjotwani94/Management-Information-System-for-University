package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.MyViewHolder> {
    private List<RecyclerListData> functionsList;
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView courseDesc, courseMarks,courseGrade;
        MyViewHolder(View view) {
            super(view);
            courseDesc = view.findViewById(R.id.course_desc);
            courseMarks = view.findViewById(R.id.course_marks);
            courseGrade = view.findViewById(R.id.course_grade);
        }
    }
    public RecyclerListAdapter(List<RecyclerListData> functionsList) {
        this.functionsList = functionsList;
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
    }

    @Override
    public int getItemCount() {
        return functionsList.size();
    }
}
