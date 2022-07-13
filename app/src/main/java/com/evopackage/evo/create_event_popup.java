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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class create_event_popup extends AppCompatDialogFragment implements AdapterView.OnItemSelectedListener {
    private EditText txtName;
    private EditText txtDate;
    private EditText txtAddress;
    private Button btnConfirm;
    private Spinner spinner;
    private DialogListener listener;

    @SuppressLint("CutPasteId")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.event_dialog, null);
        Spinner spinner = v.findViewById(R.id.spinner);
        txtName = v.findViewById(R.id.txtName);
        txtDate = v.findViewById(R.id.txtDate);
        txtAddress = v.findViewById(R.id.txtLocation);
        btnConfirm = v.findViewById(R.id.btnConfirm);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.theme, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        builder.setView(v)
                .setTitle("EventPopUp")
                .setNegativeButton("cancel", (dialog, which) -> {

                })
                .setPositiveButton("create", (dialog, which) -> {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    Event event = new Event(txtName.getText().toString(), txtDate.getText().toString(),
                            txtAddress.getText().toString(), spinner.getSelectedItem().toString(), user);
                    String eventUid = FirebaseDatabase.getInstance().getReference().child("events").push().getKey();

                    DatabaseReference firebaseEvent = FirebaseDatabase.getInstance().getReference().child("events").child(eventUid);

                    firebaseEvent.child("_name").setValue(event.GetName());
                    firebaseEvent.child("_date").setValue(event.GetDate());
                    firebaseEvent.child("_address").setValue(event.GetLocation());
                    firebaseEvent.child("_category").setValue(event.GetCategory());
                    firebaseEvent.child("_creator").setValue(event.GetCreator().getUid());
                });

           return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (DialogListener) context;
        } catch (ClassCastException e){
            throw  new ClassCastException(context +
            "must implement DialogListener");
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public interface DialogListener
    {
        void applyTexts(String _evtName, String _evtDate, String _evtAdder);

        void applyTexts(String _evtName, String _evtDate, String _evtAddr, String _evtTheme);
    }

}

