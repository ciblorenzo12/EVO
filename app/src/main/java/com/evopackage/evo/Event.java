package com.evopackage.evo;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;

public class Event implements Serializable {
    private String _key, _name, _date, _location, _category, _uri, _description, _creator;

    public Event() {};

    public Event(String key, String name, String date, String location, String category, String creator, String uri, String description) {
        _key = key;
        _name = name;
        _date = date;
        _location = location;
        _category = category;
        _creator = creator;
        _uri = uri;
        _description = description;
    }

    public Event(String key)
    {
        FirebaseDatabase.getInstance().getReference().child("events").child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                _key = key;
                _name = snapshot.child("name").getValue(String.class);
                _date = snapshot.child("date").getValue(String.class);
                _location = snapshot.child("address").getValue(String.class);
                _category = snapshot.child("category").getValue(String.class);
                _creator = snapshot.child("creator").getValue(String.class);
                //_uri = snapshot.child("uri").getValue(String.class);
                //_description = snapshot.child("description").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public String GetKey() { return _key; }

    public String GetName() { return _name; }

    public String GetDate() { return _date; }

    public String GetLocation() { return _location; }

    public String GetCategory() { return _category; }

    public String GetCreator() { return _creator; }

    public String GetUri() { return _uri; }

    public String GetDescription() { return _description; }

}
