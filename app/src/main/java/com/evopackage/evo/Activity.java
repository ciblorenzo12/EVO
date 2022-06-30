package com.evopackage.evo;

public class Activity {
    private String _name;
    private String _time;

    public Activity(String name, String time){
        _name = name;
        _time = time;
    }

    public String GetName(){
        return _name;
    }

    public String GetTime(){
        return _time;
    }
}
