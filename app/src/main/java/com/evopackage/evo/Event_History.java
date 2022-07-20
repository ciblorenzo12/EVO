package com.evopackage.evo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.os.Bundle;
import android.text.Layout;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Event_History extends AppCompatActivity {

    LinearLayout _historyLayout = findViewById(R.id.HistoryLayout);

    LinearLayout.LayoutParams _parameters = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);



    TextView test = new TextView(this);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_history);




        for(int i =0; i < 5 ; i++){
            test.setLayoutParams(_parameters);
            test.setText("test " + i);
            this._historyLayout.addView(test);
        }
    }
}