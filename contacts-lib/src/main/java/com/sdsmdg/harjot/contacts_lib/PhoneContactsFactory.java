package com.sdsmdg.harjot.contacts_lib;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.sdsmdg.harjot.contacts_lib.interfaces.PhoneContactsFetchListener;
import com.sdsmdg.harjot.contacts_lib.models.PhoneContact;

import java.util.ArrayList;

public class PhoneContactsFactory implements PhoneContactsFetchListener {

    private Context context;
    private PhoneContactsFetchListener phoneContactsFetchListener;

    private boolean isFetched = false;
    private ArrayList<PhoneContact> fetchedPhoneContacts;
    private ArrayList<PhoneContact> selectedPhoneContacts;

    public PhoneContactsFactory(Context context) {
        this.context = context;
        fetchedPhoneContacts = new ArrayList<>();
        selectedPhoneContacts = new ArrayList<>();
    }

    public void requestPermissionAndFetch() {
        if (isFetched && phoneContactsFetchListener != null) {
            phoneContactsFetchListener.onFetchComplete(fetchedPhoneContacts);
            return;
        }
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    Constants.READ_CONTACTS_PERMISSION_REQUEST_CODE);
        } else {
            getContacts();
        }
    }

    public void handleRequestPermissions(@NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getContacts();
        } else {
            Toast.makeText(context, "Permission denied to read contacts", Toast.LENGTH_SHORT).show();
        }
    }

    private void getContacts() {
        GetContactsRunnable getContactsRunnable = new GetContactsRunnable(context, this);
        Thread thread = new Thread(getContactsRunnable);
        thread.start();
    }

    public boolean isFetched() {
        return isFetched;
    }

    public ArrayList<PhoneContact> getFetchedPhoneContacts() {
        return fetchedPhoneContacts;
    }

    public ArrayList<PhoneContact> getSelectedPhoneContacts() {
        return selectedPhoneContacts;
    }

    public void addListener(PhoneContactsFetchListener phoneContactsFetchListener) {
        this.phoneContactsFetchListener = phoneContactsFetchListener;
    }

    public PhoneContactsFetchListener getPhoneContactsFetchListener() {
        return phoneContactsFetchListener;
    }

    @Override
    public void onFetchStart() {
        if (phoneContactsFetchListener != null)
            phoneContactsFetchListener.onFetchStart();
    }

    @Override
    public void onFetchComplete(ArrayList<PhoneContact> phoneContactList) {
        isFetched = true;
        fetchedPhoneContacts = phoneContactList;
        if (phoneContactsFetchListener != null)
            phoneContactsFetchListener.onFetchComplete(phoneContactList);
    }
}
