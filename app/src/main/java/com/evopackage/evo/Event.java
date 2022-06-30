package com.evopackage.evo;

public class Event
{
    private String _name;
    private int _id;

    public Event(String name, int id)
    {
        _name = name;
        _id = id;
    }

    public String GetName()
    {
        return _name;
    }

    public int GetId()
    {
        return _id;
    }
}
