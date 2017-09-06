package com.sdsmdg.harjot.contacts_lib.interfaces;

import com.sdsmdg.harjot.contacts_lib.models.PhoneContact;

import java.util.ArrayList;

public interface PhoneContactsFetchListener {
    void onFetchStart();

    void onFetchComplete(ArrayList<PhoneContact> phoneContactList);
}
