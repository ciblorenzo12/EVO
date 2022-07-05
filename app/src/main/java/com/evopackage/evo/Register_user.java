package com.evopackage.evo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Objects;

public class Register_user extends AppCompatActivity implements View.OnClickListener ,DatePickerDialog.OnDateSetListener{


    //firebase Auth
    private FirebaseAuth _authentication;
    private FirebaseDatabase _database = FirebaseDatabase.getInstance();
    //information need it
    private EditText _fullname,_lastname,_email, _password, _phone, _dob;

    //buttons
    private Button _registerBtn;
    private ImageButton _back_Login;
    private ProgressBar _progressbar;
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
         _progressbar = findViewById(R.id.register_progressBar);

        _back_Login.setOnClickListener(this);
        _dob.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.register_back) {

            startActivity(new Intent(this, Login_screen.class));

        }
        if (v.getId() == R.id.register_btn) {
            RegisterUser();

        }
        if(v.getId()==R.id.register_dob) {

Pick_a_Date();

        }

    }

    private void Pick_a_Date() {

        DatePickerDialog _choosedate = new DatePickerDialog(
                this,
                       this,
                Calendar.getInstance().get(Calendar.YEAR ),
                        Calendar.getInstance().get(Calendar.MONTH),
                                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)


        );
        _choosedate.show();

    }

    private void RegisterUser() {
        String _FirstName = _fullname.getText().toString().trim();
        String _LastName = _lastname.getText().toString().trim();
        String _Email = _email.getText().toString().trim();
        String _Password = _password.getText().toString().trim();
        String _Phone = _phone.getText().toString().trim();
        String _Dob = _dob.getText().toString().trim();


        if (_FirstName.isEmpty()) {
            _fullname.setError("You should enter your first name");
            _fullname.requestFocus();
            return;

        }

        if (_LastName.isEmpty()) {
            _fullname.setError("You should enter your last Name name");
            _fullname.requestFocus();
            return;

        }
        if (_Email.isEmpty()) {
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
        if (_Dob.isEmpty()) {

            _dob.setError("Please enter your date of birth \n hint: select month day and year");
            _dob.requestFocus();
            return;


        }
        if (!Patterns.EMAIL_ADDRESS.matcher(_Email).matches()) {
            _email.setError("You should enter a valid email");
            _email.requestFocus();
            return;
        }
        if (_Password.length() < 6) {

            _password.setError("Password should be a minimum of  8 characters ");

            _password.requestFocus();
            return;
        }
        if (!HasCapitalLetters(_Password)) {

            _password.setError("Password should have at least one capital letter ");
            _password.requestFocus();

        }
        _progressbar.setVisibility(View.VISIBLE);

        _authentication.createUserWithEmailAndPassword(_Email, _Password)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {

    User_information users= new User_information(_FirstName,_LastName,_Dob,_Email,_Password,_Phone);
    _database.getReference("users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser())
            .getUid()).setValue(users)
            .addOnCompleteListener
                    (task1 -> {
                        if(task1.isComplete()){

                            Toast.makeText(Register_user.this ,"Yay! You are register!! ",Toast.LENGTH_LONG).show();
                            _progressbar.setVisibility(View.GONE);
                        }else {

                            Toast.makeText(Register_user.this ,"Oh no something went wrong ,Try again !! ",Toast.LENGTH_LONG).show();
                            _progressbar.setVisibility(View.GONE);

                        }
                    });

                    }else {

                        Toast.makeText(Register_user.this ,"Oh no something went wrong ,Try again !! ",Toast.LENGTH_LONG).show();
                        _progressbar.setVisibility(View.GONE);


                    }
                    });
    }
    public boolean HasCapitalLetters(String s){
        for(int i=0;i<s.length();i++){
            if(Character.isUpperCase(s.charAt(i))){
                return true;
            }
        }
        return false;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
      String dob = month + "/" +dayOfMonth+"/"+year;
      _dob.setText(dob);



    }
}



