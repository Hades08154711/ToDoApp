package com.example.lisa.myapplication;



public class User {


    private String name, username, password;


    public User(String name, String username, String password){
        this.name = name;

        this.username = username;
        this.password = password;
    }
     public User(String username, String password){
         this.username = username;
         this.password = password;

         this.name = "";
     }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


}