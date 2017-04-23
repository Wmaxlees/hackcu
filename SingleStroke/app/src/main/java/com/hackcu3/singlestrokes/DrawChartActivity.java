package com.hackcu3.singlestrokes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class DrawChartActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(DrawChartActivity.class.getName(), "Test");
        setContentView(new TouchEventView(this, null));


    }


}

