package com.evopackage.evo;

public class   User_information {

    public String _firstname, _lastname, _dob, _email, _password, _phone,_event,_uri, _display;

    public User_information(String _Firstname, String _Lastname, String _Dob, String _Email, String _Password, String _Phone,String Event,String Uri, String _Display) {

        _display = _Display;
        _firstname = _Firstname;
        _lastname = _Lastname;
        _dob = _Dob;
        _email = _Email;
        _password = _Password;
        _phone = _Phone;
       _event = Event;
       _uri=Uri;
    }


}
