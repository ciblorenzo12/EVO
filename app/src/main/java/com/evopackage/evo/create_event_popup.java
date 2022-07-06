package com.evopackage.evo;

import android.annotation.SuppressLint;
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
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;


public class create_event_popup extends AppCompatDialogFragment {
    private EditText txtName;
    private EditText txtDate;
    private EditText txtAddress;
    private Button btnConfirm;

    @SuppressLint("CutPasteId")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.event_dialog,null);

        txtName = v.findViewById(R.id.txtName);
        txtDate = v.findViewById(R.id.txtDate);
        txtAddress = v.findViewById(R.id.txtLocation);
        btnConfirm = v.findViewById(R.id.btnConfirm);

        builder.setView(v)
                .setTitle("EventPopUp")
                .setNegativeButton("cancel", (dialog, which) -> {

                })
                .setPositiveButton("create", (dialog, which) -> {
                    Event event = new Event(txtName.toString(), txtDate.toString(), txtAddress.toString(), "null category");
                    FirebaseDatabase.getInstance().getReference("events").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser())
                            .getUid()).setValue(event);
                });

        EditText editName = v.findViewById(R.id.txtName);
        EditText editLocation = v.findViewById(R.id.txtName);

           return builder.create();
    }


}

