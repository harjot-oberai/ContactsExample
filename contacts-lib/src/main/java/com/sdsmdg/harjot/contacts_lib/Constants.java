package com.sdsmdg.harjot.contacts_lib;


import android.net.Uri;
import android.provider.ContactsContract;

public class Constants {
    static final Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
    static final String _ID = ContactsContract.Contacts._ID;
    static final String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;

    static final String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
    static final Uri PHONE_CONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
    static final String PHONE_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
    static final String PHONE_NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;

    public static final int READ_CONTACTS_PERMISSION_REQUEST_CODE = 90;

}
