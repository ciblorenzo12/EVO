package com.evopackage.evo;

import com.google.firebase.auth.FirebaseUser;

public class Event
{
    private String _name, _date, _location, _category,_uri;
    private FirebaseUser _creator;

    public Event(String name, String date, String location, String category, FirebaseUser creator,String uri)
    {
        _name = name;
        _date = date;
        _location = location;
        _category = category;
        _creator = creator;
        _uri = uri;
    }

    public String GetName() { return _name; }

    public String GetDate() { return _date; }

    public String GetLocation() { return _location; }

    public String GetCategory() { return _category; }

    public FirebaseUser GetCreator() { return _creator; }



}
