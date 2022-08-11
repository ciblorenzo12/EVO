package com.evopackage.evo;

public class   User_information {

    public String _firstname, _lastname, _dob, _email, _password, _phone,_event,_uri;

    public String get_firstname() {
        return _firstname;
    }

    public void set_firstname(String _firstname) {
        this._firstname = _firstname;
    }

    public String get_lastname() {
        return _lastname;
    }

    public void set_lastname(String _lastname) {
        this._lastname = _lastname;
    }

    public String get_dob() {
        return _dob;
    }

    public void set_dob(String _dob) {
        this._dob = _dob;
    }

    public String get_email() {
        return _email;
    }

    public void set_email(String _email) {
        this._email = _email;
    }

    public String get_password() {
        return _password;
    }

    public void set_password(String _password) {
        this._password = _password;
    }

    public String get_phone() {
        return _phone;
    }

    public void set_phone(String _phone) {
        this._phone = _phone;
    }

    public String get_event() {
        return _event;
    }

    public void set_event(String _event) {
        this._event = _event;
    }

    public String get_uri() {
        return _uri;
    }

    public void set_uri(String _uri) {
        this._uri = _uri;
    }

    public User_information(String _Firstname, String _Lastname, String _Dob, String _Email, String _Password, String _Phone, String Event, String Uri) {

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
