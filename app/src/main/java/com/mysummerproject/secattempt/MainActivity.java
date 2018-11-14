package com.mysummerproject.secattempt;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import com.mancj.materialsearchbar.MaterialSearchBar;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import Data.DatabaseHandler;
import Model.Courses;
import Utils.Util;

import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements OnItemSelectedListener {

    private List<Courses> courses = new ArrayList<>();
    DatabaseHandler db;
    List<String> courseNames = new ArrayList<>();
    List<String> subjectList = new ArrayList<>();
    List<String> typeList = new ArrayList<>();
    String subject = "";
    String type = "";
    Spinner spinner;
    Spinner spinner2;
    ArrayAdapter<String> dataAdapter;
    ArrayAdapter<String> dataAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init our database
        db = new DatabaseHandler(this);

        //reading the passed info from homepage
        Bundle bundle = getIntent().getExtras();
        String myGrade = bundle.getString("myGrade");
        String grade = myGrade.substring(5);

        //spinner one element
        spinner = (Spinner) findViewById(R.id.subjectPicker);
        spinner.setOnItemSelectedListener(this);
        //because the first time I want the spinner retrieves everything I send mySQL an empty string
        subjectList = db.getSubjects(grade, "");
        //for position 0 place ALL option in the list
        subjectList.add(0, "All");
        dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, subjectList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        //spinner two elements
        spinner2 = (Spinner) findViewById(R.id.typePicker);
        spinner2.setOnItemSelectedListener(this);
        //because the first time I want the spinner retrieves everything I send mySQL an empty string
        typeList = db.getTypes(grade, "");
        //for position 0 place ALL option in the list
        typeList.add(0, "All");
        dataAdapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, typeList);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //init gridview and searchbar
        GridView gv = (GridView) findViewById(R.id.mGridView);
        MaterialSearchBar searchBar = (MaterialSearchBar) findViewById(R.id.searchBar);
        searchBar.setHint("Search...");
        searchBar.setSpeechMode(false);

        readCoursesData();

        /* I changed this because now I am browsing the courses based on their grades from the
        main page */
        //List<String> courseNames = db.getCourseNames();
        List<String> courseNames = db.getCourseByGrade(grade);

        final ArrayAdapter adapter = new ArrayAdapter<String>
                (this,android.R.layout.simple_list_item_1, courseNames);
        gv.setAdapter(adapter);

        //text change listener
        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //search filter
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

              /* to pass objects of courses type from one main_activity to activity2 */
                String CCode = adapter.getItem(position).toString().substring(0,6);
                Courses myCourse = db.getCourse(CCode);

                final Intent intent;
                intent = new Intent(MainActivity.this, Activity2.class);
                intent.putExtra("myCourse", (Parcelable) myCourse);
                MainActivity.this.startActivity(intent);
            }
        });
    }

    //importing csv data file
    private void readCoursesData() {

        InputStream is = getResources().openRawResource(R.raw.data);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is,
                Charset.forName("UTF-8")));

        String line = "";
        try {

            //to step over the title line in data file
            reader.readLine();
            while ((line = reader.readLine()) != null) {

                //split by comma
                String[] token = line.split("\t");

                Courses newCourse = new Courses();

                //read the data
                newCourse.setCode(token[0]);
                newCourse.setName(token[1]);
                newCourse.setGrade(Integer.parseInt(token[2]));
                newCourse.setSubject(token[3]);
                newCourse.setType(token[4]);
                newCourse.setDescription(token[5]);

                db.addCourse(newCourse);
            }
        } catch (IOException e) {

            Log.wtf("MyActivity", "Error reading data file on line " + line, e);
            e.printStackTrace();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Bundle bundle = getIntent().getExtras();
        String myGrade = bundle.getString("myGrade");
        String grade = myGrade.substring(5);

       String mySQL = "";

        switch (parent.getId()){

            case R.id.subjectPicker:
                subject = (String)((TextView) view).getText();
                mySQL = " AND " + Util.KEY_SUBJECT + " = '" + subject + "'";

                //an option to clear subject filter
                if(subject == "All"){
                    subject = "";
                    mySQL = "";
                }

                List<String> typeListUpdate = new ArrayList<>();

                //filter types based on the subject selected
                typeListUpdate = db.getTypes(grade, mySQL);
                typeList.clear();
                typeList.add("All");
                for(int i = 0; i<typeListUpdate.size(); i++){
                    typeList.add(typeListUpdate.get(i));
                    dataAdapter2.notifyDataSetChanged();
                }
                break;

            case R.id.typePicker:
                type = (String)((TextView) view).getText();
                mySQL = " AND " + Util.KEY_TYPE + " = '" + type + "'";

                //an option to clear type filter
                if(type == "All"){
                    type = "";
                    mySQL = "";
                }

                List<String> subjectListUpdate = new ArrayList<>();

                //filtering subjects based on the type selected
                subjectListUpdate = db.getSubjects(grade, mySQL);
                subjectList.clear();
                subjectList.add("All");
                for(int i = 0; i<subjectListUpdate.size(); i++){
                    subjectList.add(subjectListUpdate.get(i));
                    dataAdapter.notifyDataSetChanged();
                }
                break;
        }

        GridView gv = (GridView) findViewById(R.id.mGridView);
        MaterialSearchBar searchBar = (MaterialSearchBar) findViewById(R.id.searchBar);
        searchBar.setHint("Search...");
        searchBar.setSpeechMode(false);

        //adding filters for subject and type to browse our database
        mySQL = "";

        if(subject.length() > 0){
            mySQL = " AND " + Util.KEY_SUBJECT + " = '" + subject + "' ";
        }
        if(type.length() > 0){
            mySQL = mySQL + " AND " + Util.KEY_TYPE + " = '" + type + "' ";
        }
        List<String> courseNames = db.getCourseByGradeSQL(grade, mySQL);


        final ArrayAdapter adapter = new ArrayAdapter<String>
                (this,android.R.layout.simple_list_item_1, courseNames);
        gv.setAdapter(adapter);

        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //search filter
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                /* to pass objects of courses type from one main_activity to activity2 */
                String CCode = adapter.getItem(position).toString().substring(0,6);
                Courses myCourse = db.getCourse(CCode);

                final Intent intent;
                intent = new Intent(MainActivity.this, Activity2.class);
                intent.putExtra("myCourse", (Parcelable) myCourse);
                MainActivity.this.startActivity(intent);
            }
        });

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}