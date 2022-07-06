package com.evopackage.evo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;


public class create_event_popup extends AppCompatDialogFragment {

    private EditText editName;
    private EditText editLocation;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.event_dialog,null);

        builder.setView(v)
                .setTitle("EventPopUp")
                .setNegativeButton("cancel", (dialog, which) -> {

                })
                .setPositiveButton("create", (dialog, which) -> {

                });

           editName = v.findViewById(R.id.edit_name);
           editLocation = v.findViewById(R.id.edit_location);

           return builder.create();
    }


}

