package com.mysummerproject.secattempt;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class HomePage extends AppCompatActivity {

    private  ListView myListView;
    static final String[] gradesList = new String[] { "Grade 9", "Grade 10", "Grade 11",
            "Grade 12"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        ///////the info button
        Button button = (Button)findViewById(R.id.infobutt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this,Pop.class));
            }
        });
        ///////

        myListView =(ListView)findViewById(R.id.gradeListView);
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, gradesList);
        myListView.setAdapter(adapter);

        myListView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String myGrade = (String)((TextView) view).getText();

                final Intent intent;
                intent = new Intent(HomePage.this, MainActivity.class);
                intent.putExtra("myGrade", myGrade);
                HomePage.this.startActivity(intent);
            }
        });
    }

    public void wci(View view){
        Intent wciHomepage = new Intent(Intent.ACTION_VIEW, Uri.parse("https://wci.wrdsb.ca"));
        startActivity(wciHomepage);
    }
}
