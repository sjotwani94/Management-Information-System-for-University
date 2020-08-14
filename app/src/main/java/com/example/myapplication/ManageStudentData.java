package com.example.myapplication;

public class ManageStudentData {
    private String studentName;
    private String studentEmail;
    private String studentDept;
    private int studentBatch;

    public ManageStudentData(String studentName, String studentEmail, String studentDept, int studentBatch) {
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.studentDept = studentDept;
        this.studentBatch = studentBatch;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getStudentDept() {
        return studentDept;
    }

    public void setStudentDept(String studentDept) {
        this.studentDept = studentDept;
    }

    public int getStudentBatch() {
        return studentBatch;
    }

    public void setStudentBatch(int studentBatch) {
        this.studentBatch = studentBatch;
    }
}
