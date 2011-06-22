package com.icepack.MeetUp1;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class dummyClass1 extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textview = new TextView(this);
        textview.setText("subclass startup");
        setContentView(textview);
    }

}
