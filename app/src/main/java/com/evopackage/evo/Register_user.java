package com.evopackage.evo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

public class Register_user extends AppCompatActivity implements View.OnClickListener {


    //firebase Auth
    private FirebaseAuth _authentication;

    //information need it
    private EditText _email, _password, _phone, _dob;

    //buttons
    private Button _registerBtn;
    private ImageButton _back_Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        _authentication = FirebaseAuth.getInstance();
        _email = findViewById(R.id.register_email);
        _password = findViewById(R.id.register_password);
        _phone = findViewById(R.id.register_phone);
        _dob = findViewById(R.id.register_dob);

        //buttons
        _registerBtn = findViewById(R.id.register_btn);
        _back_Login = findViewById(R.id.register_back);


        _back_Login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_back:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}