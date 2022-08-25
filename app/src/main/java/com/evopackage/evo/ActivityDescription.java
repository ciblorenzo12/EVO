package com.evopackage.evo;
import android.content.Intent;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;

public class ActivityDescription extends AppCompatDialogFragment  {

    private Activity ac;
    private TextView txtName;
    private TextView txtDes;
    private TextView txtDate;


    public ActivityDescription(Activity _ac){
        ac = _ac;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    public ActivityDescription() {
        super();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
       // LayoutInflater cardIn = requireActivity().getLayoutInflater();
        View vr = inflater.inflate(R.layout.activity_description, null);
        txtName = vr.findViewById(R.id.txtActivityName);
        txtDate = vr.findViewById(R.id.txtActivityDate);
        txtDes = vr.findViewById(R.id.txtActivityDes);
        populateData();

        builder.setView(vr)
                .setTitle("Activity Description")
                .setPositiveButton("Close", (dialog, which) -> {


                });

        return builder.create();

    }

    public void populateData() {
        txtName.setText(ac.GetName());

        txtDate.setText(ac.GetTime());

        txtDes.setText(ac.GetAddress());


    }
}

