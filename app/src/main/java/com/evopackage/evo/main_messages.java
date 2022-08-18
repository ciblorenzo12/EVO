package com.evopackage.evo;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class main_messages extends AppCompatActivity {
    List<Messenges> messenges_List;
    Adapter_Messangers adaptor_m ;
    RecyclerView reciclemsg;
    private EditText mess;
    private ScrollView sv;
    FloatingActionButton send_btn;
    LinearLayoutManager llman_;
    FirebaseUser user;
    FirebaseAuth Auth_;
    private DatabaseReference refMessanging;
    @Override
    public void onCreate(Bundle savedInstanceState){
        setContentView(R.layout.main_messenges_);
        Auth_ = FirebaseAuth.getInstance();
        user = Auth_.getCurrentUser();
        send_btn = findViewById(R.id.sent_input);
        refMessanging =  FirebaseDatabase.getInstance().getReference();
        mess = findViewById(R.id.Mensage_in);
        reciclemsg = findViewById(R.id.recicle_mesg);
        messenges_List=new ArrayList<>();
sv = findViewById(R.id.scrollView2);
        adaptor_m = new Adapter_Messangers(this,messenges_List);

        llman_=(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));

        reciclemsg.setLayoutManager(llman_);
        reciclemsg.setAdapter(adaptor_m);

            super.onCreate(savedInstanceState);

        send_btn.setOnClickListener(v -> click());


        }

    private void click() {
        mess = findViewById(R.id.Mensage_in);
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm a ");

        String Name = user.getDisplayName();
        String Date = df.format(System.currentTimeMillis());
        Messenges mensage = new Messenges(Name, Date,mess.getText().toString());

        refMessanging.child("events").child("-N9Zrr8wPPsWmL_eQGgK").child("Message").push().setValue(mensage);
        mess.setText("");
        mess.setHint(" ");
    }


    @Override
    protected void onStart() {
        super.onStart();
        Messanges_toDisp();
    }

    private void Messanges_toDisp() {

        refMessanging.child("events").child("-N9Zrr8wPPsWmL_eQGgK").child("Message").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messenges_List.clear();
                for(DataSnapshot snap:snapshot.getChildren()){

                    Messenges messenges = new Messenges(snap.child("sender").getValue(String.class),
                            snap.child("date").getValue(String.class),
                            snap.child("messenger").getValue(String.class));
                    messenges_List.add(messenges);

                }
                reciclemsg.scrollToPosition(messenges_List.size()-1);
                adaptor_m.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });    }
}
