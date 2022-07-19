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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class create_event_popup extends AppCompatDialogFragment implements AdapterView.OnItemSelectedListener, View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private EditText txtName;
    private TextView txtDate;
    private EditText txtAddress;

    private DialogListener listener;
   private  Event event;
   private  DatabaseReference firebaseEvent ;
    private FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
    private  DatabaseReference firebaseUsers= FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

    //caleb code
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

                   event = new Event(txtName.getText().toString(), txtDate.getText().toString(),
                            txtAddress.getText().toString(), spinner.getSelectedItem().toString(), user);
                    String eventUid_value = FirebaseDatabase.getInstance().getReference().child("events").push().getKey();





                    if (txtName.getText().toString().isEmpty()){

                         dialog.dismiss();
                        Toast.makeText(v.getContext(), "Error name can't be empty", Toast.LENGTH_SHORT).show();
                        Toast.makeText(v.getContext(), "Please enter a valid name", Toast.LENGTH_SHORT).show();
                        Toast.makeText(v.getContext(), "don't be a dum dum", Toast.LENGTH_SHORT).show();
                       return;
                    }else {




                        firebaseEvent = FirebaseDatabase.getInstance().getReference().child("events").child(eventUid_value);
                        firebaseEvent.child("_name").setValue(event.GetName());
                        current_event = event.GetName();

                    }




                    firebaseEvent.child("_date").setValue(event.GetDate());
                    firebaseEvent.child("_address").setValue(event.GetLocation());
                    firebaseEvent.child("_category").setValue(event.GetCategory());
                    firebaseEvent.child("_creator").setValue(event.GetCreator().getUid());
                    firebaseEvent.child("_eventid").setValue(eventUid_value);
                    firebaseEvent.child("Users").child(current_user.getUid()).setValue("Creator");
                    current_eventID = eventUid_value;
                    firebaseEvent.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for(DataSnapshot snapsh :snapshot.getChildren()){

                                _events.add(snapshot.getKey());
               User_information user_information = new User_information(current_user.getDisplayName(), " "," ",current_user.getEmail()," ",current_user.getPhoneNumber(),"Events");


                                firebaseUsers.child("Events").child(snapshot.getKey()).setValue("Creator");

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

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

    @Override
    public void onClick(View v) {
        if(v.getId()==txtDate.getId()){


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

    public interface DialogListener
    {
        void applyTexts(String _evtName, String _evtDate, String _evtAdder);

        void applyTexts(String _evtName, String _evtDate, String _evtAddr, String _evtTheme);
    }

    public static String GetCurrent_Event(){




      return current_event;
    }
    public static String GetCurrent_EventID(){




        return current_eventID;
    }

}

