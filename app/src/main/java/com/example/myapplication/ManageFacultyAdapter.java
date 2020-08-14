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

public class ManageFacultyAdapter extends RecyclerView.Adapter<ManageFacultyAdapter.MyViewHolder> {
    private List<ManageFacultyData> functionsList;
    DBHelper dbHelper;
    Cursor cursor;
    Context context;
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView facultyName, facultyEmail, facultyDepartment;
        LinearLayout facultyContainer;
        MyViewHolder(View view) {
            super(view);
            facultyName = view.findViewById(R.id.faculty_name);
            facultyEmail = view.findViewById(R.id.email_faculty);
            facultyDepartment = view.findViewById(R.id.dept_desg);
            facultyContainer = view.findViewById(R.id.faculty_container);
        }
    }
    public ManageFacultyAdapter(List<ManageFacultyData> functionsList, Context context) {
        this.functionsList = functionsList;
        this.context = context;
    }
    @NonNull
    @Override
    public ManageFacultyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.faculty_file,parent,false);
        return new ManageFacultyAdapter.MyViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ManageFacultyAdapter.MyViewHolder holder, final int position) {
        final ManageFacultyData listData = functionsList.get(position);
        holder.facultyName.setText(listData.getFacultyName());
        holder.facultyEmail.setText(listData.getFacultyEmail());
        holder.facultyDepartment.setText(listData.getFacultyDept()+" ("+listData.getFacultyDesg()+")");
        holder.facultyContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = v;
                dbHelper = new DBHelper(context);
                cursor = dbHelper.getFacultyRegistrationDetails(listData.getFacultyEmail());
                cursor.moveToFirst();
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle(listData.getFacultyName());
                dialog.setMessage("Name: "+cursor.getString(1)+"\n"+
                        "E-Mail: "+cursor.getString(2)+"\n"+
                        "Contact: "+cursor.getLong(5)+"\n"+
                        "Department: "+cursor.getString(7)+"\n"+
                        "Designation: "+cursor.getString(8));
                dialog.setPositiveButton("Go Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                dialog.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.removeFacultyEntry(cursor.getInt(0));
                        notifyDataSetChanged();
                        Snackbar.make(view,"Entry of "+listData.getFacultyName()+" Deleted",Snackbar.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });
                dialog.setNeutralButton("Update Details", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(context,UpdateFaculty.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("Email",listData.getFacultyEmail());
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
