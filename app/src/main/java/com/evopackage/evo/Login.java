package com.evopackage.evo;


import static com.evopackage.evo.R.layout.firebase_login_activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;


public class Login extends AppCompatActivity implements View.OnClickListener {

    //google
    int REQUEST_CODE = 123456;
    GoogleSignInClient mGoogleSignInClient;
    //clickable objects
    private TextView _forgotPass, _create_User;
    private Button _google_button;
    private Button _login;
    //Firebase authentication
    private FirebaseAuth _authent;
    private FirebaseUser _userdata;
    private final FirebaseDatabase _database = FirebaseDatabase.getInstance();
    //textObjects
    private EditText _email, _password;

    //animated Objects
    private ProgressBar progressbar_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);



        setContentView(firebase_login_activity);


        //google
        GoogleSignInOptions _google_Signin_Option = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        //fields
        _create_User = findViewById(R.id.create_an_account);
        _forgotPass = findViewById(R.id.forgota_password);
        _authent = FirebaseAuth.getInstance();
        _login = findViewById(R.id.login_btn);
        progressbar_ = findViewById(R.id.login_progressBar);
        _email = findViewById(R.id.TextEmailAddress);
        _password = findViewById(R.id.TextPass);
        _google_button = findViewById(R.id._googlebuton);
        _userdata = FirebaseAuth.getInstance().getCurrentUser();
        //OnClick

        _create_User.setOnClickListener(this);
        _forgotPass.setOnClickListener(this);
        _login.setOnClickListener(this);
        _google_button.setOnClickListener(this);

        mGoogleSignInClient = GoogleSignIn.getClient(this, _google_Signin_Option);


    }


    public void onClick(View v) {

        if (v.getId() == _login.getId()) {
            firebase_user_credentials();

        }
        if (v.getId() == _create_User.getId()) {

            startActivity(new Intent(this, Register_user.class));
        }
        if (v.getId() == _forgotPass.getId()) {
            startActivity(new Intent(this, Forgot_password.class));


        }
        if (v.getId() == _google_button.getId()) {

            signin();

        }

    }



    //Sign in Method
    private void signin() {

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, REQUEST_CODE);


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


        }
        if (_PASSWORD.length() < 6) {

            _password.setError("You need to enter a password with at least 8 characters \nhint: P2345678 ");
            _password.requestFocus();
            return;

        }

        progressbar_.setVisibility(View.VISIBLE);
        _authent.signInWithEmailAndPassword(_EMAIL, _PASSWORD).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
while (_userdata==null){
    progressbar_.setVisibility(View.VISIBLE);
  _userdata=  _authent.getCurrentUser();

}
                if (_userdata.isEmailVerified()) {

                    startActivity(new Intent(Login.this, MainWindows_Create_Join_Event.class));

                }   if (!_userdata.isEmailVerified())  {
                    _userdata.sendEmailVerification();
                    Toast.makeText(Login.this, "Check your email for verification", Toast.LENGTH_LONG).show();
                }
                progressbar_.setVisibility(View.GONE);


            } else {

                Toast.makeText(Login.this, "Ups something went wrong please check your credentials", Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == REQUEST_CODE) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            if (task.isSuccessful()) {
                try {
                    GoogleSignInAccount acct = task.getResult(ApiException.class);
                    Firebase_google(acct.getIdToken());

                } catch (ApiException ae) {

                    ae.printStackTrace();
                }
            }
        }
    }

    private void Firebase_google(String idToken) {

        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        _authent.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {

                        UI_Update();
                  User_information data = new User_information(_authent.getCurrentUser().getDisplayName()," "," ", _authent.getCurrentUser().getEmail()," ",_authent.getCurrentUser().getPhoneNumber(),"Events"," ");
                        _database.getReference("users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser())
                                .getUid()).setValue(data);
                    }
                });


    }

    private void UI_Update() {


        Intent intent_ = new Intent(Login.this, MainWindows_Create_Join_Event.class);
        startActivity(intent_);
        Login.this.finish();
        Toast.makeText(this, "Welcome back "+FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onStart() {

        if (_userdata != null) {
            Intent intent_ = new Intent(Login.this, MainWindows_Create_Join_Event.class);
            startActivity(intent_);
            this.finish();
        }
        super.onStart();


    }


}

