package com.mysummerproject.secattempt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import Model.Courses;

public class Activity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Courses myCourse = (Courses) getIntent().getParcelableExtra("myCourse");

        String code = myCourse.getCode();
        String name = myCourse.getName();
        String subject = myCourse.getSubject();
        int grade = myCourse.getGrade();
        String type = myCourse.getType();
        String description = myCourse.getDescription();


        TextView textView1 = (TextView) findViewById(R.id.code);
        TextView textView2 = (TextView) findViewById(R.id.name);
        TextView textView3 = (TextView) findViewById(R.id.subject);
        TextView textView4 = (TextView) findViewById(R.id.grade);
        TextView textView5 = (TextView) findViewById(R.id.type);
        TextView textView6 = (TextView) findViewById(R.id.description);

        textView1.setText(code);
        textView2.setText(name);
        textView3.setText("Subject: " + subject);
        textView4.setText("Grade: "+Integer.toString(grade));
        textView5.setText("Course Category:" + type);
        textView6.setText(description);
        //this is to make long descriptions scrollable
        textView6.setMovementMethod(new ScrollingMovementMethod());
    }
}
