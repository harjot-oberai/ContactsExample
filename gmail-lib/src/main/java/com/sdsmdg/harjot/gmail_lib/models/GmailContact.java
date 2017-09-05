package com.sdsmdg.harjot.gmail_lib.models;


public class GmailContact {

    private String name;
    private String email;

    public GmailContact(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
