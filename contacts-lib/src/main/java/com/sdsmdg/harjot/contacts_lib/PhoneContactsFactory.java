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

public class PhoneContactsFactory {

    private Context context;
    private PhoneContactsFetchListener phoneContactsFetchListener;

    public PhoneContactsFactory(Context context) {
        this.context = context;
    }

    public void requestPermissionAndFetch() {
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
        GetContactsRunnable getContactsRunnable = new GetContactsRunnable(context, phoneContactsFetchListener);
        Thread thread = new Thread(getContactsRunnable);
        thread.start();
    }

    public void addListener(PhoneContactsFetchListener phoneContactsFetchListener) {
        this.phoneContactsFetchListener = phoneContactsFetchListener;
    }

}
