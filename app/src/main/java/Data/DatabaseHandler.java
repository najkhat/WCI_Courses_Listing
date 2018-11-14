package Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;
import Model.Courses;
import Utils.Util;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    //create tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        try
        {
            //SQL
            String CREATE_COURSE_TABLE = "CREATE TABLE " + Util.TABLE_NAME + "(" +
                    Util.KEY_CODE + " TEXT PRIMARY KEY," + Util.KEY_NAME + " TEXT," +
                    Util.KEY_GRADE + " INTEGER," + Util.KEY_SUBJECT + " TEXT," +
                    Util.KEY_TYPE + " TEXT," + Util.KEY_DESCRIPTION + " TEXT)";

            db.execSQL(CREATE_COURSE_TABLE);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //deleting the table
        db.execSQL("DROP TABLE IF EXISTS " + Util.TABLE_NAME);

        //create the table again
        onCreate(db);
    }

    public void addCourse(Courses course) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put(Util.KEY_CODE, course.getCode());
        value.put(Util.KEY_NAME, course.getName());
        value.put(Util.KEY_GRADE, course.getGrade());
        value.put(Util.KEY_SUBJECT, course.getSubject());
        value.put(Util.KEY_TYPE, course.getType());
        value.put(Util.KEY_DESCRIPTION, course.getDescription());

        //insert to row
        db.insert(Util.TABLE_NAME, null, value);
        db.close();//close db connection
    }

    public Courses getCourse(String code) {

        SQLiteDatabase db = this.getReadableDatabase();

        String selectAll = "SELECT * FROM " + Util.TABLE_NAME + " WHERE " + Util.KEY_CODE +
                "  =  '" + code + "'";

        Cursor cursor = db.rawQuery(selectAll, null);


        if (cursor != null) {
            cursor.moveToFirst();
        }

        Courses course = new Courses(cursor.getString(0), cursor.getString(1),
                Integer.parseInt(cursor.getString(2)), cursor.getString(3),
                cursor.getString(4), cursor.getString(5));

        return (course);
    }

    //function to get all courses
    public List<Courses> getAllCourses() {

        SQLiteDatabase db = this.getReadableDatabase();
        List<Courses> coursesList = new ArrayList<>();

        //select all courses
        String selectAll = "SELECT * FROM " + Util.DATABASE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);

        //looping through courses
        if (cursor.moveToFirst()) {
            do {
                Courses course = new Courses();

                course.setCode(cursor.getString(0));
                course.setName(cursor.getString(1));
                course.setGrade(Integer.parseInt(cursor.getString(2)));
                course.setSubject(cursor.getString(3));
                course.setType(cursor.getString(4));
                course.setDescription(cursor.getString(5));

                //add course object to our course list
                coursesList.add(course);

            } while (cursor.moveToNext());
        }
        return (coursesList);
    }

    //function to get all the courses names
    public List<String> getCourseNames() {

        SQLiteDatabase db = this.getReadableDatabase();
        List<String> result = new ArrayList<>();

        //select all courses
        String selectAll = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);

        //looping through courses
        if (cursor.moveToFirst()) {
            do {
                result.add(cursor.getString(cursor.getColumnIndex(Util.KEY_CODE))+ " : " +
                        cursor.getString(cursor.getColumnIndex(Util.KEY_NAME)));
            } while (cursor.moveToNext());
        }
        return (result);
    }

    //function to get all courses by their names
    public List<Courses> getCourseByName(String name) {

        SQLiteDatabase db = this.getReadableDatabase();
        List<Courses> result = new ArrayList<>();

        //select all courses
        String selectAll = "SELECT * FROM " + Util.DATABASE_NAME + " WHERE " + Util.KEY_NAME +
                " LIKE %" + name + "%";
        Cursor cursor = db.rawQuery(selectAll, null);

        //looping through courses
        if (cursor.moveToFirst()) {
            do {
                Courses course = new Courses();

                course.setCode(cursor.getString(0));
                course.setName(cursor.getString(1));
                course.setGrade(Integer.parseInt(cursor.getString(2)));
                course.setSubject(cursor.getString(3));
                course.setType(cursor.getString(4));
                course.setDescription(cursor.getString(5));

                result.add(course);

            } while (cursor.moveToNext());
        }
        return (result);
    }

    public List<String> getCourseByGrade(String grade) {

        SQLiteDatabase db = this.getReadableDatabase();
        List<String> result = new ArrayList<>();

        //select all courses
        String selectAll = "SELECT * FROM " + Util.TABLE_NAME + " WHERE " + Util.KEY_GRADE +
                " = " + grade;
        Cursor cursor = db.rawQuery(selectAll, null);

        //looping through courses
        if (cursor.moveToFirst()) {
            do {
                result.add(cursor.getString(cursor.getColumnIndex(Util.KEY_CODE))+ " : " +
                        cursor.getString(cursor.getColumnIndex(Util.KEY_NAME)));
            } while (cursor.moveToNext());
        }
        return (result);
    }

    public List<String> getCourseByGradeSQL(String grade, String mySQL) {

        SQLiteDatabase db = this.getReadableDatabase();
        List<String> result = new ArrayList<>();

        //select all courses
        String selectAll = "SELECT * FROM " + Util.TABLE_NAME + " WHERE " + Util.KEY_GRADE +
                " = " + grade + mySQL;
        Cursor cursor = db.rawQuery(selectAll, null);

        //looping through courses
        if (cursor.moveToFirst()) {
            do {
                result.add(cursor.getString(cursor.getColumnIndex(Util.KEY_CODE))+ " : " +
                        cursor.getString(cursor.getColumnIndex(Util.KEY_NAME)));
            } while (cursor.moveToNext());
        }
        return (result);
    }

    //adding this to manage spinner one
    //made changes so the two spinners can work together
    public List<String> getSubjects(String grade, String mySQL) {

        SQLiteDatabase db = this.getReadableDatabase();
        List<String> result = new ArrayList<>();

        //select all courses
        String selectAll = "SELECT DISTINCT " + Util.KEY_SUBJECT + " FROM " + Util.TABLE_NAME+
                " WHERE " + Util.KEY_GRADE + " = " + grade + mySQL;
        Cursor cursor = db.rawQuery(selectAll, null);

        //looping through courses
        if (cursor.moveToFirst()) {
            do {
                result.add(cursor.getString(cursor.getColumnIndex(Util.KEY_SUBJECT)));
            } while (cursor.moveToNext());
        }
        return (result);
    }

    //adding this to manage spinner two
    public List<String> getTypes(String grade, String mySQL) {

        SQLiteDatabase db = this.getReadableDatabase();
        List<String> result = new ArrayList<>();

        //select all courses
        String selectAll = "SELECT DISTINCT " + Util.KEY_TYPE + " FROM " + Util.TABLE_NAME+
                " WHERE " + Util.KEY_GRADE + " = " + grade + mySQL;
        Cursor cursor = db.rawQuery(selectAll, null);

        //looping through courses
        if (cursor.moveToFirst()) {
            do {
                result.add(cursor.getString(cursor.getColumnIndex(Util.KEY_TYPE)));
            } while (cursor.moveToNext());
        }
        return (result);
    }
}
