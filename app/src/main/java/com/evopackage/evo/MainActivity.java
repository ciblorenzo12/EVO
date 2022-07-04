package com.evopackage.evo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //clickable objects
private TextView _forgotPass;
private TextView _create_User;
private Button _login;
//Firebase authentication
private FirebaseAuth authent;

//textObjects
private EditText _email,password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firebase_login_activity);


      //fields
        _create_User = findViewById(R.id.create_an_account) ;
        _forgotPass =  findViewById(R.id.forgota_password) ;
         authent = FirebaseAuth.getInstance();
         _login= findViewById(R.id.login_btn);


         //OnClick
        _create_User.setOnClickListener(this);
        _forgotPass.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.create_an_account:startActivity(new Intent(this,Register_user.class));
            break;
            case R.id.forgota_password:startActivity(new Intent(this,Forgot_password.class));
            break;
        }
    }
}