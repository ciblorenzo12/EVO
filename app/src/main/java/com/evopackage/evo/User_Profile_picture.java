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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class User_Profile_picture extends AppCompatActivity implements View.OnClickListener {
    //Firebase stuff
    private FirebaseAuth _authetication;
    private FirebaseDatabase _database = FirebaseDatabase.getInstance();
    //Changes to be made to data on firebase
    private EditText _firstName, _lastName, _password, _phoneNumber,_userID;

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
        FirebaseUser user = _authetication.getCurrentUser();
        //UserID
        _userID = findViewById(R.id.UserID);
        _userID.setText(user.getUid());
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
        String _UserID = _userID.getText().toString().trim();
        String _FirstName = _firstName.getText().toString().trim();
        String _LastName = _lastName.getText().toString().trim();
        String _PhoneNum = _phoneNumber.getText().toString().trim();
        String _Password = _password.getText().toString().trim();



        //User_information info = User_information();

        FirebaseUser user = _authetication.getCurrentUser();
        String path = user.getUid();
        DatabaseReference Ref;

        if(!_UserID.isEmpty()){
            if(_UserID.length() > 16){
                _userID.setError("Display Name must be 16 characters or less.");
                return;
            }
            Ref = _database.getReference("users");
            Ref.child(user.getUid()).setValue(_UserID);
        }

        if(!_FirstName.isEmpty()){
            Ref = _database.getReference("users/"+user.getUid()+"/_firstname");
            Ref.setValue(_FirstName);
            _firstName.setText("");
        }

        if(!_LastName.isEmpty()){
            Ref = _database.getReference("users/"+user.getUid()+"/_lastname");
            Ref.setValue(_LastName);
            _lastName.setText("");
        }

        if(!_PhoneNum.isEmpty()){
            Ref = _database.getReference("users/"+user.getUid()+"/_phone");
            //_database.getReference("users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("_phone").setValue(_phoneNumber);
            //_database.
            //Ref.child(user.getUid()).child("_phone").setValue(_PhoneNum);
            Ref.setValue(_PhoneNum);
            _phoneNumber.setText("");
        }

        if(!_Password.isEmpty()){
            if (_Password.length() < 6) {

                _password.setError("Password must be a minimum of 6 characters");

                _password.requestFocus();
                return;
            }
            if (!HasCapitalLetters(_Password)) {

                _password.setError("Password must contain at least one capital letter");
                _password.requestFocus();
                return;
            }
            user.updatePassword(_Password);
            _password.setText("");
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