package com.sdsmdg.harjot.contacts_lib.models;


import java.util.ArrayList;

public class PhoneContact {

    private String name;
    private ArrayList<String> phoneNumbers;

    public PhoneContact() {
    }

    public PhoneContact(String name, ArrayList<String> phoneNumber) {
        this.name = name;
        this.phoneNumbers = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getPhoneNumber() {
        return phoneNumbers;
    }

    public void setPhoneNumber(ArrayList<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
}
