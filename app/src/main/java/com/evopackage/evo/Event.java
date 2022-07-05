package com.evopackage.evo;

public class Event
{
    private int _id;
    private String _name, _date, _location, _category;

    public Event(int id, String name, String date, String location, String category)
    {
        _id = id;
        _name = name;
        _date = date;
        _location = location;
        _category = category;
    }

    public int GetId()
    {
        return _id;
    }

    public String GetName() { return _name; }

    public String GetDate() { return _date; }

    public String GetLocation() { return _location; }

    public String GetCategory() { return _category; }
}
