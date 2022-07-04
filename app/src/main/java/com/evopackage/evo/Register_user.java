package com.evopackage.evo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class Register_user extends AppCompatActivity implements View.OnClickListener {


    //firebase Auth
    private FirebaseAuth _authentication;

    //information need it
    private EditText _fullname,_lastname,_email, _password, _phone, _dob;

    //buttons
    private Button _registerBtn;
    private ImageButton _back_Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        _authentication = FirebaseAuth.getInstance();
        _fullname = findViewById(R.id.register_firstname);
        _lastname = findViewById(R.id.register_lastname);
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
        if (v.getId() == R.id.register_back) {

            startActivity(new Intent(this, Login_screen.class));

        }
        if (v.getId() == R.id.register_btn) {
            RegisterUser();

        }

    }

    private void RegisterUser() {
        String _FirstName = _fullname.getText().toString().trim();
        String _LastName= _lastname.getText().toString().trim();
        String _Email = _email.getText().toString().trim();
        String _Password = _password.getText().toString().trim();
        String _Phone = _phone.getText().toString().trim();
        String _Dob = _dob.getText().toString().trim();


        if(_FirstName.isEmpty()) {
      _fullname.setError("You should enter your first name");
      _fullname.requestFocus();
      return;

        }

        if(_LastName.isEmpty()) {
            _fullname.setError("You should enter your last Name name");
            _fullname.requestFocus();
            return;

        }
        if (_Email.isEmpty()){
                _email.setError("You should enter your email address");
                _email.requestFocus();
                return;
            }

        if (_Password.isEmpty()) {
            _password.setError("You should enter a valid Password");
            _password.requestFocus();
            return;

        }
        if (_Phone.isEmpty()) {
            _phone.setError("Please enter a valid Phone number");
            _phone.requestFocus();
            return;

        }
        if (_Dob.isEmpty() ){

            _dob.setError("Please enter your date of birth");
            _dob.requestFocus();
            return;


        }
        if(!Patterns.EMAIL_ADDRESS.matcher(_Email).matches()) {
            _email.setError("You should enter a valid email");
            _email.requestFocus();
            return;
        }
        if(_Password.length()<6) {

            _password.setError("Password should be a minimum of  8 characters ");

            _password.requestFocus();
            return;
        }
        if (!HasCapitalLetters(_Password)){

            _password.setError("Password should have at least one capital letter ");
            _password.requestFocus();

        }
        }
    public boolean HasCapitalLetters(String s){
        for(int i=0;i<s.length();i++){
            if(Character.isUpperCase(s.charAt(i))){
                return true;
            }
        }
        return false;
    }
    }



