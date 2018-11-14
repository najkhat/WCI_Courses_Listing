package com.mysummerproject.secattempt;

import android.os.Bundle;
import android.app.Activity;
import android.util.DisplayMetrics;

public class Pop extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.infopopwindow);

        //to manage the popup window display
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.6));

    }
}
