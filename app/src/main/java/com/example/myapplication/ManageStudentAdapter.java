package com.example.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class ManageStudentAdapter extends RecyclerView.Adapter<ManageStudentAdapter.MyViewHolder> {
    private List<ManageStudentData> functionsList;
    DBHelper dbHelper;
    Cursor cursor;
    Context context;
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView studentName, studentEmail, studentDepartment;
        LinearLayout studentContainer;
        MyViewHolder(View view) {
            super(view);
            studentName = view.findViewById(R.id.student_name);
            studentEmail = view.findViewById(R.id.email_student);
            studentDepartment = view.findViewById(R.id.dept_and_yoa);
            studentContainer = view.findViewById(R.id.student_container);
        }
    }
    public ManageStudentAdapter(List<ManageStudentData> functionsList, Context context) {
        this.functionsList = functionsList;
        this.context = context;
    }
    @NonNull
    @Override
    public ManageStudentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.student_file,parent,false);
        return new ManageStudentAdapter.MyViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ManageStudentAdapter.MyViewHolder holder, final int position) {
        final ManageStudentData listData = functionsList.get(position);
        holder.studentName.setText(listData.getStudentName());
        holder.studentEmail.setText(listData.getStudentEmail());
        holder.studentDepartment.setText(listData.getStudentDept()+" ("+listData.getStudentBatch()+")");
        holder.studentContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = v;
                dbHelper = new DBHelper(context);
                cursor = dbHelper.getStudentRegistrationDetails(listData.getStudentEmail());
                cursor.moveToFirst();
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle(listData.getStudentName()+" ("+cursor.getString(8)+")");
                dialog.setMessage("Name: "+cursor.getString(1)+"\n"+
                        "E-Mail: "+cursor.getString(2)+"\n"+
                        "Contact: "+cursor.getLong(5)+"\n"+
                        "Department: "+cursor.getString(7)+"\n"+
                        "Year of Admission: "+cursor.getLong(9));
                dialog.setPositiveButton("Go Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                dialog.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.removeStudentEntry(cursor.getInt(0));
                        notifyDataSetChanged();
                        Snackbar.make(view,"Entry of "+listData.getStudentName()+" Deleted",Snackbar.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });
                dialog.setNeutralButton("Update Details", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(context,UpdateStudent.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("Email",listData.getStudentEmail());
                        bundle.putInt("Index",cursor.getInt(0));
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                        dialog.dismiss();
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
