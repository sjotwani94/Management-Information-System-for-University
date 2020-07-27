package com.example.myapplication;

public class RecyclerListData {
    private String courseDesc;
    private String courseMarks;
    private String courseGrade;

    public RecyclerListData(String courseDesc, String courseMarks, String courseGrade) {
        this.courseDesc = courseDesc;
        this.courseMarks = courseMarks;
        this.courseGrade = courseGrade;
    }

    public String getCourseDesc() {
        return courseDesc;
    }

    public void setCourseDesc(String courseDesc) {
        this.courseDesc = courseDesc;
    }

    public String getCourseMarks() {
        return courseMarks;
    }

    public void setCourseMarks(String courseMarks) {
        this.courseMarks = courseMarks;
    }

    public String getCourseGrade() {
        return courseGrade;
    }

    public void setCourseGrade(String courseGrade) {
        this.courseGrade = courseGrade;
    }
}
