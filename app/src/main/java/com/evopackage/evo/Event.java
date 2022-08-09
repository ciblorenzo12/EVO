package com.evopackage.evo;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Event {
    private String _name, _date, _location, _category,  _creator;
    private Uri _uri;

    public Event(String name, String date, String location, String category, String creator, Uri uri) {
        _name = name;
        _date = date;
        _location = location;
        _category = category;
        _creator = creator;
        _uri = uri;
    }

    public Event(String key) {
        FirebaseDatabase.getInstance().getReference().child("Events").child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                _name = snapshot.child("name").getValue(String.class);
                _date = snapshot.child("date").getValue(String.class);
                _location = snapshot.child("address").getValue(String.class);
                _category = snapshot.child("category").getValue(String.class);
                _creator = snapshot.child("creator").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public String GetName() {
        return _name;
    }

    public String GetDate() {
        return _date;
    }

    public String GetLocation() {
        return _location;
    }

    public String GetCategory() {
        return _category;
    }

    public String GetCreator() {
        return _creator;
    }

    public Uri GetUri() {
        return _uri;
    }

}
