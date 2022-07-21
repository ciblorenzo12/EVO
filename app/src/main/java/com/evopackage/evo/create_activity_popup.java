package com.evopackage.evo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class create_activity_popup extends AppCompatDialogFragment {

    private EditText txtName;
    private TextView txtDate;
    private EditText txtAddress;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.activity_dialog, null);
        txtName = v.findViewById(R.id.ActivityName);
        txtDate = v.findViewById(R.id.ActivityDate);
        txtAddress = v.findViewById(R.id.ActivityAddre);
        builder.setView(v)
                .setTitle("Add a new activity")
                .setNegativeButton("cancel", (dialog, which) -> {

                })
                .setPositiveButton("create", (dialog, which) -> {
                    Activity act = new Activity(txtName.getText().toString(), txtDate.getText().toString(), null, txtAddress.getText().toString());
                    DatabaseReference firebaseActivtiy = FirebaseDatabase.getInstance().getReference("events").child(create_event_popup.GetCurrent_EventID()).child(txtName.getText().toString());
                    firebaseActivtiy.child("_date").setValue(txtDate.getText().toString());
                    firebaseActivtiy.child("_address").setValue(txtAddress.getText().toString());
                });
        return builder.create();
    }
}
