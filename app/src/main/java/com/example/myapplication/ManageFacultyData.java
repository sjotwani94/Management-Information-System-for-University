package com.example.myapplication;

public class ManageFacultyData {
    private String facultyName;
    private String facultyEmail;
    private String facultyDept;
    private String facultyDesg;

    public ManageFacultyData(String facultyName, String facultyEmail, String facultyDept, String facultyDesg) {
        this.facultyName = facultyName;
        this.facultyEmail = facultyEmail;
        this.facultyDept = facultyDept;
        this.facultyDesg = facultyDesg;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getFacultyEmail() {
        return facultyEmail;
    }

    public void setFacultyEmail(String facultyEmail) {
        this.facultyEmail = facultyEmail;
    }

    public String getFacultyDept() {
        return facultyDept;
    }

    public void setFacultyDept(String facultyDept) {
        this.facultyDept = facultyDept;
    }

    public String getFacultyDesg() {
        return facultyDesg;
    }

    public void setFacultyDesg(String facultyDesg) {
        this.facultyDesg = facultyDesg;
    }
}
