package com.evopackage.evo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Forgot_password extends AppCompatActivity implements View.OnClickListener {


    private final FirebaseAuth _auth = FirebaseAuth.getInstance();

    private Button _submit;
    private EditText _email;
    private ImageButton _back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        _submit = findViewById(R.id.reset_submit);
        _email = findViewById(R.id.email_reset);
        _back = findViewById(R.id.reset_back);

        _back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.reset_back) {

            startActivity(new Intent(this, Login.class));

        }
        if (v.getId() == R.id.reset_submit) {

            ForgotPassword();


        }
    }

    private void ForgotPassword() {

        String _Email = _email.getText().toString().trim();
        _auth.sendPasswordResetEmail(_Email).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                Toast.makeText(Forgot_password.this, "Weee ,We send a link to your email ", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Forgot_password.this, "Provide a valid email address ", Toast.LENGTH_SHORT).show();
            }
        });


    }
}