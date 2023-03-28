package com.sai.buddyup;
//User Model for pushing to database.
public class User {
    public String name,email,fav,uid;

    public User(){

    }

    public User(String name, String email, String fav, String uid){
        this.name = name;
        this.email = email;
        this.fav = fav;
        this.uid = uid;
    }
}
