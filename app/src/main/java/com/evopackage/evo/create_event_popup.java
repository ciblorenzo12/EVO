package com.evopackage.evo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class create_event_popup extends AppCompatActivity {


    private Button button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_dialog);
        button.setOnClickListener(v -> {
            //openDialog();
        });

    }


}
