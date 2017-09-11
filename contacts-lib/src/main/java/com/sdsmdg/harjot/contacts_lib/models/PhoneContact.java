package com.sdsmdg.harjot.contacts_lib.models;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class PhoneContact implements Parcelable {

    private String name;
    private ArrayList<String> phoneNumbers;

    public PhoneContact() {
    }

    public PhoneContact(String name, ArrayList<String> phoneNumber) {
        this.name = name;
        this.phoneNumbers = phoneNumber;
    }

    private PhoneContact(Parcel in) {
        name = in.readString();
        phoneNumbers = in.createStringArrayList();
    }

    public static final Creator<PhoneContact> CREATOR = new Creator<PhoneContact>() {
        @Override
        public PhoneContact createFromParcel(Parcel in) {
            return new PhoneContact(in);
        }

        @Override
        public PhoneContact[] newArray(int size) {
            return new PhoneContact[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeStringList(phoneNumbers);
    }
}
