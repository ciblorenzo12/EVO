package com.evopackage.evo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Join_Event extends AppCompatActivity implements View.OnClickListener {

    private EditText eventCode;
    private Button codeJoin, QRJoin;
    private String evtCode;
    private DatabaseReference evtCodeCheck;
    private DatabaseReference userEvts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.join_event);

        eventCode = findViewById(R.id.EnterCode);

        codeJoin = findViewById(R.id.JoinCode);
        QRJoin = findViewById(R.id.JoinQR);

        evtCodeCheck = FirebaseDatabase.getInstance().getReference().child("events");
        userEvts = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("user-events");
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == codeJoin.getId()){
            evtCode = eventCode.getText().toString().trim();
            if(evtCode.isEmpty()){
                eventCode.setError("Please enter the event's code to join.");
                eventCode.requestFocus();
                return;
            }
            else{
                evtCodeCheck.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot snap : snapshot.getChildren()){
                            if(eventCode == snap.getValue().toString()) {

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                })
            }
        }
        else if(view.getId() == QRJoin.getId()){

        }

    }
}
