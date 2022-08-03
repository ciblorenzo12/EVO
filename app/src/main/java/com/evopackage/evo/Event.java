package com.evopackage.evo;

public class Event
{
   public String _name, _date, _location, _category,_uri;
   public String _creator ;
public Event(){};
    public Event(String name, String date, String location, String category, String creator,String uri)
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

    public String GetCreator() { return _creator; }

   public  String GetUri(){


        return _uri;
   }

}
