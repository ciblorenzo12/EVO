package com.evopackage.evo;

public class Activity  {
    private String _name;
    private String _time;
    private String _theme;
    private String _andress;

    public Activity(String name, String time, String theme, String address){
        _name = name;
        _time = time;
        _theme = theme;
        _andress = address;
    }

    public String GetName(){
        return _name;
    }

    public String GetTime(){
        return _time;
    }

    public String GetTheme(){
        return _theme;
    }

    public String GetAddress(){
        return _andress;
    }
}
