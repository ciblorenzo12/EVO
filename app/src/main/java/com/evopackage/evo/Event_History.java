package com.evopackage.evo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.os.Bundle;
import android.text.Layout;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Event_History extends AppCompatActivity {

    LinearLayout _historyLayout = findViewById(R.id.HistoryLayout);

    RelativeLayout _relativeEventInfo;

    LinearLayout.LayoutParams _linearParameters = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

    RelativeLayout.LayoutParams _relativeParameters = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

    TextView test;



//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_event_history);
//
//
//
//
//        for(int i =0; i < 5 ; i++){
//            test = new TextView(this);
//            test.setLayoutParams(_relativeParameters);
//
//            _relativeEventInfo = new RelativeLayout(this);
//            _relativeEventInfo.setLayoutParams(_linearParameters);
//
//            _relativeEventInfo.addView(test);
//            _historyLayout.addView(_relativeEventInfo);
//
//
//            test.setText("test " + i);
//            this._historyLayout.addView(test);
//        }
//    }
}