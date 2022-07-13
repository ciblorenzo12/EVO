package com.evopackage.evo;

public class User_information {

    public String _displayname, _firstname, _lastname, _dob, _email, _password, _phone;

    public User_information(String _Displayname, String _Firstname, String _Lastname, String _Dob, String _Email, String _Password, String _Phone) {

        _displayname = _Displayname;
        _firstname = _Firstname;
        _lastname = _Lastname;
        _dob = _Dob;
        _email = _Email;
        _password = _Password;
        _phone = _Phone;


    }

    public String GetFirstName(){
        return _firstname;
    }
    public String GetLastName(){
        return _lastname;
    }
    public String GetFullName(){
        return _firstname + " " + _lastname;
    }
    public String GetDOB(){
        return _dob;
    }
    public String GetEmail(){
        return _email;
    }
    public String GetPhone(){
        return _phone;
    }
}
