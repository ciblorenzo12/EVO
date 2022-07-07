package com.evopackage.evo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class User_Profile_picture extends AppCompatActivity implements View.OnClickListener {
    //Firebase stuff
    private FirebaseAuth _authetication;
    private FirebaseDatabase _database = FirebaseDatabase.getInstance();
    //Changes to be made to data on firebase
    private EditText _firstName, _lastName, _password, _phoneNumber;
    //Clickable buttons
    //ProfPicture should open up the phones photo library and pick one to be the picture
    private Button ProfSaveBtn;
    private ImageButton ProfPicture;
    private ImageButton ProfSettingsBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_setting);

        _authetication = FirebaseAuth.getInstance();

        //EditText
        _firstName = findViewById(R.id.ProfFirstName);
        _lastName = findViewById(R.id.ProfLastName);
        _password = findViewById(R.id.ProfPassword);
        _phoneNumber = findViewById(R.id.ProfPhoneNum);

        //Buttons/Picture
        ProfSaveBtn = findViewById(R.id.ProfSave);
        ProfPicture = findViewById(R.id.ProfilePicture);
        ProfSettingsBack = findViewById(R.id.ProfileBack);

        User_information info;
        ProfPicture.setOnClickListener(this);
        ProfSettingsBack.setOnClickListener(this);
        ProfSaveBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.ProfileBack){
            startActivity(new Intent(this, MainWindows_Create_Join_Event.class));
        }
        //save changes
        else if(view.getId() == R.id.ProfSave){
            SaveProfileChanges();
        }
        //change profile pic
        //else if(view.getId() == R.id.ProfilePicture){}
    }

    private void SaveProfileChanges() {
        //String _FirstName = _firstName.getText().toString().trim();
        //String _LastName = _lastName.getText().toString().trim();
        String _PhoneNum = _phoneNumber.getText().toString().trim();
        String _Password = _password.getText().toString().trim();

        FirebaseUser user = _authetication.getCurrentUser();

        //User_information info = User_information()



        if(_PhoneNum.isEmpty()){
            _phoneNumber.setError("Please enter a valid phone number");
            _phoneNumber.requestFocus();
            return;
        }

        if(!_Password.isEmpty()){
            if (_Password.length() < 6) {

                _password.setError("Password must be a minimum of 8 characters");

                _password.requestFocus();
                return;
            }
            if (!HasCapitalLetters(_Password)) {

                _password.setError("Password must contain at least one capital letter");
                _password.requestFocus();
                return;
            }
            user.updatePassword(_Password);
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