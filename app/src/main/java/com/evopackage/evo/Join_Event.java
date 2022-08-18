package com.evopackage.evo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Join_Event extends AppCompatDialogFragment implements View.OnClickListener {

    private EditText eventCode;
    private Button codeJoin, QRJoin;
    private String evtCode;
    private DatabaseReference evtCodeCheck;
    private DatabaseReference userEvts;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.join_event, null);

        eventCode = v.findViewById(R.id.EnterCode);
        codeJoin = v.findViewById(R.id.JoinCode);
        QRJoin = v.findViewById(R.id.JoinQR);

        codeJoin.setOnClickListener(this);
        QRJoin.setOnClickListener(this);

        evtCodeCheck = FirebaseDatabase.getInstance().getReference().child("events");
        userEvts = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid());

        builder.setView(v)
                .setTitle("Join an Event")
                .setNegativeButton("Cancel", (dialog, which) -> {

                });

        return builder.create();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == codeJoin.getId()){

            evtCode = eventCode.getText().toString().trim();
            eventCode.setText("");
            if(evtCode.isEmpty()){
                eventCode.setError("Please enter the event's code to join.");
                eventCode.requestFocus();
                return;
            }
            else{
                evtCodeCheck.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        for(DataSnapshot snap : snapshot.getChildren()) {
                            if (snapshot.child(evtCode).exists()) {
                                userEvts.child("user-events").child(evtCode).setValue("Guest");
                                evtCodeCheck.child(evtCode).child("people").child(userEvts.getKey()).setValue("Guest");
                            } else {
                                eventCode.setError("Sorry, their is no event associated with that code, please re-enter your code");
                                eventCode.requestFocus();
                                return;
                            }
//                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }
        else if(view.getId() == QRJoin.getId()){

        }
    }
}
