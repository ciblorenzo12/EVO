package com.evopackage.evo;

import java.io.Serializable;

public class Activity implements Serializable {
    private String _name;
    private String _time;
    private String _andress;
    //private String _description;

    public Activity(String name, String time, String address){
        _name = name;
        _time = time;
        _andress = address;
       // _description = description;
    }

    public String GetName(){
        return _name;
    }

    public String GetTime(){
        return _time;
    }

    public String GetAddress(){
        return _andress;
    }

   // public String GetDescription() {
  //      return _description;
  //  }
}
