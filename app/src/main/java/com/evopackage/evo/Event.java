package com.evopackage.evo;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.List;

public class Event implements Serializable {
    private String _key, _name, _date, _location, _category, _uri, _description, _creator, _password;
    Boolean _priva;
    private List<String> _people;
    public Event() {};

    public Event(String key, String name, String date, String location, String category, String creator, String uri, String description, Boolean priva, String password) {
        _key = key;
        _name = name;
        _date = date;
        _location = location;
        _category = category;
        _creator = creator;
        _uri = uri;
        _description = description;
        _priva = priva;
       _people = null;

       _password = password;
    }

    public String GetKey() { return _key; }

    public String GetName() { return _name; }

    public String GetDate() { return _date; }

    public String GetLocation() { return _location; }

    public String GetCategory() { return _category; }

    public String GetCreator() { return _creator; }

    public int getPeopleCount() { return _people.size();}

    public void addPeople(String people) { _people.add(people);}

    public String GetUri() { return _uri; }

    public String GetPassword() { return _password;}

    public String GetDescription() { return _description; }

    public Boolean isEventPrivate() { return _priva;}

    public void SetEventPrivate(boolean prive){ _priva = prive;}

}
