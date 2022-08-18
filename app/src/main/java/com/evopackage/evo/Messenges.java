package com.evopackage.evo;

public class Messenges {

private String sender,date,messenger;
    public Messenges(){};

    public Messenges(String sender, String date, String messenger) {
        this.sender = sender;
        this.date = date;
        this.messenger = messenger;
    }




    public  String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessenger() {
        return messenger;
    }

    public void setMessenger(String messenger) {
        this.messenger = messenger;
    }
}