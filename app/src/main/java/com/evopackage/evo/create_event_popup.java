package com.evopackage.evo;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class create_event_popup extends AppCompatDialogFragment implements AdapterView.OnItemSelectedListener, View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private EditText txtName;
    private TextView txtDate;
    private EditText txtAddress;
    private EditText txtDescription;

    //private DialogListener listener;
    private Event event;
    private DatabaseReference firebaseEvent;
    private FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference firebaseUsers = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

    private static String current_event;
    private static String current_eventID;
    private static ArrayList<String> _events = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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
        txtDescription = v.findViewById(R.id.description);

        txtDate.setOnClickListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.theme, android.R.layout.select_dialog_item);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        builder.setView(v)
                .setTitle("Create an Event")
                .setNegativeButton("cancel", (dialog, which) -> {

                })
                .setPositiveButton("create", (dialog, which) -> {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    String eventUid = FirebaseDatabase.getInstance().getReference().child("events").push().getKey();
                    event = new Event(eventUid, txtName.getText().toString(), txtDate.getText().toString(),
                            txtAddress.getText().toString(), spinner.getSelectedItem().toString(), firebaseUsers.getKey(), "", txtDescription.getText().toString());

                    if (txtName.getText().toString().isEmpty() || txtAddress.getText().toString().isEmpty() ||
                            txtDate.getText().toString().isEmpty() || txtDescription.getText().toString().isEmpty()) {
                        dialog.dismiss();
                        Toast.makeText(v.getContext(), "Error name,address or date  can't be empty", Toast.LENGTH_SHORT).show();
                        Toast.makeText(v.getContext(), "Please enter a valid name", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
//                        listener.applyTexts(txtName.getText().toString(), txtDate.getText().toString(),
//                                txtAddress.getText().toString(), txtDescription.getText().toString());

                        firebaseEvent = FirebaseDatabase.getInstance().getReference().child("events").child(eventUid);
                        firebaseEvent.child("name").setValue(event.GetName());
                        current_event = event.GetName();

                        firebaseEvent.child("date").setValue(event.GetDate());
                        firebaseEvent.child("address").setValue(event.GetLocation());
                        firebaseEvent.child("category").setValue(event.GetCategory());
                        firebaseEvent.child("creator").setValue(event.GetCreator());
                        firebaseEvent.child("description").setValue(event.GetDescription());
                        //firebaseEvent.child("eventid").setValue(eventUid);
                        firebaseEvent.child("people").child(current_user.getUid()).setValue("Creator");

                        current_eventID = eventUid;
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
//        try {
//            listener = (DialogListener) context;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(context +
//                    "must implement DialogListener");
//        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == txtDate.getId()) {
            Pick_a_Date();
        }
    }

    private void Pick_a_Date() {
        DatePickerDialog _choosedate = new DatePickerDialog(this.getContext(),
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        _choosedate.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month = month + 1;
        String dob = month + "/" + dayOfMonth + "/" + year;
        txtDate.setText(dob);
    }

//    public interface DialogListener {
//        void applyTexts(String _evtName, String _evtDate, String _evtAddr, String _evtTheme);
//    }

    public static String GetCurrent_Event() {
        return current_event;
    }

    public static String GetCurrent_EventID() {
        return current_eventID;
    }
}

