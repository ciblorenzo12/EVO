package com.evopackage.evo;

public class Messenges {

private String sender;
    private String date;
    private String messenger;


    private String uid;

    public Messenges(){};

    public Messenges(String sender, String date, String messenger,String uid) {
        this.sender = sender;
        this.date = date;
        this.messenger = messenger;
        this.uid = uid;
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
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

}