package com.evopackage.evo;

import java.io.Serializable;

public class Event implements Serializable
{
   public String _name, _date, _location, _category,_uri, _description;
   public String _creator ;
public Event(){};



    public Event(String name, String date, String location, String category, String creator, String uri, String description)
    {
        _name = name;
        _date = date;
        _location = location;
        _category = category;
        _creator = creator;
        _uri = uri;
        _description = description;
    }

    public String GetName() { return _name; }

    public String GetDate() { return _date; }

    public String GetLocation() { return _location; }

    public String GetCategory() { return _category; }

    public String GetCreator() { return _creator; }

   public  String GetUri(){


        return _uri;
   }

    public String GetDescription() {
        return _description;
    }

}
