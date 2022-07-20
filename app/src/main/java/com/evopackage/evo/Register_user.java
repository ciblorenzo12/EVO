package com.evopackage.evo;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Objects;

public class Register_user extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {


    private final FirebaseDatabase _database = FirebaseDatabase.getInstance();
    //firebase Auth
    private FirebaseAuth _authentication;
    //information need it
    private EditText _displayname, _fullname, _lastname, _email, _password, _phone, _dob;

    //buttons
    private Button _registerBtn;
    private ImageButton _back_Login;
    private ProgressBar _progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        _authentication = FirebaseAuth.getInstance();
        _displayname = findViewById(R.id.register_displayname);
        _fullname = findViewById(R.id.register_firstname);
        _lastname = findViewById(R.id.register_lastname);
        _email = findViewById(R.id.register_email);
        _password = findViewById(R.id.register_password);
        _phone = findViewById(R.id.register_phone);
        _dob = findViewById(R.id.register_dob);

        //buttons
        _registerBtn = findViewById(R.id.register_btn);
        _back_Login = findViewById(R.id.register_back);
        _progressbar = findViewById(R.id.reset_progressBar);

        _back_Login.setOnClickListener(this);
        _dob.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.register_back) {

            startActivity(new Intent(this, Login.class));

        }
        if (v.getId() == R.id.register_btn) {
            RegisterUser();

        }
        if (v.getId() == R.id.register_dob) {

            Pick_a_Date();

        }

    }

    private void Pick_a_Date() {

        DatePickerDialog _choosedate = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)


        );
        _choosedate.show();

    }

    private void RegisterUser() {
        String _DisplayName = _displayname.getText().toString().trim();
        String _FirstName = _fullname.getText().toString().trim();
        String _LastName = _lastname.getText().toString().trim();
        String _Email = _email.getText().toString().trim();
        String _Password = _password.getText().toString().trim();
        String _Phone = _phone.getText().toString().trim();
        String _Dob = _dob.getText().toString().trim();

        if(_DisplayName.isEmpty()){
            _displayname.setError("Please enter a display name");
            _displayname.requestFocus();
            return;
        }

        //if (_DisplayName.length() < 10){
          //  _displayname.setError("Display name must be at least 10 characters");
          //  _displayname.requestFocus();
         //   return;
       // }

       // if(_DisplayName.length() > 24){
       //     _displayname.setError("Display name must be 24 characters or less");
       //     _displayname.requestFocus();
       //     return;
        //}

        if (_FirstName.isEmpty()) {
            _fullname.setError("Please enter your first name");
            _fullname.requestFocus();
            return;

        }

        if (_LastName.isEmpty()) {
            _fullname.setError("Please enter your last name");
            _fullname.requestFocus();
            return;

        }
        if (_Email.isEmpty()) {
            _email.setError("Please enter your email address");
            _email.requestFocus();
            return;
        }

        if (_Password.isEmpty()) {
            _password.setError("Please enter a valid password");
            _password.requestFocus();
            return;

        }
        if (_Phone.isEmpty()) {
            _phone.setError("Please enter a valid phone number");
            _phone.requestFocus();
            return;

        }
        if (_Dob.isEmpty()) {

            _dob.setError("Please enter your date of birth \nHint: select month, day, and year");
            _dob.requestFocus();
            return;


        }
        if (!Patterns.EMAIL_ADDRESS.matcher(_Email).matches()) {
            _email.setError("You must enter a valid email");
            _email.requestFocus();
            return;
        }
        if (_Password.length() < 6) {

            _password.setError("Password must be a minimum of 8 characters");

            _password.requestFocus();
            return;
        }
        if (!HasCapitalLetters(_Password)) {

            _password.setError("Password must contain at least one capital letter");
            _password.requestFocus();

        }
        _progressbar.setVisibility(View.VISIBLE);

        _authentication.createUserWithEmailAndPassword(_Email, _Password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        User_information users = new User_information( _FirstName, _LastName, _Dob, _Email, _Password, _Phone);
                        _database.getReference("users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser())
                                        .getUid()).setValue(users)
                                .addOnCompleteListener
                                        (task1 -> {
                                            if (task1.isComplete()) {

                                                Toast.makeText(Register_user.this, "Yay! You're registered!", Toast.LENGTH_LONG).show();
                                            } else {

                                                Toast.makeText(Register_user.this, "Oh no, something went wrong! Try again.", Toast.LENGTH_LONG).show();

                                            }
                                            _progressbar.setVisibility(View.GONE);
                                        });

                    } else {

                        Toast.makeText(Register_user.this, "Oh no, something went wrong! Try again.", Toast.LENGTH_LONG).show();
                        _progressbar.setVisibility(View.GONE);


                    }
                });
    }

    public boolean HasCapitalLetters(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (Character.isUpperCase(s.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month = month + 1;
        String dob = month + "/" + dayOfMonth + "/" + year;
        _dob.setText(dob);


    }
}



