package com.evopackage.evo;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login_screen extends AppCompatActivity implements View.OnClickListener {

    //clickable objects
    private TextView _forgotPass;
    private TextView _create_User;
    private Button _login;
    //Firebase authentication
    private FirebaseAuth _authent;
    private FirebaseUser _userdata=FirebaseAuth.getInstance().getCurrentUser();

    //textObjects
    private EditText _email, _password;

    //animated Objects
    private ProgressBar progressbar_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firebase_login_activity);


        //fields
        _create_User = findViewById(R.id.create_an_account);
        _forgotPass = findViewById(R.id.forgota_password);
        _authent = FirebaseAuth.getInstance();
        _login = findViewById(R.id.login_btn);
        progressbar_ = findViewById(R.id.login_progressBar);
        _email = findViewById(R.id.TextEmailAddress);
        _password = findViewById(R.id.TextPass);
        //OnClick
        _create_User.setOnClickListener(this);
        _forgotPass.setOnClickListener(this);
        _login.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

     if (v.getId() == R.id.login_btn) {
        firebase_user_credentials();

    }
        if (v.getId() == R.id.create_an_account) {

            startActivity(new Intent(this, Register_user.class));
        }if (v.getId() == R.id.forgota_password) {
            startActivity(new Intent(this, Forgot_password.class));


        }
    }

    private void firebase_user_credentials() {
        progressbar_.setVisibility(View.GONE);
        String _EMAIL = _email.getText().toString().trim();
        String _PASSWORD = _password.getText().toString().trim();

        if (_EMAIL.isEmpty()) {

            _email.setError("Please enter an Email address \nhint: example@email.net ");
            _email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(_EMAIL).matches()) {
            _email.setError("Please provide a valid Email");
            _email.requestFocus();
            return;
        }
        if (_PASSWORD.isEmpty()) {

            _password.setError("You need to enter a password  \nhint: Password#123 ");
            _password.requestFocus();
            return;


        }if (_PASSWORD.length()<8) {

            _password.setError("You need to enter a password with at least 8 characters \nhint: P2345678 ");
            _password.requestFocus();
            return;

        }

        progressbar_.setVisibility(View.VISIBLE);
        _authent.signInWithEmailAndPassword(_EMAIL,_PASSWORD).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {

             if(_userdata.isEmailVerified()) {

                 startActivity(new Intent(Login_screen.this,MainWindows_Create_Join_Event.class));

             }else {
         _userdata.sendEmailVerification();
         Toast.makeText(Login_screen.this,"Check your email for verification",Toast.LENGTH_LONG).show();
             }
                progressbar_.setVisibility(View.GONE);


            }else {

                Toast.makeText(Login_screen.this,"Ups something went wrong please check your credentials",Toast.LENGTH_LONG).show();

            }
        });
    }
}

