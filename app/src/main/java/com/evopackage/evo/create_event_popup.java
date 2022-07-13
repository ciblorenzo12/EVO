package com.evopackage.evo;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;


public class create_event_popup extends AppCompatDialogFragment implements AdapterView.OnItemSelectedListener {
    private EditText txtName;
    private EditText txtDate;
    private EditText txtAddress;


    private DialogListener listener;

    @SuppressLint("CutPasteId")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.event_dialog, null);
        Spinner spinner = v.findViewById(R.id.spinner);
        txtName = v.findViewById(R.id.txtName);
        txtDate = v.findViewById(R.id.txtDate);
        txtAddress = v.findViewById(R.id.txtLocation);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.theme, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        builder.setView(v)
                .setTitle("EventPopUp")
                .setNegativeButton("cancel", (dialog, which) -> {

                })
                .setPositiveButton("create", (dialog, which) -> {
                    String evtName = txtName.getText().toString();
                    String evtDate = txtDate.getText().toString();
                    String evtAddr = txtAddress.getText().toString();
                    listener.applyTexts(evtName, evtDate, evtAddr);
                    //   FirebaseDatabase.getInstance().getReference("events").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser())
                    //  .getUid()).setValue(event);
                });

        //  EditText editName = v.findViewById(R.id.txtName);
        //  EditText editLocation = v.findViewById(R.id.txtName);



        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context +
                    "must implement DialogListener");
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public interface DialogListener {
        void applyTexts(String _evtName, String _evtDate, String _evtAddr);
    }

}

