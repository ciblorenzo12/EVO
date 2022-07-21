package com.evopackage.evo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class User_Profile_picture extends AppCompatActivity implements View.OnClickListener {
    //Firebase stuff
    private FirebaseAuth _authetication;
    private FirebaseDatabase _database = FirebaseDatabase.getInstance();
    //Changes to be made to data on firebase
    private EditText _firstName, _lastName, _phoneNumber,_userID;

    //Clickable buttons
    //ProfPicture should open up the phones photo library and pick one to be the picture
    private Button ProfSaveBtn;
    private Button _password;
    private ImageButton ProfPicture;
    private ImageButton ProfSettingsBack;
    private String[] permissions_ = {Manifest.permission.MANAGE_MEDIA};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_setting);

        _authetication = FirebaseAuth.getInstance();
        FirebaseUser user = _authetication.getCurrentUser();
        //EditText
        _firstName = findViewById(R.id.ProfFirstName);
        _lastName = findViewById(R.id.ProfLastName);
        _phoneNumber = findViewById(R.id.ProfPhoneNum);
        _userID = findViewById(R.id.UserID);

        //Buttons/Picture
        _password = findViewById(R.id.ChangePassword);
        ProfSaveBtn = findViewById(R.id.ProfSave);
        ProfPicture = findViewById(R.id.ProfilePicture);
        ProfSettingsBack = findViewById(R.id.ProfileSettingsBack);

        User_information info;
        ProfPicture.setOnClickListener(this);
        ProfSettingsBack.setOnClickListener(this);
        ProfSaveBtn.setOnClickListener(this);
        _password.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.ProfileSettingsBack){
            startActivity(new Intent(this, Profile_Page.class));
        }
        //save changes
        else if(view.getId() == R.id.ProfSave){
            SaveProfileChanges();
        }

        else if(view.getId() == R.id.ChangePassword){
            startActivity(new Intent(this,Forgot_password.class));
        }
        //change profile pic
        else if(view.getId() == R.id.ProfilePicture){
            Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(openGalleryIntent,1000);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){
                Uri profilePicUri = data.getData();
                ProfPicture.setImageURI(profilePicUri);

                UploadImagetoFirebase(profilePicUri);
            }
        }
    }

    private void UploadImagetoFirebase(Uri profilePicUri) {
       // DatabaseReference fileRef = FirebaseDatabase.getInstance().getReference().child("users").child(_authetication.getCurrentUser().getUid()).child("_uri");
        StorageReference fileRef = FirebaseStorage.getInstance().getReference().child(_authetication.getCurrentUser().getUid()+"profile.jpg");
        fileRef.putFile(profilePicUri).addOnSuccessListener(taskSnapshot -> Toast.makeText(User_Profile_picture.this,"Image Uploaded.",Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Toast.makeText(User_Profile_picture.this,"Upload Failed.",Toast.LENGTH_SHORT).show());

    }

    private void SaveProfileChanges() {
        String _UserID = _userID.getText().toString().trim();
        String _FirstName = _firstName.getText().toString().trim();
        String _LastName = _lastName.getText().toString().trim();
        String _PhoneNum = _phoneNumber.getText().toString().trim();


        //User_information info = User_information();

        FirebaseUser user = _authetication.getCurrentUser();
        String path = user.getUid();
        DatabaseReference Ref;

        if(!_UserID.isEmpty()){
            if(_UserID.length() > 16){
                _userID.setError("Display Name must be 16 characters or less.");
                return;
            }
            Ref = _database.getReference("users/"+user.getUid()+"/_displayName");
            Ref.setValue(_UserID);
            _userID.setText("");
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

    }

}