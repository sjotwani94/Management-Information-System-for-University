package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.INotificationSideChannel;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MISData";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "registration_details";
    private static final String FACULTY_DETAILS = "faculty_registrations";
    private static final String STUDENT_DETAILS = "student_registrations";
    private static final String COURSE_DETAILS = "course_details";
    private static final String NOTICES_DETAILS = "notices_details";
    private static final String STUDENT_ENROLLED_COURSES = "student_enrolled_courses";
    private static final String COL_INDEX = "Id";
    private static final String COLUMN1 = "Name";
    private static final String COLUMN2 = "EMail";
    private static final String COLUMN3 = "Address";
    private static final String COLUMN4 = "Age";
    private static final String COLUMN5 = "ContactNo";
    private static final String COLUMN6 = "Gender";
    private static final String COLUMN7 = "Password";
    private static final String FAC_COLUMN1 = "Department";
    private static final String FAC_COLUMN2 = "Position";
    private static final String FAC_COLUMN3 = "JoinDate";
    private static final String STUD_COLUMN1 = "RollNumber";
    private static final String STUD_COLUMN2 = "Year";
    private static final String COURSE_COLUMN1 = "CourseCode";
    private static final String COURSE_COLUMN2 = "CourseName";
    private static final String COURSE_COLUMN3 = "CourseDescription";
    private static final String COURSE_COLUMN4 = "Semester";
    private static final String COURSE_COLUMN5 = "CoursePrerequisites";
    private static final String STUD_COURSE_1 = "CourseName";
    private static final String STUD_COURSE_2 = "ClassTest";
    private static final String STUD_COURSE_3 = "MidSem";
    private static final String STUD_COURSE_4 = "Assignments";
    private static final String STUD_COURSE_5 = "LabPracticals";
    private static final String STUD_COURSE_6 = "FinalExam";
    private static final String STUD_COURSE_7 = "TotalPresence";
    private static final String STUD_COURSE_8 = "TotalLectures";
    private static final String NOTICE_COLUMN1 = "NoticePostedBy";
    private static final String NOTICE_COLUMN2 = "NoticeSubject";
    private static final String NOTICE_COLUMN3 = "NoticeImage";
    private static final String NOTICE_COLUMN4 = "NoticeDescription";

    SQLiteDatabase db;
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_INDEX + " INTEGER PRIMARY KEY, " +
                COLUMN1 + " TEXT, " +
                COLUMN2 + " TEXT, " +
                COLUMN3 + " TEXT, " +
                COLUMN4 + " INTEGER, " +
                COLUMN5 + " LONG, " +
                COLUMN6 + " TEXT, " +
                COLUMN7 + " TEXT);";
        String sql1 = "CREATE TABLE " + FACULTY_DETAILS + " (" +
                COL_INDEX + " INTEGER PRIMARY KEY, " +
                COLUMN1 + " TEXT, " +
                COLUMN2 + " TEXT, " +
                COLUMN3 + " TEXT, " +
                COLUMN4 + " INTEGER, " +
                COLUMN5 + " LONG, " +
                COLUMN6 + " TEXT, " +
                FAC_COLUMN1 + " TEXT, " +
                FAC_COLUMN2 + " TEXT, " +
                FAC_COLUMN3 + " TEXT, " +
                COLUMN7 + " TEXT);";
        String sql2 = "CREATE TABLE " + STUDENT_DETAILS + " (" +
                COL_INDEX + " INTEGER PRIMARY KEY, " +
                COLUMN1 + " TEXT, " +
                COLUMN2 + " TEXT, " +
                COLUMN3 + " TEXT, " +
                COLUMN4 + " INTEGER, " +
                COLUMN5 + " LONG, " +
                COLUMN6 + " TEXT, " +
                FAC_COLUMN1 + " TEXT, " +
                STUD_COLUMN1 + " TEXT, " +
                STUD_COLUMN2 + " INTEGER, " +
                COLUMN7 + " TEXT);";
        String sql3 = "CREATE TABLE " + COURSE_DETAILS + " (" +
                COL_INDEX + " INTEGER PRIMARY KEY, " +
                COURSE_COLUMN1 + " TEXT UNIQUE, " +
                COURSE_COLUMN2 + " TEXT UNIQUE, " +
                COURSE_COLUMN3 + " TEXT, " +
                COURSE_COLUMN4 + " INTEGER, " +
                COURSE_COLUMN5 + " TEXT);";
        String sql4 = "CREATE TABLE " + STUDENT_ENROLLED_COURSES + " (" +
                COL_INDEX + " INTEGER PRIMARY KEY, " +
                STUD_COLUMN1 + " TEXT, " +
                STUD_COURSE_1 + " TEXT, " +
                STUD_COURSE_2 + " INTEGER DEFAULT 0, " +
                STUD_COURSE_3 + " INTEGER DEFAULT 0, " +
                STUD_COURSE_4 + " INTEGER DEFAULT 0, " +
                STUD_COURSE_5 + " INTEGER DEFAULT 0, " +
                STUD_COURSE_6 + " INTEGER DEFAULT 0, " +
                STUD_COURSE_7 + " INTEGER DEFAULT 0, " +
                STUD_COURSE_8 + " INTEGER DEFAULT 0);";
        String sql5 = "CREATE TABLE " + NOTICES_DETAILS + " (" +
                COL_INDEX + " INTEGER PRIMARY KEY, " +
                NOTICE_COLUMN1 + " TEXT, " +
                NOTICE_COLUMN2 + " TEXT, " +
                NOTICE_COLUMN3 + " BLOB NOT NULL, " +
                NOTICE_COLUMN4 + " TEXT);";
        db.execSQL(sql);
        db.execSQL(sql1);
        db.execSQL(sql2);
        db.execSQL(sql3);
        db.execSQL(sql4);
        db.execSQL(sql5);
        Log.d("DBHelper", "DataBase Created Successfully!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '"+ TABLE_NAME+"'");
        db.execSQL("DROP TABLE IF EXISTS '"+ FACULTY_DETAILS+"'");
        db.execSQL("DROP TABLE IF EXISTS '"+ STUDENT_DETAILS+"'");
        db.execSQL("DROP TABLE IF EXISTS '"+ COURSE_DETAILS+"'");
        db.execSQL("DROP TABLE IF EXISTS '"+ STUDENT_ENROLLED_COURSES+"'");
        onCreate(db);
    }

    public long saveRegistrationDetails(String name, String email, String address, int age, long contactno, String gender, String password){
        db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN1,name);
        contentValues.put(COLUMN2,email);
        contentValues.put(COLUMN3,address);
        contentValues.put(COLUMN4,age);
        contentValues.put(COLUMN5,contactno);
        contentValues.put(COLUMN6,gender);
        contentValues.put(COLUMN7,password);

        long rowCount = db.insert(TABLE_NAME,null,contentValues);
        db.close();
        return rowCount;
    }

    public boolean getSavedDetails(String Email, String Password){
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN2+"='"+Email+"' AND "+COLUMN7+"='"+Password+"'";
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.getCount()!=0){
            return true;
        }
        else {
            return false;
        }
    }

    public long saveFacultyRegistrationDetails(String name, String email, String address, int age, long contactno, String gender, String department, String position, String joindate, String password){
        db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN1,name);
        contentValues.put(COLUMN2,email);
        contentValues.put(COLUMN3,address);
        contentValues.put(COLUMN4,age);
        contentValues.put(COLUMN5,contactno);
        contentValues.put(COLUMN6,gender);
        contentValues.put(FAC_COLUMN1,department);
        contentValues.put(FAC_COLUMN2,position);
        contentValues.put(FAC_COLUMN3,joindate);
        contentValues.put(COLUMN7,password);

        long rowCount = db.insert(FACULTY_DETAILS,null,contentValues);
        db.close();
        return rowCount;
    }

    public long saveStudentRegistrationDetails(String name, String email, String address, int age, long contactno, String gender, String department, String rollno, long batch, String password){
        db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN1,name);
        contentValues.put(COLUMN2,email);
        contentValues.put(COLUMN3,address);
        contentValues.put(COLUMN4,age);
        contentValues.put(COLUMN5,contactno);
        contentValues.put(COLUMN6,gender);
        contentValues.put(FAC_COLUMN1,department);
        contentValues.put(STUD_COLUMN1,rollno);
        contentValues.put(STUD_COLUMN2,batch);
        contentValues.put(COLUMN7,password);

        long rowCount = db.insert(STUDENT_DETAILS,null,contentValues);
        db.close();
        return rowCount;
    }

    public Cursor getStudentRollNo(){
        db = getReadableDatabase();
        String [] columns=new String[]{STUD_COLUMN1};
        return db.query(STUDENT_DETAILS, columns, null, null, null, null, null);
    }

    public long saveStudentCourseDetails(String rollno, String coursecode){
        db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(STUD_COLUMN1,rollno);
        contentValues.put(STUD_COURSE_1,coursecode);

        long rowCount = db.insert(STUDENT_ENROLLED_COURSES,null,contentValues);
        db.close();
        return rowCount;
    }

    public long saveNoticeDetails(String noticeSender, String noticeSubject, byte[] noticeImage, String noticeDescription){
        db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTICE_COLUMN1,noticeSender);
        contentValues.put(NOTICE_COLUMN2,noticeSubject);
        contentValues.put(NOTICE_COLUMN3,noticeImage);
        contentValues.put(NOTICE_COLUMN4,noticeDescription);

        return db.insert(NOTICES_DETAILS,null,contentValues);
    }

    public Cursor getNoticeDetails(){
        db = getReadableDatabase();
        String [] columns=new String[]{NOTICE_COLUMN1,NOTICE_COLUMN2,NOTICE_COLUMN3,NOTICE_COLUMN4};
        return db.query(NOTICES_DETAILS,columns,null,null,null,null,null);
    }

    public boolean getFacultySavedDetails(String Email, String Password){
        String query = "SELECT * FROM "+FACULTY_DETAILS+" WHERE "+COLUMN2+"='"+Email+"' AND "+COLUMN7+"='"+Password+"'";
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.getCount()!=0){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean getStudentSavedDetails(String Email, String Password){
        String query = "SELECT * FROM "+STUDENT_DETAILS+" WHERE "+COLUMN2+"='"+Email+"' AND "+COLUMN7+"='"+Password+"'";
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.getCount()!=0){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean resetPassword(String Email, String Position, String NewPassword){
        ContentValues updateValues=new ContentValues();
        updateValues.put(COLUMN7, NewPassword);

        db = getWritableDatabase();
        if(Position.matches("Faculty")){
            db.update(FACULTY_DETAILS,
                    updateValues,
                    COLUMN2 + " = ?",
                    new String[]{Email});
        }
        else if (Position.matches("Student")){
            db.update(STUDENT_DETAILS,
                    updateValues,
                    COLUMN2 + " = ?",
                    new String[]{Email});
        }
        return true;
    }

    public long saveCourseDetails(String coursecode, String coursename, String coursedescription, int semester, String coursePrerequisites){
        db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COURSE_COLUMN1,coursecode);
        contentValues.put(COURSE_COLUMN2,coursename);
        contentValues.put(COURSE_COLUMN3,coursedescription);
        contentValues.put(COURSE_COLUMN4,semester);
        contentValues.put(COURSE_COLUMN5,coursePrerequisites);

        long rowCount = db.insert(COURSE_DETAILS,null,contentValues);
        db.close();
        return rowCount;
    }

    public Cursor getCourseDetails(){
        db = getReadableDatabase();
        String [] columns=new String[]{COURSE_COLUMN1,COURSE_COLUMN2,COURSE_COLUMN3,COURSE_COLUMN4,COURSE_COLUMN5};
        return db.query(COURSE_DETAILS, columns, null,null,null,null,null);
    }

    public Cursor getCourseDetailsWithFilter(String filter){
        db = getReadableDatabase();
        String [] columns=new String[]{COURSE_COLUMN1,COURSE_COLUMN2,COURSE_COLUMN3,COURSE_COLUMN4,COURSE_COLUMN5};
        return db.query(COURSE_DETAILS, columns, COURSE_COLUMN1 + " LIKE ?",
                new String[] { filter+"%" }, null, null, null);
    }

    public String getCourseName(String coursecode){
        db = getReadableDatabase();
        String[] columns=new String[]{COURSE_COLUMN2};
        Cursor cursor = db.query(COURSE_DETAILS, columns, COURSE_COLUMN1 + " = ?", new String[]{coursecode},null,null,null);
        cursor.moveToFirst();
        return cursor.getString(0);
    }

    public boolean removeCourse(String rowIndex) {
        db = getWritableDatabase();
        int result = db.delete(COURSE_DETAILS,
                COURSE_COLUMN1+" = ?",
                new String[]{rowIndex});
        int result1 = db.delete(STUDENT_ENROLLED_COURSES,
                STUD_COURSE_1+" = ?",
                new String[]{rowIndex});
        db.close();
        if (result>0 && result1>0)
        {
            return true;
        }
        else {
            return false;
        }
    }

    public int updateMarks(String rollno, String coursecode, Integer classtest, Integer midsem, Integer assignments, Integer labprac, Integer finalexam) {

        ContentValues updateValues=new ContentValues();
        updateValues.put(STUD_COURSE_2, classtest);
        updateValues.put(STUD_COURSE_3, midsem);
        updateValues.put(STUD_COURSE_4, assignments);
        updateValues.put(STUD_COURSE_5, labprac);
        updateValues.put(STUD_COURSE_6, finalexam);

        db = getWritableDatabase();

        return db.update(STUDENT_ENROLLED_COURSES,
                updateValues,
                STUD_COLUMN1 + " = ? AND " + STUD_COURSE_1 + " = ?",
                new String[]{rollno, coursecode});
    }

    public int updateAttendance(String rollno, String coursecode, Integer totalpresent, Integer totalclasses) {

        ContentValues updateValues=new ContentValues();
        updateValues.put(STUD_COURSE_7, totalpresent);
        updateValues.put(STUD_COURSE_8, totalclasses);

        db = getWritableDatabase();

        return db.update(STUDENT_ENROLLED_COURSES,
                updateValues,
                STUD_COLUMN1 + " = ? AND " + STUD_COURSE_1 + " = ?",
                new String[]{rollno, coursecode});
    }

    public Cursor getMarks(String rollno){
        db = getReadableDatabase();
        String [] columns=new String[]{STUD_COURSE_1,STUD_COURSE_2,STUD_COURSE_3,STUD_COURSE_4,STUD_COURSE_5,STUD_COURSE_6};
        return db.query(STUDENT_ENROLLED_COURSES, columns, STUD_COLUMN1 + " = ?", new String[]{rollno}, null, null, null);
    }

    public Cursor getMarksByCourse(String rollno, String coursecode){
        db = getReadableDatabase();
        String [] columns=new String[]{STUD_COURSE_2,STUD_COURSE_3,STUD_COURSE_4,STUD_COURSE_5,STUD_COURSE_6};
        return db.query(STUDENT_ENROLLED_COURSES, columns, STUD_COLUMN1 + " = ? AND " + STUD_COURSE_1 + " = ?", new String[]{rollno,coursecode}, null, null, null);
    }

    public Cursor getAttendanceByCourse(String rollno, String coursecode){
        db = getReadableDatabase();
        String [] columns=new String[]{STUD_COURSE_7,STUD_COURSE_8};
        return db.query(STUDENT_ENROLLED_COURSES, columns, STUD_COLUMN1 + " = ? AND " + STUD_COURSE_1 + " = ?", new String[]{rollno,coursecode}, null, null, null);
    }

    public Cursor getAttendance(String rollno){
        db = getReadableDatabase();
        String [] columns=new String[]{STUD_COURSE_1,STUD_COURSE_7,STUD_COURSE_8};
        return db.query(STUDENT_ENROLLED_COURSES, columns, STUD_COLUMN1 + " = ?", new String[]{rollno}, null, null, null);
    }

    public Cursor getStudentByCourse(String coursecode){
        db = getReadableDatabase();
        String [] columns=new String[]{STUD_COLUMN1};
        return db.query(STUDENT_ENROLLED_COURSES, columns, STUD_COURSE_1 + " = ?", new String[]{coursecode}, null, null, null);
    }

    public Cursor getRequestedData(String sql) {
        db = getReadableDatabase();
        return db.rawQuery(sql,null);
    }

    public Cursor getAdminContacts(){
        db = getReadableDatabase();
        String [] columns=new String[]{COLUMN1,COLUMN5,COLUMN2};
        return db.query(TABLE_NAME, columns, null, null, null, null, null);
    }

    public Cursor getFacultyContacts(){
        db = getReadableDatabase();
        String [] columns=new String[]{COLUMN1,COLUMN5,COLUMN2};
        return db.query(FACULTY_DETAILS, columns, null, null, null, null, null);
    }

    public Cursor getStudentContacts(){
        db = getReadableDatabase();
        String [] columns=new String[]{COLUMN1,COLUMN5,COLUMN2};
        return db.query(STUDENT_DETAILS, columns, null, null, null, null, null);
    }
}
