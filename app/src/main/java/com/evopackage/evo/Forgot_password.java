package com.evopackage.evo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class Forgot_password extends AppCompatActivity implements View.OnClickListener {


    private Button _submit;
    private EditText _email;
    private ImageButton _back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        _submit= findViewById(R.id.reset_submit);
        _email = findViewById(R.id.email_reset);
        _back = findViewById(R.id.reset_back);

_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reset_back:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}