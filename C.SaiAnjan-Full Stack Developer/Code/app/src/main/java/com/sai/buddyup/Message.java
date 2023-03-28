package com.sai.buddyup;
//Message Model for pushing to database.
public class Message {
    public String msg, suid,ruid;

    public Message() {
    }

    public Message(String msg, String suid, String ruid) {
        this.msg = msg;
        this.suid = suid;
        this.ruid = ruid;
    }
}
