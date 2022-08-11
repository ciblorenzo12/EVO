package com.evopackage.evo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Join_Event extends AppCompatActivity implements View.OnClickListener {

    private EditText eventCode;
    private Button codeJoin, QRJoin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.join_event);

        eventCode = findViewById(R.id.EnterCode);

        codeJoin = findViewById(R.id.JoinCode);
        QRJoin = findViewById(R.id.JoinQR);

    }

    @Override
    public void onClick(View view) {



    }
}
