package com.sdsmdg.harjot.contacts_lib;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;

import com.sdsmdg.harjot.contacts_lib.interfaces.PhoneContactsFetchListener;
import com.sdsmdg.harjot.contacts_lib.models.PhoneContact;

import java.util.ArrayList;

import static com.sdsmdg.harjot.contacts_lib.Constants.CONTENT_URI;
import static com.sdsmdg.harjot.contacts_lib.Constants.DISPLAY_NAME;
import static com.sdsmdg.harjot.contacts_lib.Constants.HAS_PHONE_NUMBER;
import static com.sdsmdg.harjot.contacts_lib.Constants.PHONE_CONTACT_ID;
import static com.sdsmdg.harjot.contacts_lib.Constants.PHONE_CONTENT_URI;
import static com.sdsmdg.harjot.contacts_lib.Constants.PHONE_NUMBER;

class GetContactsRunnable implements Runnable {

    private Context context;
    private PhoneContactsFetchListener phoneContactsFetchListener;

    GetContactsRunnable(Context context, PhoneContactsFetchListener phoneContactsFetchListener) {
        this.context = context;
        this.phoneContactsFetchListener = phoneContactsFetchListener;
    }

    @Override
    public void run() {
        final ArrayList<PhoneContact> phoneContactList = new ArrayList<>();

        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(CONTENT_URI, null, null, null, null);

        if (phoneContactsFetchListener != null) {
            ((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    phoneContactsFetchListener.onFetchStart();
                }
            });
        }

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String contact_id = cursor.getString(cursor.getColumnIndex(Constants._ID));
                String name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {
                    PhoneContact phoneContact = new PhoneContact();

                    phoneContact.setName(name);

                    Cursor phoneCursor = contentResolver.query(PHONE_CONTENT_URI, null, PHONE_CONTACT_ID + " = ?", new String[]{contact_id}, null);
                    ArrayList<String> phoneNumbers = new ArrayList<>();
                    while (phoneCursor.moveToNext()) {
                        String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(PHONE_NUMBER));
                        phoneNumbers.add(phoneNumber);
                    }
                    phoneContact.setPhoneNumber(phoneNumbers);
                    phoneContactList.add(phoneContact);

                    phoneCursor.close();
                }
            }
            cursor.close();
        }

        if (phoneContactsFetchListener != null) {
            ((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    phoneContactsFetchListener.onFetchComplete(phoneContactList);
                }
            });
        }
    }
}
