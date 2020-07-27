package com.example.myapplication;

public class AttendanceListData {
    private String courseDesc;
    private String courseAttendance;
    private String coursePenaltyCategory;

    public AttendanceListData(String courseDesc, String courseAttendance, String coursePenaltyCategory) {
        this.courseDesc = courseDesc;
        this.courseAttendance = courseAttendance;
        this.coursePenaltyCategory = coursePenaltyCategory;
    }

    public String getCourseDesc() {
        return courseDesc;
    }

    public void setCourseDesc(String courseDesc) {
        this.courseDesc = courseDesc;
    }

    public String getCourseAttendance() {
        return courseAttendance;
    }

    public void setCourseAttendance(String courseAttendance) {
        this.courseAttendance = courseAttendance;
    }

    public String getCoursePenaltyCategory() {
        return coursePenaltyCategory;
    }

    public void setCoursePenaltyCategory(String coursePenaltyCategory) {
        this.coursePenaltyCategory = coursePenaltyCategory;
    }


}
